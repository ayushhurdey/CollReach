package com.collreach.posts.service.impl;

import com.collreach.posts.model.bo.Users;
import com.collreach.posts.model.repositories.UsersRepository;
import com.collreach.posts.model.requests.UserRequest;
import com.collreach.posts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Boolean addNewUser(UserRequest userRequest) {
        try {
            Users users = new Users();
            users.setUserName(userRequest.getUserName());
            users.setName(userRequest.getName());
            usersRepository.save(users);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
