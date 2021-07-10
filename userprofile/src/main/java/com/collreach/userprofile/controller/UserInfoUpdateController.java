package com.collreach.userprofile.controller;

import com.collreach.userprofile.model.request.UserLoginUpdateRequest;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserSkillUpdateRequest;
import com.collreach.userprofile.service.UserInfoUpdateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(path = "/user")
@CrossOrigin("*")
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

    @PutMapping(path = "/semester")
    public ResponseEntity<String> updateCourseInfo(@RequestParam Integer semester,
                                                   @RequestParam String username){
        String msg = userInfoUpdateService.updateCourseInfo(semester, username);
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

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @PutMapping(path = "/profile-photo")
    public ResponseEntity<String> updateProfilePhoto(@RequestParam("file") MultipartFile file ,
                                                     @RequestParam("miniFile") MultipartFile miniFile,
                                                     @RequestParam("userName") String userName) throws IOException {
        String msg = userInfoUpdateService.updateProfilePhoto(file, miniFile, userName);
        return ResponseEntity.ok().body(msg);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @DeleteMapping(path = "/profile-photo")
    public ResponseEntity<String> deleteProfilePhoto(@RequestParam("userName") String userName) {
        String msg = userInfoUpdateService.deleteUserProfilePhoto(userName);
        return ResponseEntity.ok().body(msg);
    }
}
