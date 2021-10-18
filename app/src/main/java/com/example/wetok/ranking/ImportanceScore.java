package com.example.wetok.ranking;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.UserDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Xinyu Kang
 * This class evaluate the importance of the retrieved posts from three aspects:
 *   1. post time
 *   2. post likes
 *   3. sender's followers
 * We expect the posts with more likes, the posts sent by users with more followers, and the latest posts to be more important to society,
 * and thus have higher ranking scores.
 */
public class ImportanceScore extends ScoreTemplate {

    // the weights of importance factor: time, followers, likes
    private final float[] weights;

    public ImportanceScore(User currentUser, HashSet<String> query, HashSet<Post> retrievedPosts, float[] weights){
        super(currentUser, query, retrievedPosts);
        this.weights = weights;
    }

    // the weights of importance factor: time, followers, likes
    //final float[] weights = {0.7f, 0.2f, 0.1f};

    /**
     *
     * @return a map of the retrieved posts and their importance scores
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Map<Post, Float> getScore() {
        Map<Post, Float> score_map = new HashMap<>();
        for (Post post : retrievedPosts){
            float time_score = timeScore(post);
            float follower_score = followerScore(post);
            float like_score = likeScore(post);
            float score = (float) ( weights[0] * time_score + weights[1] * follower_score + weights[2] * like_score);
            score_map.put(post, score);
        }
        return score_map;
    }

    /**
     *
     * @param post the post
     * @return the time score of the post; range = (0,1], the posts sent today have score = 1
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public float timeScore(Post post){
        LocalDate postTime = LocalDate.parse(post.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDate currentTime = LocalDate.now();
        int days = (int) ChronoUnit.DAYS.between(postTime, currentTime);
        return 1f / (1 + days);
    }

    /**
     *
     * @param post the post
     * @return the follower score of the post; range = [0,1], if the sender's followers >= 10000 then score = 1, if the sender's followers <= 10 then score = 0
     */
    public float followerScore(Post post){
        User user = UserDao.findUserById(post.getUid());

        assert user != null;
        if (user.getFollowers().isEmpty()){
            return 0;
        }
        int score_bf_scale = user.getFollowers().size();
        float score_aft_scale = (float) (Math.log10(score_bf_scale)/4);
        if (score_aft_scale < 0){
            return 0f;
        }
        if (score_aft_scale > 1){
            return 1f;
        }
        return score_aft_scale;
    }

    /**
     *
     * @param post the post
     * @return the like score of the post; range = [0,1], if the post's likes >= 10000 then score = 1, if the post's likes <= 10 then score = 0
     */
    public float likeScore(Post post){
        int score_bf_scale = post.getLikes();
        float score_aft_scale = (float) (Math.log10(score_bf_scale)/4);
        if (score_aft_scale < 0){
            return 0f;
        }
        if (score_aft_scale > 1){
            return 1f;
        }
        return score_aft_scale;
    }

}
