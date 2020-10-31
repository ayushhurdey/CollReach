package com.collreach.userprofile.model.bo;

import javax.persistence.*;

@Entity
public class UserExtras {
    @Id
    @Column(name = "other_email")
    private String otherEmail;

    @Column(name = "phone_no", unique = true)
    private String phoneNo;

    @OneToOne
    @JoinColumn(name = "user_name", referencedColumnName = "userName")
    private User user;


    public String getOtherEmail() { return otherEmail; }

    public void setOtherEmail(String otherEmail) { this.otherEmail = otherEmail; }

    public String getPhoneNo() { return phoneNo; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
