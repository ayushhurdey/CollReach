package com.collreach.chat.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import java.util.Date;

@Document(collection="message")
public class Message {
    @Id
    private String messageId;

    private String message;

    //@JsonFormat(pattern="dd-MM-yyyy")
    private Date date;

    //@JsonFormat(pattern = "HH:mm:ss")
    private Date time;
    private Room roomId;      // TODO: convert to to normal String roomId.
    private String sender;
    private String receiver;

    public Message() {
    }

    public Message(String message, Date date, Date time, Room roomId, String sender, String receiver) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
