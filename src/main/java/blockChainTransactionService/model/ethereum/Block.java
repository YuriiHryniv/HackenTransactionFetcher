package blockChainTransactionService.model.ethereum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "block")
@Getter
@Setter
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "block_seq")
    @SequenceGenerator(name = "block_seq", sequenceName = "block_sequence", allocationSize = 1)
    private Long id;

    private String baseFeePerGas;
    private String blobGasUsed;
    private String difficulty;
    private String excessBlobGas;
    private String extraData;
    private String gasLimit;
    private String gasUsed;
    private String hash;
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

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Withdrawal> withdrawals;
}
