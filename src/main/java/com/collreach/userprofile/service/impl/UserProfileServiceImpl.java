package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.model.bo.*;
import com.collreach.userprofile.model.repositories.SkillsInfoRepository;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.repositories.UserSkillsRepository;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.model.request.UsersFromSkillsRequest;
import com.collreach.userprofile.model.response.UsersSkillsResponse;
import com.collreach.userprofile.service.UserProfileService;
import com.collreach.userprofile.util.FtpUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    UserPersonalInfoRepository userPersonalInfoRepository;
    
    @Autowired
    UserSkillsRepository userSkillsRepository;

    @Autowired
    SkillsInfoRepository skillsInfoRepository;

    @Autowired
    private FtpUtil ftpUtil;

    @Value("${ftp.host-dir}")
    private String hostDir;

    @Override
    public String signup(UserSignupRequest userSignupRequest) {
        CourseInfo courseInfo = new CourseInfo();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        UserLogin userLogin = new UserLogin();
        String defaultProfilePhotoAddress = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\images\\default.jpeg";

        if(userSignupRequest.getEmail() != null) {
            courseInfo.setCourseId(userSignupRequest.getCourseId());
            userPersonalInfo.setCourseInfo(courseInfo);
            userPersonalInfo.setEmail(userSignupRequest.getEmail());
            userPersonalInfo.setName(userSignupRequest.getName());
            userPersonalInfo.setAlternateEmail(userSignupRequest.getAlternateEmail());
            userPersonalInfo.setPhoneNo(userSignupRequest.getPhoneNo());
            userPersonalInfo.setLinkedinLink(userSignupRequest.getLinkedinLink());
            userPersonalInfo.setDescription(userSignupRequest.getDescription());
            userPersonalInfo.setUserProfilePhoto(defaultProfilePhotoAddress);
            userLogin.setUserPersonalInfo(userPersonalInfo);
        }
        userLogin.setPassword(userSignupRequest.getPassword());
        userLogin.setUserName(userSignupRequest.getUserName());

        userLoginRepository.save(userLogin);
        return "User Signed up successfully.";
    }


    @Override
    public String updateUserPersonalInfo(UserSignupRequest userSignupRequest){
        CourseInfo courseInfo = new CourseInfo();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        String defaultProfilePhotoAddress = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\images\\default.jpeg";

        courseInfo.setCourseId(userSignupRequest.getCourseId());
        userPersonalInfo.setCourseInfo(courseInfo);
        userPersonalInfo.setEmail(userSignupRequest.getEmail());
        userPersonalInfo.setName(userSignupRequest.getName());
        userPersonalInfo.setAlternateEmail(userSignupRequest.getAlternateEmail());
        userPersonalInfo.setPhoneNo(userSignupRequest.getPhoneNo());
        userPersonalInfo.setLinkedinLink(userSignupRequest.getLinkedinLink());
        userPersonalInfo.setDescription(userSignupRequest.getDescription());
        userPersonalInfo.setUserProfilePhoto(defaultProfilePhotoAddress);
        //userLogin.setUserPersonalInfo(userPersonalInfo);
        String userName = userSignupRequest.getUserName();
        var userLogin = userLoginRepository.findById(userName);
        if(userLogin.isPresent()){
            userPersonalInfoRepository.save(userPersonalInfo);
            userLoginRepository.updateUserPersonalInfo(userName,userPersonalInfo);
            return "Profile updated Successfully.";
        }
       return "Some error occurred while updating user info.";
    }

    @Override
    public byte[] getImage(String filename) throws Exception {
        return ftpUtil.downloadFile(hostDir + filename);
    }

    @Override
    public String deleteUser(String userName){
        Optional<UserLogin> userLogin = userLoginRepository.findById(userName);
        if(userLogin.isPresent()) {
            UserPersonalInfo user = userLogin.get().getUserPersonalInfo();
            System.out.println("User id is :" + user.getUserId());
            userSkillsRepository.deleteByUserId(user);
            userLoginRepository.deleteById(userName);
            userPersonalInfoRepository.deleteByUserId(user.getUserId());
            return "deleted Successfully.";
        }
        else
            return "User not found.";
    }

    @Override
    public UsersSkillsResponse getUsersFromSkills(UsersFromSkillsRequest usersFromSkillsRequest){
        var list = usersFromSkillsRequest.getSkills();
        HashMap<String, ArrayList<String>> userSkillsMap = new HashMap<String, ArrayList<String>>();

        for(String skill: list){
            Optional<SkillsInfo> skillsInfo = skillsInfoRepository.findBySkill(skill);

            if(skillsInfo.isPresent()) {
                List<UserSkills> userSkills = userSkillsRepository.findAllBySkillId(skillsInfo.get());

                for (UserSkills userSkill : userSkills)
                    if (!userSkillsMap.containsKey(userSkill.getUserId().getName())) {
                        userSkillsMap.put(userSkill.getUserId().getName(), new ArrayList<String>());
                        userSkillsMap.get(userSkill.getUserId().getName()).add(userSkill.getSkillId().getSkill());
                    } else {
                        userSkillsMap.get(userSkill.getUserId().getName()).add(userSkill.getSkillId().getSkill());
                    }
            }
        }
        UsersSkillsResponse usersSkillsResponse = new UsersSkillsResponse();
        usersSkillsResponse.setUsersSkills(userSkillsMap);
        return usersSkillsResponse;
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
        Boolean altEmail = userPersonalInfoRepository.existsByAlternateEmail(userSignupRequest.getEmail());
        if(email || altEmail){
            return "Email already exists.";
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

    @Override
    public String checkLinkedinLink(UserSignupRequest userSignupRequest){
        Boolean linkedinLink = userPersonalInfoRepository.existsByLinkedinLink(userSignupRequest.getLinkedinLink());
        if(linkedinLink){
            return "This linkedin link already exists.";
        }
        return "";
    }
}
