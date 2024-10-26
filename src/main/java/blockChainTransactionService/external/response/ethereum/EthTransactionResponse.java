package blockChainTransactionService.external.response.ethereum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EthTransactionResponse {
    private String jsonrpc;
    private int id;
    private EthTransactionDetailsResponse result;
}
