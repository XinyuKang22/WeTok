package com.example.rankingTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.PostDao;
import com.example.wetok.dao.UserDao;
import com.example.wetok.ranking.ImportanceScore;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImportanceScoreTest {
    @BeforeClass
    public static void setup(){
        // attributes
        String content = "COMP2100";
        String uid = "0";
        String author = "Yuxin";
        String email = "u123456@anu.edu.au";
        String u_img = "default";
        String time1 = "2021-10-20 14:44:00";
        String time2 = "2021-10-19 14:44:00";
        List<String> tag = Arrays.asList("#weekend","#mood");
        int like1 = 50;
        int like2 = 10;
        int star = 0;
        User empty = new User();
        List<User> followers1 = Arrays.asList();
        List<User> followers2 = Arrays.asList(empty,empty,empty);
        List<User> subscribers = Arrays.asList();

        // u1's post
        Post p1 = new Post(content, uid, author, email, u_img, time1, tag, like1, star);
        Post p2 = new Post(content, uid, author, email, u_img, time2, tag, like1, star);
        Post p3 = new Post(content, uid, author, email, u_img, time2, tag, like2, star);

        List<Post> lp1 = Arrays.asList(p1,p2,p3);

        // u2's post
        Post p4 = new Post(content, "1", author, email, u_img, time2, tag, like2, star);

        // create user
        User u1 = new User(uid, author, "123456", "female", 21,
                followers1, subscribers, lp1, "Canberra", email, "123", u_img);

        User u2 = new User("2", author, "123456", "female", 21,
                followers2, subscribers, Arrays.asList(p4), "Canberra", email, "123", u_img);
        // put in UserDao and PostDao
        UserDao.users = Arrays.asList(u1,u2);
        UserDao.users_size = 2;
        PostDao.posts = Arrays.asList(p1,p2,p3,p4);
        PostDao.post_size = 4;
    }

    // get User and Post
    User u1 = UserDao.findUserById(0);
    Post p1 = PostDao.getPosts().get(0);
    Post p2 = PostDao.getPosts().get(1);
    Post p3 = PostDao.getPosts().get(2);
    Post p4 = PostDao.getPosts().get(3);


    // create hashmap
    float[] weight = {(float) 0.3, (float) 0.3, (float) 0.3};
    ImportanceScore i = new ImportanceScore(u1,Arrays.asList("",""),PostDao.getPosts(),weight);
    Map<Post, Float> m = i.getScore();



    @Test
    public void timeScoreTest(){
        assertTrue(m.get(p1) > m.get(p2));
    }

    @Test
    public void likeScoreTest(){
        assertTrue(m.get(p2) > m.get(p3));
    }

    @Test
    public void followerScoreTest(){
        assertTrue(m.get(p4) > m.get(p3));
    }


}

