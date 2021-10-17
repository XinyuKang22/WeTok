package com.example.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RelevanceScore {
    // TODO: 连接搜索结果（posts)
    ArrayList<String> searchResults;

    // TODO: 搜索结果总数
    int N;

    // TODO: 搜索的tag放在一个list里
    ArrayList<String> query;

    public static ArrayList<String> tokenizePost(String post){
        String delimiters = "[-\\t,; .?!:@'\\[\\](){}_*/]";
        String[] tokens = post.split(delimiters);
        ArrayList<String> toReturn = new ArrayList<>();
        for (String s : tokens){
            if (!s.trim().isEmpty()){
                toReturn.add(s.toLowerCase());
            }
        }
        return toReturn;
    }

    public static void main(String[] args) {
        ArrayList<String> tokens = tokenizePost("welcome home@! Smith's!");
        for (String s : tokens){
            System.out.println(s);
        }
    }


    public Map<Integer, Float> score(ArrayList<String> searchResults, ArrayList<String> query){

        // initialize a map of <post_id, tf-idf score>
        Map<Integer, Float> scores = new HashMap<>();
        for (int i = 0; i < N; i ++){
            scores.put(i, 0F);
        }

        // accumulate the tf-idf scores for each tag in the query
        for (String tag : query){

            // initialize the document frequency to be 1 instead of 0
            // will NOT affect the ranking results, but avoid the potential illegal denominator problem
            int documentFrequency = 1;

            // a map of <post_id, term_frequency>
            Map<Integer, Integer> termFrequencies = new HashMap<>();

            int id = 0;
            for (String post : searchResults){
                termFrequencies.put(id, 0);
                id ++;
                ArrayList<String> tokens = tokenizePost(post);

                // accumulate the term frequency in this post
                for (String token: tokens){
                    if (token.equals(tag)) termFrequencies.replace(id, termFrequencies.get(id) + 1);
                }

                // if the tag appears in this post at least once, increase the document frequency by 1
                if (termFrequencies.get(id) > 0) documentFrequency++;
            }

            // calculate the inverse document frequency for this tag
            float idf = (float) Math.log(N / (documentFrequency+0.0));

            // update the accumulated tf-idf score for each post
            for (Map.Entry<Integer, Float> entry : scores.entrySet()){
                int tf = termFrequencies.get(entry.getKey());
                float tf_idf = (tf * idf);
                entry.setValue(entry.getValue() + tf_idf);
            }
        }
        return scores;
    }

}
