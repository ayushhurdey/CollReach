package com.collreach.posts.service;

import com.collreach.posts.model.requests.CreatePollRequest;

public interface PollsService {
    public String createPoll(CreatePollRequest createPollRequest);
}
