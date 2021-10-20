package com.example.wetok.dao;

import com.example.wetok.bean.User;

/**
 * The CurrentUser class is used to store the current user and the status such login or logout
 * @author Yuxin Hong
 */
public class CurrentUser {
    public static CurrentUser instance = null;
    public static User current_user = null;
    public static User current_visitor = null;


    private CurrentUser() {}

    /**
     * Get current user
     * @return User
     */
    public static CurrentUser getInstance() {
        if(instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    /**
     * Set the email and password for registered user
     * @param email
     * @param password
     */
    public static void register(String email, String password){
        if (instance == null) {
            instance = new CurrentUser();
            current_user = new User();
            current_user.setEmail(email);
            current_user.setPassword(password);
        }
    }

    /**
     * Record the user when login
     * @param u
     */
    public static void login(User u){
        if (instance == null) {
            instance = new CurrentUser();
            current_user = u;
        }
    }

    /**
     * Remove the user when logout
     */
    public static void logout(){
        instance = null;
        current_user = null;
    }

}
