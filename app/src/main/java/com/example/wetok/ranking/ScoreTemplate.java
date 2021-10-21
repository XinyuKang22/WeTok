package com.example.wetok.ranking;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Xinyu Kang
 *
 * This is an abstract class in Template design pattern, presenting the main structure of the scoring process
 */
public abstract class ScoreTemplate {
    final User currentUser;
    final List<String> query;
    final List<Post> retrievedPosts;

    /**
     * @param currentUser the user who made the query
     * @param query a list of searched tags
     * @param retrievedPosts a map of the retrieved posts and their scores
     */
    public ScoreTemplate(User currentUser, List<String> query, List<Post> retrievedPosts){
        this.currentUser = currentUser;
        this.query = query;
        this.retrievedPosts = retrievedPosts;
    }

    /**
     *
     * @return a map of the retrieved posts and their scores
     */
    public abstract Map<Post, Float> getScore();

    /**
     *
     * @return the normalized post:score map
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<Post, Float> getNormalizedScore(){
        Map<Post, Float> map = getScore();
        float max = Collections.max(map.values());
        float min = Collections.min(map.values());
        for (Map.Entry<Post, Float> entry : map.entrySet()){
            float normalizedScore = (entry.getValue() - min) / (max - min);
            map.replace(entry.getKey(), normalizedScore);
        }
        return map;
    }
}
