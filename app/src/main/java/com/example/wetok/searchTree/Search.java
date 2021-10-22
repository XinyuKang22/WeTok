package com.example.wetok.searchTree;
/**
 * This is the search class for building search tree and finding the specific results.
 * @author Xinyue Hu
 */


import com.example.wetok.bean.Post;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.view.SearchActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Search implements Serializable {
    public Search(){}
    public static Search instance = null;
    private AVLTree<String, Post> tagIndexPostTree = new AVLTree<>();
    private AVLTree<String, Post> timeIndexPostTree = new AVLTree<>();

    /**
     * Get Search engine
     * @return
     */
    public static Search getInstance(List<Post> posts) {
        if(instance == null) {
            instance = new Search();
            instance.buildIndexTrees(posts);
        }
        return instance;
    }

    /**
     * Record search engine
     * @return
     */
    public static void recordSearch(Search s){
        if(instance == null){
            instance = new Search();
            instance = s;
        }
    }

    /**
     * Build the search tree
     * @param posts
     */
    public void buildIndexTrees(List<Post> posts){
        for(Post post:posts){
            List<String> tags= post.getTag();
            for(String tag: tags){
                tagIndexPostTree.insert(tag,post);
            }
            timeIndexPostTree.insert(post.getTime(),post);
        }
    }

    /**
     * Find the searching results
     * @param key String representing tag
     * @return
     */
    public List<Post> search(String key){
        List<Post> result = new ArrayList<>();
        if(tagIndexPostTree.find(key)!=null){
            result.addAll(tagIndexPostTree.find(key));
        }
        return result;
    }

    /**
     * Delete specific post
     * @param key
     */
    public void remove(String key){
        if(timeIndexPostTree.find(key)!=null) {
            timeIndexPostTree.delete(key);
        }
    }


}
