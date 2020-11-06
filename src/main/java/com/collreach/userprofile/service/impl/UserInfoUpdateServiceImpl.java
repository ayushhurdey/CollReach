package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.CourseInfo;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import com.collreach.userprofile.model.repositories.CourseInfoRepository;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserInfoUpdateService;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoUpdateServiceImpl implements UserInfoUpdateService {
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    UserLoginRepository userLoginRepository;
    @Autowired
    UserPersonalInfoRepository userPersonalInfoRepository;

    @Autowired
    CourseInfoRepository courseInfoRepository;

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    @Override
    public String updateEmail(UserInfoUpdateRequest userInfoUpdateRequest){
        UserLoginResponse user = userLoginService.login(
                userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        if(user != null) {
            String oldEmail = user.getUserPersonalInfoResponse().getEmail();
            //System.out.println("OldEmail : " + oldEmail);
            //System.out.println("newMail: " + userInfoUpdateRequest.getEmail());
            userPersonalInfoRepository.updateEmail(oldEmail, userInfoUpdateRequest.getEmail());
            return "Updated Email.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updateAlternateEmail(UserInfoUpdateRequest userInfoUpdateRequest){
        UserLoginResponse user = userLoginService.login(
                userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        if(user != null) {
            String oldEmail = user.getUserPersonalInfoResponse().getAlternateEmail();
            //System.out.println("OldEmail : " + oldEmail);
            //System.out.println("newMail: " + userInfoUpdateRequest.getAlternateEmail());
            userPersonalInfoRepository.updateAlternateEmail(oldEmail, userInfoUpdateRequest.getAlternateEmail());
            return "Updated Alternate Email.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updatePhoneNo(UserInfoUpdateRequest userInfoUpdateRequest){
        UserLoginResponse user = userLoginService.login(
                userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        if(user != null) {
            String oldPhoneNo = user.getUserPersonalInfoResponse().getPhoneNo();
            //System.out.println("OldPhone : " + oldPhoneNo);
            //System.out.println("newPhone: " + userInfoUpdateRequest.getPhoneNo());
            userPersonalInfoRepository.updatePhoneNo(oldPhoneNo, userInfoUpdateRequest.getPhoneNo());
            return "Updated Phone No.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updateCourseId(UserInfoUpdateRequest userInfoUpdateRequest){
        UserLoginResponse user = userLoginService.login(
                userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        if(user != null) {
            String email = user.getUserPersonalInfoResponse().getEmail();
            //System.out.println("OldPhone : " + oldPhoneNo);
            //System.out.println("newPhone: " + userInfoUpdateRequest.getPhoneNo());
            //CourseInfo courseInfo = new CourseInfo();
            var courseInfo = courseInfoRepository.findById(userInfoUpdateRequest.getCourseId()).orElse(null);
            if(courseInfo != null) {
                userPersonalInfoRepository.updateCourseId(email, courseInfo);
                return "Updated Course.";
            }
            return "Course Info invalid.";
        }
        return "Invalid credentials..";
    }
}
