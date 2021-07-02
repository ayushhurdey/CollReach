package com.collreach.userprofile.model.response;

public class UsersInfo {
    private String name;
    private String profileAccessKey;
    private String username;

    public UsersInfo() {
    }

    public UsersInfo(String name, String profileAccessKey, String username) {
        this.name = name;
        this.profileAccessKey = profileAccessKey;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileAccessKey() {
        return profileAccessKey;
    }

    public void setProfileAccessKey(String profileAccessKey) {
        this.profileAccessKey = profileAccessKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
