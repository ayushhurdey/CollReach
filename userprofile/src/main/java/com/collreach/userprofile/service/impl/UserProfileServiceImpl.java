package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.mappers.UserProfileMapper;
import com.collreach.userprofile.model.bo.*;
import com.collreach.userprofile.model.repositories.*;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.model.request.UsersFromNameRequest;
import com.collreach.userprofile.model.request.UsersFromSkillsRequest;
import com.collreach.userprofile.model.request.UsersFromUsernameRequest;
import com.collreach.userprofile.model.response.*;
import com.collreach.userprofile.service.UserProfileService;
import com.collreach.userprofile.util.FtpUtil;
import com.collreach.userprofile.util.HttpRequestUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    CourseInfoRepository courseInfoRepository;

    @Autowired
    private FtpUtil ftpUtil;

    @Autowired
    private HttpRequestUtil httpRequestUtil;

    @Value("${posts.url}")
    private String postsUrl;

    @Value("${chat.url}")
    private String chatAppUrl;

    @Value("${ftp.host-dir}")
    private String hostDir;

    @Value("${ftp.default-img}")
    private String defaultProfilePhotoAddress;

    @Value("${ftp.mini-default-img}")
    private String defaultMiniProfilePhotoAddress;

    private UserProfileMapper userProfileMapper = Mappers.getMapper( UserProfileMapper.class );

    // TODO: Same user multiple signup validation required
    @Override
    public String signup(UserSignupRequest userSignupRequest) {
//        CourseInfo courseInfo = new CourseInfo();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        UserLogin userLogin = new UserLogin();

        if(userSignupRequest.getEmail() != null) {
            CourseInfo courseInfo = extractCourseInfo(userSignupRequest);
            if(courseInfo == null) return "Error Signing up";
//            courseInfo.setCourseId(courseId);
            userPersonalInfo.setCourseInfo(courseInfo);
            userPersonalInfo.setEmail(userSignupRequest.getEmail().toLowerCase());
            userPersonalInfo.setName(userSignupRequest.getName().toLowerCase());
            userPersonalInfo.setAlternateEmail(userSignupRequest.getAlternateEmail().toLowerCase());
            userPersonalInfo.setPhoneNo(userSignupRequest.getPhoneNo());
            userPersonalInfo.setLinkedinLink(userSignupRequest.getLinkedinLink());
            userPersonalInfo.setDescription(userSignupRequest.getDescription());
            userPersonalInfo.setUserProfilePhoto(defaultProfilePhotoAddress);
            userPersonalInfo.setMiniUserProfilePhoto(defaultMiniProfilePhotoAddress);
            userPersonalInfo.setProfileAccessKey(userSignupRequest.getName().toLowerCase().replace(" ", "-") +
                    userSignupRequest.getUserName().hashCode());
            userLogin.setUserPersonalInfo(userPersonalInfo);
        }
        userLogin.setPassword(userSignupRequest.getPassword());
        userLogin.setUserName(userSignupRequest.getUserName());
        Boolean userAddedInPosts = httpRequestUtil.setNewUserAtUrl(userSignupRequest.getUserName(),
                                                                        userSignupRequest.getName(),postsUrl);
        Boolean userAddedInChat = httpRequestUtil.setNewUserAtUrl(userSignupRequest.getUserName(),
                userSignupRequest.getName(), chatAppUrl);

        if(!userAddedInChat || !userAddedInPosts)
            return "Error Signing up.";

        userLoginRepository.save(userLogin);
        return "User Signed up successfully.";
    }

    private CourseInfo extractCourseInfo(UserSignupRequest userSignupRequest){
        String branch = userSignupRequest.getBranch();
        String course = userSignupRequest.getCourseName();
        int semester = userSignupRequest.getSemester();
        Optional<CourseInfo> courseObj = courseInfoRepository.findByBranchAndCourseNameAndSemester(branch, course, semester);
        return courseObj.orElse(null);
    }

    @Override
    public String updateUserPersonalInfo(UserSignupRequest userSignupRequest){
//        CourseInfo courseInfo = new CourseInfo();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        CourseInfo courseInfo = extractCourseInfo(userSignupRequest);
        if(courseInfo == null) return "Error Signing up";

//        courseInfo.setCourseId(userSignupRequest.getCourseId());
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
    public InputStream getProfileImageByUsername(String username, Boolean ifMini) throws Exception {
        Optional<UserLogin> user =  userLoginRepository.findByUserName(username);
        Optional<UserPersonalInfo> userInfo;
        if(user.isPresent()){
            System.out.println("++++++++++++++++++++++++ " + user.get().getUserName() + " ++++++++++++++++++++++++++");
            userInfo = userPersonalInfoRepository.findById(user.get().getUserPersonalInfo().getUserId());
            String userProfilePhoto;
            if(ifMini)
                userProfilePhoto = userInfo.get().getMiniUserProfilePhoto();
            else
                userProfilePhoto = userInfo.get().getUserProfilePhoto();
            return ftpUtil.downloadFile(hostDir + userProfilePhoto);
        }
        else {
            return new InputStream() {
                @Override
                public int read() throws IOException {
                    return 0;
                }
            };
        }
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
            Optional<SkillsInfo> skillsInfo = skillsInfoRepository.findBySkillIgnoreCase(skill);

            if(skillsInfo.isPresent()) {
                List<UserSkills> userSkills = userSkillsRepository.findAllBySkillId(skillsInfo.get());

                for (UserSkills userSkill : userSkills) {
                    if (!userSkillsMap.containsKey(userSkill.getUserId().getName())) {
                        UserProfileSkillsResponse userProfileSkillsResponse = new UserProfileSkillsResponse();
                        userProfileSkillsResponse
                                .setProfileAccessKey(userSkill.getUserId().getProfileAccessKey());
                        String username = userLoginRepository.findByUserPersonalInfo(userSkill.getUserId()).get().getUserName();
                        userProfileSkillsResponse.setUsername(username);
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
        List<UserPersonalInfo> allUsersByName = userPersonalInfoRepository.findAllByNameStartsWithIgnoreCase(name);
        List<UsersInfo> usersByName = new ArrayList<UsersInfo>();

        for(UserPersonalInfo userPersonalInfo:  allUsersByName){
            String username = userLoginRepository.findByUserPersonalInfo(userPersonalInfo).get().getUserName();
            usersByName.add(new UsersInfo(userPersonalInfo.getName(),userPersonalInfo.getProfileAccessKey(), username));
        }

        return new UsersFromNameResponse(usersByName);
    }

    @Override
    public Map<String, Integer> getAllSkills() {
        Iterable<SkillsInfo> skillsInfoList = skillsInfoRepository.findAll();
        Map<String,Integer> allSkills =  new HashMap<>();
        skillsInfoList.forEach(skillsInfo -> allSkills.put(skillsInfo.getSkill(), skillsInfo.getSkillId()));
        return allSkills;
    }

    @Override
    public UserFromUsernameResponse getAllUsersFromUserNames(UsersFromUsernameRequest usersFromUsernameRequest) {
        Map<String, String> usersMap = new HashMap<>();
        usersFromUsernameRequest.getUsernames().forEach(username -> {
            Optional<UserLogin> user = userLoginRepository.findByUserName(username);

            user.ifPresent(u -> {
                Optional<UserPersonalInfo> userInfo  = userPersonalInfoRepository
                        .findById(u.getUserPersonalInfo().getUserId());
                usersMap.put(u.getUserName() , userInfo.get().getName());
            });

        });
        return new UserFromUsernameResponse(usersMap);
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
