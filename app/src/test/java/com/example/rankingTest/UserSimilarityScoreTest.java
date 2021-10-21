package com.example.rankingTest;

import static org.junit.Assert.assertTrue;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.PostDao;
import com.example.wetok.dao.UserDao;
import com.example.wetok.ranking.RelevanceScore;
import com.example.wetok.ranking.UserSimilarityScore;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserSimilarityScoreTest {
    @BeforeClass
    public static void setup() {
        // attributes
        String content1 = "COMP2100";
        String content2 = "COMP2100 is useful";
        String content3 = "COMP2100 is hard";
        String content4 = "COMP3670 is useful";

        String author1 = "a1";
        String author2 = "a2";
        String author3 = "a3";
        String author4 = "a4";
        String email1 = "u1@anu.edu.au";
        String email2 = "u2@anu.edu.au";
        String email3 = "u3@anu.edu.au";
        String email4 = "u4@anu.edu.au";

        String u_img = "default";
        String time = "2021-10-20 14:44:00";
        List<String> tag1 = Arrays.asList("#COMP2100");
        List<String> tag2 = Arrays.asList("#COMP2100", "#useful");
        List<String> tag3 = Arrays.asList("#COMP2100", "#hard");
        List<String> tag4 = Arrays.asList("#COMP3670", "#useful");
        int like = 50;
        int star = 0;
        User empty = new User();
        List<User> followers1 = Arrays.asList();
        List<User> followers2 = Arrays.asList(empty, empty, empty);

        // user's post
        Post p1 = new Post(content1, "0", author1, email1, u_img, time, tag1, like, star);
        Post p2 = new Post(content2, "1", author2, email2, u_img, time, tag2, like, star);
        Post p3 = new Post(content3, "2", author3, email3, u_img, time, tag3, like, star);
        Post p4 = new Post(content4, "3", author4, email4, u_img, time, tag4, like, star);


        // create user
        User u1 = new User("1", author1, "123456", "female", 21,
                followers1, Arrays.asList(), Arrays.asList(p1), "Canberra", email1,
                "123", u_img);

        User u2 = new User("2", author2, "123456", "female", 21,
                followers2, Arrays.asList(u1), Arrays.asList(p2), "Canberra", email2,
                "123", u_img);

        User u3 = new User("3", author3, "123456", "female", 21,
                followers2, Arrays.asList(u1,u2), Arrays.asList(p3), "Canberra", email3,
                "123", u_img);

        User u4 = new User("4", author4, "123456", "female", 21,
                followers2, Arrays.asList(u1,u2,u3), Arrays.asList(p4), "Sydney", email4,
                "123", u_img);

        // put in UserDao and PostDao
        UserDao.users = Arrays.asList(u1, u2, u3, u4);
        UserDao.users_size = 4;
        PostDao.posts = Arrays.asList(p1, p2, p3, p4);
        PostDao.post_size = 4;
    }

    float[] weight = {(float) 0.3, (float) 0.3, (float) 0.3};

    // get User and Post
    User u4 = UserDao.findUserById(3);
    Post p1 = PostDao.getPosts().get(0);
    Post p2 = PostDao.getPosts().get(1);
    Post p3 = PostDao.getPosts().get(2);
    Post p4 = PostDao.getPosts().get(3);

    @Test
    public void locationRelevantScoreTest() {
        // create hashmap
        List<String> q = Arrays.asList("useful");
        UserSimilarityScore i = new UserSimilarityScore(u4, q, PostDao.getPosts(),weight);
        Map<Post, Float> m = i.getScore();
        assertTrue(m.get(p2) < m.get(p3));
    }

    @Test
    public void subscriberRelevantScoreTest() {
        // create hashmap
        List<String> q = Arrays.asList("useful");
        UserSimilarityScore i = new UserSimilarityScore(u4, q, PostDao.getPosts(),weight);
        Map<Post, Float> m = i.getScore();
        assertTrue(m.get(p2) < m.get(p3));
    }

    @Test
    public void postRelevantScoreTest() {
        // create hashmap
        List<String> q = Arrays.asList("useful");
        UserSimilarityScore i = new UserSimilarityScore(u4, q, PostDao.getPosts(),weight);
        Map<Post, Float> m = i.getScore();
        assertTrue(m.get(p3) > m.get(p2));
    }
}
