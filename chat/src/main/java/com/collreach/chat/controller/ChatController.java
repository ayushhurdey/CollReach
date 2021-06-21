package com.collreach.chat.controller;

import com.collreach.chat.model.bo.Message;
import com.collreach.chat.model.bo.Room;
import com.collreach.chat.model.bo.User;
import com.collreach.chat.model.repository.MessageRepository;
import com.collreach.chat.model.repository.RoomRepository;
import com.collreach.chat.model.repository.UserRepository;
import com.collreach.chat.model.request.MessageRequest;
import com.collreach.chat.model.request.UserLoginRequest;
import com.collreach.chat.model.response.TestResponse;
import com.collreach.chat.model.response.UserLoginResponse;
import com.collreach.chat.model.response.UserNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class ChatController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MessageRepository messageRepository;


    @GetMapping(path = "/test")
    public List<String> test(){
        return Arrays.asList("Ayush", "Ankit", "Aakash", "Arpit");
    }

    @RequestMapping(value = "/get-user-details", method = RequestMethod.GET)
    //public String getUserDetails(@RequestBody UserLoginRequest userLoginRequest) {
    public ResponseEntity<UserLoginResponse> getUserDetails() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // token valid for 10 years. Created on 9/5/2021
        headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIyY3MxMDQ5IiwiZXhwIjoxOTM1OTM2MTU4LCJpYXQiOjE2MjA1NzYxNTh9.SPqXqwLGHXWl485jr_sojbseRkiYiMMKTL-9gjCVYzxO6mok4mnWaS_Sy9KPmT-dzHhjj_lj32wLyB8UKT5AGw");
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("1822cs1049");
        userLoginRequest.setPassword("Ayush@123");
        HttpEntity<UserLoginRequest> entity = new HttpEntity<UserLoginRequest>(userLoginRequest,headers);

        try {
            return ResponseEntity.ok()
                                  .body(restTemplate.exchange("http://userprofile/user/login",
                                          HttpMethod.POST, entity, UserLoginResponse.class).getBody());
             /*
            TestResponse testResponse =  new TestResponse();
            testResponse.setName("Ayush Choudhary");
            testResponse.setUsername("1822cs1049");

            UserNameResponse userNameResponse1 = new UserNameResponse();
            userNameResponse1.setUserName("ccsdfg344");

            UserNameResponse userNameResponse2 = new UserNameResponse();
            userNameResponse2.setUserName("akfgsg379");

            testResponse.setList(Arrays.asList(userNameResponse1, userNameResponse2));
            return ResponseEntity.ok().body(testResponse);*/
        }catch(Exception e){
            System.out.println("Some Error occurred." + e);
            return ResponseEntity.ok().body(new UserLoginResponse());
        }
    }

    @GetMapping(path = "/add-user/{username}/{name}")
    public List<User> addUser(@PathVariable(value = "username") String username,
                            @PathVariable(value = "name") String name){
        String userId = UUID.randomUUID().toString().replace("-", "");
        userRepository.save(new User(userId, username, name));
        return userRepository.findAll();
    }

    @GetMapping(path = "/add-room")
    public List<Room> addRoom(@RequestParam(value = "senderId") String senderId,
                                @RequestParam(value = "receiverId") String receiverId){
        String roomId = UUID.randomUUID().toString().replace("-", "");
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);
        roomRepository.save(new Room(sender.get(), receiver.get(), new Date()));
        return roomRepository.findAll();
    }

    @PostMapping(path = "/add-message")
    public Boolean addMessage(@RequestBody MessageRequest messageRequest){
        //String messageId = UUID.randomUUID().toString().replace("-", "");
        String senderUsername = messageRequest.getSender();
        String receiverUsername = messageRequest.getReceiver();
        try {
            Optional<User> sender = userRepository.findByUsername(senderUsername);
            sender.ifPresent(System.out::println);

            Optional<User> receiver = userRepository.findByUsername(receiverUsername);
            receiver.ifPresent(System.out::println);

            Optional<Room> room = roomRepository.findByMemberOneAndMemberTwoOrMemberTwoAndMemberOne(sender.get(), receiver.get(), sender.get(), receiver.get());
            room.ifPresent(System.out::println);

            Room room1 = null;
            //room1 = room.orElseGet(() -> roomRepository.save(new Room(sender.get(), receiver.get(), messageRequest.getDate())));
            if(room.isPresent()){
                room.get().setLastContact(messageRequest.getDate());
                room1 = room.get();
                roomRepository.save(room1);
            }
            else{
                room1 = roomRepository.save(new Room(sender.get(), receiver.get(), messageRequest.getDate()));
            }
            messageRepository.save(new Message(messageRequest.getMessage(), messageRequest.getDate(),
                                                messageRequest.getTime(), room1,
                                                messageRequest.getSender(), messageRequest.getReceiver()));
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @GetMapping(path = "/get-room-by-members")
    public Room getRoomByMembers(@RequestParam(value = "memberOne") String memberOne,
                                       @RequestParam(value = "memberTwo") String memberTwo){
        Optional<User> member1 = userRepository.findByUsername(memberOne);
        Optional<User> member2 = userRepository.findByUsername(memberTwo);
        Optional<Room> room = roomRepository.findByMemberOneAndMemberTwoOrMemberTwoAndMemberOne(member1.get(), member2.get(), member1.get(), member2.get());
        return room.orElse(null);
    }

    @GetMapping(path= "/get-all-contacts")
    public List<User> getAllContactsOfUser(@RequestParam(value = "username") String username,
                                           @RequestParam(value = "pageNo") int pageNo,
                                           @RequestParam(value = "pageSize") int pageSize
                                        ){
        List<User> contacts = new ArrayList<>();
        Optional<User> user = userRepository.findByUsername(username);

        Sort dateSort = Sort.by("lastContact").descending();
        Pageable paging = PageRequest.of(pageNo, pageSize, dateSort);
        List<Room> room = roomRepository.findAllByMemberOneOrMemberTwo(user.get(), user.get(), paging);
        if(room.size() > 0){
            for(Room each: room){
                User memberOne = each.getMemberOne();
                User memberTwo = each.getMemberTwo();
                if(memberOne.getUsername().equalsIgnoreCase(username)){
                    contacts.add(memberTwo);
                }
                else{
                    contacts.add(memberOne);
                }
            }
        }
        return contacts;
    }

    @GetMapping(path = "/get-all-sorted-messages")
    public List<Message> getAllSortedMessage(@RequestParam(value = "memberOne") String memberOne,
                                             @RequestParam(value = "memberTwo") String memberTwo,
                                             @RequestParam(value = "pageNo") int pageNo,
                                             @RequestParam(value = "pageSize") int pageSize
                                             ){
        Sort dateSort = Sort.by("date").descending();
        Pageable paging = PageRequest.of(pageNo, pageSize, dateSort);

        List<Message> messages = messageRepository.findAllBySenderAndReceiverOrReceiverAndSender(memberOne, memberTwo, memberOne, memberTwo, paging);
        return messages;
    }

    @GetMapping(path = "/get-all-messages")
    public List<Message> getAllMessage(@RequestParam(value = "memberOne") String memberOne,
                                       @RequestParam(value = "memberTwo") String memberTwo){
        List<Message> messages = messageRepository.findAllBySenderAndReceiverOrReceiverAndSender(memberOne, memberTwo, memberOne, memberTwo);
        return messages;
    }
}
