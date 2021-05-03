package com.collreach.userprofile.controller;

import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.model.request.UsersFromSkillsRequest;
import com.collreach.userprofile.model.response.UserPersonalInfoResponse;
import com.collreach.userprofile.model.response.UsersSkillsResponse;
import com.collreach.userprofile.service.UserProfileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@Controller
@RequestMapping(path = "/user")
@CrossOrigin("*")
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

    @PutMapping(path = "/user-personal-info")
    public ResponseEntity<String> updateUserPersonalInfo(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.updateUserPersonalInfo(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping(value = "/get-image", produces = MediaType.IMAGE_JPEG_VALUE)
    @Cacheable(value="profile-image")
    public ResponseEntity<byte[]> getImage(String filename) throws Exception {
        InputStream inputStream = userProfileService.getImage(filename);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return ResponseEntity.ok().body(bytes);
    }

    @GetMapping(path = "/profile/{profile-access-key}")
    public ResponseEntity<UserPersonalInfoResponse> getUserPersonalInfo(
            @PathVariable(value = "profile-access-key") String profileAccessKey){
        UserPersonalInfoResponse userPersonalInfoResponse = userProfileService.getUserPersonalInfo(profileAccessKey);
        return ResponseEntity.ok().body(userPersonalInfoResponse);
    }

    @DeleteMapping(path = "/delete-user/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "username") String userName){
        String msg = userProfileService.deleteUser(userName);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path = "/get-users-from-skills")
    public ResponseEntity<UsersSkillsResponse> getUserFromSkills(@RequestBody UsersFromSkillsRequest usersFromSkillsRequest){
        UsersSkillsResponse msg = userProfileService.getUsersFromSkills(usersFromSkillsRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path = "/check-username")
    public ResponseEntity<String> checkUsername(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.checkUsername(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path = "/check-email")
    public ResponseEntity<String> checkEmail(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.checkEmail(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path = "/check-phone-no")
    public ResponseEntity<String> checkPhoneNo(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.checkPhoneNo(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path = "/check-linkedin-link")
    public ResponseEntity<String> checkLinkedinLink(@RequestBody UserSignupRequest userSignupRequest){
        String msg = userProfileService.checkLinkedinLink(userSignupRequest);
        return ResponseEntity.ok().body(msg);
    }
}
