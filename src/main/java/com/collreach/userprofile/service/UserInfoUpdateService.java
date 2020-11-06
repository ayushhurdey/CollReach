package com.collreach.userprofile.service;

import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserSignupRequest;

public interface UserInfoUpdateService {
    public String updateEmail(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updateAlternateEmail(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updatePhoneNo(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updateCourseId(UserInfoUpdateRequest userInfoUpdateRequest);
}
