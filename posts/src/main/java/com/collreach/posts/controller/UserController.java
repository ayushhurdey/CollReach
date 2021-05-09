package com.collreach.posts.controller;

import com.collreach.posts.model.requests.UserRequest;
import com.collreach.posts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/add-user")
    public ResponseEntity<Boolean> addNewUsers(@RequestBody UserRequest userRequest){
        Boolean response = userService.addNewUser(userRequest);
        return ResponseEntity.ok().body(response);
    }
}
