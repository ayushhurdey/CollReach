package com.collreach.userprofile.util;

import com.collreach.userprofile.model.request.AddNewUserRequest;
import com.collreach.userprofile.model.request.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class HttpRequestUtil {

    @Autowired
    RestTemplate restTemplate;

    public Boolean setNewUserAtUrl(String userName, String name, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        AddNewUserRequest addNewUserRequest = new AddNewUserRequest();
        addNewUserRequest.setUsername(userName);
        addNewUserRequest.setName(name);
        HttpEntity<AddNewUserRequest> entity = new HttpEntity<>(addNewUserRequest,headers);

        try {
            // "http://localhost:8084/user/add-user"
            return restTemplate.exchange(url, HttpMethod.POST, entity, Boolean.class).getBody();
        }catch(Exception e){
            System.out.println("Some Error occurred." + e);
            return false;
        }
    }
}
