package com.example.wetok.dao;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Serializable {
//    public static InformationResource info = null;
    public static List<User> users = null;
    public static int users_size = 0;

    public UserDao(List<User> u) {
        users = u;
    }

    //TODO: 新用户注册addUser方法
    public static void addUser(User u){
        users.add(u);
    }

    public static User findUserByEmail(String email) {
        for (User u: users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public static User findUserById(String id){
        int index = Integer.parseInt(id);
        if (index < users_size) {
            return users.get(index);
        } else {
            return null;
        }
    }

    public static User findUserById(int id){
        if (id < users_size) {
            return users.get(id);
        } else {
            return null;
        }
    }

    public static void setFriends(User u){
        int id = (int)(Math.random()*users_size);
        List<User> friends = new ArrayList<>();
        for(int i=0; i<5;i++){
            id = (int)(Math.random()*users_size);
            friends.add(findUserById(id));
        }
        u.setFriends(friends);
    }

    public static void setFollowers(User u){
        int id = (int)(Math.random()*users_size);
        List<User> followers = new ArrayList<>();
        for(int i=0; i<5;i++){
            id = (int)(Math.random()*users_size);
            followers.add(findUserById(id));
        }
        u.setFollowers(followers);
    }

    public static void setSubscribers(User u){
        int id = (int)(Math.random()*users_size);
        List<User> subscribers = new ArrayList<>();
        for(int i=0; i<5;i++){
            id = (int)(Math.random()*users_size);
            subscribers.add(findUserById(id));
        }
        u.setSubscribers(subscribers);
    }

    public static List<Post> getPosts() { //
        List<Post> posts = new ArrayList<>();
        for (User u : users) {
            setPostInfo(u);
            posts.addAll(u.getPosts());
        }
        return posts;
    }

    public static void setPostInfo(User u) {
        String author = u.getName();
        String email = u.getEmail();
        String id = u.getId();
        List<Post> postList = u.getPosts();
        for (Post p : postList) {
            p.setAuthor(author);
            p.setUid(id);
            p.setEmail(email);
        }
    }

}


