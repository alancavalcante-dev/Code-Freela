package io.github.alancavalcante_dev.codefreelaapi.service;

import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
import io.github.alancavalcante_dev.codefreelaapi.model.Transactions;
import io.github.alancavalcante_dev.codefreelaapi.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.repository.TransactionsRepository;
import io.github.alancavalcante_dev.codefreelaapi.validate.TransactionValidate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;



@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final ProfileRepository profileRepository;
    private final TransactionsRepository transactionsRepository;
    private final TransactionValidate validator;

    @Transactional
    public Transactions createTransaction(Transactions transaction) {
        validator.validate(transaction);

        if (transaction.getProfilePayer() != null) {
            Profile origin = profileRepository.findById(transaction.getProfilePayer().getIdProfile())
                    .orElseThrow();

            BigDecimal newBalance = origin.getBalance().subtract(transaction.getValue());

            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Valor insuficiente para a transação");
            }

            origin.setBalance(newBalance);
            profileRepository.save(origin);
        }

        if (transaction.getProfileReceived() != null) {
            Profile destination = profileRepository.findById(transaction.getProfileReceived().getIdProfile())
                    .orElseThrow();

            destination.setBalance(destination.getBalance().add(transaction.getValue()));
            profileRepository.save(destination);
        }

        return transactionsRepository.save(transaction);
    }

}


