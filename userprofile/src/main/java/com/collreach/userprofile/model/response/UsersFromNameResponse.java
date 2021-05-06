package com.collreach.userprofile.model.response;

import java.util.List;

public class UsersFromNameResponse {
    private List<UsersInfo> usersByName;

    public UsersFromNameResponse() {
    }

    public UsersFromNameResponse(List<UsersInfo> usersByName) {
        this.usersByName = usersByName;
    }

    public List<UsersInfo> getUsersByName() {
        return usersByName;
    }

    public void setUsersByName(List<UsersInfo> usersByName) {
        this.usersByName = usersByName;
    }
}
