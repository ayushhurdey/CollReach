package com.collreach.posts.model.response;

import java.util.Collections;
import java.util.HashSet;

public class MessagesResponse {
    HashSet<MessageResponse> postsSet;

    public MessagesResponse(HashSet<MessageResponse> postsSet) {
        this.postsSet = postsSet;
    }

    public MessagesResponse(MessageResponse messageResponse) {
        this.postsSet = new HashSet<>(Collections.singletonList(messageResponse));
    }

    public HashSet<MessageResponse> getPostsSet() {
        return postsSet;
    }

    public void setPostsSet(HashSet<MessageResponse> postsSet) {
        this.postsSet = postsSet;
    }
}
