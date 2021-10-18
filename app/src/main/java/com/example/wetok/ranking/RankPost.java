package com.example.wetok.ranking;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Xinyu Kang
 *
 * This class ranks the retrieved posts based on three criteria presented in the Pariser talk:
 *   1. Relevance
 *   2. Importance
 *   3. Chanlleging (user similarity)
 * using Template design pattern: ScoreTemplate(abstract class), RelevanceScore(concrete class), ImportanceScore(concrete class), UserSimilarityScore(concrete class).
 */
public class RankPost {

    /*
    The ranked posts.
    Calculated when create a RankPost object,
    i.e. RankPost rankPost = new RankPost(...)
         Set<Post> posts = rankPost.rankedPosts
     */
    public final Set<Post> rankedPosts;

    // the weights for relevancy, importance, user similarity
    float[] weights = {0.6f, 0.2f, 0.2f};
    // the weights of importance factor: time, followers, likes
    float[] weightsImp = {0.7f, 0.2f, 0.1f};
    // the weights of user similarity factor: subscriber similarity, post similarity, location similarity
    float[] weightsSim = {0.5f, 0.4f, 0.1f};

    private final User currentUser;
    private final HashSet<String> query;
    private final HashSet<Post> retrievedPosts;

    /**
     *
     * @param currentUser the user who made the query
     * @param query a list of searched tags
     * @param retrievedPosts a map of the retrieved posts and their scores
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public RankPost(User currentUser, HashSet<String> query, HashSet<Post> retrievedPosts){
        this.currentUser = currentUser;
        this.query = query;
        this.retrievedPosts = retrievedPosts;
        this.rankedPosts = toRankedPosts();
    }

    /**
     *
     * @return the posts ranked by their scores
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Set<Post> toRankedPosts(){
        ScoreTemplate relevanceScore = new RelevanceScore(currentUser, query, retrievedPosts);
        ScoreTemplate importanceScore = new ImportanceScore(currentUser, query, retrievedPosts, weightsImp);
        ScoreTemplate similarityScore = new UserSimilarityScore(currentUser, query, retrievedPosts, weightsSim);
        Map<Post, Float> rel = relevanceScore.getNormalizedScore();
        Map<Post, Float> imp = importanceScore.getNormalizedScore();
        Map<Post, Float> sim = similarityScore.getNormalizedScore();

        HashMap<Post, Float> scores = new HashMap<>();
        for (Map.Entry<Post, Float> entry : rel.entrySet()){
            Post post = entry.getKey();
            float rel_score = entry.getValue();
            float imp_score = imp.get(post);
            float sim_score = sim.get(post);
            float score = weights[0] * rel_score + weights[1] * imp_score + weights[2] * sim_score;
            scores.put(post, score);
        }
        return sortByValue(scores).keySet();
    }

    /**
     * Originally from: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
     * @param hm hashmap
     * @return sorted hashmap
     */
    public LinkedHashMap<Post, Float> sortByValue(HashMap<Post, Float> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Post, Float> > list = new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Post, Float> >() {
            public int compare(Map.Entry<Post, Float> o1,Map.Entry<Post, Float> o2){
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        LinkedHashMap<Post, Float> temp = new LinkedHashMap<>();
        for (Map.Entry<Post, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
