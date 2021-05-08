package com.collreach.posts.controller;


import com.collreach.posts.model.repositories.MessagesRepository;
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

    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadImages(@RequestParam("file") MultipartFile file){
        String msg = postService.uploadImages(file);
        return ResponseEntity.ok().body(msg);
    }
}
