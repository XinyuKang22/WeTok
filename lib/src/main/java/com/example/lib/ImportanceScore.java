package com.example.lib;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    public float timeScore(Post post){
        LocalDate postTime = LocalDate.parse(post.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDate currentTime = LocalDate.now();
        int days = (int) ChronoUnit.DAYS.between(postTime, currentTime);
        return 1f / (1 + days);
    }

    public float followerScore(Post post){
        User user = UserDao.findUserById(post.getUid());
        assert user != null;
        if (user.getFollowers().isEmpty()){
            return 0f;
        }
        return user.getFollowers().size();
    }

    public float likeScore(Post post){
        return post.getLikes();
    }

    public Map<Integer, Float> getScore(){
        // TODO:
        return null;
    }
}
