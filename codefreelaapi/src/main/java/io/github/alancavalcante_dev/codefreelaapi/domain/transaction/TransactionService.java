package io.github.alancavalcante_dev.codefreelaapi.domain.transaction;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.TransactionsRepository;
import io.github.alancavalcante_dev.codefreelaapi.commom.validate.TransactionValidate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final ProfileRepository profileRepository;
    private final TransactionsRepository transactionsRepository;
    private final AuthorizationTransaction authorizationTransaction;
    private final TransactionValidate validator;


    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        validator.validate(transaction);

        Profile payer = profileRepository.findById(transaction.getProfilePayer().getIdProfile())
                .orElseThrow(() -> new RuntimeException("Pagador não encontrado"));

        Profile payee = profileRepository.findById(transaction.getProfileReceived().getIdProfile())
                .orElseThrow(() -> new RuntimeException("Recebedor não encontrado"));

        if (payer.getBalance().compareTo(transaction.getValue()) < 0) {
            throw new RuntimeException("Valor insuficiente para a transação");
        }

        BigDecimal value = transaction.getValue();

        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));

        profileRepository.save(payer);
        profileRepository.save(payee);

        if (!authorizationTransaction.authorized()) {
            throw new RuntimeException("Sistema externo não autorizado.");
        }

        return transactionsRepository.save(transaction);

    }


}


