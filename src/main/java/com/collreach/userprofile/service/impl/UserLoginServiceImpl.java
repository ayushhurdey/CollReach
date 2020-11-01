package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.request.UserLoginRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

public class UserLoginServiceImpl implements UserLoginService {

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        UserLogin user = userLoginRepository.findById(userLoginRequest.getUserName()).orElse(null);
        return userProfileMapper.userLoginToUserLoginResponse(user);
    }
}
