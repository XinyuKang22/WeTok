package com.example.wetok.searchTree;
/**
 * This is the node for AVL tree
 * @Auther Xinyue Hu, Xinyu Kang
 */

import java.io.Serializable;
import java.util.HashSet;


public class Node<K extends Comparable, T> implements Serializable {

    K key; 				// Node key
    HashSet<T> value;
    Node parent; 		// Parent node
    Node left, right;// Children nodes
    int height;

    public Node(K key, HashSet<T> value) {
        this.key  = key;
        this.value = value;
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
        this.value  = new HashSet<>();
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

