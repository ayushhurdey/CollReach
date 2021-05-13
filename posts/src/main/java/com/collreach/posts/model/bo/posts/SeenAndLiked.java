package com.collreach.posts.model.bo.posts;

import com.collreach.posts.model.bo.Users;

import javax.persistence.*;

@Entity
@Table(name = "seen")
@IdClass(UserMessageKey.class)
public class SeenAndLiked {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Messages messageId;

    @Column(name = "seen")
    private Character seen;

    @Column(name = "liked")
    private Character liked;

    public SeenAndLiked() {
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

    public Character getSeen() {
        return seen;
    }

    public void setSeen(Character seen) {
        this.seen = seen;
    }

    public Character getLiked() {
        return liked;
    }

    public void setLiked(Character liked) {
        this.liked = liked;
    }
}
