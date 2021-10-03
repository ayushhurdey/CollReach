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
import com.collreach.userprofile.util.FtpUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private FtpUtil ftpUtil;

    @Value("${ftp.host-dir}")
    private String hostDir;

    @Value("${ftp.img-address-server}")
    private String imgAddressServer;

    @Value("${ftp.default-img}")
    private String defaultImg;

    private final UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    public UserInfoUpdateServiceImpl() {
    }

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
        if(!userLogin.isPresent() || userSkillUpdateRequest.getSkills().size() == 0){
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

    // TODO : Bug-correction - To be improved so that upvote can be done by a user only once.
    // new Table(skill_id, user_id, ifUpvoted) has to be made to handle this error.
    @Override
    public String updateSkillUpvoteCount(UserSkillUpdateRequest userSkillUpdateRequest){
        int skillId = userSkillUpdateRequest.getSkillId();
        Optional<UserLogin> userLogin = userLoginRepository.findById(userSkillUpdateRequest.getUserName());
        if(userLogin.isPresent()){
            UserPersonalInfo userPersonalInfo = userLogin.get().getUserPersonalInfo();

//            UserSkills userSkills = userSkillsRepository
//                    .findById(new UserSkillsKey( userPersonalInfo.getUserId(), skillId )).get();
//            int skillUpvoteCounts = userSkills.getSkillUpvoteCount() + 1;
//            SkillsInfo skillsInfo = new SkillsInfo();
//            skillsInfo.setSkillId(skillId);
//            userSkillsRepository.updateByUserIdAndSkillId(userPersonalInfo, skillsInfo, skillUpvoteCounts);
//            return "skill upvote count increased by 1.";

            AtomicReference<String> result = new AtomicReference<>("");
            userSkillsRepository.findById(new UserSkillsKey( userPersonalInfo.getUserId(), skillId ))
                    .ifPresentOrElse(userSkills -> {
                        SkillsInfo skillsInfo = new SkillsInfo();
                        skillsInfo.setSkillId(skillId);
                        userSkillsRepository.updateByUserIdAndSkillId(userPersonalInfo, skillsInfo, userSkills.getSkillUpvoteCount()+1);
                        result.set("Upvote Successful");
                    }, () -> {
                        try {
                            throw new Exception("User's skill not found");
                        } catch (Exception e) {
                            e.printStackTrace();
                            result.set("User's skill not found");
                        }
                    });
            return result.toString();
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
        try {
            UserLoginResponse user = userLoginService.getUserDetails(userInfoUpdateRequest.getUserName());
            if (user != null && userInfoUpdateRequest.getPhoneNo() == null) {
                String email = user.getUserPersonalInfoResponse().getEmail();
                userPersonalInfoRepository.updatePhoneNo(email, userInfoUpdateRequest.getPhoneNo());
                return "Updated Phone No.";
            }
            return "Invalid credentials..";
        }catch (Exception e) {
            return "Phone No invalid";
        }
    }

//    @Override
//    public String updateCourseInfo(UserInfoUpdateRequest userInfoUpdateRequest){
//        //UserLoginResponse user = userLoginService.login(
//        //        userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
//        UserLoginResponse user = userLoginService.getUserDetails(userInfoUpdateRequest.getUserName());
//        if(user != null) {
//            String email = user.getUserPersonalInfoResponse().getEmail();
//            CourseInfo courseInfo = courseInfoRepository.findById(userInfoUpdateRequest.getCourseId()).orElse(null);
//            if(courseInfo != null) {
//                userPersonalInfoRepository.updateCourseId(email, courseInfo);
//                return "Updated Course.";
//            }
//            return "Course Info invalid.";
//        }
//        return "Invalid credentials..";
//    }

    @Override
    public String updateCourseInfo(int currentSemester, String username){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userInfoUpdateRequestToUserLoginRequest(userInfoUpdateRequest));
        Optional<UserLogin> user = userLoginRepository.findByUserName(username);

        if(user.isPresent()) {
            String email = user.get().getUserPersonalInfo().getEmail();
            CourseInfo courseInfo = user.get().getUserPersonalInfo().getCourseInfo();
            if(courseInfo != null) {
                int previousSemester = courseInfo.getSemester();
                String branch = courseInfo.getBranch();
                String courseName = courseInfo.getCourseName();
                if(currentSemester > previousSemester){
                    Optional<CourseInfo> newCourse = courseInfoRepository.findByBranchAndCourseNameAndSemester(branch, courseName, currentSemester);
                    newCourse.ifPresent(info -> userPersonalInfoRepository.updateCourseId(email, info));
                    return "Updated Course.";
                }
            }
            return "Course Info invalid.";
        }
        return "Invalid credentials..";
    }

    @Override
    public String updatePassword(UserLoginUpdateRequest userLoginUpdateRequest){
        //UserLoginResponse user = userLoginService.login(
        //        userProfileMapper.userLoginUpdateRequestToUserLoginRequest(userLoginUpdateRequest));
//        UserLoginResponse user = userLoginService.getUserDetails(userLoginUpdateRequest.getUserName());
        Optional<UserLogin> user = userLoginRepository.findByUserName(userLoginUpdateRequest.getUserName());
        if(user.isPresent()) {
            String username = user.get().getUserName();
            String newPassword = userLoginUpdateRequest.getNewPassword();
            String currentPassword = user.get().getPassword();

            if(!currentPassword.equals(userLoginUpdateRequest.getPassword()))
                return "Current password does not match.";
            else if(currentPassword.equals(newPassword))
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
    public String updateProfilePhoto(MultipartFile file, MultipartFile miniFile, String userName) throws IOException{
        String fileName = file.getOriginalFilename();
        assert fileName != null;
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
                String filename = userName + "$" + ts + "." + extension;
                String photoStorageAddress = imgAddressServer + "/" + filename;
                UserPersonalInfo userPersonalInfo = userLoginRepository.findById(userName).get().getUserPersonalInfo();

                if(userPersonalInfo != null){
                    String email = userPersonalInfo.getEmail();
                    deleteUserProfilePhoto(userName);
                    ftpUtil.ftpUpload(file, hostDir + filename);
                    ftpUtil.ftpUpload(miniFile, hostDir + "mini_" + filename);
                    userPersonalInfoRepository.updateMiniUserProfilePhoto(email, "mini_" + filename);
                    userPersonalInfoRepository.updateUserProfilePhoto(email, filename);
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
        //String defaultAddress = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\images\\default.jpeg";
        AtomicReference<String> msg = new AtomicReference<>("");
        userLoginRepository.findById(userName).ifPresentOrElse(
                (y) -> {
                        String filename = y.getUserPersonalInfo().getUserProfilePhoto()
                                .replace(imgAddressServer + "/","");
                        String miniFilename = y.getUserPersonalInfo().getMiniUserProfilePhoto()
                                .replace(imgAddressServer + "/","");
                        System.out.println(filename);
                        System.out.println(miniFilename);
                        //address = defaultAddress.replace("default.jpeg",address);
                        //File imgFile = new File(address);
                        String message = "";
                        if(!filename.contains(defaultImg)) {
                            message = ftpUtil.deletingFile(filename);
                            ftpUtil.deletingFile(miniFilename);
                        }
                        if(message.contains("deleted Successfully"))
                            System.out.println(filename + "   File deleted");
                        else System.out.println("File " + message);
                        if(!message.contains("Could not connect"))
                            userPersonalInfoRepository.updateUserProfilePhoto(y.getUserPersonalInfo().getEmail(),
                                   imgAddressServer + "/" + defaultImg);
                        msg.set("Profile photo set as default.");
                        },
                () -> msg.set("Some Error Occurred."));
        return msg.get();
    }
}
