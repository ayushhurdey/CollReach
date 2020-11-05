package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserInfoUpdateService;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    @Override
    public String updateEmail(UserInfoUpdateRequest userInfoUpdateRequest){
        UserLoginResponse user = userLoginService.login(
                userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        if(user != null) {
            UserPersonalInfo userInfo = new UserPersonalInfo();
            //UserLogin usr = userLoginRepository.findById(userInfoUpdateRequest.getUserName()).get();
            String oldEmail = user.getUserPersonalInfoResponse().getEmail();
            //user
            //userInfo.setEmail(userInfoUpdateRequest.getEmail());
            userPersonalInfoRepository.updateEmail(oldEmail, userInfoUpdateRequest.getEmail());
            userPersonalInfoRepository.save(userInfo);
            return "Updated.";
        }
        return "Invalid credentials..";
    }
}
