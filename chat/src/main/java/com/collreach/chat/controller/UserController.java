package com.collreach.chat.controller;

import com.collreach.chat.model.bo.User;
import com.collreach.chat.model.repository.UserRepository;
import com.collreach.chat.model.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(path = "/add-user")
    public ResponseEntity<Boolean> addNewUsers(@RequestBody UserRequest userRequest){
        try {
            String userId = UUID.randomUUID().toString().replace("-", "");
            userRepository.save(new User(userId, userRequest.getUserName(), userRequest.getName()));
            return ResponseEntity.ok().body(true);
        }catch(Exception e){
            return ResponseEntity.ok().body(false);
        }
    }
}
