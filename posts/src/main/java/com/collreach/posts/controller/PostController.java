package com.collreach.posts.controller;


import com.collreach.posts.model.bo.Messages;
import com.collreach.posts.model.repositories.MessagesRepository;
import com.collreach.posts.model.response.ImageResponse;
import com.collreach.posts.model.response.ImagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin("*")
public class PostController {

    @Autowired
    MessagesRepository messagesRepository;

    @GetMapping(path = "/get-images/{numberOfImages}")
    public ResponseEntity<ImagesResponse> getImages(@PathVariable(value = "numberOfImages") String numberOfImages) throws IOException {
        HashSet<ImageResponse> set = new HashSet<>();
        messagesRepository.findAll().forEach(message -> {
            set.add(new ImageResponse(message.getFilename(), message.getFiletype(), message.getImage()));
        });
        ImagesResponse imagesResponse = new ImagesResponse(set);
        return ResponseEntity.ok().body(imagesResponse);
    }


    @GetMapping(path = "/get-random-image")
    public ResponseEntity<ImagesResponse> getRandomImage() throws IOException {
        HashSet<ImageResponse> set = new HashSet<>();

        messagesRepository.findAll().forEach(message -> {
            set.add(new ImageResponse(message.getFilename(), message.getFiletype(), message.getImage()));
        });

        int random = new Random().nextInt(set.size());
        AtomicInteger i = new AtomicInteger();

        return ResponseEntity.ok().body(
                new ImagesResponse(
                    set.parallelStream().filter(imageResponse -> {
                                return i.getAndIncrement() == random;
                    }).findFirst().get())
        );

//        for(ImageResponse imageResponse: set){
//            if(i == random)
//                return ResponseEntity.ok().body(new ImagesResponse(imageResponse));
//            i++;
//        }
//        ImagesResponse imagesResponse = new ImagesResponse(set);
//        return ResponseEntity.ok().body(imagesResponse);
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadImages(@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf('.');
        System.out.println("file size : " + ((float)(file.getSize()/1024)/1024) + "MB");

        if(index > 0) {
            String extension = fileName.substring(index + 1);
            System.out.println("File extension is " + extension);
            try {
                Messages messages = new Messages(file.getBytes(), file.getOriginalFilename(), extension);
                messagesRepository.save(messages);
            }catch(Exception e){
                return ResponseEntity.ok().body(e.getLocalizedMessage());
            }
        }
        return ResponseEntity.ok().body("true");
    }
}
