package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.request.UserLoginRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if(username.equals(userLoginRequest.getUserName()) && password.equals(userLoginRequest.getPassword())){
                return userProfileMapper.userLoginToUserLoginResponse(user);
            }
        }
        return userProfileMapper.userLoginToUserLoginResponse(null);
    }
}
