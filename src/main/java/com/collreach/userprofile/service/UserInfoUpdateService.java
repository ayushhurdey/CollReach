package com.collreach.userprofile.service;

import com.collreach.userprofile.model.request.UserLoginUpdateRequest;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;

public interface UserInfoUpdateService {
    public String updateEmail(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updateAlternateEmail(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updatePhoneNo(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updateCourseInfo(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updatePassword(UserLoginUpdateRequest userLoginUpdateRequest);
    public String updateUserName(UserLoginUpdateRequest userLoginUpdateRequest);
    /*public String updateSkills(UserInfoUpdateRequest userInfoUpdateRequest);*/
}
