package com.example.wetok.searchTree;
/**
 * This is the AVL tree for search function
 * @Auther Xinyue Hu, Xinyu Kang
 */

import java.io.Serializable;
import java.util.HashSet;

public class AVLTree<K extends Comparable, T> implements Serializable {
    public Node root;
    private int size;

    public AVLTree(){
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int getHeight(Node node){
        if(node==null) return 0;
        return node.height;
    }

    public void insert(K key, T value) {
        if(key != null) {
            Node<K,T> node = new Node<>(key, value);
            root = insert(this.root, node);
        }
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    public Node getNode(K key) {
        return getNode(root, key);
    }

    public Node getNode(Node node, K key) {
        if (node == null || node.key == null)
            return null;
        if (node.key.compareTo(key) < 0)
            return getNode(node.left, key);
        else if (node.key.compareTo(key) > 0)
            return getNode(node.right, key);
        else
            return node;
    }

    public HashSet<T> find(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    public void set(K key, HashSet<T> newValue) {
        Node node = getNode(key);
        if (node != null)
            node.value = newValue;
        else
            throw new IllegalArgumentException(key + "doesn't exist");
    }

    public boolean contains(K key) {
        return getNode(key) != null;
    }

    public Node minimum() {
        if (size == 0 || root == null || root.key == null)
            throw new IllegalArgumentException("The tree is empty");
        return minimum(root);
    }

    public Node maximum() {
        if (size == 0 || root == null || root.key == null)
            throw new IllegalArgumentException("The tree is empty");
        return maximum(root);
    }

    public void removeMinimum() {
        if (size == 0 || root == null || root.key == null)
            throw new IllegalArgumentException("The tree is empty, cannot remove any node");
        removeMinimum(root);
    }

    public void removeMaximum() {
        if (size == 0 || root == null || root.key == null)
            throw new IllegalArgumentException("The tree is empty, cannot remove any node");
        removeMaximum(root);
    }

    private Node minimum(Node node) {
        if (node == null || node.key == null || node.left == null || node.left.key == null)
            return node;
        return minimum(node.left);
    }

    private Node maximum(Node node) {
        if (node == null || node.key == null || node.right == null || node.right.key == null)
            return node;
        return maximum(node.right);
    }

    private Node removeMinimum(Node node) {
        if (node.left == null || node.left.key == null) {
            Node rightNode = node.right;
            node.right = null;
            size = size - 1;
            return rightNode;
        }
        node.left = removeMinimum(node.left);
        node.left = balance(node.left);
        return node;
    }

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


