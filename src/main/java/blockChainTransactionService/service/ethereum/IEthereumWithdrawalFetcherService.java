package blockChainTransactionService.service.ethereum;

import blockChainTransactionService.external.response.ethereum.EthBlockResponse;
import blockChainTransactionService.external.response.ethereum.EthWithdrawalResponse;

import java.util.List;

public interface IEthereumWithdrawalFetcherService {
    void saveWithdrawals(List<EthWithdrawalResponse> withdrawals, EthBlockResponse blockResponse);
}
