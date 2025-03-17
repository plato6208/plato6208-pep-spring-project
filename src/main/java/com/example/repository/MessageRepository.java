package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.*;

import javax.transaction.Transactional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    List<Message> findAll();
    List<Message> findByPostedBy(Integer accountId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    int deleteMessageById(@Param("messageId") Integer messageId);
}
