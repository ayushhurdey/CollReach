package com.collreach.userprofile.model.bo;

import javax.persistence.*;
import java.util.Set;

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

    @Column(name = "alternate_email")
    private String alternateEmail;

    @Column(name = "phone_no", unique = true)
    private String phoneNo;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private CourseInfo courseInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_skills",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "skill_id"))
    private Set<SkillsInfo> userSkills;


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

    public Set<SkillsInfo> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(Set<SkillsInfo> userSkills) {
        this.userSkills = userSkills;
    }
}
