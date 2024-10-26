package blockChainTransactionService.service.ethereum.impl;

import blockChainTransactionService.dao.ethereum.ITransactionDAO;
import blockChainTransactionService.dao.ethereum.impl.TransactionDAO;
import blockChainTransactionService.dto.TransactionDTO;
import blockChainTransactionService.mapper.ethereum.TransactionMapper;
import blockChainTransactionService.service.ethereum.IEthereumTransactionSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EthereumTransactionSearchService implements IEthereumTransactionSearchService {

    private final ITransactionDAO transactionDAO;
    private final TransactionMapper transactionMapper;

    @Autowired
    public EthereumTransactionSearchService(TransactionDAO transactionDAO, TransactionMapper transactionMapper) {
        this.transactionDAO = transactionDAO;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<TransactionDTO> searchTransactions(String type, String value, String blockHash, String hash, String fullSearch) {
        return transactionDAO.searchTransactions(type, value, blockHash, hash, fullSearch)
                .stream()
                .map(transactionMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}