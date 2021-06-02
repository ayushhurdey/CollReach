package com.collreach.posts.model.response;

public class LikesAndViewsUpdateResponse {
    private int messageId;
    private int likes;
    private int views;
    private long votes;

    public LikesAndViewsUpdateResponse(int messageId, int likes, int views) {
        this.messageId = messageId;
        this.likes = likes;
        this.views = views;
    }

    public LikesAndViewsUpdateResponse(int messageId, long votes) {
        this.messageId = messageId;
        this.votes = votes;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }
}
