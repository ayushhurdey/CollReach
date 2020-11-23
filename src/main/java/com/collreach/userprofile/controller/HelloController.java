package com.collreach.userprofile.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
@CrossOrigin("*")
public class HelloController {

    @RequestMapping("/hello")
    public String hello() { return "Hello! Server is listening at port 8082."; }

    @RequestMapping("/server")
    public String server(){
        return "Server@AyushChoudhary listening..";
    }
}