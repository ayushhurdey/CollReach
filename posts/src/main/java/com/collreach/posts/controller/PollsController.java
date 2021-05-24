package com.collreach.posts.controller;

import com.collreach.posts.model.requests.CreatePollRequest;
import com.collreach.posts.model.response.UserPollsResponse;
import com.collreach.posts.service.PollsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class PollsController {

    @Autowired
    private PollsService pollsService;

    @PostMapping(path = "/create-poll")
    public ResponseEntity<String> createPoll(@RequestBody CreatePollRequest createPollRequest){
        String msg = pollsService.createPoll(createPollRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/update-answer-votes/{username}/{pollId}/{answerId}")
    public ResponseEntity<UserPollsResponse> updateAnswerVotes(@PathVariable(value = "username") String userName,
                                                     @PathVariable(value = "pollId") int pollId,
                                                     @PathVariable(value = "answerId") int answerId){

        UserPollsResponse msg = pollsService.updatesAnswersVotes(userName, pollId, answerId);
        return ResponseEntity.ok().body(msg);
    }
}
