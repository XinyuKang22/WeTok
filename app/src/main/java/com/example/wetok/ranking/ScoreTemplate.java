package com.example.wetok.ranking;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Xinyu Kang
 * This is the abstract class in Template design pattern
 */
public abstract class ScoreTemplate {

    /**
     *
     * @param currentUser the user who made the query
     * @param query a list of searched tags
     * @param retrievedPosts a list of retrieved posts
     * @return a map of the retrieved posts and their scores
     */
    public abstract Map<Post, Float> getScore(User currentUser, ArrayList<String> query, ArrayList<Post> retrievedPosts);


}
