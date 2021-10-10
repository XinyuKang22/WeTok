package com.example.wetok.resources;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class InformationResource {
    List<User> users = new ArrayList<>();
    List<Post> posts = new ArrayList<>();
    List<User> followers = new ArrayList<>();
    List<User> subscribers = new ArrayList<>();

    public InformationResource(){
        InputStream file;
        //TODO need to modified
        file = InformationResource.class.getResourceAsStream("src/main/assets/infoResource.json");

        final Type classType = new TypeToken<List<User>>(){}.getType();
        Gson gson = new Gson();
        try {
            JsonReader  reader = new JsonReader(new InputStreamReader(file));
            this.users.addAll(gson.fromJson(reader, classType));
            for(User u: users){
                this.posts.addAll(u.getPosts());
                this.subscribers.addAll(u.getSubscribers());
                this.followers.addAll(u.getFollowers());
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public InformationResource(List<User> users, List<Post> posts, List<User> followers, List<User> subscribers) {
        this.users = users;
        this.posts = posts;
        this.followers = followers;
        this.subscribers = subscribers;
    }


    public List<User> getUsers() {return users;}

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
        subscribers = subscribers;
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
        final Type classType = new TypeToken<List<User>>(){}.getType();
        Gson gson = new Gson();
        try {
            JsonReader  reader = new JsonReader(new InputStreamReader(file));
            this.users.addAll(gson.fromJson(reader, classType));
            for(User u: users){
                this.posts.addAll(u.getPosts());
                this.subscribers.addAll(u.getSubscribers());
                this.followers.addAll(u.getFollowers());
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

}
