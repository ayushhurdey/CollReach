package com.collreach.posts.model.response;

public class ImageResponse {
    private String filename;
    private String filetype;
    private byte[] image;

    public ImageResponse(String filename, String filetype, byte[] image) {
        this.filename = filename;
        this.filetype = filetype;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
