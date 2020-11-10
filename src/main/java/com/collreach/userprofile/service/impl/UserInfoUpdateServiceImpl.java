package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.*;
import com.collreach.userprofile.model.repositories.*;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserLoginUpdateRequest;
import com.collreach.userprofile.model.request.UserSkillUpdateRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserInfoUpdateService;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserSkillsRepository userSkillsRepository;

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    /*
    @Override
    public String updateSkills(UserInfoUpdateRequest userInfoUpdateRequest){
        UserLogin user = userLoginRepository.findById(userInfoUpdateRequest.getUserName()).get();
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        int userId = userPersonalInfo.getUserId();
        Set<SkillsInfo> skills = new HashSet<>();
        for(int skillId : userInfoUpdateRequest.getSkills()){
            SkillsInfo skillsInfo = new SkillsInfo();
            skillsInfo.setSkillId(skillId);
            skills.add(skillsInfo);
        }
        userPersonalInfo.setUserSkills(skills);
        userPersonalInfoRepository.save(userPersonalInfo);
        return "Updated Skills successfully.";
    }*/

    @Override
    public String updateSkills(UserSkillUpdateRequest userSkillUpdateRequest){
        Optional<UserLogin> userLogin = userLoginRepository.findById(userSkillUpdateRequest.getUserName());
        UserLogin user;
        if(!userLogin.isPresent()){
            return "Invalid details provided.";
        }
        user = userLogin.get();
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        for(int skillId : userSkillUpdateRequest.getSkills()){
            SkillsInfo skillsInfo = new SkillsInfo();
            UserSkills userSkills = new UserSkills();
            skillsInfo.setSkillId(skillId);
            Optional<UserSkills> oldSkills = userSkillsRepository
                    .findById(new UserSkillsKey(userPersonalInfo.getUserId(),skillsInfo.getSkillId()));
            if(!oldSkills.isPresent()){
                userSkills.setUserId(userPersonalInfo);
                userSkills.setSkillId(skillsInfo);
                userSkills.setSkillUpvoteCount(0);
                userSkillsRepository.save(userSkills);
            }
        }
        return "Updated Skills successfully.";
    }

    @Override
    public String updateSkillUpvoteCount(UserSkillUpdateRequest userSkillUpdateRequest){
        int skillId = userSkillUpdateRequest.getSkillId();
        Optional<UserLogin> userLogin = userLoginRepository.findById(userSkillUpdateRequest.getUserName());
        if(userLogin.isPresent()){
            UserPersonalInfo userPersonalInfo = userLogin.get().getUserPersonalInfo();
            UserSkills userSkills = userSkillsRepository
                    .findById(new UserSkillsKey( userPersonalInfo.getUserId(), skillId )).get();
            int skillUpvoteCounts = userSkills.getSkillUpvoteCount() + 1;
            SkillsInfo skillsInfo = new SkillsInfo();
            skillsInfo.setSkillId(skillId);
            userSkillsRepository.updateByUserIdAndSkillId(userPersonalInfo, skillsInfo, skillUpvoteCounts);
            return "skill upvote count increased by 1.";
        }
        return "Something went wrong.";
    }

    @Override
    public String deleteUserSkills(UserSkillUpdateRequest userSkillUpdateRequest){
        Optional<UserLogin> userLogin = userLoginRepository.findById(userSkillUpdateRequest.getUserName());
        if(userLogin.isPresent()) {
            for(int skillId : userSkillUpdateRequest.getSkills()){
                userSkillsRepository.deleteById(new UserSkillsKey( userLogin.get().getUserPersonalInfo().getUserId(), skillId ));
            }
            return "skills deleted successfully.";
        }
        return "Something went wrong.";
    }

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
            CourseInfo courseInfo = courseInfoRepository.findById(userInfoUpdateRequest.getCourseId()).orElse(null);
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
