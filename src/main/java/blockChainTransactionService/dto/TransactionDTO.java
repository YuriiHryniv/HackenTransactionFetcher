package blockChainTransactionService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
    private String blockHash;
    private String blockNumber;
    private String from;
    private String gas;
    private String gasPrice;
    private String maxPriorityFeePerGas;
    private String maxFeePerGas;
    private String hash;
    private String input;
    private String nonce;
    private String to;
    private String transactionIndex;
    private String value;
    private String type;
    private String chainId;
    private String v;
    private String yParity;
    private String r;
    private String s;
}
