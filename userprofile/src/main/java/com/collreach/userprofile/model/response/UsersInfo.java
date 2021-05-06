package com.collreach.userprofile.model.response;

public class UsersInfo {
    private String name;
    private String profileAccessKey;

    public UsersInfo() {
    }

    public UsersInfo(String name, String profileAccessKey) {
        this.name = name;
        this.profileAccessKey = profileAccessKey;
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
}
