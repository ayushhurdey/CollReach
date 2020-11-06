package com.collreach.userprofile.controller;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserLoginRequest;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserInfoUpdateService;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserInfoUpdateController {
    //@Autowired
    //UserLoginService userLoginService;
    @Autowired
    UserInfoUpdateService userInfoUpdateService;

    //private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    @PutMapping(path = "/updateEmail")
    public ResponseEntity<String> updateEmail(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updateEmail(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/updateAlternateEmail")
    public ResponseEntity<String> updateAlternateEmail(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updateAlternateEmail(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/updatePhoneNo")
    public ResponseEntity<String> updatePhoneNo(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updatePhoneNo(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/updateCourseId")
    public ResponseEntity<String> updateCourseId(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updateCourseId(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }
}
