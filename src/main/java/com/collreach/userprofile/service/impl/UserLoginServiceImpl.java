package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.SkillsInfo;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.request.UserLoginRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        String username;
        String password;
        UserLogin user = userLoginRepository.findById(userLoginRequest.getUserName()).orElse(null);
        if(user != null){
            username = user.getUserName();
            password = user.getPassword();
            var x = user.getUserPersonalInfo().getUserSkills();
            for(SkillsInfo skillsInfo : x){
                System.out.println("Skills of  " + username + " : " + skillsInfo.getSkill());
            }

            if(username.equals(userLoginRequest.getUserName()) && password.equals(userLoginRequest.getPassword())){
                 UserLoginResponse userLoginResponse = userProfileMapper.userLoginToUserLoginResponse(user);
                 List<String> skills = new ArrayList<String>();
                 for(SkillsInfo skillsInfo : x){
                    skills.add(skillsInfo.getSkill());
                 }
                 userLoginResponse.getUserPersonalInfoResponse().setSkills(skills);
                 return userLoginResponse;
            }
        }
        return userProfileMapper.userLoginToUserLoginResponse(null);
    }
}
