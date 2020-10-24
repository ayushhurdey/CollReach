package com.collreach.userprofile.controller;

import com.collreach.userprofile.model.bo.User;
import com.collreach.userprofile.model.repositories.UserRepository;
import com.collreach.userprofile.model.request.UserAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path="/user")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/")
    public ResponseEntity<String> addNewUser(@RequestBody UserAddRequest userAddRequest){
        User user = new User();
        user.setUserName(userAddRequest.getUserName());
        user.setPassword(userAddRequest.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok().body("User Added Successfully");
    }

    @RequestMapping("/hello")
    public String hello() {

        return "Hello...";
    }

    @RequestMapping("/server")
    public String server(){
        return "Server Responded...";
    }

}
