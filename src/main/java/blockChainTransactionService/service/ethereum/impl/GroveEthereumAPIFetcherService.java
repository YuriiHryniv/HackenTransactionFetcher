package blockChainTransactionService.service.ethereum.impl;

import blockChainTransactionService.constants.ethereum.EthJsonRpcMethod;
import blockChainTransactionService.external.request.ethereum.EthRpcRequest;
import blockChainTransactionService.external.GroveAPI;
import blockChainTransactionService.external.response.ethereum.EthBlockResponse;
import blockChainTransactionService.external.response.ethereum.EthTransactionResponse;
import blockChainTransactionService.service.ethereum.IGroveEthereumAPIFetcherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GroveEthereumAPIFetcherService implements IGroveEthereumAPIFetcherService {

    private final ObjectMapper objectMapper;
    private final GroveAPI groveApi;

    @Value("${grove.secret.key}")
    private String authorizationToken;

    @Autowired
    public GroveEthereumAPIFetcherService(ObjectMapper objectMapper, GroveAPI groveApi) {
        this.objectMapper = objectMapper;
        this.groveApi = groveApi;
    }

    @Override
    public EthBlockResponse getBlockByNumber(String blockNumber, boolean isDetailed) {
        EthRpcRequest request = new EthRpcRequest(
                EthJsonRpcMethod.ETH_GET_BLOCK_BY_NUMBER.getMethodName(),
                new Object[] {blockNumber, isDetailed},
                1,
                "2.0"
        );
        String response = groveApi.fetchBlockByNumber(authorizationToken, request);
        try {
            return objectMapper.readValue(response, EthBlockResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch block by number: " + blockNumber, e);
        }
    }

    @Override
    public EthTransactionResponse getTransactionByHash(String transactionHash) {
        EthRpcRequest request = new EthRpcRequest(
                EthJsonRpcMethod.ETH_GET_TRANSACTION_BY_HASH.getMethodName(),
                new Object[] {transactionHash},
                1,
                "2.0"
        );
        String response = groveApi.fetchTransactionDetails(authorizationToken, request);
        try {
            return objectMapper.readValue(response, EthTransactionResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch transaction by hash: " + transactionHash, e);
        }
    }
}
