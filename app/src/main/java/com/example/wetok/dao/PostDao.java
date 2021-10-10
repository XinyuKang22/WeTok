package com.example.wetok.dao;

import com.example.wetok.bean.Post;
import com.example.wetok.resources.InformationResource;

import java.util.List;

public class PostDao {
    public static InformationResource info = new InformationResource();
    List<Post> posts = info.getPosts();
}
