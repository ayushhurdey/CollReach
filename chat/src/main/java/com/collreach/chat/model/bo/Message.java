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

    private Room roomId;

    public Message() {
    }

    public Message(String message, Date date, Date time, Room roomId) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.roomId = roomId;
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
}
