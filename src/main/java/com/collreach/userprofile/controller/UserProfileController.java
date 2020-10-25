package com.collreach.userprofile.controller;

import com.collreach.userprofile.model.bo.User;
import com.collreach.userprofile.model.repositories.UserRepository;
import com.collreach.userprofile.model.request.UserAddRequest;
import com.collreach.userprofile.model.request.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping(path="/user")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/")
    public ResponseEntity<String> addNewUser(@RequestBody UserAddRequest userAddRequest) throws Exception{
        try {
            User user = new User();
            user.setId(userAddRequest.getId());
            user.setUserName(userAddRequest.getUserName());
            user.setPassword(userAddRequest.getPassword());
            int id = userAddRequest.getId();
            Optional <User> optional = userRepository.findById(id);
            if (optional.isPresent()) {
                return ResponseEntity.ok().body("User Already Exists.");
            } else {
                userRepository.save(user);
                return ResponseEntity.ok().body("User Added Successfully");
            }
        }
        catch(Exception e){
            return ResponseEntity.ok().body("User Already exists.");
        }
    }

    @PostMapping(path="/login")
    public ResponseEntity<String> checkLogin(@RequestBody UserLoginRequest userLoginRequest){
        int id = userLoginRequest.getId();
        Optional <User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            System.out.println(optional.get());
            return ResponseEntity.ok().body("Login successful." + optional);
        } else {
            //System.out.printf("No employee found with id %d%n", id);
            return ResponseEntity.ok().body("Invalid credentials.");
        }
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
