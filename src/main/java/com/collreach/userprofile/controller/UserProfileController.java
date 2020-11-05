package com.collreach.userprofile.controller;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.CourseInfo;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    UserPersonalInfoRepository userPersonalInfoRepository;

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequest userSignupRequest){
        try{
            String userSignupResponse = userProfileService.signup(userSignupRequest);
            return ResponseEntity.ok().body(userSignupResponse);
        }catch(Exception e){
            return ResponseEntity.ok().body("Some Error Occurred. Please review your information.");
        }
    }

    @PostMapping(path = "/checkUsername")
    public ResponseEntity<String> checkUsername(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.checkUsername(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path = "/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.checkEmail(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path = "/checkAlternateEmail")
    public ResponseEntity<String> checkAlternateEmail(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.checkAlternateEmail(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path = "/checkPhoneNo")
    public ResponseEntity<String> checkPhoneNo(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.checkPhoneNo(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }
}
