package blockChainTransactionService.service.ethereum;

import blockChainTransactionService.external.response.ethereum.EthBlockResponse;
import blockChainTransactionService.external.response.ethereum.EthTransactionResponse;

public interface IGroveEthereumAPIFetcherService {
    EthBlockResponse getBlockByNumber(String blockNumber, boolean isDetailed);
    EthTransactionResponse getTransactionByHash(String transactionHash);
}
