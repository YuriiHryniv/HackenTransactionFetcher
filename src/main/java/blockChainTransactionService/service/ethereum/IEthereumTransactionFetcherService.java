package blockChainTransactionService.service.ethereum;

import blockChainTransactionService.external.response.ethereum.EthBlockDetailsResponse;

public interface IEthereumTransactionFetcherService {
    void fetchAndSaveTransactions(EthBlockDetailsResponse block);
}
