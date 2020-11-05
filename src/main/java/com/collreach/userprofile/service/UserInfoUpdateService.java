package com.collreach.userprofile.service;

import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserSignupRequest;

public interface UserInfoUpdateService {
    public String updateEmail(UserInfoUpdateRequest userInfoUpdateRequest);
}
