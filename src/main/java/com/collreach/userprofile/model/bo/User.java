package com.collreach.userprofile.model.bo;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private int id;                                      // unique id

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private int yearOfStudy;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private String course;

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getYearOfStudy() { return yearOfStudy; }

    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBranch() { return branch; }

    public void setBranch(String branch) { this.branch = branch; }

    public String getCourse() { return course; }

    public void setCourse(String course) { this.course = course; }
}
