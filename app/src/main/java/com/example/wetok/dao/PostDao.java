package com.example.wetok.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;
import com.google.android.gms.common.internal.Objects;

import java.io.Serializable;
import java.net.PortUnreachableException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Locale;

public class PostDao implements Serializable {

    public static List<Post> posts = null;
    public static int post_size = 0;

    public PostDao(List<Post> p) {
        posts = p;
    }

    public static List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post p){
           posts.add(p);
    }

    public List<Post> findPostByTag(String tag){
         List<Post> sameTag = new ArrayList<>();
          for(Post p: posts){
              if(p.getTag().equals(tag)){
                  sameTag.add(p);
              }
          }
        return sameTag;
    }

    public User findUserById(String id) {
        List<User> users = UserDao.users;
        for (User u : users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public static void addPost(String content,Context activity) {
        User u = CurrentUser.current_user;
        if(u == null) {
            Toast.makeText(activity,"Please login first.",Toast.LENGTH_LONG).show();
        }else {
            String uid = u.getId();
            String author = u.getName();
            String email = u.getEmail();
            String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            String u_img = u.getImgloc();
            List<String> content_list = Arrays.asList(content.split("#"));
            String tag;
            if (content_list.size() > 1) {
                //多个tag要改这里
                tag = "#" + content_list.get(1);
            } else {
                tag = "#no_tag";
            }
            tag = tag.trim();

            Post po = new Post(content, uid, author, email, u_img, time, tag, "", 0, 0, 0);
            // User: add new post to user
            List<Post> user_post = CurrentUser.current_user.getPosts();
            user_post.add(findInsertIndex(user_post), po);

            // database: add new post, update post size
            posts.add(findInsertIndex(posts), po);
            post_size += 1;
        }
    }



    public void deletePost(Post p){
        posts.remove(p);
    }

    public static int findInsertIndex(List<Post> posts ) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Post p;
        for (int i = 0; i < posts.size(); i++) {
            p = posts.get(i);
            if (Integer.parseInt(p.getTime().replaceAll("[-: ]","")) <=
                    Integer.parseInt(time.replaceAll("[-: ]",""))) {
                return i;
            }
        }
        return post_size;
    }

    @Override
    public String toString() {
        String res = "";
        for (Post p : posts) {
            res += p.getContent();
        }
        return res;
    }
}


