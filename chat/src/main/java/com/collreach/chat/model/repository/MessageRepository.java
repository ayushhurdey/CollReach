package com.collreach.chat.model.repository;

import com.collreach.chat.model.bo.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String>{
    List<Message> findAllBySenderAndReceiverOrReceiverAndSender(String sender, String receiver, String senderTwo, String receiverTwo, Pageable pageable);
    List<Message> findAllBySenderAndReceiverOrReceiverAndSender(String sender, String receiver, String senderTwo, String receiverTwo);

}
