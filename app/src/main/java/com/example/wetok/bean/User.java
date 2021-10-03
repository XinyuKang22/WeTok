package com.example.wetok.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private String id;
    private String name;
    private String password;
    private String gender;
    private int age;
    private List<User> followers;
    private List<User> Subscribers;
    private List<Post> posts;
    private String address;
    private String email;
    private String phone;
    private String imgloc;

    public User(){}

    public User(String id, String name, String password, String gender, int age, List<User> followers, List<User> subscribers, List<Post> posts,
                String address, String email, String phone, String imgloc) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.followers = followers;
        this.Subscribers = subscribers;
        this.posts = posts;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.imgloc = imgloc;
    }

    public void setId(String id){this.id = id;}

    public String getId() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("password", password);
        result.put("gender", gender);
        result.put("age", age);
        result.put("followers", followers);
        result.put("subscribers",Subscribers);
        result.put("posts",posts);
        result.put("address",address);
        result.put("email",email);
        result.put("phone",phone);
        result.put("imgloc",imgloc);

        return result;
    }
}
