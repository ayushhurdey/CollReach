package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserSkills;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserSkillsRepository;
import com.collreach.userprofile.model.request.UserLoginRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    @Autowired
    private UserLoginRepository userLoginRepository;
    @Autowired
    private UserSkillsRepository userSkillsRepository;


    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        String username;
        String password;
        UserLogin user = userLoginRepository.findById(userLoginRequest.getUserName()).orElse(null);
        if(user != null){
            username = user.getUserName();
            password = user.getPassword();

            if(username.equals(userLoginRequest.getUserName()) && password.equals(userLoginRequest.getPassword())){
                UserLoginResponse userLoginResponse = userProfileMapper.userLoginToUserLoginResponse(user);
                List<UserSkills> userSkills = userSkillsRepository.findAllByUserId(user.getUserPersonalInfo());

                Map<String,Integer> skills = new HashMap<String,Integer>();
                for(UserSkills userSkill: userSkills){
                    skills.put(userSkill.getSkillId().getSkill(), userSkill.getSkillUpvoteCount());
                }
                userLoginResponse.getUserPersonalInfoResponse().setSkills(skills);
                return userLoginResponse;
            }
        }
        return userProfileMapper.userLoginToUserLoginResponse(null);
    }
}
