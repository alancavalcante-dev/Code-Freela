package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.transaction.Transaction;
import io.github.alancavalcante_dev.codefreelaapi.domain.transaction.TransactionService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transações")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserLogged logged;

    @PostMapping
    @Operation(summary = "Cadastra uma transação")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }
}
