package com.example.wetok.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    public List<User> getFriends() {
        return friends;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public void setPosts(List<Post> posts) { this.posts = posts;
    }

    //add post
    public void addPosts(Post pos) {
        posts.add(pos);
    }

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
        result.put("subscribers", subscribers);
        result.put("posts",posts);
        result.put("address",address);
        result.put("email",email);
        result.put("phone",phone);
        result.put("imgloc",imgloc);

        return result;
    }

    public String toString(List<Post> posts) {
        String res = "size = " + posts.size() +"; content = {";
        for (Post p : posts) {
            res += p;
            res += ", ";
        }
        return res+="}";
    }

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
