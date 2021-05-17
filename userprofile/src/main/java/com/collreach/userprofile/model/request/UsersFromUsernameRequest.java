package com.collreach.userprofile.model.request;

import java.util.List;

public class UsersFromUsernameRequest {
    private List<String> usernames;

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
