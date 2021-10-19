package com.example.wetok.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Post implements Comparable, Serializable {
    private String content;
    // user info
    private String uid;
    private String author;
    private String email;
    private String u_img;

    // post info
    private String time;
    private List<String> tag;

    // post property
    private int likes;
    private int star = 0;

    public Post(List<String> tag, String content, String id) {
        this.tag = tag;
        this.content = content;
        this.uid = id;
    }

    @Override
    public String toString() {
        return content;
    }

    public Post(){}

    public Post(String uid, String author, String u_img, String time, List<String> tag, String comments, int likes, int repost,
                String content, String imgloc) {
        this.uid = uid;
        this.author = author;
        this.u_img = u_img;
        this.time = time;
        this.tag = tag;
        this.likes = likes;
        this.content = content;
    }



    public Post(String content, String uid, String author, String email, String u_img, String time, List<String> tag, String comments, int likes, int star, int repost) {
        this.content = content;
        this.uid = uid;
        this.author = author;
        this.email = email;
        this.u_img = u_img;
        this.time = time;
        this.tag = tag;
        this.likes = likes;
        this.star = star;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getU_img() {
        return u_img;
    }

    public void setU_img(String u_img) {
        this.u_img = u_img;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getLikes() {
        return likes;
    }
    //add likes
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("uimg", u_img);
        result.put("tag", tag);
        result.put("likes", likes);
        result.put("content", content);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        String o_time = ((Post) o).getTime().replaceAll("[-: ]","");
        String this_time = this.getTime().replaceAll("[-: ]","");
        long diff = Long.parseLong(o_time) - Long.parseLong(this_time);
        if (diff > 0){
            return 1;
        } else if (diff < 0){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        Post p = (Post) o;
        boolean b1 = content.equals(p.getContent());
        boolean b2 = uid.equals(p.getUid());
        return (b1 && b2);
    }
}
