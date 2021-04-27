package com.collreach.userprofile.model.request;

import java.util.List;

public class UsersFromSkillsRequest {
    private List<String> skills;

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
