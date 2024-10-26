package blockChainTransactionService.external.request.ethereum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EthRpcRequest {
    private String method;
    private Object[] params;
    private int id;
    private String jsonrpc;

    public EthRpcRequest(String method, Object[] params, int id, String jsonrpc) {
        this.method = method;
        this.params = params;
        this.id = id;
        this.jsonrpc = jsonrpc;
    }
}