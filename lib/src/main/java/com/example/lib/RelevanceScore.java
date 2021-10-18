package com.example.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RelevanceScore {

    // TODO: 连接搜索结果
    Map<Integer, Post> posts;
    ArrayList<String> allPosts;
    ArrayList<String> query;


    public static int getTermFrequency(String tag, String post){
        return post.length() - post.toLowerCase().replaceAll(tag,"").length();
    }

    /**
     *
     * @param query: a list of query tokens
     * @param allPosts: a list of all posts
     * @return a map of query tokens and their corresponding document frequencies
     */
    public static Map<String, Integer> getDocumentFrequency(ArrayList<String> query, ArrayList<String> allPosts){

        Map<String, Integer> documentFrequencies = new HashMap<>();
        for (String s : query){
            documentFrequencies.put(s, 0);
        }

        for (String post : allPosts){
            for (String s : query){
                if (post.toLowerCase().contains(s)) documentFrequencies.replace(s,documentFrequencies.get(s) + 1);
            }
        }
        return documentFrequencies;
    }

    /**
     *
     * @param query: a list of query tokens
     * @param searchResults: a list of retrieved posts
     * @param allPosts: a list of all posts
     * @param N: the total number of posts
     * @return a map of the retrieved posts and their tf-idf scores
     */
    public static Map<String, Float> getScore(ArrayList<String> query, ArrayList<String> searchResults,ArrayList<String> allPosts, int N){
        Map<String, Integer> df_map = getDocumentFrequency(query, allPosts);

        Map<String, Float> scores = new HashMap<>();
        for (String post : searchResults){
            float score = 0;

            for (String tag : query){
                int tf = getTermFrequency(tag, post);
                // modified tf-idf, added 1 in document frequency to avoid invalid denominator problem, will NOT affect the ranking result
                float tf_idf = (float) (tf * Math.log(N / (1.0+df_map.get(tag))));
                score = score + tf_idf;
            }
            scores.put(post, score);
        }
        return scores;
    }

    public static void main(String[] args) {
        String[] searchResults = {
                "Weekend family time ",
                "Sundays at home ",
                "Weekends expolring ",
                "Weekends well spent ",
                "London weekends ",
                "Spent the weekend exploring with this little one ",
                "Sundays at home ",
                "Kicking off the Valentines weekend ",
                "Bring on the weekend ",
                "culture weekends exploring ",
                "Spent the weekend exploring with this little one ",
                "Sundays at home ",
                "Weekends expolring ",
                "Weekend family time ",
                "Before \u0026 after weekend feeding ",
                "A little weekend love from Australia ",
                "Before \u0026 after weekend feeding ",
                "Before \u0026 after weekend feeding ",
                "Before \u0026 after weekend feeding ",
                "Sundays at home ",
                "Weekends well spent ",
                "Before \u0026 after weekend feeding ",
                "Weekends well spent ",
                "I grew up in a family that took photos of everything "
        };

        String[] query = {"weekend","family"};

        Map<String, Float> scores = getScore(new ArrayList<>(Arrays.asList(query)),new ArrayList<>(Arrays.asList(searchResults)),new ArrayList<>(Arrays.asList(searchResults)), searchResults.length);
        for (Map.Entry<String, Float> entry : scores.entrySet()){
            System.out.println(entry.getKey() + " ---- " + entry.getValue());
        }
    }
}
