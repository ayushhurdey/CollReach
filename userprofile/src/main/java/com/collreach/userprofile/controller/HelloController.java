package com.collreach.userprofile.controller;

import com.collreach.userprofile.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    HttpRequestUtil httpRequestUtil;

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

    @GetMapping("/test-eureka")
    public ResponseEntity<Boolean> testEureka(){
        return ResponseEntity.ok().body(httpRequestUtil.setNewUserAtUrl( "123456789",
                "Test Name", "http://posts/posts/user/test"));
    }
}