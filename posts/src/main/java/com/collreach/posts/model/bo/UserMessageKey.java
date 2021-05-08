package com.collreach.posts.model.bo;

import java.io.Serializable;
import java.util.Objects;

public class UserMessageKey implements Serializable {

    private int userId;
    private int messageId;

    public UserMessageKey() {
    }

    public UserMessageKey(int userId, int messageId) {
        this.userId = userId;
        this.messageId = messageId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, messageId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMessageKey userMessageKey = (UserMessageKey) o;
        return userId == (userMessageKey.userId) &&
                messageId == (userMessageKey.messageId);
    }
}
