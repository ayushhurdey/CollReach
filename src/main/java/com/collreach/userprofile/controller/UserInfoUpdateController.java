package com.collreach.userprofile.controller;

import com.collreach.userprofile.model.request.UserLoginUpdateRequest;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserSkillUpdateRequest;
import com.collreach.userprofile.service.UserInfoUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserInfoUpdateController {

    @Autowired
    UserInfoUpdateService userInfoUpdateService;


    @PutMapping(path = "/skills")
    public ResponseEntity<String> updateSkills(@RequestBody UserSkillUpdateRequest userSkillUpdateRequest){
        String msg = userInfoUpdateService.updateSkills(userSkillUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/skills-upvote-count")
    public ResponseEntity<String> updateSkillUpvoteCount(@RequestBody UserSkillUpdateRequest userSkillUpdateRequest){
        String msg = userInfoUpdateService.updateSkillUpvoteCount(userSkillUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @DeleteMapping(path = "/skills")
    public ResponseEntity<String> deleteSkills(@RequestBody UserSkillUpdateRequest userSkillUpdateRequest){
        String msg = userInfoUpdateService.deleteUserSkills(userSkillUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/email")
    public ResponseEntity<String> updateEmail(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updateEmail(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/alternate-email")
    public ResponseEntity<String> updateAlternateEmail(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updateAlternateEmail(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/phone-no")
    public ResponseEntity<String> updatePhoneNo(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updatePhoneNo(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/course-id")
    public ResponseEntity<String> updateCourseInfo(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updateCourseInfo(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/password")
    public ResponseEntity<String> updatePassword(@RequestBody UserLoginUpdateRequest userLoginUpdateRequest){
        String msg = userInfoUpdateService.updatePassword(userLoginUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/username")
    public ResponseEntity<String> updateUserName(@RequestBody UserLoginUpdateRequest userLoginUpdateRequest){
        String msg = userInfoUpdateService.updateUserName(userLoginUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/linkedin-link")
    public ResponseEntity<String> updateLinkedinLink(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updateLinkedinLink(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/description")
    public ResponseEntity<String> updateDescription(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        String msg = userInfoUpdateService.updateDescription(userInfoUpdateRequest);
        return ResponseEntity.ok().body(msg);
    }
}
