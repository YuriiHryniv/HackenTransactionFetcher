package blockChainTransactionService.dao.ethereum;

import blockChainTransactionService.model.ethereum.Block;

import java.util.List;
import java.util.Optional;

public interface IBlockDAO {
    void save(Block block);
    Block findById(Long id);
    List<Block> findAll();
    void delete(Block block);
    Optional<Block> findLatestBlock();
    Optional<Block> findBlockByHash(String hash);
}
