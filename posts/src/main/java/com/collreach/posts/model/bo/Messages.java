package com.collreach.posts.model.bo;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    @Column(name = "image", nullable = false, columnDefinition="mediumblob")
    private byte[] image;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filetype", nullable = false)
    private String filetype;


    public Messages() {
    }

    public Messages(byte[] image, String filename, String filetype) {
        this.image = image;
        this.filename = filename;
        this.filetype = filetype;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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
