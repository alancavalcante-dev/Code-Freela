package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.transaction.Transactions;
import io.github.alancavalcante_dev.codefreelaapi.domain.transaction.TransactionsService;
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

    private final TransactionsService transactionService;

    @PostMapping
    @Operation(summary = "Cadastra uma transação")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Transactions> create(@RequestBody Transactions transaction) {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }
}
