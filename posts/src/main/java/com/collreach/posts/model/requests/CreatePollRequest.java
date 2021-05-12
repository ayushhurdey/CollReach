package com.collreach.posts.model.requests;

import java.util.Date;
import java.util.List;

public class CreatePollRequest {
    private String userName;
    private String question;
    private int validityInWeeks;
    private Date dateCreated;
    private Date timeCreated;
    private String visibility;
    private int recurrences;
    private List<String> answers;

    public CreatePollRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
