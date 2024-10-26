package blockChainTransactionService.external.response.ethereum;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EthBlockDetailsResponse {
    private String baseFeePerGas;
    private String blobGasUsed;
    private String difficulty;
    private String excessBlobGas;
    private String extraData;
    private String gasLimit;
    private String gasUsed;
    private String hash;
    private String logsBloom;
    private String miner;
    private String mixHash;
    private String nonce;
    private String number;
    private String parentBeaconBlockRoot;
    private String parentHash;
    private String receiptsRoot;
    private String sha3Uncles;
    private String size;
    private String stateRoot;
    private String timestamp;
    private String totalDifficulty;
    private List<String> transactions;
    private List<EthWithdrawalResponse> withdrawals;
}