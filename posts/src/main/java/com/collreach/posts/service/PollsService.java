package com.collreach.posts.service;

import com.collreach.posts.model.requests.CreatePollRequest;
import com.collreach.posts.model.response.MessageResponse;

import java.util.LinkedHashSet;

public interface PollsService {
    public String createPoll(CreatePollRequest createPollRequest);
    public LinkedHashSet<MessageResponse> getPolls(Integer pageNo, Integer pageSize, String visibility);
    public String updatesAnswersVotes(String userName, int pollId, int answerId);
}
