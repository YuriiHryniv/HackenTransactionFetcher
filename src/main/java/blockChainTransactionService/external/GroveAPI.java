package blockChainTransactionService.external;

import blockChainTransactionService.external.request.ethereum.EthRpcRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "grove-api", url = "${grove-api.url}")
public interface GroveAPI {
    @PostMapping
    String fetchBlockByNumber(@RequestHeader("Authorization") String authToken, @RequestBody EthRpcRequest requestBody);

    @PostMapping
    String fetchTransactionDetails(@RequestHeader("Authorization") String authToken, @RequestBody EthRpcRequest requestBody);
}
