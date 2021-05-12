package com.collreach.posts.model.response;

import com.collreach.posts.model.response.PollAnswersResponse;

import java.util.Date;
import java.util.List;

public class MessageResponse {

    private String visibility;
    private int lifetimeInWeeks;
    private int recurrences;
    private Date uploadTime;
    private Date createDate;

    /*------Posts------*/
    private String filename;
    private String filetype;
    private byte[] image;
    private int likes;
    private int views;
    private String message;

    /*------Polls------*/
    private String question;
    private List<PollAnswersResponse> answers;

    public MessageResponse() {
    }

    public MessageResponse(String filename, String filetype, byte[] image) {
        this.filename = filename;
        this.filetype = filetype;
        this.image = image;
    }

    public MessageResponse(String filename, String filetype,
                           byte[] image, String visibility,
                           int lifetimeInWeeks, int recurrences,
                           int likes, int views, String message,
                           Date uploadTime, Date createDate) {
        this.filename = filename;
        this.filetype = filetype;
        this.image = image;
        this.visibility = visibility;
        this.lifetimeInWeeks = lifetimeInWeeks;
        this.recurrences = recurrences;
        this.likes = likes;
        this.views = views;
        this.message = message;
        this.uploadTime = uploadTime;
        this.createDate = createDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<PollAnswersResponse> getAnswers() {
        return answers;
    }

    public void setAnswers(List<PollAnswersResponse> answers) {
        this.answers = answers;
    }
}
