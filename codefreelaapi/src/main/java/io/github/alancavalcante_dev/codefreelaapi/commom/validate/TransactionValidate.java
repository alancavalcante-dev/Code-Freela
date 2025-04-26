package io.github.alancavalcante_dev.codefreelaapi.commom.validate;

import java.math.BigDecimal;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.TransactionalTypes;
import io.github.alancavalcante_dev.codefreelaapi.domain.transaction.Transactions;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidate {
    public void validate(Transactions transaction) {
        if (transaction.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (transaction.getProfilePayer() != null &&
                transaction.getProfilePayer().getBalance().compareTo(transaction.getValue()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        if (transaction.getTransactionalTypes() == TransactionalTypes.REFUND &&
                transaction.getProfileReceived() == null) {
            throw new IllegalArgumentException("Refund must have a destination account");
        }
    }
}


