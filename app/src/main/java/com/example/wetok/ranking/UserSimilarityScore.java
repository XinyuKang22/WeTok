package com.example.wetok.ranking;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xinyu Kang
 * This class evaluate the similarity between the senders of retrieved posts and the current user who made the query from three aspects:
 *   1. users' subscribers
 *   2. users' posts
 *   3. users' addresses
 * The post sent by the user who is considered as more 'similar' with the current user will have a lower ranking score.
 * We expect to use this algotithm to offset some negative effects of the 'Filter Bubbles'.
 */
public class UserSimilarityScore extends ScoreTemplate{

    // the weights of user similarity factor: subscriber similarity, post similarity, location similarity
    private final float[] weights;

    public UserSimilarityScore(User currentUser, List<String> query, List<Post> retrievedPosts, float[] weights){
        super(currentUser, query, retrievedPosts);
        this.weights = weights;
    }

    /**
     *
     * @return a map of the retrieved posts and the similarity score between their senders and the current user
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<Post, Float> getScore() {

        // deal with guest login
        if (currentUser == null){
            Map<Post, Float> postScoreMap = new HashMap<>();
            for (Post post:retrievedPosts){
                postScoreMap.put(post, 0f);
            }
            return postScoreMap;
        }

        // create a <user, post list> map
        Map<User, ArrayList<Post>> userPostMap = new HashMap<>();
        for (Post post: retrievedPosts){
            User user = UserDao.findUserByEmail(post.getEmail());
            if (!userPostMap.containsKey(user)) userPostMap.put(user, new ArrayList<>());
            userPostMap.get(user).add(post);
        }

        // process current user first to save runtime
        // create hashmap for {subscriber id:-1} and {tag:-1}
        Map<Integer, Integer> subIDs = getSubIds(currentUser);
        Map<String, Integer> tags = getTags(currentUser);
        String city = currentUser.getAddress();

        // process every user in the <user, post list> map
        // and calculate the similarity score between the user and the current user
        Map<Post, Float> postScoreMap = new HashMap<>();
        for (Map.Entry<User, ArrayList<Post>> entry : userPostMap.entrySet()){
            User user = entry.getKey();
            float subscriber_similarity = subscriberSimilarity(subIDs, user);
            float post_similarity = postSimilarity(tags, user);
            int location_similarity = locationSimilarity(city, user);
            float score = weights[0] * subscriber_similarity + weights[1] * post_similarity + weights[2] * location_similarity;
            for (Post post : entry.getValue()){
                postScoreMap.put(post, score);
                System.out.println(post.getTag()+": "+score);
            }
        }
        return postScoreMap;
    }

    /**
     *
     * @param subID1 a map of subscriber ids for user1, i.e., (id:-1)
     * @param user2 the user to compare
     * @return the subscriber similarity = 1 / (1 + count of same subscribers between user1 and user2)
     */
    public float subscriberSimilarity(Map<Integer, Integer> subID1, User user2){

        // convert user2 to a collection of subscriber ids
        Map<Integer, Integer> subID2 = getSubIds(user2);

        // find the larger & the smaller subscriber maps
        Map<Integer, Integer> max;
        Map<Integer, Integer> min;
        if (subID1.size() > subID2.size()){
            max = subID1;
            min = subID2;
        }else {
            max = subID2;
            min = subID1;
        }

        // count the common subscribers
        int count = 0;
        for (int id : min.keySet()){
            if (max.get(id) != null) count ++;
        }

        return 1f / (1+count);
    }

    /**
     *
     * @param tags1 a map of tags for user1's posts, i.e., (tag:-1)
     * @param user2 the user to compare
     * @return the post similarity = 1 / (1 + count of same tags between the posts of user1 and user2)
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public float postSimilarity(Map<String, Integer> tags1, User user2){

        // convert user2 to a map of tags
        Map<String, Integer> tags2 = getTags(user2);

        // find the larger & the smaller tag map
        Map<String, Integer> max;
        Map<String, Integer> min;
        if (tags1.size() > tags2.size()){
            max = tags1;
            min = tags2;
        }else {
            max = tags2;
            min = tags1;
        }

        // count the common tags
        int count = 0;
        for (String tag : min.keySet()){
            if (max.get(tag) != null) count ++;
        }
        return 1f / (1 +count);
    }

    /**
     *
     * @param city1 the address of user1
     * @param user2 the user to compare
     * @return the location similarity = 1 if user1 and user2 are in same city and = 0 otherwise
     */
    public int locationSimilarity(String city1, User user2){
        String city2 = user2.getAddress();

        // find the longer & shorter city name
        String max;
        String min;
        if (city1.length() > city2.length()){
            max = city1;
            min = city2;
        }else {
            max = city2;
            min = city1;
        }

        // check if they have same part
        if (max.toLowerCase().contains(min.toLowerCase())){
            System.out.println("address: have same part");
            return 1;
        }else {
            System.out.println("address: have no same part");
            return 0;
        }
    }

    /**
     *
     * @param user the user
     * @return a map of (subscriber id:-1)
     */
    public Map<Integer, Integer> getSubIds(User user){
        Map<Integer, Integer> subIDs = new HashMap<>();
        if (!user.getSubscribers().isEmpty()){
            for (User sub: user.getSubscribers()){
                subIDs.put(Integer.parseInt(sub.getId()), -1);
            }
        }
        return subIDs;
    }

    /**
     *
     * @param user the user
     * @return a map of (tag:-1)
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String, Integer> getTags(User user){
        Map<String, Integer> tags = new HashMap<>();
        for (Post post : user.getPosts()){
            for (String tag : post.getTag()){
                tags.putIfAbsent(tag, -1);
            }
        }
        return tags;
    }

}
