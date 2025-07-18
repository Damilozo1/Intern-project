package com.example.learning_project.service;

import com.example.learning_project.model.Account;
import com.example.learning_project.model.User;
import com.example.learning_project.repository.AccountRepository;
import com.example.learning_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    // create account for a user
    public Account createAccount (Long userId, Account account) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        account.setUser(user);
        if (account.getBalance() == null) {
            account.setBalance (BigDecimal.ZERO);
        }
        return accountRepository.save(account);
    }

    //List accounts for a User
    public List<Account> getAccountsForUser (Long userId) {
        return accountRepository.findByUserId (userId);
        }

    // Delete Account only if balance is zero
    public void deleteAccount (Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Account cannot be deleted unless balance is zero.");
        }
        accountRepository.delete(account);
    }
}
