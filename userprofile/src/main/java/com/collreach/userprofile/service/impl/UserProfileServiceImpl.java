package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.*;
import com.collreach.userprofile.model.repositories.SkillsInfoRepository;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.repositories.UserSkillsRepository;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.model.request.UsersFromNameRequest;
import com.collreach.userprofile.model.request.UsersFromSkillsRequest;
import com.collreach.userprofile.model.response.*;
import com.collreach.userprofile.service.UserProfileService;
import com.collreach.userprofile.util.FtpUtil;
import com.collreach.userprofile.util.HttpRequestUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private HttpRequestUtil httpRequestUtil;

    @Value("${posts.url}")
    private String postsUrl;

    @Value("${ftp.host-dir}")
    private String hostDir;

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    @Override
    public String signup(UserSignupRequest userSignupRequest) {
        CourseInfo courseInfo = new CourseInfo();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        UserLogin userLogin = new UserLogin();
        String defaultProfilePhotoAddress = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\images\\default.jpeg";

        if(userSignupRequest.getEmail() != null) {
            courseInfo.setCourseId(userSignupRequest.getCourseId());
            userPersonalInfo.setCourseInfo(courseInfo);
            userPersonalInfo.setEmail(userSignupRequest.getEmail().toLowerCase());
            userPersonalInfo.setName(userSignupRequest.getName().toLowerCase());
            userPersonalInfo.setAlternateEmail(userSignupRequest.getAlternateEmail().toLowerCase());
            userPersonalInfo.setPhoneNo(userSignupRequest.getPhoneNo());
            userPersonalInfo.setLinkedinLink(userSignupRequest.getLinkedinLink());
            userPersonalInfo.setDescription(userSignupRequest.getDescription());
            userPersonalInfo.setUserProfilePhoto(defaultProfilePhotoAddress);
            userPersonalInfo.setProfileAccessKey(userSignupRequest.getName().toLowerCase().replace(" ", "-") +
                    userSignupRequest.getUserName().hashCode());
            userLogin.setUserPersonalInfo(userPersonalInfo);
        }
        userLogin.setPassword(userSignupRequest.getPassword());
        userLogin.setUserName(userSignupRequest.getUserName());
        Boolean response = httpRequestUtil.setNewUserAtUrl(userSignupRequest.getUserName(), postsUrl);

        if(!response)
            return "Error Signing up.";

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
    public InputStream getImage(String filename) throws Exception {
        return ftpUtil.downloadFile(hostDir + filename);
    }

    @Override
    public UserPersonalInfoResponse getUserPersonalInfo(String profileAccessKey){
        Optional<UserPersonalInfo> user = userPersonalInfoRepository.findByProfileAccessKey(profileAccessKey);
        UserPersonalInfoResponse userPersonalInfoResponse = null;
        if(user.isPresent()){
            userPersonalInfoResponse = userProfileMapper.userPersonalInfoToUserPersonalInfoResponse(user.get());
            List<UserSkills> skills = userSkillsRepository.findAllByUserId(user.get());
            HashMap<String, Integer> skillMap = new HashMap<>();
            for(UserSkills userSkill: skills){
                skillMap.put(userSkill.getSkillId().getSkill(),userSkill.getSkillUpvoteCount());
            }
            userPersonalInfoResponse.setSkills(skillMap);
        }
        return userPersonalInfoResponse;
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

//    @Override
//    public UsersSkillsResponse getUsersFromSkills(UsersFromSkillsRequest usersFromSkillsRequest){
//        var list = usersFromSkillsRequest.getSkills();
//        HashMap<String, ArrayList<String>> userSkillsMap = new HashMap<String, ArrayList<String>>();
//
//        for(String skill: list){
//            Optional<SkillsInfo> skillsInfo = skillsInfoRepository.findBySkill(skill);
//
//            if(skillsInfo.isPresent()) {
//                List<UserSkills> userSkills = userSkillsRepository.findAllBySkillId(skillsInfo.get());
//
//                for (UserSkills userSkill : userSkills)
//                    if (!userSkillsMap.containsKey(userSkill.getUserId().getName())) {
//                        userSkillsMap.put(userSkill.getUserId().getName(), new ArrayList<String>());
//                        userSkillsMap.get(userSkill.getUserId().getName()).add(userSkill.getSkillId().getSkill());
//                    } else {
//                        userSkillsMap.get(userSkill.getUserId().getName()).add(userSkill.getSkillId().getSkill());
//                    }
//            }
//        }
//        UsersSkillsResponse usersSkillsResponse = new UsersSkillsResponse();
//        usersSkillsResponse.setUsersSkills(userSkillsMap);
//        return usersSkillsResponse;
//    }

    @Override
    public UsersSkillsResponse getUsersFromSkills(UsersFromSkillsRequest usersFromSkillsRequest){
        List<String> list = usersFromSkillsRequest.getSkills();
        LinkedHashMap<String, UserProfileSkillsResponse> userSkillsMap = new LinkedHashMap<>();
        LinkedHashMap<String, UserProfileSkillsResponse> userSkillsSortedMap = new LinkedHashMap<>();

        for(String skill: list){
            Optional<SkillsInfo> skillsInfo = skillsInfoRepository.findBySkill(skill);

            if(skillsInfo.isPresent()) {
                List<UserSkills> userSkills = userSkillsRepository.findAllBySkillId(skillsInfo.get());

                for (UserSkills userSkill : userSkills) {
                    if (!userSkillsMap.containsKey(userSkill.getUserId().getName())) {
                        UserProfileSkillsResponse userProfileSkillsResponse = new UserProfileSkillsResponse();
                        userProfileSkillsResponse
                                .setProfileAccessKey(userSkill.getUserId().getProfileAccessKey());
                        userProfileSkillsResponse
                                .getSkillsUpvote()
                                .put(userSkill.getSkillId().getSkill(), userSkill.getSkillUpvoteCount());
                        userSkillsMap.put(userSkill.getUserId().getName(), userProfileSkillsResponse);
                    } else {
                        userSkillsMap
                                .get(userSkill.getUserId().getName()).getSkillsUpvote()
                                .put(userSkill.getSkillId().getSkill(), userSkill.getSkillUpvoteCount());
                    }
                }
            }
        }
        Comparator<UserProfileSkillsResponse> bySkillsUpvote = Comparator.
                comparingInt((UserProfileSkillsResponse u) -> u.getSkillsUpvote().values().stream()
                        .mapToInt(Integer::valueOf)
                        .sum());

        Comparator<UserProfileSkillsResponse> bySkillsSize =
                Comparator.comparingInt((UserProfileSkillsResponse o) -> o.getSkillsUpvote().size())
                        .thenComparing(bySkillsUpvote).reversed();

        userSkillsMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(bySkillsSize))
                .forEachOrdered(x ->
                    userSkillsSortedMap.put(x.getKey(),x.getValue())
                );

        for(Map.Entry<String, UserProfileSkillsResponse> userProfileSkills: userSkillsSortedMap.entrySet()){
                var sortedSkillsMap =
                        userProfileSkills.getValue().getSkillsUpvote()
                        .entrySet()
                        .stream()
                        .sorted((i1, i2) -> i2.getValue().compareTo(i1.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
                userProfileSkills.getValue().setSkillsUpvote(sortedSkillsMap);
        }

        UsersSkillsResponse usersSkillsResponse = new UsersSkillsResponse();
        usersSkillsResponse.setUsersSkills(userSkillsSortedMap);
        userSkillsMap = null;
        return usersSkillsResponse;
    }

    @Override
    public UsersFromNameResponse getUsersFromName(UsersFromNameRequest usersFromNameRequest) {
        String name = usersFromNameRequest.getUsers();
        List<UserPersonalInfo> allUsersByName = userPersonalInfoRepository.findAllByNameStartsWith(name);
        List<UsersInfo> usersByName = new ArrayList<UsersInfo>();

        for(UserPersonalInfo userPersonalInfo:  allUsersByName){
            usersByName.add(new UsersInfo(userPersonalInfo.getName(),userPersonalInfo.getProfileAccessKey()));
        }

        return new UsersFromNameResponse(usersByName);
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
