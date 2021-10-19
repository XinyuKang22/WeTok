package com.example.wetok.beanTest;

import static org.junit.Assert.assertEquals;

import com.example.wetok.bean.Post;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UserTest {
    String content = "Good luck!";
    // user info
    String uid = "6684233";
    String author = "Yuxin Hong";
    String email = "u1234567@anu.edu.au";
    String u_img = "default";

    // post info
    String time = "2021-10-19 11:37";
    List<String> tag = Arrays.asList("#final_exam", "#group_project");

    // post property
    int likes = 999;
    int star = 99999;

    @Test
    public void userEmptyTest() {
        Post p = new Post();
        p.setStar(star);
        assertEquals(star,p.getStar());
        p.setTime(time);
        assertEquals(time,p.getTime());
        p.setEmail(email);
        assertEquals(email,p.getEmail());
        p.setAuthor(author);
        assertEquals(author,p.getAuthor());
        p.setTag(tag);
        assertEquals(tag,p.getTag());
        p.setU_img(u_img);
        assertEquals(u_img,p.getU_img());
        p.setUid(uid);
        assertEquals(uid,p.getUid());

    }

    @Test
    public void constructorTest() {
        Post p = new Post(content, uid, author, email, u_img, time, tag, likes, star);

    }
}
