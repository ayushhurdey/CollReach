package com.collreach.chat.model.repository;

import com.collreach.chat.model.bo.Room;
import com.collreach.chat.model.bo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {
    Optional<Room> findByMemberOneAndMemberTwoOrMemberTwoAndMemberOne(User sender, User receiver, User senderTwo, User receiverTwo);
}

