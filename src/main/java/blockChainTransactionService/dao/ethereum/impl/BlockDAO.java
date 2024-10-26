package blockChainTransactionService.dao.ethereum.impl;

import blockChainTransactionService.dao.ethereum.IBlockDAO;
import blockChainTransactionService.model.ethereum.Block;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class BlockDAO implements IBlockDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Block block) {
        entityManager.persist(block);
    }

    @Override
    public Block findById(Long id) {
        return entityManager.find(Block.class, id);
    }

    @Override
    public List<Block> findAll() {
        return entityManager.createQuery("FROM Block", Block.class).getResultList();
    }

    @Override
    public void delete(Block block) {
        entityManager.remove(block);
    }

    @Override
    public Optional<Block> findLatestBlock() {
        Optional<Block> optional = Optional.empty();
        try {
            List<Block> blocks = entityManager.createQuery(
                            "SELECT e "
                                    + "FROM Block e "
                                    + "ORDER BY e.id DESC", Block.class)
                    .setMaxResults(1)
                    .getResultList();
            if (!blocks.isEmpty()) {
                optional = Optional.of(blocks.getFirst());
            }
        } catch (Exception e) {
            log.error("Error finding latest block", e);
        }
        return optional;
    }

    @Override
    public Optional<Block> findBlockByHash(String hash) {
        Optional<Block> optional = Optional.empty();
        try {
            Block block = entityManager.createQuery("select e"
                            + " from Block e"
                            + " where e.hash = :hash", Block.class)
                    .setParameter("hash", hash)
                    .getSingleResult();
            optional = Optional.of(block);
        } catch (NoResultException e) {
            log.error("Error finding block by hash", e);
        }
        return optional;
    }
}
