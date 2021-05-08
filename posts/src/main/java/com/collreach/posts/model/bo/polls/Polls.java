package com.collreach.posts.model.bo.polls;

import com.collreach.posts.model.bo.Users;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "polls")
public class Polls {

    @Id
    @Column(name = "poll_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pollId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @Column(name = "question", nullable = false, length = 160)
    private String question;

    @Column(name = "validity_in_weeks", nullable = false)
    private int validityInWeeks;

    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateCreated;

    @Column(name = "time_created", nullable = false)
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm:ss")
    private Date timeCreated;

    @Column(name = "visibility", nullable = false, length = 30)
    private String visibility;

    @Column(name = "recurrences", nullable = false)
    private int recurrences;

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getValidityInWeeks() {
        return validityInWeeks;
    }

    public void setValidityInWeeks(int validityInWeeks) {
        this.validityInWeeks = validityInWeeks;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public int getRecurrences() {
        return recurrences;
    }

    public void setRecurrences(int recurrences) {
        this.recurrences = recurrences;
    }
}
