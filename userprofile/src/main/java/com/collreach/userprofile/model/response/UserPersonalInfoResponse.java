package com.collreach.userprofile.model.response;



import java.util.Map;

public class UserPersonalInfoResponse {
    private String userName;
    private String email;
    private String name;
    private String alternateEmail;
    private String phoneNo;
    private String linkedinLink;
    private String description;
    private String userProfilePhoto;
    private String profileAccessKey;
    private CourseInfoResponse courseInfoResponse;
    private Map<String,Integer> skills;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public CourseInfoResponse getCourseInfoResponse() {
        return courseInfoResponse;
    }

    public void setCourseInfoResponse(CourseInfoResponse courseInfoResponse) {
        this.courseInfoResponse = courseInfoResponse;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserProfilePhoto() {
        return userProfilePhoto;
    }

    public void setUserProfilePhoto(String userProfilePhoto) {
        this.userProfilePhoto = userProfilePhoto;
    }

    public String getProfileAccessKey() {
        return profileAccessKey;
    }

    public void setProfileAccessKey(String profileAccessKey) {
        this.profileAccessKey = profileAccessKey;
    }
}
