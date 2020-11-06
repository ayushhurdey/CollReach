package com.collreach.userprofile.controller;

import com.collreach.userprofile.model.request.UserLoginRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path="/user")
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping(path = "/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        UserLoginResponse userLoginResponse = userLoginService.login(userLoginRequest);
        if(userLoginResponse == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(userLoginResponse);
    }

    /*
    @PostMapping(path="/signup")
    public ResponseEntity<String> addNewUser(@RequestBody UserAddRequest userAddRequest) throws Exception{
        try {
            UserLogin user = new UserLogin();
            UserPersonalInfo userExtras = new UserPersonalInfo();
            user.setId(userAddRequest.getId());
            user.setUserName(userAddRequest.getUserName());
            user.setPassword(userAddRequest.getPassword());
            user.setEmail(userAddRequest.getEmail());
            user.setName(userAddRequest.getName());
            user.setBranch(userAddRequest.getBranch());
            user.setCourse(userAddRequest.getCourse());
            user.setYearOfStudy(userAddRequest.getYearOfStudy());
            userExtras.setOtherEmail(userAddRequest.getOtherEmail());
            userExtras.setPhoneNo(userAddRequest.getPhoneNo());
            userExtras.setUser(user);

            String username = userAddRequest.getUserName();
            String otherEmail = userAddRequest.getOtherEmail();
            System.out.println("Email additional : " + otherEmail);
            String phoneNo = userAddRequest.getPhoneNo();
            System.out.println("Phone additional : " + phoneNo);
            Optional <UserLogin> optional = userRepository.findById(username);
            if (optional.isPresent()) {
                return ResponseEntity.ok().body("User Already Exists.");
            } else {
                userRepository.save(user);
                if(!(otherEmail.equals("")) || !(phoneNo.equals("")) || !(phoneNo == null) || !(otherEmail == null))
                    userExtrasRepository.save(userExtras);
                return ResponseEntity.ok().body("User Added Successfully");
            }
        }
        catch(Exception e){
            return ResponseEntity.ok().body("Invalid Entries. Try Something else.");
        }


    }
*/
/*
    @PostMapping(path="/login")
    public ResponseEntity<String> checkLogin(@RequestBody UserLoginRequest userLoginRequest){
        String username = userLoginRequest.getUserName();
        String password = userLoginRequest.getPassword();
        int id = userLoginRequest.getId();
        Optional <UserLogin> optional = userRepository.findById(username);

        if (optional.isPresent() &&
                optional.get().getId() == id &&
                optional.get().getPassword().equals(password) &&
                optional.get().getUserName().equals(username)) {
            System.out.println(optional.get().getId());
            System.out.println(optional.get().getUserName());
            System.out.println(optional.get().getPassword());
            return ResponseEntity.ok().body("Login successful.");
        } else {
            //System.out.printf("No employee found with id %d%n", id);
            return ResponseEntity.ok().body("Invalid credentials.");
        }
    }
 */

}
