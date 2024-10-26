package blockChainTransactionService.model.ethereum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", nullable = false)
    private Block block;

    @Column(length = 512)
    private String blockHash;

    private String blockNumber;
    private String fromAddress;
    private String gas;
    private String gasPrice;
    private String maxPriorityFeePerGas;
    private String maxFeePerGas;

    @Column(length = 512)
    private String hash;

    @Column(columnDefinition = "text")
    private String input;

    private String nonce;
    private String toAddress;
    private String transactionIndex;
    private String value;
    private String type;
    private String chainId;
    private String v;
    private String yParity;

    @Column(columnDefinition = "text")
    private String r;

    @Column(columnDefinition = "text")
    private String s;

    @Transient
    private String searchVector;
}