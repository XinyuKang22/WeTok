package com.example.wetok.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Post implements Serializable {
    private String u_id;
    private String author;
    private String u_img;
    private String tag;
    private String comments;
    private int likes;
    private int repost;
    private String content;
    String imgloc;
    public User user;

    public Post(){}

    public Post(String u_id, String author, String u_img, String tag, String comments, int likes, int repost,
                String content, String imgloc) {
        this.u_id = u_id;
        this.author = author;
        this.u_img = u_img;
        this.tag = tag;
        this.comments = comments;
        this.likes = likes;
        this.repost = repost;
        this.content = content;
        this.imgloc = imgloc;
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

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
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
        result.put("uid", u_id);
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

}
