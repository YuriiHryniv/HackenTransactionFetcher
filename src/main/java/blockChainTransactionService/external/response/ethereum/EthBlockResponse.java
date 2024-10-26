package blockChainTransactionService.external.response.ethereum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EthBlockResponse {
    private String jsonrpc;
    private int id;
    private EthBlockDetailsResponse result;
}
