package com.collreach.userprofile.model.response;

import java.util.*;

//public class UsersSkillsResponse {
//    private HashMap<String, ArrayList<String>> usersSkills;
//
//    public HashMap<String, ArrayList<String>> getUsersSkills() {
//        return usersSkills;
//    }
//
//    public void setUsersSkills(HashMap<String, ArrayList<String>> usersSkills) {
//        this.usersSkills = usersSkills;
//    }
//}
public class UsersSkillsResponse {
    private LinkedHashMap<String, UserProfileSkillsResponse> usersSkills;

    public LinkedHashMap<String, UserProfileSkillsResponse> getUsersSkills() {
        return usersSkills;
    }

    public void setUsersSkills(LinkedHashMap<String, UserProfileSkillsResponse> usersSkills) {
        this.usersSkills = usersSkills;
    }
}
