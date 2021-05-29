package com.collreach.posts.service;

import com.collreach.posts.model.bo.polls.Polls;
import com.collreach.posts.model.requests.CreatePollRequest;
import com.collreach.posts.model.response.MessageResponse;
import com.collreach.posts.model.response.UserPollsResponse;

import java.util.LinkedHashSet;

public interface PollsService {
    public String createPoll(CreatePollRequest createPollRequest);
    public LinkedHashSet<MessageResponse> getPolls(Integer pageNo, Integer pageSize, String visibility);
    public UserPollsResponse updatesAnswersVotes(String userName, int pollId, int answerId);
    UserPollsResponse getAllAnswersOfPoll(Polls pollId, int pollID);
    String deletePoll(int pollId, String userName);
}
