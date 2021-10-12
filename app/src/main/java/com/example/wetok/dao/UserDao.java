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
        for(User u: users){
            if(u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }


    public static void setFriends(User u){
        int id = (int)(Math.random()*100);
        List<User> friends = new ArrayList<>();
        for(int i=0; i<5;i++){
            while(u.getId().equals(String.valueOf(id)) ){
                id = (int)(Math.random()*100);
            }
            friends.add(findUserById(String.valueOf(id)));
        }
        u.setFriends(friends);
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


