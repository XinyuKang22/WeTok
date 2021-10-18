package com.example.lib;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportanceScore {


    // TODO: 连接搜索结果
    Map<Integer, Post> posts;
    ArrayList<String> allPosts;
    ArrayList<String> query;

    // importance部分思路：
    // 发帖时间更近的更重要
    // 发帖者粉丝更多的更重要
    // 帖子赞数更多的更重要
    static float[] weights = {0.7f, 0.2f, 0.1f};

    public static float timeScore(Post post){
        LocalDate postTime = LocalDate.parse(post.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDate currentTime = LocalDate.now();
        int days = (int) ChronoUnit.DAYS.between(postTime, currentTime);
        return 1f / (1 + days);
    }

    public static float followerScore(Post post){
        //User user = UserDao.findUserById(post.getUid());

        // 临时建立的user，之后应该使用上面那句
        User user = new User();
        User a = new User();
        User b = new User();
        User c = new User();
        List<User> fol = new ArrayList<>();
        fol.add(a);
        fol.add(b);
        fol.add(c);
        user.setFollowers(fol);

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

    public static float likeScore(Post post){
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

    public static Map<Integer, Float> getScore(Map<Integer, Post> posts){
        Map<Integer, Float> score_map = new HashMap<>();
        for (Map.Entry<Integer, Post> entry : posts.entrySet()){
            float time_score = timeScore(entry.getValue());
            float follower_score = followerScore(entry.getValue());
            float like_score = likeScore(entry.getValue());
            float score = (float) ( weights[0] * time_score + weights[1] * follower_score + weights[2] * like_score);
            score_map.put(entry.getKey(), score);
        }
        return score_map;
    }

    public static void main(String[] args) {

        Post post = new Post();
        post.setUid("0");
        post.setLikes(100);
        post.setTime("2020-10-18 14:02");

        System.out.println(timeScore(post));
        System.out.println(followerScore(post));
        System.out.println(likeScore(post));
    }
}
