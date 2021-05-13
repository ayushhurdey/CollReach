package com.collreach.posts.model.bo.polls;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "answers")
public class PollAnswers {

    @Id
    @Column(name= "answer_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerId;

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Polls pollId;

    @Column(name= "answer", nullable = false, length = 55)
    private String answer;

    @Column(name= "votes", nullable = false)
    private int votes;

    @PrePersist
    public void setVotesOnNewPoll(){
        this.votes = 0;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public Polls getPollId() {
        return pollId;
    }

    public void setPollId(Polls pollId) {
        this.pollId = pollId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
