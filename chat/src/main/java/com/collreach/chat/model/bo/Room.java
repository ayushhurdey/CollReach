package com.collreach.chat.model.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection="room")
public class Room {
    @Id
    private String roomId;
    private User memberOne;
    private User memberTwo;
    private Date lastContact;

    public Room() {
    }

    public Room(User memberOne, User memberTwo, Date lastContact) {
        this.memberOne = memberOne;
        this.memberTwo = memberTwo;
        this.lastContact = lastContact;
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

    public Date getLastContact() {
        return lastContact;
    }

    public void setLastContact(Date lastContact) {
        this.lastContact = lastContact;
    }
}
