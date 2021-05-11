package com.collreach.posts.controller;


import com.collreach.posts.model.repositories.posts.MessagesRepository;
import com.collreach.posts.model.requests.CreatePostRequest;
import com.collreach.posts.model.response.ImagesResponse;
import com.collreach.posts.service.PostService;
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

    //private ObjectMapper objectMapper;

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

    @PostMapping(path = "/get-all-posts")
    public ResponseEntity<ImagesResponse> getAllPosts(){
        ImagesResponse imagesResponse = postService.getAllPosts();
        return ResponseEntity.ok().body(imagesResponse);
    }

    @PostMapping(path = "/get-paged-posts")
    public ResponseEntity<ImagesResponse> getPostsByPagination(@RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "2") Integer pageSize,
                                                               @RequestParam(defaultValue = "college") String visibility){
        //ImagesResponse imagesResponse = postService.getPostsByPagination(pageNo, pageSize);
        ImagesResponse imagesResponse = postService.getPostsPaginationFilteredByVisibility(pageNo, pageSize,visibility);
        return ResponseEntity.ok().body(imagesResponse);
    }
}
