package com.collreach.userprofile.controller;

import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.request.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path="/user")
public class UserProfileController {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserPersonalInfoRepository userExtrasRepository;

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

    @PostMapping(path = "/login")
    public ResponseEntity<UserLogin> login(@RequestBody UserLoginRequest userLoginRequest){
        UserLogin user =  userLoginRepository.findById(userLoginRequest.getUserName()).get();
        return ResponseEntity.ok().body(user);
    }
}
