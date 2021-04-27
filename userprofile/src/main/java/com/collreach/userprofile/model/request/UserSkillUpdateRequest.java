package com.collreach.userprofile.model.request;

import java.util.List;

public class UserSkillUpdateRequest {
    private String userName;
    private int skillId;
    private List<Integer> skills;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }
}
