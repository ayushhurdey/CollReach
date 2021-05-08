package com.collreach.posts.model.bo.posts;

import javax.persistence.*;

@Entity
@Table(name = "seen")
@IdClass(UserMessageKey.class)
public class Seen {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Messages messageId;

    @Column(name = "seen")
    private char seen;

    public Seen() {
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Messages getMessageId() {
        return messageId;
    }

    public void setMessageId(Messages messageId) {
        this.messageId = messageId;
    }

    public char getSeen() {
        return seen;
    }

    public void setSeen(char seen) {
        this.seen = seen;
    }
}
