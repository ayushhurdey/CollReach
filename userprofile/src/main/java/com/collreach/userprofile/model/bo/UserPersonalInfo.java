package com.collreach.userprofile.model.bo;

import javax.persistence.*;

@Entity
public class UserPersonalInfo {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String profileAccessKey;

    @Column(name = "alternate_email")
    private String alternateEmail;

    @Column(name = "phone_no", unique = true)
    private String phoneNo;

    @Column(name = "linkedin_link", unique = true)
    private String linkedinLink;

    @Column(name = "description")
    private String description;

    @Column(name = "user_profile_photo")
    private String userProfilePhoto;

    @Column(name = "mini_user_profile_photo")
    private String miniUserProfilePhoto;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private CourseInfo courseInfo;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_skills",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "skill_id"))
    private Set<SkillsInfo> userSkills;
     */


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getProfileAccessKey() {
        return profileAccessKey;
    }

    public void setProfileAccessKey(String profileAccessKey) {
        this.profileAccessKey = profileAccessKey;
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

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
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

    public String getMiniUserProfilePhoto() {
        return miniUserProfilePhoto;
    }

    public void setMiniUserProfilePhoto(String miniUserProfilePhoto) {
        this.miniUserProfilePhoto = miniUserProfilePhoto;
    }
}
