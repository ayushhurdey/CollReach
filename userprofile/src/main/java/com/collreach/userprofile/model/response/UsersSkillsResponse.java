package com.collreach.userprofile.model.response;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersSkillsResponse {
    private HashMap<String, ArrayList<String>> usersSkills;

    public HashMap<String, ArrayList<String>> getUsersSkills() {
        return usersSkills;
    }

    public void setUsersSkills(HashMap<String, ArrayList<String>> usersSkills) {
        this.usersSkills = usersSkills;
    }
}
