package com.example.wetok.searchTree;
/**
 * This is the node for AVL tree
 * @Auther Xinyue Hu, Xinyu Kang
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Node<K extends Comparable, T> implements Serializable {

    public K key; 				// Node key
    public List<T> value;
    public Node parent;	// Parent node
    public Node left, right;// Children nodes
    public int height;

    public Node(K key, T value) {
        this.key  = key;
        this.value = new ArrayList<>();
        this.value.add(value);
        this.parent = null;
        // Initialise children leaf nodes
        this.left 			= new Node<>();  //leaf node
        this.right 			= new Node<>();  //leaf node
        this.left.parent 	= this; //reference to parent
        this.right.parent 	= this; //reference to parent
        this.height = 1;
    }

    // Leaf node
    public Node() {
        this.key 	= null;
        this.value  = new ArrayList<>();
    }

    public K getKey() {
        return key;
    }

    public String toString(int depthFromRoot){
        if (this.key == null){
            return "leaf";
        }
        String space = "";
        for (int i = 0; i < depthFromRoot; i++){
            space = space + "        ";
        }
        String sLeft = "";
        String sRight = "";
        if (left!= null){
            sLeft = "\n"+space+"Left: "+left.toString(depthFromRoot+1);
        }
        if (right!=null){
            sRight = "\n"+space+"Right: "+right.toString(depthFromRoot+1);
        }
        return "{Key:"+key+", Value:"+value+", Height:"+height+sLeft+sRight+"}";
    }
}

