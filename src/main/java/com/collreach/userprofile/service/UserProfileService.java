package com.collreach.userprofile.service;

import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.model.request.UsersFromSkillsRequest;
import com.collreach.userprofile.model.response.UsersSkillsResponse;

public interface UserProfileService {
    public String signup(UserSignupRequest userSignupRequest);
    public byte[] getImage(String filename) throws Exception;
    public String deleteUser(String userName);
    public String checkUsername(UserSignupRequest userSignupRequest);
    public String checkEmail(UserSignupRequest userSignupRequest);
    public String checkPhoneNo(UserSignupRequest userSignupRequest);
    public String checkLinkedinLink(UserSignupRequest userSignupRequest);
    public String updateUserPersonalInfo(UserSignupRequest userSignupRequest);
    public UsersSkillsResponse getUsersFromSkills(UsersFromSkillsRequest usersFromSkillsRequest);
}
