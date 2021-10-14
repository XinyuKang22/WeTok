package com.example.wetok.dao;

import com.example.wetok.bean.User;
import java.util.Calendar;
import java.util.Date;

public class CurrentUser {
    public static CurrentUser instance = null;
    public static User current_user = null;
    Date current_time = Calendar.getInstance().getTime();

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
