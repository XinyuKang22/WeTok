package com.example.wetok.resource;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * The InformationResource class is used to get user and post information from json file.
 * @author Xinyue Hu
 */
public class InformationResource {
    List<User> users = new ArrayList<>(); // Store users
    List<Post> posts = new ArrayList<>(); // Store posts


    public InformationResource(){}

    public InformationResource(List<User> users, List<Post> posts, List<User> followers, List<User> subscribers) {
        this.users = users;
        this.posts = posts;
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

    public String postlistSize(){
        return Integer.toString(this.posts.size());
    }

    /**
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
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }


}
