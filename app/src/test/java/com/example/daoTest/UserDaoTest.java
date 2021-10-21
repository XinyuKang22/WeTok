package com.example.daoTest;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.PostDao;
import com.example.wetok.dao.UserDao;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class test UserDao.class
 * @author Xinyue Hu
 */
public class UserDaoTest {
    @BeforeClass
    public static void setup(){
    // attributes
    String content1 = "COMP2100";
    String content2 = "COMP2100 is useful";

    String author1 = "a1";
    String author2 = "a2";
    String email1 = "u1@anu.edu.au";
    String email2 = "u2@anu.edu.au";

    String u_img = "default";
    String time = "2021-10-20 14:44:00";
    List<String> tag1 = Arrays.asList("#COMP2100");
    List<String> tag2 = Arrays.asList("#COMP2100", "#useful");
    int like = 50;
    int star = 0;

    // user's post
    Post p1 = new Post(content1, "0", author1, email1, u_img, time, tag1, like, star);
    Post p2 = new Post(content2, "1", author2, email2, u_img, time, tag2, like, star);

    // create user
    User u1 = new User("1", author1, "123456", "female", 21,
            null, null, Arrays.asList(p1), "Canberra", email1,
            "123", u_img);

    User u2 = new User("2", author2, "123456", "female", 21,
            null, null, Arrays.asList(p2), "Canberra", email2,
            "123", u_img);


    //put users in UserDao
    UserDao.users = Arrays.asList(u1,u2);
    UserDao.users_size = UserDao.users.size();

    }


   @Test(timeout = 1000)
   public void findUserByEmailTest(){
        User u = UserDao.findUserByEmail("u1@anu.edu.au");
       assert u != null;
       assertEquals("1",u.getId());
   }

   @Test(timeout = 1000)
   public void findUserByIdTest(){
        User u = UserDao.findUserById(0);
       assert u != null;
       assertEquals("a1",u.getName());
   }

   @Test(timeout = 1000)
   public void setFriendsTest(){
        User u = UserDao.users.get(1);
        UserDao.setFriends(u);
        assertEquals(5,u.getFriends().size());
   }

   @Test(timeout = 1000)
   public void setFollowersTest(){
       User u = UserDao.users.get(1);
       UserDao.setFollowers(u);
       assertEquals(5,u.getFollowers().size());
   }

   @Test(timeout = 1000)
   public void setSubscribersTest(){
       User u = UserDao.users.get(1);
       UserDao.setSubscribers(u);
       assertEquals(5,u.getSubscribers().size());
   }

   @Test(timeout = 1000)
   public void getPostsTest(){
        assertEquals(2,UserDao.getPosts().size());
   }

   @Test(timeout = 1000)
   public void setPostInfoTest(){
       User u = UserDao.users.get(0);
       UserDao.setPostInfo(u);
       assertEquals("a1",u.getPosts().get(0).getAuthor());
   }


}
