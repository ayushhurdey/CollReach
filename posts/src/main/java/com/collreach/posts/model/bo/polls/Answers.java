package com.collreach.posts.model.bo.polls;


import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answers {

    @Id
    @Column(name= "answer_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerId;

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Polls poll_id;

    @Column(name= "answer", nullable = false, length = 55)
    private String answer;

    @Column(name= "votes", nullable = false)
    private int votes;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public Polls getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(Polls poll_id) {
        this.poll_id = poll_id;
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
