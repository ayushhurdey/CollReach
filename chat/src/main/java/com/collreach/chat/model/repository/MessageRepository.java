package com.collreach.chat.model.repository;

import com.collreach.chat.model.bo.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String>{
}
