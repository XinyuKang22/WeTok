package com.example.rankingTest;

import static org.junit.Assert.assertTrue;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.PostDao;
import com.example.wetok.dao.UserDao;
import com.example.wetok.ranking.RelevanceScore;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Yuxin Hong
 * This class tests RelevanceScore.java class
 */

public class RelevanceScoreTest {
    @BeforeClass
    public static void setup() {
        // attributes
        String content1 = "COMP2100";
        String content2 = "COMP2100 is useful";
        String content3 = "COMP2100 is hard";
        String content4 = "COMP3670 is useful";


        String uid = "0";
        String author = "Yuxin";
        String email = "u123456@anu.edu.au";
        String u_img = "default";
        String time1 = "2021-10-20 14:44:00";
        String time2 = "2021-10-19 14:44:00";
        List<String> tag1 = Arrays.asList("#COMP2100");
        List<String> tag2 = Arrays.asList("#COMP2100", "#useful");
        List<String> tag3 = Arrays.asList("#COMP2100", "#hard");
        List<String> tag4 = Arrays.asList("#COMP3670", "#useful");

        int like1 = 50;
        int like2 = 10;
        int star = 0;
        User empty = new User();
        List<User> followers1 = Arrays.asList();
        List<User> followers2 = Arrays.asList(empty, empty, empty);
        List<User> subscribers = Arrays.asList();

        // u1's post
        Post p1 = new Post(content1, uid, author, email, u_img, time1, tag1, like1, star);
        Post p2 = new Post(content2, uid, author, email, u_img, time2, tag2, like1, star);
        Post p3 = new Post(content3, uid, author, email, u_img, time2, tag3, like2, star);

        List<Post> lp1 = Arrays.asList(p1, p2, p3);

        // u2's post
        Post p4 = new Post(content4, "1", author, email, u_img, time2, tag4, like2, star);

        // create user
        User u1 = new User(uid, author, "123456", "female", 21,
                followers1, subscribers, lp1, "Canberra", email, "123", u_img);

        User u2 = new User("2", author, "123456", "female", 21,
                followers2, subscribers, Arrays.asList(p4), "Canberra", email, "123", u_img);
        // put in UserDao and PostDao
        UserDao.users = Arrays.asList(u1, u2);
        UserDao.users_size = 2;
        PostDao.posts = Arrays.asList(p1, p2, p3, p4);
        PostDao.post_size = 4;
    }

    // get User and Post
    User u1 = UserDao.findUserById(0);
    Post p1 = PostDao.getPosts().get(0);
    Post p2 = PostDao.getPosts().get(1);
    Post p3 = PostDao.getPosts().get(2);
    Post p4 = PostDao.getPosts().get(3);


    @Test
    public void singleTagRelevantScoreTest() {
        // create hashmap
        List<String> q = Arrays.asList("useful");
        RelevanceScore i = new RelevanceScore(u1, q, PostDao.getPosts());
        Map<Post, Float> m = i.getScore();
        assertTrue(m.get(p4) > m.get(p3));
    }

    @Test
    public void twoTagRelevantScoreTest() {
        // create hashmap
        List<String> q = Arrays.asList("comp2100", "useful");
        RelevanceScore i = new RelevanceScore(u1, q, PostDao.getPosts());
        Map<Post, Float> m = i.getScore();
        assertTrue(m.get(p1) < m.get(p2));
    }
}
