package com.collreach.userprofile.model.response;

import java.util.SortedMap;
import java.util.TreeMap;

public class UserProfileSkillsResponse {
    String profileAccessKey;
    SortedMap<String, Integer> skillsUpvote;

    public UserProfileSkillsResponse() {
        this.skillsUpvote = new TreeMap<>();
    }

    public UserProfileSkillsResponse(String profileAccessKey) {
        this.profileAccessKey = profileAccessKey;
        this.skillsUpvote = new TreeMap<>();
    }

    public String getProfileAccessKey() {
        return profileAccessKey;
    }

    public void setProfileAccessKey(String profileAccessKey) {
        this.profileAccessKey = profileAccessKey;
    }

    public SortedMap<String, Integer> getSkillsUpvote() {
        return skillsUpvote;
    }

    public void setSkillsUpvote(SortedMap<String, Integer> skillsUpvote) {
        this.skillsUpvote = skillsUpvote;
    }
}
