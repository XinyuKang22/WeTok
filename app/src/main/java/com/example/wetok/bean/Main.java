package com.example.wetok.bean;

import com.example.wetok.dao.CurrentUser;

public class Main {
    public static User u = new User("Joy");

    public static void main(String[] args) {
        CurrentUser.login(u);
        System.out.println(CurrentUser.current_user);
    }
}
