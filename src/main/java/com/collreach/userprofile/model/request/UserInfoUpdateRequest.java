package com.collreach.userprofile.model.request;

import java.util.List;

public class UserInfoUpdateRequest extends UserSignupRequest{
    private List<Integer> skills;

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }
}
