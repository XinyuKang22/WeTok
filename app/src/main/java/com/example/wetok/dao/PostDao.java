package com.example.wetok.dao;

import com.example.wetok.bean.Post;
import com.example.wetok.resources.InformationResource;

import java.util.HashMap;
import java.util.List;

public class PostDao {
    public static InformationResource info = new InformationResource();
    public static List<Post> posts = info.getPosts();
    // TODO:post根据tag做map, 去InfoRes写然后过来读
    public static HashMap tag_map = new HashMap();




    public static Post findUserByUid(String id) {
        List<Post> post_list = null;
        for (Post p: posts) {
            if (p.getUid().equals(id)) {
                post_list.add(p);
            }
        }
        return null;
    }



}
