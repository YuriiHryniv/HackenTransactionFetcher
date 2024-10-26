package blockChainTransactionService.service.ethereum.impl;

import blockChainTransactionService.dao.ethereum.IBlockDAO;
import blockChainTransactionService.dao.ethereum.ITransactionDAO;
import blockChainTransactionService.external.response.ethereum.EthBlockDetailsResponse;
import blockChainTransactionService.external.response.ethereum.EthTransactionResponse;
import blockChainTransactionService.mapper.ethereum.TransactionMapper;
import blockChainTransactionService.model.ethereum.Block;
import blockChainTransactionService.model.ethereum.Transaction;
import blockChainTransactionService.service.ethereum.IEthereumTransactionFetcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EthereumTransactionFetcherService implements IEthereumTransactionFetcherService {

    private final GroveEthereumAPIFetcherService groveEthereumAPIFetcherService;
    private final ITransactionDAO ITransactionDAO;
    private final TransactionMapper transactionMapper;
    private final IBlockDAO IBlockDAO;

    @Value("${ethereum.transactions.max-retries}")
    private int maxRetries;

    @Value("${ethereum.transactions.retry-delay}")
    private long retryDelayMillis;

    @Autowired
    public EthereumTransactionFetcherService(blockChainTransactionService.service.ethereum.impl.GroveEthereumAPIFetcherService groveEthereumAPIFetcherService,
                                             ITransactionDAO ITransactionDAO,
                                             TransactionMapper transactionMapper,
                                             IBlockDAO IBlockDAO) {
        this.groveEthereumAPIFetcherService = groveEthereumAPIFetcherService;
        this.ITransactionDAO = ITransactionDAO;
        this.transactionMapper = transactionMapper;
        this.IBlockDAO = IBlockDAO;
    }

    @Async("transactionAsyncExecutor")
    @Override
    public void fetchAndSaveTransactions(EthBlockDetailsResponse block) {
        List<String> transactionHashes = block.getTransactions();

        transactionHashes.parallelStream().forEach(hash -> {
            try {
                EthTransactionResponse transactionDetails = fetchTransactionWithRetry(hash, maxRetries, retryDelayMillis);
                if (transactionDetails.getResult() != null) {
                    Transaction transaction = transactionMapper.mapToEntity(transactionDetails.getResult());

                    Optional<Block> blockByHash = IBlockDAO.findBlockByHash(block.getHash());
                    blockByHash.ifPresent(currentBlock -> {
                        transaction.setBlock(currentBlock);
                        ITransactionDAO.save(transaction);
                    });
                }
            } catch (Exception e) {
                log.error("Error fetching and saving transaction with hash {}", hash, e);
            }
        });
    }

    private EthTransactionResponse fetchTransactionWithRetry(String hash, int maxRetries, long delayMillis) throws InterruptedException {
        EthTransactionResponse transactionDetails = null;
        int attempt = 0;

        while (attempt < maxRetries && (transactionDetails == null || transactionDetails.getResult() == null)) {
            transactionDetails = groveEthereumAPIFetcherService.getTransactionByHash(hash);
            if (transactionDetails.getResult() == null) {
                log.info("Transaction with hash {} not found, retrying... (attempt {})", hash, attempt + 1);
                Thread.sleep(delayMillis);  // Wait before the next attempt
                attempt++;
            }
        }

        if (transactionDetails == null || transactionDetails.getResult() == null) {
            log.warn("Transaction with hash {} could not be retrieved after {} attempts", hash, maxRetries);
        }

        return transactionDetails;
    }
}
