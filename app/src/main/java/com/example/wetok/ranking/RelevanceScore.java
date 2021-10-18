package com.example.wetok.ranking;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xinyu Kang
 * This class evaluate the relecance of the retrieved posts and the query by calculating the tf-idf scores.
 */
public class RelevanceScore extends ScoreTemplate{

    /**
     *
     * @param currentUser the user who made the query
     * @param query a list of searched tags
     * @param retrievedPosts a list of retrieved posts
     * @return a map of the retrieved posts and their relevance scores
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<Post, Float> getScore(User currentUser, ArrayList<String> query, ArrayList<Post> retrievedPosts) {
        // TODO: allPosts need to modify
        ArrayList<Post> allPosts = retrievedPosts;
        int N = allPosts.size();

        Map<String, Integer> df_map = getDocumentFrequency(query, allPosts);

        Map<Post, Float> scores = new HashMap<>();
        for (Post post : retrievedPosts){
            float score = 0;

            for (String tag : query){
                int tf = getTermFrequency(tag, post.getContent());
                // modified tf-idf, added 1 in document frequency to avoid invalid denominator problem, will NOT affect the ranking result
                float tf_idf = (float) (tf * Math.log(N / (1.0+df_map.get(tag))));
                score = score + tf_idf;
            }
            scores.put(post, score);
        }
        return scores;
    }

    /**
     *
     * @param tag the tag
     * @param post post content
     * @return term frequency of the tag in the post content
     */
    public static int getTermFrequency(String tag, String post){
        return post.length() - post.toLowerCase().replaceAll(tag,"").length();
    }

    /**
     *
     * @param query: a list of query tokens
     * @param allPosts: a list of all posts
     * @return a map of query tokens and their corresponding document frequencies
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Map<String, Integer> getDocumentFrequency(ArrayList<String> query, ArrayList<Post> allPosts){

        Map<String, Integer> documentFrequencies = new HashMap<>();
        for (String s : query){
            documentFrequencies.put(s, 0);
        }

        for (Post post : allPosts){
            for (String s : query){
                if (post.getContent().toLowerCase().contains(s)) documentFrequencies.replace(s,documentFrequencies.get(s) + 1);
            }
        }
        return documentFrequencies;
    }

}
