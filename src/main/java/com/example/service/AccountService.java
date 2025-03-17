package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {
private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<?> registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            return ResponseEntity.status(400).build();
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }

        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount.isPresent()) {
            return ResponseEntity.status(409).build();
        }

        Account savedAccount = accountRepository.save(account);
        
        return ResponseEntity.status(200).body(savedAccount);
    }
}
