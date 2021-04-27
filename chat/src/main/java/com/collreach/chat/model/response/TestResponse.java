package com.collreach.chat.model.response;

import java.util.List;

public class TestResponse {
    String username;
    String name;
    List<UserNameResponse> list;
    public TestResponse(){}

    public TestResponse(String username, String name, List<UserNameResponse> list) {
        this.username = username;
        this.name = name;
        this.list = list;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserNameResponse> getList() {
        return list;
    }

    public void setList(List<UserNameResponse> list) {
        this.list = list;
    }
}
