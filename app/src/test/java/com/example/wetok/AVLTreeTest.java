package com.example.wetok;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import java.util.HashSet;
import static org.junit.Assert.*;

import com.example.wetok.searchTree.AVLTree;
import com.example.wetok.searchTree.Node;

/*
This class tests Insertion and Deletion operations of AVLTree.
The expected output refers to the visualisation platform:
https://www.cs.usfca.edu/~galles/visualization/AVLtree.html
(except for duplicates, we expect that duplicate insertions will give an exception)

@Auther Xinyu Kang
*/

public class AVLTreeTest {

    @Test(timeout = 1000)
    public void insertInOrderTest() {
        // Simply check if the insertion correctly places values (no rotation check).
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(5,new HashSet<>()));
        avl.insert(new Node<>(10,new HashSet<>()));
        String expected = "AVLTree:    size = 2\n" +
                "{Key:5, Value:[], Height:2\n" +
                "Left: leaf\n" +
                "Right: {Key:10, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}}";
        assertNotNull(
                "\nInsertion does not properly position values" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.toString());
        assertEquals(
                "\nInsertion does not properly position values" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl
                ,
                10, (int) avl.root.right.key);

        avl.insert(new Node<>(1,new HashSet<>()));
        expected = "AVLTree:    size = 3\n" +
                "{Key:5, Value:[], Height:2\n" +
                "Left: {Key:1, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:10, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}}";
        assertNotNull(
                "\nInsertion does not properly position values" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.toString());
        assertEquals(
                "\nInsertion does not properly position values" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                1, (int) avl.root.left.key);
    }

    @Test(timeout = 1000)
    public void insertDuplicateException() {
        // Duplicates should throw exceptions
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                AVLTree avl = new AVLTree();
                avl.insert(new Node<>(5,new HashSet<>()));
                avl.insert(new Node<>(5,new HashSet<>()));
            }
        });
    }

    @Test(timeout = 1000)
    public void leftRotateTest() {
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(5,new HashSet<>()));
        avl.insert(new Node<>(8,new HashSet<>()));
        avl.insert(new Node<>(10,new HashSet<>()));
        String expected = "AVLTree:    size = 3\n" +
                "{Key:8, Value:[], Height:2\n" +
                "Left: {Key:5, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:10, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}}";
        // Check root value
        assertNotNull(
                "\nLeft rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.toString());
        assertEquals(
                "\nLeft rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                8, (int) avl.root.key);

        // Check left child value
        assertNotNull(
                "\nLeft rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.left.key);
        assertEquals(
                "\nLeft rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                5, (int) avl.root.left.key);

        // Check right child value
        assertNotNull(
                "\nLeft rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.right.key);
        assertEquals(
                "\nLeft rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                10, (int) avl.root.right.key);
    }

    @Test(timeout = 1000)
    public void rightRotateTest() {
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(10,new HashSet<>()));
        avl.insert(new Node<>(6,new HashSet<>()));
        avl.insert(new Node<>(3,new HashSet<>()));
        String expected = "AVLTree:    size = 3\n" +
                "{Key:6, Value:[], Height:2\n" +
                "Left: {Key:3, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:10, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}}";
        // Check root value
        assertNotNull(
                "\nRight rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.key);
        assertEquals(
                "\nRight rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                6, (int) avl.root.key);

        // Check left child value
        assertNotNull(
                "\nRight rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.left.key);
        assertEquals(
                "\nRight rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                3, (int) avl.root.left.key);

        // Check right child value
        assertNotNull(
                "\nRight rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.right.key);
        assertEquals(
                "\nRight rotation failed" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                10, (int) avl.root.right.key);
    }

    @Test(timeout = 1000)
    public void balanceFactorTest() {
        // Ensure insertion results in balanced tree.
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(5,new HashSet<>()));
        avl.insert(new Node<>(10,new HashSet<>()));
        avl.insert(new Node<>(20,new HashSet<>()));
        String expected = "AVLTree:    size = 3\n" +
                "{Key:10, Value:[], Height:2\n" +
                "Left: {Key:5, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:20, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}}";
        assertEquals(
                "\nInsertion does not properly balance tree (must left rotate)" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                0, avl.getBalanceFactor(avl.root)
        );

        avl.insert(new Node<>(22,new HashSet<>()));
        avl.insert(new Node<>(21,new HashSet<>()));
        expected = "AVLTree:    size = 5\n" +
                "{Key:10, Value:[], Height:3\n" +
                "Left: {Key:5, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:21, Value:[], Height:2\n" +
                "        Left: {Key:20, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:22, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}}}";
        assertEquals(
                "\nInsertion does not properly balance tree (must left, right, left rotate)" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                -1, avl.getBalanceFactor(avl.root)
        );

        avl.insert(new Node<>(23,new HashSet<>()));
        expected = "AVLTree:    size = 6\n" +
                "{Key:21, Value:[], Height:3\n" +
                "Left: {Key:10, Value:[], Height:2\n" +
                "        Left: {Key:5, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:20, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}}\n" +
                "Right: {Key:22, Value:[], Height:2\n" +
                "        Left: leaf\n" +
                "        Right: {Key:23, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}}}";
        assertEquals(
                "\nInsertion does not properly balance tree (must left, right, left, left rotate)" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                0, avl.getBalanceFactor(avl.root)
        );

        //Advanced
        avl = new AVLTree();
        avl.insert(new Node<>(10,new HashSet<>()));
        avl.insert(new Node<>(5,new HashSet<>()));
        avl.insert(new Node<>(6,new HashSet<>()));
        avl.insert(new Node<>(4,new HashSet<>()));
        avl.insert(new Node<>(7,new HashSet<>()));
        avl.insert(new Node<>(2,new HashSet<>()));
        avl.insert(new Node<>(1,new HashSet<>()));
        avl.insert(new Node<>(3,new HashSet<>()));
        expected = "AVLTree:    size = 8\n" +
                "{Key:6, Value:[], Height:4\n" +
                "Left: {Key:4, Value:[], Height:3\n" +
                "        Left: {Key:2, Value:[], Height:2\n" +
                "                Left: {Key:1, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: {Key:3, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}\n" +
                "        Right: {Key:5, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}}\n" +
                "Right: {Key:10, Value:[], Height:2\n" +
                "        Left: {Key:7, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}\n" +
                "        Right: leaf}}";
        assertEquals(
                "\nInsertion does not properly balance tree (must left, right, right, right, left, right rotate)" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                1, avl.getBalanceFactor(avl.root)
        );
    }

    @Test(timeout = 1000)
    public void advancedRotationsTest() {
        // Cause a situation with a RR, RL, LL or LR rotation is required.
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(14,new HashSet<>()));
        avl.insert(new Node<>(17,new HashSet<>()));
        avl.insert(new Node<>(11,new HashSet<>()));
        avl.insert(new Node<>(7,new HashSet<>()));
        avl.insert(new Node<>(53,new HashSet<>()));
        avl.insert(new Node<>(4,new HashSet<>()));
        avl.insert(new Node<>(13,new HashSet<>()));
        avl.insert(new Node<>(12,new HashSet<>()));
        avl.insert(new Node<>(8,new HashSet<>()));
        avl.insert(new Node<>(60,new HashSet<>()));
        avl.insert(new Node<>(19,new HashSet<>()));
        avl.insert(new Node<>(16,new HashSet<>()));
        avl.insert(new Node<>(20,new HashSet<>()));

        String expected = "AVLTree:    size = 13\n" +
                "{Key:14, Value:[], Height:4\n" +
                "Left: {Key:11, Value:[], Height:3\n" +
                "        Left: {Key:7, Value:[], Height:2\n" +
                "                Left: {Key:4, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: {Key:8, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}\n" +
                "        Right: {Key:12, Value:[], Height:2\n" +
                "                Left: leaf\n" +
                "                Right: {Key:13, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}}\n" +
                "Right: {Key:19, Value:[], Height:3\n" +
                "        Left: {Key:17, Value:[], Height:2\n" +
                "                Left: {Key:16, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:53, Value:[], Height:2\n" +
                "                Left: {Key:20, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: {Key:60, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}}}";
        assertNotNull(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.key);
        assertNotNull(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.left.key);
        assertNotNull(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.right.key);
        assertEquals(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                0, avl.getBalanceFactor(avl.root)
        );
        assertEquals(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                14, (int) avl.root.key
        );
        assertEquals(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                11, (int) avl.root.left.key
        );
        assertEquals(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                19, (int) avl.root.right.key
        );

        // Another double rotation requiring test.
        avl = new AVLTree();
        avl.insert(new Node<>(40,new HashSet<>()));
        avl.insert(new Node<>(20,new HashSet<>()));
        avl.insert(new Node<>(10,new HashSet<>()));
        avl.insert(new Node<>(25,new HashSet<>()));
        avl.insert(new Node<>(30,new HashSet<>()));
        avl.insert(new Node<>(22,new HashSet<>()));
        avl.insert(new Node<>(50,new HashSet<>()));

        expected = "AVLTree:    size = 7\n" +
                "{Key:25, Value:[], Height:3\n" +
                "Left: {Key:20, Value:[], Height:2\n" +
                "        Left: {Key:10, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:22, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}}\n" +
                "Right: {Key:40, Value:[], Height:2\n" +
                "        Left: {Key:30, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:50, Value:[], Height:1\n" +
                "                Left: leaf\n" +
                "                Right: leaf}}}";
        assertNotNull(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.key);
        assertNotNull(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.left.key);
        assertNotNull(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.right.key);
        assertEquals(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                0, avl.getBalanceFactor(avl.root)
        );
        assertEquals(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                25, (int) avl.root.key
        );
        assertEquals(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                20, (int) avl.root.left.key
        );
        assertEquals(
                "\nInsertion cannot handle either right right, right left, left left or left right double rotations." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                40, (int) avl.root.right.key
        );
    }

    @Test(timeout = 1000)
    public void deleteNotExistException(){
        // Delete a node which is not in the tree
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                AVLTree avl = new AVLTree();
                avl.insert(new Node<>(5,new HashSet<>()));
                avl.delete(1);
            }
        });
    }

    @Test(timeout = 1000)
    public void deleteLeafTest(){
        // Delete a node without any child
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(5,new HashSet<>()));
        avl.insert(new Node<>(4,new HashSet<>()));
        avl.delete(4);
        String expected = "AVLTree:    size = 2\n" +
                "{Key:5, Value:[], Height:1\n" +
                "Left: leaf\n" +
                "Right: leaf}";
        assertNull("\nDeletion cannot handle case 1: delete a leaf node." +
                "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl
                , avl.root.left.key);
    }

    @Test(timeout = 1000)
    public void deleteSingleChildNodeTest(){
        // Delete a node with one child
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(5,new HashSet<>()));
        avl.insert(new Node<>(6,new HashSet<>()));
        avl.insert(new Node<>(3,new HashSet<>()));
        avl.insert(new Node<>(4,new HashSet<>()));
        avl.delete(3);
        String expected = "AVLTree:    size = 3\n" +
                "{Key:5, Value:[], Height:2\n" +
                "Left: {Key:4, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:6, Value:[], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}}";
        assertEquals("\nDeletion cannot handle case 2: delete a node with one child." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl, 4,
                avl.root.left.key);
    }

    @Test(timeout = 1000)
    public void deleteTwoChildrenNodeTest(){
        // Delete a node with two children
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(14,new HashSet<>()));
        avl.insert(new Node<>(17,new HashSet<>()));
        avl.insert(new Node<>(11,new HashSet<>()));
        avl.insert(new Node<>(7,new HashSet<>()));
        avl.insert(new Node<>(53,new HashSet<>()));
        avl.insert(new Node<>(4,new HashSet<>()));
        avl.insert(new Node<>(13,new HashSet<>()));
        avl.insert(new Node<>(12,new HashSet<>()));
        avl.insert(new Node<>(8,new HashSet<>()));
        avl.insert(new Node<>(60,new HashSet<>()));
        avl.insert(new Node<>(19,new HashSet<>()));
        avl.insert(new Node<>(16,new HashSet<>()));
        avl.insert(new Node<>(20,new HashSet<>()));

        // simple case
        avl.delete(53);
        String expected = "AVLTree:    size = 12\n" +
                "{Key:14, Value:[], Height:4\n" +
                "Left: {Key:11, Value:[], Height:3\n" +
                "        Left: {Key:7, Value:[], Height:2\n" +
                "                Left: {Key:4, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: {Key:8, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}\n" +
                "        Right: {Key:12, Value:[], Height:2\n" +
                "                Left: leaf\n" +
                "                Right: {Key:13, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}}\n" +
                "Right: {Key:19, Value:[], Height:3\n" +
                "        Left: {Key:17, Value:[], Height:2\n" +
                "                Left: {Key:16, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:20, Value:[], Height:2\n" +
                "                Left: leaf\n" +
                "                Right: {Key:60, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}}}";
        assertEquals("\nDeletion cannot handle case 3: delete a node with two children." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl, 20,
                avl.root.right.right.key);
        assertEquals("\nDeletion cannot handle case 3: delete a node with two children." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl, 60,
                avl.root.right.right.right.key);

        // advance case, requires re-balancing of the tree
        avl.delete(11);
        expected = "AVLTree:    size = 11\n" +
                "{Key:14, Value:[], Height:4\n" +
                "Left: {Key:8, Value:[], Height:3\n" +
                "        Left: {Key:7, Value:[], Height:2\n" +
                "                Left: {Key:4, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:12, Value:[], Height:2\n" +
                "                Left: leaf\n" +
                "                Right: {Key:13, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}}\n" +
                "Right: {Key:19, Value:[], Height:3\n" +
                "        Left: {Key:17, Value:[], Height:2\n" +
                "                Left: {Key:16, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:20, Value:[], Height:2\n" +
                "                Left: leaf\n" +
                "                Right: {Key:60, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}}}";
        assertEquals("\nDeletion cannot handle case 3: delete a node with two children." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl, 8,
                avl.root.left.key);
        assertEquals("\nDeletion cannot handle case 3: delete a node with two children." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl, 7,
                avl.root.left.left.key);
        assertNull("\nDeletion cannot handle case 3: delete a node with two children." +
                "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl,
                avl.root.left.left.right.key);
    }

    @Test(timeout = 1000)
    public void deleteRootTest(){
        // Delete a root node
        AVLTree avl = new AVLTree();
        avl.insert(new Node<>(1,new HashSet<>()));

        // simple case
        avl.delete(1);
        String expected = "AVLTree:    size = 1\n" + "leaf";
        assertNull("\nDeletion cannot handle case 1: delete a leaf node." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl
                , avl.root.key);

        // advanced case
        avl.insert(new Node<>(14,new HashSet<>()));
        avl.insert(new Node<>(17,new HashSet<>()));
        avl.insert(new Node<>(11,new HashSet<>()));
        avl.insert(new Node<>(7,new HashSet<>()));
        avl.insert(new Node<>(53,new HashSet<>()));
        avl.insert(new Node<>(4,new HashSet<>()));
        avl.insert(new Node<>(13,new HashSet<>()));
        avl.insert(new Node<>(12,new HashSet<>()));
        avl.insert(new Node<>(8,new HashSet<>()));
        avl.insert(new Node<>(60,new HashSet<>()));
        avl.insert(new Node<>(19,new HashSet<>()));
        avl.insert(new Node<>(16,new HashSet<>()));
        avl.insert(new Node<>(20,new HashSet<>()));
        avl.delete(14);
        expected = "AVLTree:    size = 12\n" +
                "{Key:13, Value:[], Height:4\n" +
                "Left: {Key:11, Value:[], Height:3\n" +
                "        Left: {Key:7, Value:[], Height:2\n" +
                "                Left: {Key:4, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: {Key:8, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}\n" +
                "        Right: {Key:12, Value:[], Height:2\n" +
                "                Left: leaf\n" +
                "                Right: leaf}}\n" +
                "Right: {Key:19, Value:[], Height:3\n" +
                "        Left: {Key:17, Value:[], Height:2\n" +
                "                Left: {Key:16, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: leaf}\n" +
                "        Right: {Key:53, Value:[], Height:2\n" +
                "                Left: {Key:20, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}\n" +
                "                Right: {Key:60, Value:[], Height:1\n" +
                "                        Left: leaf\n" +
                "                        Right: leaf}}}}";
        assertEquals("\nDeletion cannot handle case 1: delete a leaf node." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl
                , 13,avl.root.key);
    }
}