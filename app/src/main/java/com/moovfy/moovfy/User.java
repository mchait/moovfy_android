package com.moovfy.moovfy;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String username;
    private String name;
    private String avatar;
    private List<String> ChatsOberts;

    public User() {
        ChatsOberts = new ArrayList<>();
    }

    public User(String email, String username, String avatar,String name) {
        this.email = email;
        this.username = username;
        this.avatar = avatar;
        ChatsOberts = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }



    public String getUsername() {
        return username;
    }


    public String getName() {
        return name;
    }

    public List<String> getChatsOberts() {
        return ChatsOberts;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void AddtoList(String chat_uid){
        if(ChatsOberts.contains(chat_uid)){
            ChatsOberts.remove(chat_uid);
        }
        ChatsOberts.add(0,chat_uid);
    }
}

