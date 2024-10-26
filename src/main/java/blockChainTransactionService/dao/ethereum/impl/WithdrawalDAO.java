package blockChainTransactionService.dao.ethereum.impl;

import blockChainTransactionService.model.ethereum.Withdrawal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Transactional
@Repository
public class WithdrawalDAO implements blockChainTransactionService.dao.ethereum.IWithdrawalDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveAll(List<Withdrawal> withdrawals) {
        int batchSize = 50;
        for (int i = 0; i < withdrawals.size(); i++) {
            entityManager.persist(withdrawals.get(i));
            if (i % batchSize == 0 && i > 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public void save(Withdrawal withdrawal) {
        entityManager.persist(withdrawal);
    }

    @Override
    public Withdrawal findById(Long id) {
        return entityManager.find(Withdrawal.class, id);
    }

    @Override
    public List<Withdrawal> findByBlockId(Long blockId) {
        return entityManager.createQuery("FROM Withdrawal WHERE block.id = :blockId", Withdrawal.class)
                .setParameter("blockId", blockId)
                .getResultList();
    }

    @Override
    public List<Withdrawal> findAll() {
        return entityManager.createQuery("FROM Withdrawal", Withdrawal.class).getResultList();
    }

    @Override
    public void delete(Withdrawal withdrawal) {
        entityManager.remove(withdrawal);
    }
}
