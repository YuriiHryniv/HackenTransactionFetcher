package blockChainTransactionService.service.ethereum.impl;

import blockChainTransactionService.dao.ethereum.IBlockDAO;
import blockChainTransactionService.dao.ethereum.IWithdrawalDAO;
import blockChainTransactionService.external.response.ethereum.EthBlockResponse;
import blockChainTransactionService.external.response.ethereum.EthWithdrawalResponse;
import blockChainTransactionService.mapper.ethereum.WithdrawalMapper;
import blockChainTransactionService.model.ethereum.Block;
import blockChainTransactionService.model.ethereum.Withdrawal;
import blockChainTransactionService.service.ethereum.IEthereumWithdrawalFetcherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EthereumWithdrawalFetcherService implements IEthereumWithdrawalFetcherService {

    private final WithdrawalMapper withdrawalMapper;
    private final IWithdrawalDAO withdrawalDAO;
    private final IBlockDAO blockDAO;

    public EthereumWithdrawalFetcherService(WithdrawalMapper withdrawalMapper, IWithdrawalDAO IWithdrawalDAO, IBlockDAO blockDAO) {
        this.withdrawalMapper = withdrawalMapper;
        this.withdrawalDAO = IWithdrawalDAO;
        this.blockDAO = blockDAO;
    }

    @Transactional
    public void saveWithdrawals(List<EthWithdrawalResponse> withdrawals, EthBlockResponse block) {
        Optional<Block> blockByHash = blockDAO.findBlockByHash(block.getResult().getHash());
        blockByHash.ifPresent(eteBlock -> {
            List<Withdrawal> withdrawalEntities = withdrawals.stream()
                    .map(withdrawalMapper::mapToEntity)
                    .collect(Collectors.toList());
            withdrawalEntities.forEach(withdrawal -> withdrawal.setBlock(eteBlock));
            withdrawalDAO.saveAll(withdrawalEntities);
        });
    }
}
