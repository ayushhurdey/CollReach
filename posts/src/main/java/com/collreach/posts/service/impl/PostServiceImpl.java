package com.collreach.posts.service.impl;

import com.collreach.posts.model.bo.posts.Messages;
import com.collreach.posts.model.repositories.posts.MessagesRepository;
import com.collreach.posts.model.response.ImageResponse;
import com.collreach.posts.model.response.ImagesResponse;
import com.collreach.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    MessagesRepository messagesRepository;

    @Override
    public ImagesResponse getImages(String numberOfImages) throws IOException {
        HashSet<ImageResponse> set = new HashSet<>();
        messagesRepository.findAll().forEach(message -> {
            set.add(new ImageResponse(message.getFilename(), message.getFiletype(), message.getImage()));
        });
        return new ImagesResponse(set);
    }

    @Override
    public ImagesResponse getRandomImage() throws IOException {
        HashSet<ImageResponse> set = new HashSet<>();

        messagesRepository.findAll().forEach(message -> {
            set.add(new ImageResponse(message.getFilename(), message.getFiletype(), message.getImage()));
        });

        int random = new Random().nextInt(set.size());
        AtomicInteger i = new AtomicInteger();

        return new ImagesResponse(
                        set.parallelStream().filter(imageResponse -> {
                            return i.getAndIncrement() == random;
                        }).findFirst().get());

//        for(ImageResponse imageResponse: set){
//            if(i == random)
//                return ResponseEntity.ok().body(new ImagesResponse(imageResponse));
//            i++;
//        }
//        return new ImagesResponse(set);
    }

    @Override
    public String uploadImages(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        int index = fileName.lastIndexOf('.');
        System.out.println("file size : " + ((float)(file.getSize()/1024)/1024) + "MB");

        if(index > 0) {
            String extension = fileName.substring(index + 1);
            System.out.println("File extension is " + extension);
            try {
                Messages messages = new Messages(file.getBytes(), file.getOriginalFilename(), extension);
                messagesRepository.save(messages);
            }catch(Exception e){
                return "false : " + e.getLocalizedMessage();
            }
        }
        return "true";
    }
}
