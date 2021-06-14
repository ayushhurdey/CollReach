package com.collreach.chat.model.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {
    @Id
    private String userId;
    private String username;
    private String name;

    public User() {
    }

    public User(String userId, String username, String name) {
        this.userId = userId;
        this.username = username;
        this.name = name;
    }

    @Override
    public String toString(){
        return "[userId: " + userId + ", username: " + username + ", name: " + name;
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
}
