package com.example.wetok.bean;

import com.example.wetok.dao.CurrentUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private String tag;
    private String comments;

    // post property
    private int likes;
    private int star = 0;
    private int repost;

    String imgloc;
    public User user;



    @Override
    public String toString() {
        return content;
    }

    public Post(){}

    public Post(String uid, String author, String u_img, String time, String tag, String comments, int likes, int repost,
                String content, String imgloc) {
        this.uid = uid;
        this.author = author;
        this.u_img = u_img;
        this.time = time;
        this.tag = tag;
        this.comments = comments;
        this.likes = likes;
        this.repost = repost;
        this.content = content;
        this.imgloc = imgloc;
    }



    public Post(String content, String uid, String author, String email, String u_img, String time, String tag, String comments, int likes, int star, int repost) {
        this.content = content;
        this.uid = uid;
        this.author = author;
        this.email = email;
        this.u_img = u_img;
        this.time = time;
        this.tag = tag;
        this.comments = comments;
        this.likes = likes;
        this.star = star;
        this.repost = repost;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }
    //add likes
    public void setLikes(int likes) {
        this.likes += likes;
    }

    public int getRepost() {
        return repost;
    }
    //add repost
    public void setRepost(int repost) {
        this.repost += repost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgloc() {
        return imgloc;
    }

    public void setImgloc(String imgloc) {
        this.imgloc = imgloc;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("uimg", u_img);
        result.put("tag", tag);
        result.put("comments", comments);
        result.put("likes", likes);
        result.put("reposts", repost);
        result.put("content", content);
        result.put("imgloc",imgloc );

        return result;
    }

    @Override
    public int compareTo(Object o) {
        String o_time = ((Post) o).getTime().replace(":","");
        String this_time = this.getTime().replace(":","");
        return Integer.parseInt(o_time) - Integer.parseInt(this_time);
    }
}
