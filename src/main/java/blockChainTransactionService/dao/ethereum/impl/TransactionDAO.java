package blockChainTransactionService.dao.ethereum.impl;

import blockChainTransactionService.dao.ethereum.ITransactionDAO;
import blockChainTransactionService.dto.TransactionDTO;
import blockChainTransactionService.model.ethereum.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Repository
public class TransactionDAO implements ITransactionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Transaction transaction) {
        entityManager.persist(transaction);
    }

    @Override
    public Transaction findById(Long id) {
        return entityManager.find(Transaction.class, id);
    }

    @Override
    public List<Transaction> findByBlockId(Long blockId) {
        return entityManager.createQuery("FROM Transaction WHERE block.id = :blockId", Transaction.class)
                .setParameter("blockId", blockId)
                .getResultList();
    }

    @Override
    public List<Transaction> findAll() {
        return entityManager.createQuery("FROM Transaction", Transaction.class).getResultList();
    }

    @Override
    public void delete(Transaction transaction) {
        entityManager.remove(transaction);
    }

    @Override
    public List<Transaction> searchTransactions(String value, String type, String blockHash, String hash, String fullSearch) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = cb.createQuery(Transaction.class);
        Root<Transaction> root = query.from(Transaction.class);

        List<Predicate> predicates = new ArrayList<>();

        if (value != null) {
            predicates.add(cb.equal(root.get("value"), value));
        }
        if (type != null) {
            predicates.add(cb.equal(root.get("type"), type));
        }
        if (blockHash != null) {
            predicates.add(cb.equal(root.get("block").get("hash"), blockHash));
        }
        if (hash != null) {
            predicates.add(cb.equal(root.get("hash"), hash));
        }

        query.select(root);
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        List<Transaction> filteredTransactions = entityManager.createQuery(query).getResultList();

        if (value == null && type == null && blockHash == null && hash == null) {
            String searchQuery = fullSearch != null ? fullSearch : ""; // Adjust logic to combine relevant fields

            String fullTextSearchQuery = "SELECT * FROM transaction WHERE search_vector @@ plainto_tsquery('english', :query)";
            List<Transaction> fullTextResults = entityManager.createNativeQuery(fullTextSearchQuery, Transaction.class)
                    .setParameter("query", searchQuery)
                    .getResultList();

            return fullTextResults;
        } else {
            return filteredTransactions;
        }
    }
}

