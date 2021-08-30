package com.collreach.posts.model.bo.posts;

import com.collreach.posts.model.bo.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@DynamicUpdate
@Table(name = "messages")
public class Messages {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date createDate;

    @Column(name = "upload_time", nullable = false)
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm:ss")
    private Date uploadTime;

    @Column(name = "visibility", nullable = false, columnDefinition = "varchar(15) default 'college'")
    private String visibility;   // Specifies to Department or College level visibility

    @Column(name = "lifetime_in_weeks", nullable = false)
    private int lifetimeInWeeks;

    @Column(name = "recurrences", nullable = false)
    private int recurrences;

    @Column(name = "likes", columnDefinition = "integer default 0")
    private Integer likes;

    @Column(name = "views", columnDefinition = "integer default 0")
    private Integer views;

    @Column(name = "message", nullable = false)
    private String message;

    //@Column(name = "image", columnDefinition="mediumblob")
    @Column(name = "image", columnDefinition="bytea")
    private byte[] image;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filetype", length = 10)
    private String filetype;


    public Messages() {
    }

    public Messages(byte[] image, String filename, String filetype) {
        this.image = image;
        this.filename = filename;
        this.filetype = filetype;
    }

    public Messages(Users userId, Date createDate,
                    Time uploadTime, String visibility,
                    int lifetimeInWeeks, int recurrences,
                    int likes, int views, String message,
                    byte[] image, String filename, String filetype) {
        this.userId = userId;
        this.createDate = createDate;
        this.uploadTime = uploadTime;
        this.visibility = visibility;
        this.lifetimeInWeeks = lifetimeInWeeks;
        this.recurrences = recurrences;
        this.likes = likes;
        this.views = views;
        this.message = message;
        this.image = image;
        this.filename = filename;
        this.filetype = filetype;
    }

    @PrePersist
    public void setLikesAndViewsOnNewMessage(){
        this.likes = 0;
        this.views = 0;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Users getUserName() {
        return userId;
    }

    public void setUserName(Users userId) {
        this.userId = userId;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
}
