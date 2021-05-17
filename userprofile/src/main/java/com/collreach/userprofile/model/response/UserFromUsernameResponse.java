package com.collreach.userprofile.model.response;

import java.util.Map;

public class UserFromUsernameResponse {
    private Map<String, String> users;

    public UserFromUsernameResponse() {
    }

    public UserFromUsernameResponse(Map<String, String> users) {
        this.users = users;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }
}
