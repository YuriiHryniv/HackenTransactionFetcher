package blockChainTransactionService.dao.ethereum;

import blockChainTransactionService.model.ethereum.Transaction;
import org.apache.commons.collections4.set.TransformedNavigableSet;

import java.util.List;

public interface ITransactionDAO {
    void save(Transaction transaction);
    Transaction findById(Long id);
    List<Transaction> findByBlockId(Long blockId);
    List<Transaction> findAll();
    void delete(Transaction transaction);
    List<Transaction> searchTransactions(String fromAddress, String toAddress, String blockHash, String hash, String fullSearch);
}
