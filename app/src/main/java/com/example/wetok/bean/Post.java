package com.example.wetok.bean;

public class Post {
    private int p_id;
    private int u_id;
    private String name;
    private String u_img;
    private String comments;
    private int likes;
    private int repost;
    private String content;
    String imgloc;

    public Post(int p_id, int u_id, String name, String u_img, String comments, int likes, int repost,
                String content, String imgloc) {
        this.p_id = p_id;
        this.u_id = u_id;
        this.name = name;
        this.u_img = u_img;
        this.comments = comments;
        this.likes = likes;
        this.repost = repost;
        this.content = content;
        this.imgloc = imgloc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getU_img() {
        return u_img;
    }

    public void setU_img(String u_img) {
        this.u_img = u_img;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
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


}
