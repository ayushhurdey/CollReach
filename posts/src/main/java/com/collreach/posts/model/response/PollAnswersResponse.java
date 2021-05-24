package com.collreach.posts.model.response;

public class PollAnswersResponse {
    private int answerId;
    private String answer;
    private int votes;
    private int percentage;

    public PollAnswersResponse() {
    }

    public PollAnswersResponse(int answerId, String answer, int votes) {
        this.answerId = answerId;
        this.answer = answer;
        this.votes = votes;
    }

    public PollAnswersResponse(int answerId, String answer, int votes, int percentage) {
        this.answerId = answerId;
        this.answer = answer;
        this.votes = votes;
        this.percentage = percentage;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
