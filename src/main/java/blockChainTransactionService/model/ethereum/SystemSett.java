package blockChainTransactionService.model.ethereum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_sett")
@Setter
@Getter
public class SystemSett {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_sett_seq")
    @SequenceGenerator(name = "system_sett_seq", sequenceName = "system_sett_sequence", allocationSize = 1)
    private Long id;

    private String name;
    private String value;

    @Column(name = "last_edit", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastEdit;

    @Column(name = "last_user", nullable = false)
    private String lastUser;

    private String description;
}
