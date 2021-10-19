package com.example.wetok.searchTree;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xinyue Hu, Xinyu Kang
 *
 * This class implements AVL Tree data structure,
 * mainly includes find, insert, and delete operations.
 */
public class AVLTree<K extends Comparable, T> implements Serializable {
    public Node root;
    public int size;

    public AVLTree(){
        root = null;
        size = 0;
    }

    public int getHeight(Node node){
        if(node==null) return 0;
        return node.height;
    }

    /**
     * Find the node by key, insert new value into its value list. Create a new node if the key is not found.
     * @param key the key
     * @param value a single value
     */
    public void insert(K key, T value) {
        if(key != null) {
            Node<K,T> node = new Node<>(key, value);
            root = insert(this.root, node);
        }
    }

    /**
     * Delete the node with the key. Throw IllegalInputException if the node does not exist
     * @param key the key
     */
    public void delete(K key) {
        root = delete(root, key);
    }

    /**
     * Get the node with the key, search in entire tree
     * @param key the key
     * @return the node searched by the key, return null if not found
     */
    public Node getNode(K key) {
        return getNode(root, key);
    }

    /**
     * Get the node with the key, search the subtree from the given node to leaves
     * @param node the start node (can be a root node or any other nodes)
     * @param key the key
     * @return the node searched by the key, return null if not found
     */
    public Node getNode(Node node, K key) {
        if (node == null || node.key == null){
            return null;
        }
        if (node.key.compareTo(key) < 0){
            return getNode(node.right, key);
        }else if (node.key.compareTo(key) > 0){
            return getNode(node.left, key);
        }else{
            return node;
        }
    }

    /**
     * Find the node with the key, search in entire tree
     * @param key the key
     * @return the node searched by the key, return null if not found
     */
    public List<T> find(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    /**
     * Check whether the tree contains a node with the key
     * @param key the key
     * @return true if the tree contains a node with the key, and vise versa
     */
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    private Node maximum(Node node) {
        if (node == null || node.key == null || node.right == null || node.right.key == null)
            return node;
        return maximum(node.right);
    }

    /**
     * Remove the max node in the subtree from the node to leaves
     * @param node the start node
     * @return the root node of the subtree after deleting the max node
     */
    private Node removeMaximum(Node node) {
        if (node.right == null || node.right.key == null) {
            Node leftNode = node.left;
            node.left = null;
            size = size - 1;
            return leftNode;
        }
        node.right = removeMaximum(node.right);
        node.right = balance(node.right);
        return node;
    }

    public int getBalanceFactor(Node node){
        if(node==null || node.key == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node t = x.right;
        x.right = y;
        y.left = t;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    private Node leftRotate(Node y) {
        Node x = y.right;
        Node t = x.left;
        x.left = y;
        y.right = t;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    private Node balance(Node node){
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {  // LL
            return rightRotate(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0){ // RR
            return leftRotate(node);
        }
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0){ // LR
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0){ // RL
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private Node insert(Node theRoot, Node y){
        if (theRoot == null || theRoot.key == null){
            this.size = this.size + 1;
            return y;
        }
        if(y.key.compareTo(theRoot.key) < 0){
            theRoot.left = insert(theRoot.left, y);
        }else if(y.key.compareTo(theRoot.key) > 0){
            theRoot.right = insert(theRoot.right,y);
        }else {
            theRoot.value.addAll(y.value);
        }
        theRoot = balance(theRoot);
        theRoot.height = 1 + Math.max(getHeight(theRoot.left), getHeight(theRoot.right));
        return theRoot;
    }

    private Node delete(Node node, K key) {
        if (node == null || node.key == null)
            return null;
        Node retNode = null;

        if ((node.left == null || node.left.key == null) && (node.right == null || node.right.key == null) && key.compareTo(node.key) != 0){
            throw new IllegalArgumentException("Cannot find the node with key = "+key+" in the tree");
        }

        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
            retNode = node;
        } else {
            if ((node.left == null || node.left.key == null) && (node.right == null || node.right.key == null)){
                return new Node();
            }
            if (node.left == null || node.left.key == null) {
                Node rightNode = node.right;
                node.right = null;
                size = size - 1;
                retNode = rightNode;
            }else if (node.right == null || node.right.key == null) {
                Node leftNode = node.left;
                node.left = null;
                size = size - 1;
                retNode = leftNode;
            }else{
                Node successor = maximum(node.left);
                Node newLeft = removeMaximum(node.left);
                Node newRight = node.right;
                successor.right = newRight;
                successor.left = newLeft;
                node.left = null;
                node.right = null;
                retNode = successor;
            }
        }
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));
        retNode = balance(retNode);
        return retNode;
    }

    @Override
    public String toString() {
        if (this.root == null){
            return "AVLTree:    size = "+this.size;
        }
        return "AVLTree:    size = "+this.size+"\n"+this.root.toString(0);
    }
}


