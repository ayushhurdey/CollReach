package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.model.bo.CourseInfo;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    UserPersonalInfoRepository userPersonalInfoRepository;

    @Override
    public String signup(UserSignupRequest userSignupRequest) {
        CourseInfo courseInfo = new CourseInfo();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        UserLogin userLogin = new UserLogin();

        courseInfo.setCourseId(userSignupRequest.getCourseId());
        userPersonalInfo.setCourseInfo(courseInfo);
        userPersonalInfo.setAlternateEmail(userSignupRequest.getAlternateEmail());
        userPersonalInfo.setEmail(userSignupRequest.getEmail());
        userPersonalInfo.setName(userSignupRequest.getName());
        userPersonalInfo.setPhoneNo(userSignupRequest.getPhoneNo());
        userLogin.setUserPersonalInfo(userPersonalInfo);
        userLogin.setPassword(userSignupRequest.getPassword());
        userLogin.setUserName(userSignupRequest.getUserName());

        userLoginRepository.save(userLogin);
        return "User Signed up successfully.";
    }

    @Override
    public String checkUsername(UserSignupRequest userSignupRequest){
        try {
            boolean user = userLoginRepository.existsById(userSignupRequest.getUserName());
            String msg = "";
            if (user)
                msg = "Username exists already.";
            return msg;
        }
        catch(Exception e){
            return "Username required.";
        }
    }

    @Override
    public String checkEmail(UserSignupRequest userSignupRequest){
        Boolean email = userPersonalInfoRepository.existsByEmail(userSignupRequest.getEmail());
        String msg = "";
        if(email){
            msg = "Email already exists.";
        }
        return msg;
    }

    @Override
    public String checkAlternateEmail(UserSignupRequest userSignupRequest){
        Boolean email = userPersonalInfoRepository.existsByAlternateEmail(userSignupRequest.getAlternateEmail());
        String msg = "";
        if(email){
            msg = "This Email already exists.";
        }
        return msg;
    }

    @Override
    public String checkPhoneNo(UserSignupRequest userSignupRequest){
        Boolean phone = userPersonalInfoRepository.existsByPhoneNo(userSignupRequest.getPhoneNo());
        String msg = "";
        if(phone){
            msg = "Phone No. already exists.";
        }
        return msg;
    }
}
