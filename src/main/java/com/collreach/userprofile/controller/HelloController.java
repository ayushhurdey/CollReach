package com.collreach.userprofile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
public class HelloController {

    @RequestMapping("/hello")
    public String hello() { return "Hello..."; }

    @RequestMapping("/server")
    public String server(){
        return "Server Responded...";
    }
}
