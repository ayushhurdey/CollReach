package com.collreach.chat.model.response;

import com.collreach.chat.model.bo.User;

import java.util.Date;

public class ContactsResponse {
    private String userId;
    private String username;
    private String name;
    private Date lastContact;
    private String lastMessage;


    public ContactsResponse() {
    }

    public ContactsResponse(String userId, String username, String name, String lastMessage, Date lastContact) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.lastMessage = lastMessage;
        this.lastContact = lastContact;
    }

    public ContactsResponse(User user, String lastMessage, Date lastContact) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastMessage = lastMessage;
        this.lastContact = lastContact;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastContact() {
        return lastContact;
    }

    public void setLastContact(Date lastContact) {
        this.lastContact = lastContact;
    }
}
