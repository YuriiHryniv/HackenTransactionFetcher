package blockChainTransactionService.mapper.ethereum;

import blockChainTransactionService.external.response.ethereum.EthBlockDetailsResponse;
import blockChainTransactionService.model.ethereum.Block;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlockMapper {
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "withdrawals", ignore = true)
    Block mapToEntity(EthBlockDetailsResponse response);
}
