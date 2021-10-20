package com.example.wetok.dao;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The PostDao class is used to store the database operation of post.
 * @author Yuxin Hong
 * @author Xinyu Kang
 * @author Xinyue Hu
 */
public class PostDao implements Serializable {

    public static List<Post> posts = null; // Store all the posts
    public static int post_size = 0; // Store the size of post list

    public PostDao(List<Post> p) {
        posts = p;
    }

    public static List<Post> getPosts() {
        return posts;
    }

    /**
     * Get tags from each post
     * @param post
     * @return all the tags
     */
    public static List<String> getTagList(List<Post> post){
           List<String> tags = new ArrayList<>();
           for(Post p:post){
               tags.addAll(p.getTag());
           }
        return tags;
    }

    /**
     * Instantiate new post and add post to database
     * @param content
     */
    public static void addPost(String content) {
        User u = CurrentUser.current_user;
        // establish post class
        String uid = u.getId();
        String author = u.getName();
        String email = u.getEmail();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String u_img = u.getImgloc();
        List<String> content_list = Arrays.asList(content.split("#"));
        List<String> tag = new ArrayList<>();
        if (content_list.size() > 1) {
            for(int i=1;i<content_list.size();i++){
                tag.add("#"+content_list.get(i).trim());
            }
        } else {
            tag.add("#no_tag");
        }


        Post po = new Post(content_list.get(0), uid, author, email, u_img, time, tag, 0, 0);
        // User: add new post to user
        List<Post> user_post = CurrentUser.current_user.getPosts();
        user_post.add(findInsertIndex(user_post), po);

        // database: add new post, update post size
        posts.add(findInsertIndex(posts), po);
        post_size += 1;
    }

    /**
     * Find the right place to show the post
     * @param posts
     * @return the index of post list
     */
    public static int findInsertIndex(List<Post> posts ) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Post p;
        for (int i = 0; i < posts.size(); i++) {
            p = posts.get(i);
            if (Long.parseLong(p.getTime().replaceAll("[-: ]","")) <=
                    Long.parseLong(time.replaceAll("[-: ]",""))) {
                return i;
            }
        }
        return post_size;
    }

    /**
     * Show the post content
     * @return all the content of the post
     */
    @Override
    public String toString() {
        String res = "";
        for (Post p : posts) {
            res += p.getContent();
        }
        return res;
    }
}


