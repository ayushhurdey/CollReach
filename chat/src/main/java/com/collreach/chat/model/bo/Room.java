package com.collreach.chat.model.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="room")
public class Room {
    @Id
    private String roomId;
    private User sender;
    private User receiver;

    public Room() {
    }

    public Room(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
