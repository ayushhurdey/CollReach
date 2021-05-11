package com.collreach.posts.service;

import com.collreach.posts.model.requests.CreatePostRequest;
import com.collreach.posts.model.response.ImagesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {

    public ImagesResponse getImages(String numberOfImages) throws IOException;
    public ImagesResponse getRandomImage() throws IOException;
    public String uploadImage(MultipartFile file, String userName, String messageId);
    public String createPost(CreatePostRequest createPostRequest);
    public ImagesResponse getAllPosts();
    public ImagesResponse getPostsByPagination(Integer pageNo, Integer pageSize);
    ImagesResponse getPostsPaginationFilteredByVisibility(Integer pageNo, Integer pageSize, String visibility);
}
