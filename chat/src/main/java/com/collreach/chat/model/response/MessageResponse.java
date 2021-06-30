package com.collreach.chat.model.response;

import com.collreach.chat.model.bo.Message;

import java.util.Date;

public class MessageResponse {

    private String message;
    private Date date;
    private String sender;
    private String receiver;

    public MessageResponse() {
    }

    public MessageResponse(Message message){
        this.message = message.getMessage();
        this.date = message.getDate();
        this.sender = message.getSender();
        this.receiver = message.getReceiver();
    }

    public MessageResponse(String message, Date date, String sender, String receiver) {
        this.message = message;
        this.date = date;
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
