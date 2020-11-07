package com.collreach.userprofile.service;

import com.collreach.userprofile.model.request.UserSignupRequest;

public interface UserProfileService {
    public String signup(UserSignupRequest userSignupRequest);
    public String deleteUser(String userName);
    public String checkUsername(UserSignupRequest userSignupRequest);
    public String checkEmail(UserSignupRequest userSignupRequest);
    public String checkAlternateEmail(UserSignupRequest userSignupRequest);
    public String checkPhoneNo(UserSignupRequest userSignupRequest);
}
