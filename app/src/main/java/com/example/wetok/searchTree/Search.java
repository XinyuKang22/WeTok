package com.example.wetok.searchTree;
/**
 * This is the search class for building search tree and finding the specific results.
 * @author Xinyue Hu
 */


import com.example.wetok.bean.Post;
import com.example.wetok.view.SearchActivity;

import java.util.ArrayList;
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
            List<String> tags= post.getTag();
            for(String tag: tags){
                tagIndexPostTree.insert(tag,post);
            }
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


}
