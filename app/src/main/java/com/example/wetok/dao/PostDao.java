package com.example.wetok.dao;

import android.content.Context;
import android.widget.Toast;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostDao implements Serializable {

    public static List<Post> posts = null;
    public static int post_size = 0;

    public PostDao(List<Post> p) {
        posts = p;
    }

    public static List<Post> getPosts() {
        return posts;
    }

    public static List<String> getTagList(List<Post> post){
           List<String> tags = new ArrayList<>();
           for(Post p:post){
               tags.addAll(p.getTag());
           }
        return tags;
    }

    public static void addPost(String content,Context activity) {
        User u = CurrentUser.current_user;
        if(u == null) {
            Toast.makeText(activity,"Please login first.",Toast.LENGTH_LONG).show();
        }else {
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


            Post po = new Post(content, uid, author, email, u_img, time, tag, "", 0, 0, 0);
            // User: add new post to user
            List<Post> user_post = CurrentUser.current_user.getPosts();
            user_post.add(findInsertIndex(user_post), po);

            // database: add new post, update post size
            posts.add(findInsertIndex(posts), po);
            post_size += 1;
        }
    }


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

    @Override
    public String toString() {
        String res = "";
        for (Post p : posts) {
            res += p.getContent();
        }
        return res;
    }
}


