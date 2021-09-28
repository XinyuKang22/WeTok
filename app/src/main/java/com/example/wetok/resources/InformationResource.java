package com.example.wetok.resources;

import android.util.JsonReader;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

public class InformationResource {
    List<User> users;
    List<Post> posts;
    List<User> followers;
    List<User> subscribers;

    public InformationResource(){};

    public InformationResource(List<User> users, List<Post> posts, List<User> followers, List<User> subscribers) {
        this.users = users;
        this.posts = posts;
        this.followers = followers;
        this.subscribers = subscribers;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public String userlistSize(){
        return Integer.toString(this.users.size());
    }

    public String followerlistSize(){
        return Integer.toString(this.followers.size());
    }

    public String subscribelistSize(){
        return Integer.toString(this.subscribers.size());
    }

    public String postlistSize(){
        return Integer.toString(this.posts.size());
    }

    /*
     * Read instances from Json
     * @param file
     */
    public void readFromJson(InputStream file){
        JsonReader reader;
        ArrayList<User> followers = new ArrayList<User>();
        ArrayList<User> subscribers = new ArrayList<User>();
        ArrayList<Post> posts = new ArrayList<Post>();
        final Type classType = new TypeToken<List<User>>(){}.getType();
        Gson gson = new Gson();
        try {
            reader = new JsonReader(new InputStreamReader(file));
            List<User> users = gson.fromJson(String.valueOf(reader), classType);

            for(User u: users){
                followers.addAll(u.getFollowers());
                subscribers.addAll(u.getSubscribers());
                posts.addAll(u.getPosts());
            }
            this.users.addAll(users);
            this.followers.addAll(followers);
            this.subscribers.addAll(subscribers);
            this.posts.addAll(posts);

            System.out.println("successfully read from json file!");
            }catch (Exception e){
            System.out.println("cannot read from json file!");
        }

    }


}
