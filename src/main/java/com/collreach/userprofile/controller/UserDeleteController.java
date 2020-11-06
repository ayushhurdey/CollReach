package com.collreach.userprofile.controller;

import com.collreach.userprofile.service.UserDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserDeleteController {
    @Autowired
    UserDeleteService userDeleteService;

    @DeleteMapping(path = "/deleteUser/{username}")
    public ResponseEntity<String> deleteUser(){
        String msg = userDeleteService.deleteUser();
        return ResponseEntity.ok().body(msg);
    }
}
