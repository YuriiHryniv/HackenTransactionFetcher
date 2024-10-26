package blockChainTransactionService.mapper.ethereum;

import blockChainTransactionService.dto.TransactionDTO;
import blockChainTransactionService.external.response.ethereum.EthTransactionDetailsResponse;
import blockChainTransactionService.model.ethereum.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction mapToEntity(EthTransactionDetailsResponse response);
    TransactionDTO mapToDTO(Transaction entity);
}
