package com.example.wetok.searchTree;
/**
 * Search class is to search for the specific results
 * @Author Xinyue Hu
 */


import com.example.wetok.bean.Post;
import com.example.wetok.view.SearchActivity;

import java.util.HashSet;
import java.util.List;

public class Search {
    public Search(){}

    private AVLTree<String, Post> tagIndexPostTree = new AVLTree<>();

    /**
     * Build the search tree
     * @param posts
     */
    public void buildIndexTrees(List<Post> posts){
        for(Post post:posts){
            tagIndexPostTree.insert(post.getTag(),post);
        }
    }

    /**
     * Find the searching results
     * @param key String representing tag
     * @return
     */
    public HashSet search(String key){
        HashSet result = new HashSet<>();
        if(tagIndexPostTree.find(key)!=null){
            result.addAll(tagIndexPostTree.find(key));
        }
        return result;
    }

}
