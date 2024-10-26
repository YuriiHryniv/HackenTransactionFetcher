package blockChainTransactionService.service.ethereum;

import blockChainTransactionService.dto.TransactionDTO;

import java.util.List;

public interface IEthereumTransactionSearchService {
    List<TransactionDTO> searchTransactions(String type, String value, String blockHash, String hash, String fullSearch);
}
