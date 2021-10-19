package com.example.wetok.dao;

import com.example.wetok.bean.User;

public class CurrentUser {
    public static CurrentUser instance = null;
    public static User current_user = null;
    public static User current_visitor = null;


    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if(instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public static void register(String email, String password){
        if (instance == null) {
            instance = new CurrentUser();
            current_user = new User();
            current_user.setEmail(email);
            current_user.setPassword(password);
        }
    }

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
