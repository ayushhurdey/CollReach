package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.*;
import com.collreach.userprofile.model.repositories.*;
import com.collreach.userprofile.model.request.UserInfoUpdateRequest;
import com.collreach.userprofile.model.request.UserLoginUpdateRequest;
import com.collreach.userprofile.model.request.UserSkillUpdateRequest;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.service.UserInfoUpdateService;
import com.collreach.userprofile.service.UserLoginService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class UserInfoUpdateServiceImpl implements UserInfoUpdateService {
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    UserLoginRepository userLoginRepository;
    @Autowired
    UserPersonalInfoRepository userPersonalInfoRepository;
    @Autowired
    CourseInfoRepository courseInfoRepository;
    @Autowired
    UserSkillsRepository userSkillsRepository;

    @Value("${images.server-address}")
    private String imgServerAddress;

    @Value("${images.default-image}")
    private String defaultImgAddress;

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    /*
    @Override
    public String updateSkills(UserInfoUpdateRequest userInfoUpdateRequest){
        UserLogin user = userLoginRepository.findById(userInfoUpdateRequest.getUserName()).get();
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        int userId = userPersonalInfo.getUserId();
        Set<SkillsInfo> skills = new HashSet<>();
        for(int skillId : userInfoUpdateRequest.getSkills()){
            SkillsInfo skillsInfo = new SkillsInfo();
            skillsInfo.setSkillId(skillId);
            skills.add(skillsInfo);
        }
        userPersonalInfo.setUserSkills(skills);
        userPersonalInfoRepository.save(userPersonalInfo);
        return "Updated Skills successfully.";
    }*/

    @Override
    public String updateSkills(UserSkillUpdateRequest userSkillUpdateRequest){
        Optional<UserLogin> userLogin = userLoginRepository.findById(userSkillUpdateRequest.getUserName());
        UserLogin user;
        if(!userLogin.isPresent()){
            return "Invalid details provided.";
        }
        user = userLogin.get();
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        for(int skillId : userSkillUpdateRequest.getSkills()){
            SkillsInfo skillsInfo = new SkillsInfo();
            UserSkills userSkills = new UserSkills();
            skillsInfo.setSkillId(skillId);
            Optional<UserSkills> oldSkills = userSkillsRepository
                    .findById(new UserSkillsKey(userPersonalInfo.getUserId(),skillsInfo.getSkillId()));
            if(!oldSkills.isPresent()){
                userSkills.setUserId(userPersonalInfo);
                userSkills.setSkillId(skillsInfo);
                userSkills.setSkillUpvoteCount(0);
                userSkillsRepository.save(userSkills);
            }
        }
        return "Updated Skills successfully.";
    }

    @Override
    public String updateSkillUpvoteCount(UserSkillUpdateRequest userSkillUpdateRequest){
        int skillId = userSkillUpdateRequest.getSkillId();
        Optional<UserLogin> userLogin = userLoginRepository.findById(userSkillUpdateRequest.getUserName());
        if(userLogin.isPresent()){
            UserPersonalInfo userPersonalInfo = userLogin.get().getUserPersonalInfo();
            UserSkills userSkills = userSkillsRepository
                    .findById(new UserSkillsKey( userPersonalInfo.getUserId(), skillId )).get();
            int skillUpvoteCounts = userSkills.getSkillUpvoteCount() + 1;
            SkillsInfo skillsInfo = new SkillsInfo();
            skillsInfo.setSkillId(skillId);
            userSkillsRepository.updateByUserIdAndSkillId(userPersonalInfo, skillsInfo, skillUpvoteCounts);
            return "skill upvote count increased by 1.";
        }
        return "Something went wrong.";
    }

    @Override
    public String deleteUserSkills(UserSkillUpdateRequest userSkillUpdateRequest){
        Optional<UserLogin> userLogin = userLoginRepository.findById(userSkillUpdateRequest.getUserName());
        if(userLogin.isPresent()) {
            for(int skillId : userSkillUpdateRequest.getSkills()){
                userSkillsRepository.deleteById(new UserSkillsKey( userLogin.get().getUserPersonalInfo().getUserId(), skillId ));
            }
            return "skills deleted successfully.";
        }
        return "Something went wrong.";
    }

    @Override
    public String updateEmail(UserInfoUpdateRequest userInfoUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));

        UserLoginResponse user = userLoginService.getUserDetails(userInfoUpdateRequest.getUserName());
        if(user != null) {
            String oldEmail = user.getUserPersonalInfoResponse().getEmail();
            userPersonalInfoRepository.updateEmail(oldEmail, userInfoUpdateRequest.getEmail());
            return "Updated Email.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updateAlternateEmail(UserInfoUpdateRequest userInfoUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        UserLoginResponse user = userLoginService.getUserDetails(userInfoUpdateRequest.getUserName());
        if(user != null) {
            String email = user.getUserPersonalInfoResponse().getEmail();
            userPersonalInfoRepository.updateAlternateEmail(email, userInfoUpdateRequest.getAlternateEmail());
            return "Updated Alternate Email.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updatePhoneNo(UserInfoUpdateRequest userInfoUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        UserLoginResponse user = userLoginService.getUserDetails(userInfoUpdateRequest.getUserName());
        if(user != null) {
            String email = user.getUserPersonalInfoResponse().getEmail();
            userPersonalInfoRepository.updatePhoneNo(email, userInfoUpdateRequest.getPhoneNo());
            return "Updated Phone No.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updateCourseInfo(UserInfoUpdateRequest userInfoUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        UserLoginResponse user = userLoginService.getUserDetails(userInfoUpdateRequest.getUserName());
        if(user != null) {
            String email = user.getUserPersonalInfoResponse().getEmail();
            CourseInfo courseInfo = courseInfoRepository.findById(userInfoUpdateRequest.getCourseId()).orElse(null);
            if(courseInfo != null) {
                userPersonalInfoRepository.updateCourseId(email, courseInfo);
                return "Updated Course.";
            }
            return "Course Info invalid.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updatePassword(UserLoginUpdateRequest userLoginUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userLoginUpdateRequestToUserLoginRequest(userLoginUpdateRequest));
        UserLoginResponse user = userLoginService.getUserDetails(userLoginUpdateRequest.getUserName());
        if(user != null) {
            String username = user.getUserName();
            String newPassword = userLoginUpdateRequest.getNewPassword();

            if(user.getPassword().equals(newPassword))
                return "Try password other than previous one.";

            if(!newPassword.equals("")){
                userLoginRepository.updatePassword(username, newPassword);
                return "Password updated";
            }
            return "Invalid new Password.";
        }
        return "Invalid credentials.";
    }

    @Override
    public String updateUserName(UserLoginUpdateRequest userLoginUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userLoginUpdateRequestToUserLoginRequest(userLoginUpdateRequest));
        UserLoginResponse user = userLoginService.getUserDetails(userLoginUpdateRequest.getUserName());
        if(user != null) {
            String username = user.getUserName();
            String newUserName = userLoginUpdateRequest.getNewUserName();

            if(username.equals(newUserName))
                return "Try username other than previous one.";

            if(!newUserName.equals("")){
                userLoginRepository.updateUserName(username, newUserName);
                return "Username updated.";
            }
            return "Invalid new username.";
        }
        return "Invalid credentials.";
    }

    @Override
    public String updateLinkedinLink(UserInfoUpdateRequest userInfoUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        UserLoginResponse user = userLoginService.getUserDetails(userInfoUpdateRequest.getUserName());
        if(user != null) {
            String email = user.getUserPersonalInfoResponse().getEmail();
            userPersonalInfoRepository.updateLinkedinLink(email, userInfoUpdateRequest.getLinkedinLink());
            return "Updated LinkedIn Link.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updateDescription(UserInfoUpdateRequest userInfoUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        UserLoginResponse user = userLoginService.getUserDetails(userInfoUpdateRequest.getUserName());
        if(user != null) {
            String email = user.getUserPersonalInfoResponse().getEmail();
            userPersonalInfoRepository.updateDescription(email, userInfoUpdateRequest.getDescription());
            return "Updated Description Successfully.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updateProfilePhoto(MultipartFile file, String userName) throws IOException{
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf('.');
        boolean userNameExists = userLoginRepository.existsById(userName);

        /* -------------max upload size exception has to be handled.---------------- */

        System.out.println("file size : " + ((float)(file.getSize()/1024)/1024) + "MB");
        if(index > 0 && userNameExists) {
            String extension = fileName.substring(index + 1);
            System.out.println("File extension is " + extension);
            String ts = "";
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                ts = String.valueOf(timestamp.getTime());
                System.out.println("Current Timestamp: " + ts);

                String photoStorageAddress = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\images\\"
                        + userName + "$" + ts + "." + extension;
                // photos in db has to be stored as "localhost:8081/images/default.jpeg"
                UserPersonalInfo userPersonalInfo = userLoginRepository.findById(userName).get().getUserPersonalInfo();

                if(userPersonalInfo != null){
                    String email = userPersonalInfo.getEmail();
                    file.transferTo(new File(photoStorageAddress));   // needs to be changed to a server address..
                    userPersonalInfoRepository.updateUserProfilePhoto(email, imgServerAddress + userName + "$" + ts + "." + extension);
                }
                else return "Please update your personal info first.";
            }catch(Exception e){
                return e.getLocalizedMessage();
            }
            return "Profile picture updated successfully. $" + ts + "." + extension;
        }
        return "Either username is invalid or file format is not supported.";
    }

    @Override
    public String deleteUserProfilePhoto(String userName){
        String defaultAddress = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\images\\default.jpeg";
        AtomicReference<String> msg = new AtomicReference<>("");
        userLoginRepository.findById(userName).ifPresentOrElse(
                (y) -> {
                        userPersonalInfoRepository.updateUserProfilePhoto(y.getUserPersonalInfo().getEmail(),defaultImgAddress);
                        msg.set("Profile photo set as default.");
                        },
                () -> msg.set("Some Error Occurred."));
        return msg.get();
    }
}
