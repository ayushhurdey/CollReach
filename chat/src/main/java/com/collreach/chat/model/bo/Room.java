package com.collreach.chat.model.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="room")
public class Room {
    @Id
    private String roomId;
    private User memberOne;
    private User memberTwo;

    public Room() {
    }

    public Room(User memberOne, User memberTwo) {
        this.memberOne = memberOne;
        this.memberTwo = memberTwo;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public User getMemberOne() {
        return memberOne;
    }

    public void setMemberOne(User memberOne) {
        this.memberOne = memberOne;
    }

    public User getMemberTwo() {
        return memberTwo;
    }

    public void setMemberTwo(User memberTwo) {
        this.memberTwo = memberTwo;
    }
}
