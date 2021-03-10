package com.collreach.userprofile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@CrossOrigin("*")
public class PagesController {

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup(){
        return "SignupUpdated";
    }

    @GetMapping("/profile")
    public String getProfile(){
        return "UserProfile";
    }

    @GetMapping("/profile-update")
    public String getProfileUpdate(){
        return "UserProfileUpdate";
    }

    @GetMapping("/profile/{profile-access-key}")
    public String getProfileForGeneralUser(@PathVariable(value = "profile-access-key") String profileAccessKey){
        return "GeneralProfile";
    }
}
