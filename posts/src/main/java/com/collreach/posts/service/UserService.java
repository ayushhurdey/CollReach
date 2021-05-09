package com.collreach.posts.service;

import com.collreach.posts.model.requests.UserRequest;

public interface UserService {

    public Boolean addNewUser(UserRequest userRequest);
}
