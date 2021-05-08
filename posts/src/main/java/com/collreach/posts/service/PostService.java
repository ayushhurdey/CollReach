package com.collreach.posts.service;

import com.collreach.posts.model.response.ImagesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {

    public ImagesResponse getImages(String numberOfImages) throws IOException;
    public ImagesResponse getRandomImage() throws IOException;
    public String uploadImages(MultipartFile file);

}
