package com.example.wetok.searchTree;

import java.util.HashSet;

public class Demo {
    public static void main(String[] args) {

        AVLTree avlTree = new AVLTree();
        avlTree.insert(new Node<>(0,new HashSet<>()));
        avlTree.insert(new Node<>(1,new HashSet<>()));
        avlTree.insert(new Node<>(5,new HashSet<>()));
        avlTree.insert(new Node<>(10,new HashSet<>()));
        avlTree.insert(new Node<>(8,new HashSet<>()));
        avlTree.insert(new Node<>(2,new HashSet<>()));
        avlTree.insert(new Node<>(3,new HashSet<>()));
        avlTree.insert(new Node<>(4,new HashSet<>()));
        avlTree.insert(new Node<>(20,new HashSet<>()));
        System.out.println(avlTree+"\n");
        avlTree.delete(1);
        System.out.println(avlTree+"\n");
        avlTree.delete(10);
        System.out.println(avlTree+"\n");
        avlTree.insert(new Node<>(15,new HashSet<>()));
        System.out.println(avlTree+"\n");
        avlTree.delete(10);
        System.out.println(avlTree+"\n");
    }
}
