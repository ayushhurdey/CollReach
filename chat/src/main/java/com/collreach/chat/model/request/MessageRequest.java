package com.collreach.chat.model.request;

import com.collreach.chat.model.bo.Room;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MessageRequest {
    private String message;
    private Date date;
    private Date time;
    private String sender;
    private String receiver;

    public MessageRequest() {
    }

    public MessageRequest(String message, Date date, Date time, String sender, String receiver) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
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
