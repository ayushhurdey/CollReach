package com.collreach.chat.model.repository;

import com.collreach.chat.model.bo.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {
    Optional<Room> findBySenderAndReceiver(String sender, String receiver);
}

