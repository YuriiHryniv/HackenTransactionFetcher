package blockChainTransactionService.dao.ethereum;

import blockChainTransactionService.model.ethereum.SystemSett;

import java.util.List;
import java.util.Optional;

public interface ISystemSettDAO {
    void save(SystemSett systemSett);
    SystemSett findById(Long id);
    List<SystemSett> findAll();
    Optional<SystemSett> findByName(String name);
    void delete(SystemSett systemSett);
}
