package com.collreach.frontend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
public class PostsPagesController {

    @GetMapping("/posts")
    public String getPosts(){
        return "CollReachPosts";
    }

    @GetMapping("/my-posts")
    public String getMyPosts(){
        return "MyPostPoll";
    }
}
