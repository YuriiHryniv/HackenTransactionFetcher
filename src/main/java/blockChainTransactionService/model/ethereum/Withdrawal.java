package blockChainTransactionService.model.ethereum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "withdrawal")
@Getter
@Setter
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "withdrawal_seq")
    @SequenceGenerator(name = "withdrawal_seq", sequenceName = "withdrawal_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", nullable = false)
    private Block block;

    private String index;
    private String validatorIndex;
    private String address;
    private String amount;
}
