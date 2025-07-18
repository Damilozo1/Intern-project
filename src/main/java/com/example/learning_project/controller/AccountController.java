package com.example.learning_project.controller;

import com.example.learning_project.model.Account;
import com.example.learning_project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create account for user
    @PostMapping
    public Account createAccount(@PathVariable Long userId, @RequestBody Account account) {
        return accountService.createAccount(userId, account);
    }

    // Get accounts for user
    @GetMapping
    public List<Account> getAccounts(@PathVariable Long userId) {
        return accountService.getAccountsForUser(userId);
    }

    // Delete account (if balance is zero)
    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
    }
}
