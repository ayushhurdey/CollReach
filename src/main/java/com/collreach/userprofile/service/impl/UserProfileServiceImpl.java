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
    public String deleteUser(String userName){
        int userId = userLoginRepository.findById(userName).get().getUserPersonalInfo().getUserId();
        System.out.println("User id is :" + userId);
        userLoginRepository.deleteById(userName);
        userPersonalInfoRepository.deleteByUserId(userId);
        return "deleted Successfully.";
    }

    @Override
    public String checkUsername(UserSignupRequest userSignupRequest){
        try {
            boolean user = userLoginRepository.existsById(userSignupRequest.getUserName());
            if (user)
                return "Username exists already.";
            return "";
        }
        catch(Exception e){
            return "Username required.";
        }
    }

    @Override
    public String checkEmail(UserSignupRequest userSignupRequest){
        Boolean email = userPersonalInfoRepository.existsByEmail(userSignupRequest.getEmail());
        if(email){
            return "Email already exists.";
        }
        return "";
    }

    @Override
    public String checkAlternateEmail(UserSignupRequest userSignupRequest){
        Boolean email = userPersonalInfoRepository.existsByAlternateEmail(userSignupRequest.getAlternateEmail());
        if(email){
            return "This Email already exists.";
        }
        return "";
    }

    @Override
    public String checkPhoneNo(UserSignupRequest userSignupRequest){
        Boolean phone = userPersonalInfoRepository.existsByPhoneNo(userSignupRequest.getPhoneNo());
        if(phone){
            return "Phone No. already exists.";
        }
        return "";
    }
}
