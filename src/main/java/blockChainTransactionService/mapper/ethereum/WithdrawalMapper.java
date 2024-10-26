package blockChainTransactionService.mapper.ethereum;

import blockChainTransactionService.external.response.ethereum.EthWithdrawalResponse;
import blockChainTransactionService.model.ethereum.Withdrawal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WithdrawalMapper {
    Withdrawal mapToEntity(EthWithdrawalResponse response);
}
