package com.collreach.userprofile.service;

import com.collreach.userprofile.model.request.UserSignupRequest;

public interface UserProfileService {
    public String signup(UserSignupRequest userSignupRequest);
}
