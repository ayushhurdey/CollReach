package com.collreach.posts.service.impl;

import com.collreach.posts.model.bo.posts.SeenAndLiked;
import com.collreach.posts.model.bo.posts.UserMessageKey;
import com.collreach.posts.model.repositories.posts.SeenAndLikedRepository;
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

    @Autowired
    SeenAndLikedRepository seenAndLikedRepository;

    @Override
    @Deprecated
    public MessagesResponse getImages(String numberOfImages) {
        HashSet<MessageResponse> set = new HashSet<>();
        messagesRepository.findAll().forEach(message -> {
            set.add(new MessageResponse(message.getFilename(), message.getFiletype(), message.getImage()));
        });
        return new MessagesResponse(set);
    }

    @Override
    @Deprecated
    public MessagesResponse getRandomImage() {
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
            set.add(new MessageResponse(message.getMessageId(), message.getFilename(), message.getFiletype(),
                                       message.getImage(), message.getVisibility(), message.getUserName().getUserName(),
                                       message.getLifetimeInWeeks(), message.getRecurrences(), message.getUserName().getName(),
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
            set.add(new MessageResponse(message.getMessageId(), message.getFilename(), message.getFiletype(),
                    message.getImage(), message.getVisibility(), message.getUserName().getUserName(),
                    message.getLifetimeInWeeks(), message.getRecurrences(), message.getUserName().getName(),
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
                    posts.add(new MessageResponse(message.getMessageId(), message.getFilename(), message.getFiletype(),
                            message.getImage(), message.getVisibility(),message.getUserName().getUserName(),
                            message.getLifetimeInWeeks(), message.getRecurrences(), message.getUserName().getName(),
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
        if(posts.size() == 0 && polls.size() == 0)
            return mergedSets;
        else if(posts.size() == 0)
            return polls;
        else if(polls.size() == 0)
            return posts;

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

    @Override
    public String updatePostViews(String userName, int messageId) {
        Optional<Users> user = usersRepository.findByUserName(userName);
        if(user.isPresent()){
            Optional<Messages> message = messagesRepository.findById(messageId);
            Optional<SeenAndLiked> userAlreadySeen = seenAndLikedRepository
                    .findById(new UserMessageKey(user.get().getUserId(),messageId));

            if((message.isPresent() && userAlreadySeen.isPresent() && userAlreadySeen.get().getSeen() != Character.valueOf('T')) ||
                    (message.isPresent() && !userAlreadySeen.isPresent())){
                try {
                    int previousViews = message.get().getViews();
                    message.get().setViews(previousViews + 1);
                    SeenAndLiked seenAndLiked = new SeenAndLiked();
                    seenAndLiked.setUserId(user.get());
                    seenAndLiked.setMessageId(message.get());
                    userAlreadySeen.ifPresent(andSeen -> seenAndLiked.setLiked(andSeen.getLiked()));
                    seenAndLiked.setSeen('T');
                    seenAndLikedRepository.save(seenAndLiked);
                    messagesRepository.save(message.get());
                    return "Success: "+ message.get().getViews();
                }catch(Exception e){
                    return ResponseMessage.PROCESSING_ERROR;
                }
            }
            else {
                return ResponseMessage.USER_ALREADY_SEEN;
            }
        }
        return ResponseMessage.USER_NOT_FOUND;
    }


    @Override
    public String updatePostLikes(String userName, int messageId) {
        Optional<Users> user = usersRepository.findByUserName(userName);
        if(user.isPresent()){
            Optional<Messages> message = messagesRepository.findById(messageId);
            Optional<SeenAndLiked> userAlreadyLiked = seenAndLikedRepository
                    .findById(new UserMessageKey(user.get().getUserId(),messageId));

            if((message.isPresent() && userAlreadyLiked.isPresent() && userAlreadyLiked.get().getLiked() != Character.valueOf('T')) ||
                    (message.isPresent() && !userAlreadyLiked.isPresent())){
                try {
                    int previousLikes = message.get().getLikes();
                    message.get().setLikes(previousLikes + 1);
                    SeenAndLiked seenAndLiked = new SeenAndLiked();
                    seenAndLiked.setUserId(user.get());
                    seenAndLiked.setMessageId(message.get());
                    userAlreadyLiked.ifPresent(andLiked -> seenAndLiked.setSeen(andLiked.getSeen()));
                    seenAndLiked.setLiked('T');
                    seenAndLikedRepository.save(seenAndLiked);
                    messagesRepository.save(message.get());
                    return "Success: "+ message.get().getLikes();
                }catch(Exception e){
                    return ResponseMessage.PROCESSING_ERROR;
                }
            }
            else {
                return ResponseMessage.USER_ALREADY_LIKED;
            }
        }
        return ResponseMessage.USER_NOT_FOUND;
    }

    @Override
    public int getTotalLikesOnMessage(int messageId){
        Optional<Messages> message = messagesRepository.findById(messageId);
        return message.map(Messages::getLikes).orElse(ResponseMessage.POST_MESSAGE_NOT_FOUND);
    }

    @Override
    public String deletePost(int messageId, String userName){
        Optional<Users> user = usersRepository.findByUserName(userName);
        Optional<Messages> message = messagesRepository.findById(messageId);
        if(user.isPresent() &&
                message.isPresent() &&
                user.get().getUserName()
                        .equals(message.get().getUserName().getUserName())) {
                    seenAndLikedRepository.deleteAllByMessageId(message.get());
                    messagesRepository.deleteById(messageId);
                    return ResponseMessage.SUCCESSFULLY_DONE;
        }
        return ResponseMessage.RECEIVED_INVALID_DATA;
    }
}
