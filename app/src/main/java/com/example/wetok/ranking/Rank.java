package com.example.wetok.ranking;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import java.util.HashSet;

/**
 * @author Xinyu Kang
 */
public class Rank {

    User currentUser;
    HashSet<Post> retrievedPosts;
    HashSet<String> query;
    // the weights of importance factor: time, followers, likes
    final float[] weightsImp = {0.7f, 0.2f, 0.1f};
    // the weights of user similarity factor: subscriber similarity, post similarity, location similarity
    final float[] weightsSim = {0.5f, 0.4f, 0.1f};

    public HashSet<Post> toRankedPosts(){
        ScoreTemplate relevanceScore = new RelevanceScore(currentUser, query, retrievedPosts);
        ScoreTemplate importanceScore = new ImportanceScore(currentUser, query, retrievedPosts, weightsImp);
        ScoreTemplate similarityScore = new UserSimilarityScore(currentUser, query, retrievedPosts, weightsSim);
        return null;
    }
}
