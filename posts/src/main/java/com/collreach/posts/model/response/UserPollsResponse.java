package com.collreach.posts.model.response;

import java.util.List;

public class UserPollsResponse {
    long messageId;
    long totalVotes;
    List<PollAnswersResponse> answers;

    public UserPollsResponse() {
    }

    public UserPollsResponse(long messageId, long totalVotes, List<PollAnswersResponse> answers) {
        this.messageId = messageId;
        this.totalVotes = totalVotes;
        this.answers = answers;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<PollAnswersResponse> getAnswers() {
        return answers;
    }

    public void setAnswers(List<PollAnswersResponse> answers) {
        this.answers = answers;
    }
}
