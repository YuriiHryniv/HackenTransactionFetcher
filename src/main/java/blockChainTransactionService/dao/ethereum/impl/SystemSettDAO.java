package blockChainTransactionService.dao.ethereum.impl;

import blockChainTransactionService.dao.ethereum.ISystemSettDAO;
import blockChainTransactionService.model.ethereum.SystemSett;
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
public class SystemSettDAO implements ISystemSettDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(SystemSett systemSett) {
        entityManager.persist(systemSett);
    }

    @Override
    public SystemSett findById(Long id) {
        return entityManager.find(SystemSett.class, id);
    }

    @Override
    public List<SystemSett> findAll() {
        return entityManager.createQuery("FROM SystemSett", SystemSett.class).getResultList();
    }

    @Override
    public Optional<SystemSett> findByName(String name) {
        try {
            SystemSett result = entityManager.createQuery("FROM SystemSett "
                            + "WHERE name = :name", SystemSett.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            log.warn("System setting with name '{}' not found.", name);
            return Optional.empty();
        }
    }

    @Override
    public void delete(SystemSett systemSett) {
        entityManager.remove(systemSett);
    }
}
