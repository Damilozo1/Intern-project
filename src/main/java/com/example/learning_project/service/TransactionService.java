package com.example.learning_project.service;

import com.example.learning_project.model.Account;
import com.example.learning_project.model.Transaction;
import com.example.learning_project.repository.AccountRepository;
import com.example.learning_project.repository.TransactionRepository;
import com.example.learning_project.kafka.TransactionEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionEventProducer transactionEventProducer;


    public Transaction createTransaction(Long fromId, Long toId, Transaction transaction) {
        Account fromAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("From Account not found"));
        Account toAccount = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("To Account not found"));

        if ("DEBIT".equalsIgnoreCase(transaction.getType())) {
            if (fromAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
                throw new RuntimeException("Insufficient balance4 for debit transaction.");
            }
            fromAccount.setBalance(fromAccount.getBalance().subtract(transaction.getAmount()));
            toAccount.setBalance(toAccount.getBalance().add(transaction.getAmount()));
        } else if ("CREDIT".equalsIgnoreCase(transaction.getType())) {
            toAccount.setBalance(toAccount.getBalance().add(transaction.getAmount()));
        } else {
            throw new RuntimeException("Invalid transaction type");
        }

        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setTimestamp(LocalDateTime.now());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction savedTransaction = transactionRepository.save(transaction);
        transactionEventProducer.sendTransactionEvent(
                "Transaction ID " + savedTransaction.getId() + " - " + savedTransaction.getType() +
                        " of " + savedTransaction.getAmount() + " from account " + fromId + " to " + toId
        );

        return savedTransaction;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> searchTransactions(Long accountId, LocalDateTime from, LocalDateTime to, BigDecimal min, BigDecimal max) {
        List<Transaction> transactions = transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
        return transactions.stream()
                .filter(t -> {
                    if (t.getTimestamp() == null || t.getAmount() == null) return false;

                    boolean matchesDate =
                            (from == null || !t.getTimestamp().isBefore(from)) &&
                                    (to == null || !t.getTimestamp().isAfter(to));

                    boolean matchesAmount =
                            (min == null || t.getAmount().compareTo(min) >= 0) &&
                                    (max == null || t.getAmount().compareTo(max) <= 0);

                    return matchesDate && matchesAmount;
                })
                .toList();
    }
}
