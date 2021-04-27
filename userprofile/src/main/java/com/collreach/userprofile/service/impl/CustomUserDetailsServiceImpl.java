package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserSkillsRepository;
import com.collreach.userprofile.service.CustomUserDetailsService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserLogin user = userLoginRepository.findById(userName).orElse(null);

        if(user != null){
           return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
        }
        else{
            throw new UsernameNotFoundException("User not found!!");
        }
    }
}
