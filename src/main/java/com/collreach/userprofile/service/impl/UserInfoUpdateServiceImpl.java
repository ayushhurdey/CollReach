package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.repositories.CourseInfoRepository;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.request.UserLoginUpdateRequest;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserInfoUpdateService;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            userPersonalInfoRepository.updatePhoneNo(oldPhoneNo, userInfoUpdateRequest.getPhoneNo());
            return "Updated Phone No.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updateCourseInfo(UserInfoUpdateRequest userInfoUpdateRequest){
        UserLoginResponse user = userLoginService.login(
                userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        if(user != null) {
            String email = user.getUserPersonalInfoResponse().getEmail();
            var courseInfo = courseInfoRepository.findById(userInfoUpdateRequest.getCourseId()).orElse(null);
            if(courseInfo != null) {
                userPersonalInfoRepository.updateCourseId(email, courseInfo);
                return "Updated Course.";
            }
            return "Course Info invalid.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updatePassword(UserLoginUpdateRequest userLoginUpdateRequest){
        UserLoginResponse user = userLoginService.login(
                userProfileMapper.userLoginUpdateRequestToUserLoginRequest(userLoginUpdateRequest));
        if(user != null) {
            String username = user.getUserName();
            String newPassword = userLoginUpdateRequest.getNewPassword();

            if(user.getPassword().equals(newPassword))
                return "Try password other than previous one.";

            if(!newPassword.equals("")){
                userLoginRepository.updatePassword(username, newPassword);
                return "Password updated";
            }
            return "Invalid new Password.";
        }
        return "Invalid credentials.";
    }

    @Override
    public String updateUserName(UserLoginUpdateRequest userLoginUpdateRequest){
        UserLoginResponse user = userLoginService.login(
                userProfileMapper.userLoginUpdateRequestToUserLoginRequest(userLoginUpdateRequest));
        if(user != null) {
            String username = user.getUserName();
            String newUserName = userLoginUpdateRequest.getNewUserName();

            if(username.equals(newUserName))
                return "Try username other than previous one.";

            if(!newUserName.equals("")){
                userLoginRepository.updateUserName(username, newUserName);
                return "Username updated.";
            }
            return "Invalid new username.";
        }
        return "Invalid credentials.";
    }
}
