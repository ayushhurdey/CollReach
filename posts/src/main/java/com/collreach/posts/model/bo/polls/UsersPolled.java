package com.collreach.posts.model.bo.polls;

import com.collreach.posts.model.bo.Users;

import javax.persistence.*;

@Entity
@Table(name = "user_polled")
@IdClass(UserPollKey.class)
public class UsersPolled {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Polls pollId;

    @Column(name = "if_voted")
    private char ifVoted;

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Polls getPollId() {
        return pollId;
    }

    public void setPollId(Polls pollId) {
        this.pollId = pollId;
    }

    public char getIfVoted() {
        return ifVoted;
    }

    public void setIfVoted(char ifVoted) {
        this.ifVoted = ifVoted;
    }
}
