package com.collreach.userprofile.model.bo;

import javax.persistence.*;

@Entity
public class UserLogin {
    @Id
    @Column(name = "user_name")
    private String userName;

    /*@Column(name = "user_id", nullable = false, unique = true)
    private int userId;
*/
    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserPersonalInfo userPersonalInfo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
/*
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
*/
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserPersonalInfo getUserPersonalInfo() {
        return userPersonalInfo;
    }

    public void setUserPersonalInfo(UserPersonalInfo userPersonalInfo) {
        this.userPersonalInfo = userPersonalInfo;
    }
}
