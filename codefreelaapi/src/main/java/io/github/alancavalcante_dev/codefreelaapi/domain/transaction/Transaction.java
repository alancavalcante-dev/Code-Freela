package io.github.alancavalcante_dev.codefreelaapi.domain.transaction;


import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.TransactionalTypes;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_transactions")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_transactions")
    private UUID idTransactions;

    @Column(name = "transactional_types", nullable = false)
    private TransactionalTypes transactionalTypes;

    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "profile_payer")
    private Profile profilePayer;

    @ManyToOne
    @JoinColumn(name = "profile_received")
    private Profile profileReceived;

    @CreatedDate
    @Column(name = "datetime_transactional", updatable = false)
    private LocalDateTime dateTimeTransaction;

}
