package blockChainTransactionService.external.response.ethereum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EthWithdrawalResponse {
    private String index;
    private String validatorIndex;
    private String address;
    private String amount;
}
