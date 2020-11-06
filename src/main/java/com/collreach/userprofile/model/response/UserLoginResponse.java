package com.collreach.userprofile.model.response;


public class UserLoginResponse {
    private String userName;
    private String password;
    private UserPersonalInfoResponse userPersonalInfoResponse;

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

    public UserPersonalInfoResponse getUserPersonalInfoResponse() {
        return userPersonalInfoResponse;
    }

    public void setUserPersonalInfoResponse(UserPersonalInfoResponse userPersonalInfoResponse) {
        this.userPersonalInfoResponse = userPersonalInfoResponse;
    }
}
