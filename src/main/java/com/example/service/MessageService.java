package com.example.service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountService accountService;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public ResponseEntity<?> CreateMessage(Message message){
        //check if message is empty
        if (message.getMessageText().isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        //check length of message
        if (message.getMessageText().length() > 255) {
            return ResponseEntity.status(400).build();
        }

        //check to see if user is real 
        Integer postedBy = message.getPostedBy();
        if(!accountService.userExists(postedBy)){
            return ResponseEntity.status(400).build();
        }
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.status(200).body(savedMessage);
    }
}
