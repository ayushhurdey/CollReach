package com.collreach.posts.service.impl;

import com.collreach.posts.responses.ResponseMessage;
import com.collreach.posts.model.bo.Users;
import com.collreach.posts.model.bo.posts.Messages;
import com.collreach.posts.model.repositories.UsersRepository;
import com.collreach.posts.model.repositories.posts.MessagesRepository;
import com.collreach.posts.model.requests.CreatePostRequest;
import com.collreach.posts.model.response.MessageResponse;
import com.collreach.posts.model.response.MessagesResponse;
import com.collreach.posts.service.PollsService;
import com.collreach.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    MessagesRepository messagesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PollsService pollsService;

    @Override
    public MessagesResponse getImages(String numberOfImages) throws IOException {
        HashSet<MessageResponse> set = new HashSet<>();
        messagesRepository.findAll().forEach(message -> {
            set.add(new MessageResponse(message.getFilename(), message.getFiletype(), message.getImage()));
        });
        return new MessagesResponse(set);
    }

    @Override
    public MessagesResponse getRandomImage() throws IOException {
        HashSet<MessageResponse> set = new HashSet<>();

        messagesRepository.findAll().forEach(message -> {
            set.add(new MessageResponse(message.getFilename(), message.getFiletype(), message.getImage()));
        });

        int random = new Random().nextInt(set.size());
        AtomicInteger i = new AtomicInteger();

        return new MessagesResponse(
                        set.parallelStream().filter(messageResponse -> {
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
    public String uploadImage(MultipartFile file, String userName, String msgId) {
        Optional<Users> user = usersRepository.findByUserName(userName);
        if(user.isPresent()) {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            int index = fileName.lastIndexOf('.');
            System.out.println("file size : " + ((float) (file.getSize() / 1024) / 1024) + "MB");

            if (index > 0) {
                String extension = fileName.substring(index + 1);
                System.out.println("File extension is " + extension);
                int messageId = Integer.parseInt(msgId);
                Optional<Messages> message = messagesRepository.findById(messageId);
                try {
                    //Messages messages = new Messages(file.getBytes(), file.getOriginalFilename(), extension);
                    //Messages messages = new Messages();

                    if(message.isPresent() &&
                            message.get().getUserName().getUserName().equals(userName) &&
                            message.get().getImage() == null) {
                        message.get().setImage(file.getBytes());
                        message.get().setFilename(file.getOriginalFilename());
                        message.get().setFiletype(extension);
                        messagesRepository.save(message.get());
                        return ResponseMessage.SUCCESSFULLY_DONE;
                    }
                    else{
                        System.out.println("Some error occurred in saving image.");
                        throw new Exception();
                    }
                } catch (Exception e) {
                    return ResponseMessage.PROCESSING_ERROR;
                }
            }
        }
        return ResponseMessage.USER_NOT_FOUND;
    }

    @Override
    public String createPost( CreatePostRequest createPostRequest ) {
        Optional<Users> user = usersRepository.findByUserName(createPostRequest.getUserName());
        if(user.isPresent()){
                try {
                    Messages messages = new Messages();
                    messages.setUserName(user.get());
                    messages.setCreateDate(createPostRequest.getCreateDate());
                    messages.setUploadTime(createPostRequest.getUploadTime());
                    messages.setLifetimeInWeeks(createPostRequest.getLifetimeInWeeks());
                    messages.setRecurrences(createPostRequest.getRecurrences());
                    messages.setMessage(createPostRequest.getMessage());
                    messages.setVisibility(createPostRequest.getVisibility());
                    Messages msg  = messagesRepository.save(messages);
                    return "" + msg.getMessageId();
                }catch(Exception e){
                    return ResponseMessage.PROCESSING_ERROR;
                }
        }
        return ResponseMessage.USER_NOT_FOUND;
    }

    @Override
    public MessagesResponse getAllPosts() {
        LinkedHashSet<MessageResponse> set = new LinkedHashSet<>();
        messagesRepository.findAll().forEach(message -> {
            set.add(new MessageResponse(message.getFilename(), message.getFiletype(),
                                       message.getImage(), message.getVisibility(),
                                       message.getLifetimeInWeeks(), message.getRecurrences(),
                                       message.getLikes(), message.getViews(),message.getMessage(),
                                       message.getCreateDate(),message.getUploadTime())
            );
        });
        return new MessagesResponse(set);
    }

    @Override
    public MessagesResponse getPostsByPagination(Integer pageNo, Integer pageSize) {
        LinkedHashSet<MessageResponse> set = new LinkedHashSet<>();
        Sort dateSort = Sort.by("createDate");
        Sort timeSort = Sort.by("uploadTime");
        Sort groupBySort = dateSort.and(timeSort);
        Pageable paging = PageRequest.of(pageNo, pageSize, groupBySort);
        messagesRepository.findAll(paging).forEach(message -> {
            set.add(new MessageResponse(message.getFilename(), message.getFiletype(),
                    message.getImage(), message.getVisibility(),
                    message.getLifetimeInWeeks(), message.getRecurrences(),
                    message.getLikes(), message.getViews(),message.getMessage(),
                    message.getUploadTime(),message.getCreateDate())
            );
        });
        return new MessagesResponse(set);
    }

    @Override
    public MessagesResponse getPostsAndPollsPaginationFilteredByVisibility(Integer pageNo, Integer pageSize, String visibility) {
        LinkedHashSet<MessageResponse> posts = new LinkedHashSet<>();
        Sort dateSort = Sort.by("createDate").descending();
        Sort timeSort = Sort.by("uploadTime").descending();
        Sort groupBySort = dateSort.and(timeSort);
        Pageable paging = PageRequest.of(pageNo, pageSize, groupBySort);

        messagesRepository.findAllByVisibilityOrVisibility(visibility,"college", paging)
                .forEach(message -> {
                    posts.add(new MessageResponse(message.getFilename(), message.getFiletype(),
                            message.getImage(), message.getVisibility(),
                            message.getLifetimeInWeeks(), message.getRecurrences(),
                            message.getLikes(), message.getViews(), message.getMessage(),
                            message.getUploadTime(), message.getCreateDate())
                    );
                });

        LinkedHashSet<MessageResponse> polls = pollsService.getPolls(pageNo,pageSize,visibility);
        //return new MessagesResponse(posts);
        return new MessagesResponse(mergeSets(posts,polls));
    }

    @Override
    public LinkedHashSet<MessageResponse> mergeSets(LinkedHashSet<MessageResponse> posts, LinkedHashSet<MessageResponse> polls){
        LinkedHashSet<MessageResponse> mergedSets = new LinkedHashSet<>();
        Iterator<MessageResponse> postItr = posts.iterator();
        Iterator<MessageResponse> pollItr = polls.iterator();

        MessageResponse postsValueCurrent = null;
        MessageResponse postsValueNext = null;
        MessageResponse pollsValueCurrent = null;
        MessageResponse pollsValueNext = null;
        int i = 0;

        while(postItr.hasNext() && pollItr.hasNext()){
            if(i++ == 0) {
                postsValueNext = postItr.next();
                pollsValueNext = pollItr.next();
            }
            if(postsValueCurrent != null){
                if(postsValueCurrent.getCreateDate().compareTo(pollsValueCurrent.getCreateDate()) == 0){
                    if(postsValueCurrent.getUploadTime().compareTo(pollsValueCurrent.getUploadTime()) > 0){
                        mergedSets.add(postsValueCurrent);
                        postsValueNext = postItr.next();
                    }
                    else{
                        mergedSets.add(pollsValueCurrent);
                        pollsValueNext = pollItr.next();
                    }
                }
                else if(postsValueCurrent.getCreateDate().compareTo(pollsValueCurrent.getCreateDate()) > 0){
                    mergedSets.add(postsValueCurrent);
                    postsValueNext = postItr.next();
                }
                else {
                    mergedSets.add(pollsValueCurrent);
                    pollsValueNext = pollItr.next();
                }
            }
            postsValueCurrent = postsValueNext;
            pollsValueCurrent = pollsValueNext;

        }

        boolean pollSwitch = false;
        assert postsValueCurrent != null;

        if(postsValueCurrent.getCreateDate().compareTo(pollsValueCurrent.getCreateDate()) == 0){
            if(postsValueCurrent.getUploadTime().compareTo(pollsValueCurrent.getUploadTime()) > 0){
                mergedSets.add(postsValueCurrent);
            }
            else{
                mergedSets.add(pollsValueCurrent);
                pollSwitch = true;
            }
        }
        mergedSets.add(pollSwitch ? postsValueCurrent : pollsValueCurrent);

        postItr.forEachRemaining(mergedSets::add);
        pollItr.forEachRemaining(mergedSets::add);

        return mergedSets;
    }
}
