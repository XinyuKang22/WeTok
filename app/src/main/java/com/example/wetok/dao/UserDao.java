package com.example.wetok.dao;

import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;

import java.util.List;

public class UserDao {
    public static InformationResource info = new InformationResource();
    public static List<User> users = info.getUsers();

    //TODO: 新用户注册addUser方法
    public static void addUser(){
    }

    public static User findUserByEmail(String email) {
        for (User u: users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

}


