package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.SkillsInfo;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserSkills;
import com.collreach.userprofile.model.repositories.SkillsInfoRepository;
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
    @Autowired
    private SkillsInfoRepository skillsInfoRepository;

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        String username;
        String password;
        UserLogin user = userLoginRepository.findById(userLoginRequest.getUserName()).orElse(null);
        if(user != null){
            username = user.getUserName();
            password = user.getPassword();
            //Set<SkillsInfo> skillSet = user.getUserPersonalInfo().getUserSkills();

            var userSkills = userSkillsRepository.findAllByUserId(user.getUserPersonalInfo());
            Set<SkillsInfo> skillSet = new HashSet<SkillsInfo>();
            /*for(Optional<UserSkills> userSkill: userSkills){
                userSkill.ifPresent(skills -> skillSet.add(skills.getSkillId()));
            }*/
            for(UserSkills userSkill: userSkills){
                //var x = userSkill.getUserId().getUserId();
                //if(x == user.getUserPersonalInfo().getUserId())
                    skillSet.add(userSkill.getSkillId());
            }


            if(username.equals(userLoginRequest.getUserName()) && password.equals(userLoginRequest.getPassword())){
                 UserLoginResponse userLoginResponse = userProfileMapper.userLoginToUserLoginResponse(user);
                 List<String> skills = new ArrayList<String>();
                 for(SkillsInfo skillsInfo : skillSet){
                    skills.add(skillsInfo.getSkill());
                 }
                 userLoginResponse.getUserPersonalInfoResponse().setSkills(skills);
                 return userLoginResponse;
            }
        }
        return userProfileMapper.userLoginToUserLoginResponse(null);
    }
}
