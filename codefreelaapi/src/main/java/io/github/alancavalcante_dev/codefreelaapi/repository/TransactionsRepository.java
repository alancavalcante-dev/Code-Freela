package io.github.alancavalcante_dev.codefreelaapi.repository;

import io.github.alancavalcante_dev.codefreelaapi.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
}
