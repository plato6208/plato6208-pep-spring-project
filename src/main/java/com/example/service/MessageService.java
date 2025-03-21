package com.example.service;

import com.example.entity.Message;

import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

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

    public ResponseEntity<?> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return ResponseEntity.status(200).body(messages);
    }

    public ResponseEntity<?> getMessageById(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        //check to see if message is present. if it is, then return message else just status 200
        if (message.isPresent()) {
            return ResponseEntity.status(200).body(message.get());
        } else {
            return ResponseEntity.status(200).build();
        }
    }

    public ResponseEntity<?> deleteMessageById(Integer messageId) {
        int rows = messageRepository.deleteMessageById(messageId);
        //check to see if message was deleted. if so rthen return row deleted else just status 200
        if(rows > 0) {
            return ResponseEntity.status(200).body(rows);
        } else {
            return ResponseEntity.status(200).build();
        }
    }

    public ResponseEntity<?> updateMessageById(Integer messageId, String messageText ) {
        Optional<Message> messOptional = messageRepository.findById(messageId);
        //validate whether message cvan be updated if not then status 400
        if(messOptional.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        if(messageText.isEmpty() || messageText.length() >255) {
            return ResponseEntity.status(400).build();
        }
        //updates message and returns status 200 along with 1 to signify one row has been updated
        Message message = messOptional.get();
        message.setMessageText(messageText);
        messageRepository.save(message);
        return ResponseEntity.status(200).body(1);
    }

    public ResponseEntity<?> getMessagesByAccountId(Integer accountId) {
        //returns a list of messages from a specified accountid 
        List<Message> messages = messageRepository.findByPostedBy(accountId);
        return ResponseEntity.status(200).body(messages);
    }

}
