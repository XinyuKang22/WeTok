package com.example.wetok.dao;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The UserDao class is used to store the database operation of user.
 * @author Yuxin Hong
 * @author Xinyue Hu
 */
public class UserDao implements Serializable {

    public static List<User> users = null;
    public static int users_size = 0;

    public UserDao(List<User> u) {
        users = u;
    }

    /**
     * Add user to database
     * @param u
     */
    public static void addUser(User u){
        users.add(u);
    }

    /**
     * @param email
     * @return user
     */
    public static User findUserByEmail(String email) {
        for (User u: users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * @param id
     * @return User
     */
    public static User findUserById(int id){
        if (id < users_size) {
            return users.get(id);
        } else {
            return null;
        }
    }


    /**
     * set friends for user
     * @param u
     */
    public static void setFriends(User u){
        int id = (int)(Math.random()*users_size);
        List<User> friends = new ArrayList<>();
        for(int i=0; i<5;i++){
            id = (int)(Math.random()*users_size);
            friends.add(findUserById(id));
        }
        u.setFriends(friends);
    }

    /**
     * set followers for user
     * @param u
     */
    public static void setFollowers(User u){
        int id = (int)(Math.random()*users_size);
        List<User> followers = new ArrayList<>();
        for(int i=0; i<5;i++){
            id = (int)(Math.random()*users_size);
            followers.add(findUserById(id));
        }
        u.setFollowers(followers);
    }

    /**
     * set subscribers for user
     * @param u
     */
    public static void setSubscribers(User u){
        int id = (int)(Math.random()*users_size);
        List<User> subscribers = new ArrayList<>();
        for(int i=0; i<5;i++){
            id = (int)(Math.random()*users_size);
            subscribers.add(findUserById(id));
        }
        u.setSubscribers(subscribers);
    }

    /**
     * @return all the posts
     */
    public static List<Post> getPosts(List<User> users) {
        List<Post> posts = new ArrayList<>();
        for (User u : users) {
            setPostInfo(u);
            posts.addAll(u.getPosts());
        }
        return posts;
    }

    /**
     * Instantiate user's post
     * @param u
     */
    public static void setPostInfo(User u) {
        String author = u.getName();
        String email = u.getEmail();
        String id = u.getId();
        List<Post> postList = u.getPosts();
        Collections.sort(postList);
        for (Post p : postList) {
            p.setAuthor(author);
            p.setUid(id);
            p.setEmail(email);
        }
    }

    /**
     * Instantiate user's image
     */
    public static void setImgInfo() {
        for (User u : users) {
            String id = u.getId();
            u.setImgloc(id.charAt(id.length()-1)+"");
        }
    }

    /**
     * Filter users in the same location
     * @param location
     * @return users in the same location
     */
    public static List<User> filterLocation(String location) {
        List<User> user_list = new ArrayList<>();
        for (User u : users) {
            if (u.getAddress().trim().equals(location.trim())) user_list.add(u);
        }
        return user_list;
    }

}


