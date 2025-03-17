package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.entity.Message;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/")
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Account account) {
        return accountService.loginAccount(account);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> CreateNewMessage(@RequestBody Message message) {
        return messageService.CreateMessage(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<?> RetrieveAllMessage() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("messages/{messageId}") 
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId) {
        return messageService.deleteMessageById(messageId);
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> updateMessageText(@PathVariable Integer messageId, @RequestBody Map<String, String> updatedFields) {
        String messageText = updatedFields.get("messageText");

        return messageService.updateMessageById(messageId, messageText);
    }
    
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesByAccountId(@PathVariable Integer accountId) {
        return messageService.getMessagesByAccountId(accountId);
    }
}
