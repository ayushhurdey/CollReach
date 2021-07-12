package com.collreach.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
public class UserProfilePagesController {

    @GetMapping("/login")
    public String getLogin(){
        return "Login";
    }

    @GetMapping("/signup")
    public String getSignup(){
        return "Signup";
    }

    @GetMapping("/profile")
    public String getProfile(){
        return "UserProfile";
    }

    @GetMapping("/profile-update")
    public String getProfileUpdate(){
        return "UserProfileUpdate";
    }

    @GetMapping("/profile/{profileAccessKey}")
    public String getGeneralProfile(){
        return "GeneralProfile";
    }
    //TODO: Getting general profile from 'profile access key' is left.
    // As of now it has been done with username instead of 'profile access key'
}
