package com.example.wetok.dao;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;

import java.util.ArrayList;
import java.util.List;

public class PostDao {
    public static InformationResource info = new InformationResource();
    List<Post> posts = info.getPosts();

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

    public User findUserById(String id){
        List<User> users = UserDao.users;
        for(User u:users){
            if(u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }

    public void setUsers(List<Post> post){
           for(Post p:post){
               p.user = findUserById(p.getU_id());
           }
    }

    public void deletePost(Post p){
        posts.remove(p);
    }

}
