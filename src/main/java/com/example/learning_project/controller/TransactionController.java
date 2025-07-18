package com.example.learning_project.controller;

import com.example.learning_project.model.Transaction;
import com.example.learning_project.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping ("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // create a transaction (credit or debit)
    @PostMapping
    public Transaction createTransaction(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestBody Transaction transaction
    ) {
        return transactionService.createTransaction(fromAccountId, toAccountId, transaction);
    }

    // Get all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    //Searching transactions by account, date, amount
    @GetMapping("/search")
    public List<Transaction> searchTransactions(
            @RequestParam Long accountId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max
    ) {
        LocalDateTime fromDate = (from != null) ? LocalDateTime.parse(from) : null;
        LocalDateTime toDate = (to != null) ? LocalDateTime.parse(to) : null;

        return transactionService.searchTransactions(accountId, fromDate, toDate, min, max);
    }

}
