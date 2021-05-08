package com.collreach.posts.model.bo.polls;


import java.io.Serializable;
import java.util.Objects;

public class UserPollKey implements Serializable {
    private int userId;
    private int pollId;

    public UserPollKey() {
    }

    public UserPollKey(int userId, int pollId) {
        this.userId = userId;
        this.pollId = pollId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, pollId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPollKey userPollKey = (UserPollKey) o;
        return userId == (userPollKey.userId) &&
                pollId == (userPollKey.pollId);
    }
}
