package com.example.wetok.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * The user class
 * @author Xinyue Hu
 * @author Yuxin Hong
 */
public class User implements Serializable {
    private String id;
    private String name;
    private String password;
    private String gender;
    private int age;
    private List<User> followers;
    private List<User> subscribers;
    private List<User> friends;
    private List<Post> posts;
    private String address;
    private String email;
    private String phone;
    private String imgloc;

    public User(){}

    public User(String name){
        this.name = name;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
    
    public User(String id, String name, String password, String gender, int age,
                List<User> followers, List<User> subscribers, List<Post> posts,
                String address, String email, String phone, String imgloc) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.followers = followers;
        this.subscribers = subscribers;
        this.posts = posts;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.imgloc = imgloc;
    }

    public SimpleDateFormat readTime(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.parse(time);
        return sdf;
    }

    public Boolean isSubscriber(User u) {
        return subscribers.contains(u);
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) { this.posts = posts;}

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) { this.followers = followers;}

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {this.subscribers = subscribers;}

    public void addSubscribers(User subscriber) {subscribers.add(subscriber);}

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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgloc() {
        return imgloc;
    }

    public void setImgloc(String imgloc) {
        this.imgloc = imgloc;
    }

    /**
     * Show number of posts published by each user
     * @param posts
     * @return String
     */
    public String toString(List<Post> posts) {
        String res = "size = " + posts.size() +"; content = {";
        for (Post p : posts) {
            res += p;
            res += ", ";
        }
        return res+="}";
    }

    /**
     * Show the information of each post
     * @return String
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", followers=" + followers +
                ", Subscribers=" + subscribers +
                ", posts=" + toString(posts) +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", imgloc='" + imgloc + '\'' +
                '}';
    }
}
