package blockChainTransactionService.service.ethereum.impl;

import blockChainTransactionService.constants.ethereum.BlockParameter;
import blockChainTransactionService.dao.ethereum.IBlockDAO;
import blockChainTransactionService.external.response.ethereum.EthBlockResponse;
import blockChainTransactionService.mapper.ethereum.BlockMapper;
import blockChainTransactionService.model.ethereum.Block;
import blockChainTransactionService.service.ethereum.IEthereumBlockFetcherService;
import blockChainTransactionService.service.ethereum.IEthereumTransactionFetcherService;
import blockChainTransactionService.service.ethereum.IEthereumWithdrawalFetcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class EthereumBlockFetcherService implements IEthereumBlockFetcherService {

    private final IBlockDAO IBlockDAO;
    private final GroveEthereumAPIFetcherService groveEthereumAPIFetcherService;
    private final BlockMapper blockMapper;
    private final IEthereumTransactionFetcherService ethereumTransactionService;
    private final IEthereumWithdrawalFetcherService ethereumWithdrawalService;

    @Autowired
    public EthereumBlockFetcherService(IBlockDAO IBlockDAO,
                                       blockChainTransactionService.service.ethereum.impl.GroveEthereumAPIFetcherService groveEthereumTransactionService,
                                       BlockMapper blockMapper,
                                       IEthereumTransactionFetcherService ethereumTransactionService,
                                       IEthereumWithdrawalFetcherService ethereumWithdrawalService) {
        this.IBlockDAO = IBlockDAO;
        this.groveEthereumAPIFetcherService = groveEthereumTransactionService;
        this.blockMapper = blockMapper;
        this.ethereumTransactionService = ethereumTransactionService;
        this.ethereumWithdrawalService = ethereumWithdrawalService;
    }

    @Override
    @Async("asyncExecutor")
    public void fetchAndSaveLatestBlock() {
        Optional<Block> latestBlock = IBlockDAO.findLatestBlock();
        if (latestBlock.isPresent()) {
            Block currentLatestBlock = latestBlock.get();
            Long nextBlockNumber = Long.parseLong(currentLatestBlock.getNumber().replace("0x", ""), 16) + 1;
            EthBlockResponse response = groveEthereumAPIFetcherService.getBlockByNumber(nextBlockNumber.toString(), false);
            if (response.getResult() != null) {
                saveBlockAndRelatedData(response);
            } else {
                log.info("Block {} is not ready or does not exist, skipping", nextBlockNumber);
            }
        } else {
            EthBlockResponse response = groveEthereumAPIFetcherService.getBlockByNumber(BlockParameter.LATEST.getValue(), false);
            saveBlockAndRelatedData(response);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBlockAndRelatedData(EthBlockResponse response) {
        Block block = blockMapper.mapToEntity(response.getResult());
        IBlockDAO.save(block);
        if (response.getResult() != null && response.getResult().getTransactions() != null) {
            ethereumTransactionService.fetchAndSaveTransactions(response.getResult());
        }
        if (response.getResult() != null && response.getResult().getWithdrawals() != null) {
            ethereumWithdrawalService.saveWithdrawals(response.getResult().getWithdrawals(), response);
        }
    }
}
