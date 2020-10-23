package com.collreach.userprofile.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/userprofile")
public class UserProfileController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello...";
    }

    @RequestMapping("/server")
    public String server(){
        return "Server Responded...";
    }

}
