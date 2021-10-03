package com.example.wetok.resources;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
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
    public InformationResource(){}

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
/*
    public List<User> readUserList(JsonReader reader) throws IOException {
        List<User> users = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            users.add(readUser(reader));
        }
        reader.endArray();
        return users;
    }

    public User readUser(JsonReader reader) throws IOException {
        String id = null;
        String name = null;
        String gender = null;
        String password = null;
        int age = 0;
        List<User> followers = null;
        List<User> Subscribers = null;
        List<Post> posts = null;
        String address = null;
        String email = null;
        String phone = null;
        String imgloc = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String str = reader.nextName();
            if (str.equals("id")) {
                id = reader.nextString();
            } else if (str.equals("name")) {
                name = reader.nextString();
            } else if (str.equals("password")) {
                password = reader.nextString();
            } else if (str.equals("gender")) {
                gender = reader.nextString();
            } else if (str.equals("age")){
                age = reader.nextInt();
            } else if (str.equals("posts") && reader.peek() != JsonToken.NULL){
                posts = readPostList(reader);
            } else if (str.equals("address")){
                address = reader.nextString();
            } else if (str.equals("email")){
                email = reader.nextString();
            } else if (str.equals("phone")){
                phone = reader.nextString();
            } else if (str.equals("imgloc")){
                imgloc = reader.nextString();
            }
        }
        reader.endObject();
        return new User(id,name,gender,password,age,followers,
                Subscribers,posts,address,email,phone,imgloc);
    }

    public List<Post> readPostList(JsonReader reader) throws IOException {
        List<Post> posts = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            posts.add(readPost(reader));
        }
        reader.endArray();
        return posts;
    }

    public Post readPost(JsonReader reader) throws IOException {
        String u_id = null;
        String author = null;
        String u_img = null;
        String tag = null;
        String comments = null;
        int likes = 0;
        int repost = 0;
        String content = null;
        String imgloc = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "u_id" -> u_id = reader.nextString();
                case "author" -> author = reader.nextString();
                case "u_img" -> u_img = reader.nextString();
                case "tag" -> tag = reader.nextString();
                case "comments" -> comments = reader.nextString();
                case "likes" -> likes = reader.nextInt();
                case "repost" -> repost = reader.nextInt();
                case "content" -> content = reader.nextString();
                case "imgloc" -> imgloc = reader.nextString();
            }
        }
        reader.endObject();
        return new Post(u_id,author,u_img,tag,
                comments,likes,repost,content,imgloc);
    }


 */

}
