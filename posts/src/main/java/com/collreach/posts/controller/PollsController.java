package com.collreach.posts.controller;

import com.collreach.posts.model.requests.CreatePollRequest;
import com.collreach.posts.service.PollsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class PollsController {

    @Autowired
    private PollsService pollsService;

    @PostMapping(path = "create-poll")
    public ResponseEntity<String> createPoll(@RequestBody CreatePollRequest createPollRequest){
        String msg = pollsService.createPoll(createPollRequest);
        return ResponseEntity.ok().body(msg);
    }
}
