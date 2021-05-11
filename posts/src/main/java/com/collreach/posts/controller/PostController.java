package com.collreach.posts.controller;


import com.collreach.posts.model.repositories.posts.MessagesRepository;
import com.collreach.posts.model.requests.CreatePostRequest;
import com.collreach.posts.model.response.ImagesResponse;
import com.collreach.posts.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
public class PostController {

    @Autowired
    MessagesRepository messagesRepository;

    @Autowired
    PostService postService;

    private ObjectMapper objectMapper;

    @GetMapping(path = "/get-images/{numberOfImages}")
    public ResponseEntity<ImagesResponse> getImages(@PathVariable(value = "numberOfImages") String numberOfImages) throws IOException {
        ImagesResponse imagesResponse = postService.getImages(numberOfImages);
        return ResponseEntity.ok().body(imagesResponse);
    }

    @GetMapping(path = "/get-random-image")
    public ResponseEntity<ImagesResponse> getRandomImage() throws IOException {
        ImagesResponse imagesResponse = postService.getRandomImage();
        return ResponseEntity.ok().body(imagesResponse);
    }

    @PostMapping(path = "/upload-post-image")
    public ResponseEntity<String> uploadImages(@RequestParam("file") MultipartFile file,
                                               @RequestParam("username") String userName,
                                               @RequestParam("messageId") String messageId){
        String msg = postService.uploadImage(file, userName, messageId);
        return ResponseEntity.ok().body(msg);
    }

    @PostMapping(path="/create-post")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest createPostRequest) {
        //CreatePostRequest  createPostRequest = objectMapper.readValue(createPostReq, CreatePostRequest.class);
        String msg = postService.createPost(createPostRequest);
        return ResponseEntity.ok().body(msg);
    }
}
