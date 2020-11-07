package com.collreach.userprofile.model.response;


import java.util.List;

public class UserPersonalInfoResponse {
    private String email;
    private String name;
    private String alternateEmail;
    private String phoneNo;
    private CourseInfoResponse courseInfoResponse;
    private List<String> skills;

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

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
