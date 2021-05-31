package com.collreach.posts.service;

import com.collreach.posts.model.requests.CreatePostRequest;
import com.collreach.posts.model.response.MessageResponse;
import com.collreach.posts.model.response.MessagesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashSet;

public interface PostService {

    public MessagesResponse getImages(String numberOfImages) throws IOException;
    public MessagesResponse getRandomImage() throws IOException;
    public String uploadImage(MultipartFile file, String userName, String messageId);
    public String createPost(CreatePostRequest createPostRequest);
    public MessagesResponse getAllPosts();
    public MessagesResponse getPostsByPagination(Integer pageNo, Integer pageSize);
    public MessagesResponse getPostsAndPollsPaginationFilteredByVisibility(Integer pageNo, Integer pageSize, String visibility);
    public LinkedHashSet<MessageResponse> mergeSets(LinkedHashSet<MessageResponse> posts, LinkedHashSet<MessageResponse> polls);
    String updatePostViews(String userName, int messageId);
    String updatePostLikes(String userName, int messageId);
    int getTotalLikesOnMessage(int messageId);
    MessagesResponse getPostsByUsername(String username);
    String deletePost(int messageId, String userName);
    MessagesResponse getTodayFeed(String username);
    String deleteExpiredPosts(String username);
}
