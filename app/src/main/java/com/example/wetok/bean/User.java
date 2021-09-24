package com.example.wetok.bean;

import com.example.wetok.R;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    public int photo = R.drawable.photo;
    private int id;
    private String name;
    private String password;
    private String gender;
    private int age;
    private List<User> followers;
    private List<User> Subscribers;
    private List<Post> posts;
    private String address;
    private String emial;
    private String phone;
    private String imgloc;

    public User(String name, int id){
        this.name = name;
        this.id = id;
    }

    public User(int id, String name, String password, String gender, int age, List<User> followers, List<User> subscribers, List<Post> posts,
                String address, String emial, String phone, String imgloc) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.followers = followers;
        this.Subscribers = subscribers;
        this.posts = posts;
        this.address = address;
        this.emial = emial;
        this.phone = phone;
        this.imgloc = imgloc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Post> getPosts() {
        return posts;
    }
    //add post
    public void setPosts(Post pos) {
        posts.add(pos);
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(User follower) {
        this.followers.add(follower);
    }

    public List<User> getSubscribers() {
        return Subscribers;
    }

    public void setSubscribers(User subscriber) {
        this.Subscribers.add(subscriber);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgloc() {
        return imgloc;
    }

    public void setImgloc(String imgloc) {
        this.imgloc = imgloc;
    }
}
