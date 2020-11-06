package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.service.UserDeleteService;
import org.springframework.stereotype.Service;

@Service
public class UserDeleteServiceImpl implements UserDeleteService {
    @Override
    public String deleteUser(){
        return "deleted Successfully.";
    }
}
