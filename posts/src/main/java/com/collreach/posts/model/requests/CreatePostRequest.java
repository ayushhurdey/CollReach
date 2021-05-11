package com.collreach.posts.model.requests;

import java.util.Date;

public class CreatePostRequest {
    private String userName;
    private String visibility;
    private Date createDate;
    private Date uploadTime;
    private int lifetimeInWeeks;
    private int recurrences;
    private String message;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String userName, String visibility,
                             Date createDate, Date uploadTime,
                             int lifetimeInWeeks, int recurrences, String message) {
        this.userName = userName;
        this.visibility = visibility;
        this.createDate = createDate;
        this.uploadTime = uploadTime;
        this.lifetimeInWeeks = lifetimeInWeeks;
        this.recurrences = recurrences;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getLifetimeInWeeks() {
        return lifetimeInWeeks;
    }

    public void setLifetimeInWeeks(int lifetimeInWeeks) {
        this.lifetimeInWeeks = lifetimeInWeeks;
    }

    public int getRecurrences() {
        return recurrences;
    }

    public void setRecurrences(int recurrences) {
        this.recurrences = recurrences;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
