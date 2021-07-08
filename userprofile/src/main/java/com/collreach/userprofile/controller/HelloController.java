package com.collreach.userprofile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/response")
@CrossOrigin("*")
public class HelloController {

    @GetMapping("/hello")
    public List<String> hello() { return Collections.singletonList("Hello! Server is listening at port 8082."); }

    @GetMapping("/server")
    public List<String> server(){
        return Collections.singletonList("Server@AyushChoudhary listening..");
    }

    // working in current configuration by putting html in templates
    @GetMapping("/login")
    public String test(){
        return "login";
    }
}