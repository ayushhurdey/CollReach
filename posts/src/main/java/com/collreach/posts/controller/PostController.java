package com.collreach.posts.controller;


import com.collreach.posts.model.repositories.posts.MessagesRepository;
import com.collreach.posts.model.requests.CreatePostRequest;
import com.collreach.posts.model.response.MessagesResponse;
import com.collreach.posts.responses.ResponseMessage;
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
    public ResponseEntity<MessagesResponse> getImages(@PathVariable(value = "numberOfImages") String numberOfImages) throws IOException {
        MessagesResponse messagesResponse = postService.getImages(numberOfImages);
        return ResponseEntity.ok().body(messagesResponse);
    }

    @GetMapping(path = "/get-random-image")
    public ResponseEntity<MessagesResponse> getRandomImage() throws IOException {
        MessagesResponse messagesResponse = postService.getRandomImage();
        return ResponseEntity.ok().body(messagesResponse);
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

    @GetMapping(path = "/get-all-posts")
    public ResponseEntity<MessagesResponse> getAllPosts(){
        MessagesResponse messagesResponse = postService.getAllPosts();
        return ResponseEntity.ok().body(messagesResponse);
    }

    @GetMapping(path = "/get-paged-posts")
    public ResponseEntity<MessagesResponse> getPostsByPagination(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(defaultValue = "2") Integer pageSize,
                                                                 @RequestParam(defaultValue = "college") String visibility){
        //ImagesResponse imagesResponse = postService.getPostsByPagination(pageNo, pageSize);
        MessagesResponse messagesResponse = postService.getPostsAndPollsPaginationFilteredByVisibility(pageNo, pageSize,visibility);
        return ResponseEntity.ok().body(messagesResponse);
    }

    @PutMapping(path = "/update-post-views/{username}/{messageId}")
    public ResponseEntity<String> updatePostViews(@PathVariable(value = "username") String userName,
                                                   @PathVariable(value = "messageId") Integer messageId){
        String msg = postService.updatePostViews(userName, messageId);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/update-post-likes/{username}/{messageId}")
    public ResponseEntity<String> updatePostLikes(@PathVariable(value = "username") String userName,
                                                  @PathVariable(value = "messageId") Integer messageId){
        String msg = postService.updatePostLikes(userName, messageId);
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping(path = "/get-all-likes/posts/{messageId}")
    public ResponseEntity<Integer> getAllLikes(@PathVariable(value = "messageId") int messageId){
        int msg = postService.getTotalLikesOnMessage(messageId);
        return ResponseEntity.ok().body(msg);
    }

    @DeleteMapping(path = "/posts/delete-post/{username}/{messageId}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "username") String userName,
                                              @PathVariable(value = "messageId") int messageId){
        String msg = postService.deletePost(messageId, userName);
        return ResponseEntity.ok().body(msg);
    }
}
