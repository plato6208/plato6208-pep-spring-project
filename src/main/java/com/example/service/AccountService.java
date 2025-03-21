package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        //checks to see if user is a valid entry 
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }

        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        //checks to see if account exists
        if (existingAccount.isPresent()) {
            return ResponseEntity.status(409).build();
        }

        Account savedAccount = accountRepository.save(account);
        return ResponseEntity.status(200).body(savedAccount);
    }

    public ResponseEntity<?> loginAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        //chcecks to see if account is a valid entry 
        if (existingAccount.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        Account storedAccount = existingAccount.get();
        if (!storedAccount.getPassword().equals(account.getPassword())) {
            return ResponseEntity.status(401).build();
        }
  
        return ResponseEntity.status(200).body(storedAccount);
    }

    //helper fuction to see if user exist by looking for account id. Used in the message service class
    public boolean userExists(Integer accountId) {
        return accountRepository.existsById(accountId);
    }
}
