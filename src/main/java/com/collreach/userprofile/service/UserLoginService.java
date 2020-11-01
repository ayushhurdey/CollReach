package com.collreach.userprofile.service;

import com.collreach.userprofile.model.request.UserLoginRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;

public interface UserLoginService {
    public UserLoginResponse login(UserLoginRequest userLoginRequest);
}
