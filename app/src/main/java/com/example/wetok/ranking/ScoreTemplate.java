package com.example.wetok.ranking;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Xinyu Kang
 * This is the abstract class in Template design pattern
 */
public abstract class ScoreTemplate {
    User currentUser;
    HashSet<String> query;
    HashSet<Post> retrievedPosts;

    /**
     *
     * @param currentUser the user who made the query
     * @param query a list of searched tags
     * @param retrievedPosts a map of the retrieved posts and their scores
     */
    public ScoreTemplate(User currentUser, HashSet<String> query, HashSet<Post> retrievedPosts){
        this.currentUser = currentUser;
        this.query = query;
        this.retrievedPosts = retrievedPosts;
    }

    /**
     *
     * @return a map of the retrieved posts and their scores
     */
    public abstract Map<Post, Float> getScore();

}
