package blockChainTransactionService.dao.ethereum;

import blockChainTransactionService.model.ethereum.Withdrawal;

import java.util.List;

public interface IWithdrawalDAO {
    void saveAll(List<Withdrawal> withdrawals);
    void save(Withdrawal withdrawal);
    Withdrawal findById(Long id);
    List<Withdrawal> findByBlockId(Long blockId);
    List<Withdrawal> findAll();
    void delete(Withdrawal withdrawal);
}
