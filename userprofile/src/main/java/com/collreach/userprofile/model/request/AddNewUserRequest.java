package com.collreach.userprofile.model.request;

public class AddNewUserRequest {
    private String userName;
    private String name;

    public AddNewUserRequest() {
    }

    public AddNewUserRequest(String userName, String name) {
        this.userName = userName;
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
