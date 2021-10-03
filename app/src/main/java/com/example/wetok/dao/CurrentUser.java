package com.example.wetok.dao;

import com.example.wetok.bean.User;

public class CurrentUser {
    public static CurrentUser instance = null;
    public static User current_user = null;

    private CurrentUser() {
    }

    private CurrentUser(User u) {
        current_user = u;
    }

    public static CurrentUser getInstance() {
        if(instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    // 加更多
    public static void login(User u){
        if (instance == null) {
            instance = new CurrentUser();
            current_user = u;
        }
    }

    public static void logout(){
        instance = null;
        current_user = null;
    }

}
