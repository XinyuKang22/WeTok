package com.example.wetok.dao;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

public class PostDao implements Serializable {
//    public static InformationResource info = new InformationResource();
    public static List<Post> posts = null;

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
    /*
    public static List<Post> posts = info.getPosts();
    // TODO:post根据tag做map, 去InfoRes写然后过来读
    public static HashMap tag_map = new HashMap();
    */


    public void deletePost(Post p){
        posts.remove(p);
    }

}


