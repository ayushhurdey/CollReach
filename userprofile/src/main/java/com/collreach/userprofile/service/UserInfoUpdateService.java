package com.collreach.userprofile.service;

import com.collreach.userprofile.model.request.UserLoginUpdateRequest;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserSkillUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserInfoUpdateService {
    public String updateEmail(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updateAlternateEmail(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updatePhoneNo(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updateCourseInfo(int currentSemester, String username);
    public String updatePassword(UserLoginUpdateRequest userLoginUpdateRequest);
    public String updateUserName(UserLoginUpdateRequest userLoginUpdateRequest);
    public String updateSkills(UserSkillUpdateRequest userSkillUpdateRequest);
    public String updateSkillUpvoteCount(UserSkillUpdateRequest userSkillUpdateRequest);
    public String deleteUserSkills(UserSkillUpdateRequest userSkillUpdateRequest);
    public String updateLinkedinLink(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updateDescription(UserInfoUpdateRequest userInfoUpdateRequest);
    public String updateProfilePhoto(MultipartFile file,MultipartFile miniFile, String userName) throws IOException;
    public String deleteUserProfilePhoto(String userName);
}
