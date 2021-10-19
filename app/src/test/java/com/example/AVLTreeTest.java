package com.example;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.example.wetok.searchTree.AVLTree;

/**
 * @author Xinyu Kang
 *
 * This class mainly tests Insertion, Deletion, and Search operations of AVLTree.
 * The expected output refers to the visualisation platform:
 * https://www.cs.usfca.edu/~galles/visualization/AVLtree.html
 * (except for duplicates, we expect that duplicate insertions will give an exception)
 *
 * This test has class coverage 100%, method coverage 94%, line coverage 91% for AVLTree.java,
 *           and class coverage 100%, method coverage 75%, line coverage 96% for Node.java.
 */
public class AVLTreeTest {

    @Test(timeout = 1000)
    public void sameKeyInsertTest() {
        AVLTree avl = new AVLTree();
        // insert multiple 'values' with same key
        avl.insert("good","good!");
        avl.insert("good","better!");
        avl.insert("good","best!");
        avl.insert("bad","bad!");
        avl.insert("bad","worse!");
        avl.insert("bad","worst!");
        String expected =
                "AVLTree:    size = 2\n" +
                "{Key:good, Value:[good!, better!, best!], Height:2\n" +
                "Left: {Key:bad, Value:[bad!, worse!, worst!], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: leaf}";
        assertEquals(
                "\nInsertion does not properly position values" +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl
                ,
                2, avl.size);
    }

    @Test(timeout = 1000)
    public void insertInOrderTest() {
        // Simply check if the insertion correctly places values (no rotation check).
        AVLTree avl = new AVLTree();
        avl.insert(5,'b');
        avl.insert(10,'c');
        String expected =
                "AVLTree:    size = 2\n" +
                "{Key:5, Value:[b], Height:2\n" +
                "Left: leaf\n" +
                "Right: {Key:10, Value:[c], Height:1\n" +
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

        avl.insert(1,'d');
        expected =
                "AVLTree:    size = 3\n" +
                "{Key:5, Value:[b], Height:2\n" +
                "Left: {Key:1, Value:[d], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:10, Value:[c], Height:1\n" +
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
    public void leftRotateTest() {
        AVLTree avl = new AVLTree();
        avl.insert(5,'a');
        avl.insert(8,'a');
        avl.insert(10,'a');
        String expected =
                "AVLTree:    size = 3\n" +
                "{Key:8, Value:[a], Height:2\n" +
                "Left: {Key:5, Value:[a], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:10, Value:[a], Height:1\n" +
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
        avl.insert(10,'a');
        avl.insert(6,'a');
        avl.insert(3,'a');
        String expected =
                "AVLTree:    size = 3\n" +
                "{Key:6, Value:[a], Height:2\n" +
                "Left: {Key:3, Value:[a], Height:1\n" +
                "        Left: leaf\n" +
                "        Right: leaf}\n" +
                "Right: {Key:10, Value:[a], Height:1\n" +
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
        avl.insert(5,'a');
        avl.insert(10,'a');
        avl.insert(20,'a');
        assertEquals("\nGenerated incorrect balance factor",0, avl.getBalanceFactor(avl.root));
        avl.insert(22,'a');
        avl.insert(21,'a');
        assertEquals( "\nGenerated incorrect balance factor",-1, avl.getBalanceFactor(avl.root));
        avl.insert(23,'a');
        assertEquals("\nGenerated incorrect balance factor",0, avl.getBalanceFactor(avl.root));

        //Advanced
        avl = new AVLTree();
        avl.insert(10,'a');
        avl.insert(5,'a');
        avl.insert(6,'a');
        avl.insert(4,'a');
        avl.insert(7,'a');
        avl.insert(2,'a');
        avl.insert(1,'a');
        avl.insert(3,'a');
        assertEquals("\nGenerated incorrect balance factor",1, avl.getBalanceFactor(avl.root));
    }

    @Test(timeout = 1000)
    public void advancedRotationsTest() {
        // Cause a situation with a RR, RL, LL or LR rotation is required.
        AVLTree avl = new AVLTree();
        avl.insert(14,' ');
        avl.insert(17,' ');
        avl.insert(11,' ');
        avl.insert(7,' ');
        avl.insert(53,' ');
        avl.insert(4,' ');
        avl.insert(13,' ');
        avl.insert(12,' ');
        avl.insert(8,' ');
        avl.insert(60,' ');
        avl.insert(19,' ');
        avl.insert(16,' ');
        avl.insert(20,' ');

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
        avl.insert(40,' ');
        avl.insert(20,' ');
        avl.insert(10,' ');
        avl.insert(25,' ');
        avl.insert(30,' ');
        avl.insert(22,' ');
        avl.insert(50,' ');

        expected =
                "AVLTree:    size = 7\n" +
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
                avl.insert(5,'a');
                avl.delete(1);
            }
        });
    }

    @Test(timeout = 1000)
    public void deleteLeafTest(){
        // Delete a node without any child
        AVLTree avl = new AVLTree();
        avl.insert(5,' ');
        avl.insert(4,' ');
        avl.delete(4);
        String expected =
                "AVLTree:    size = 2\n" +
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
        avl.insert(5,' ');
        avl.insert(6,' ');
        avl.insert(3,' ');
        avl.insert(4,' ');
        avl.delete(3);
        String expected =
                "AVLTree:    size = 3\n" +
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
        avl.insert(14,' ');
        avl.insert(17,' ');
        avl.insert(11,' ');
        avl.insert(7,' ');
        avl.insert(53,' ');
        avl.insert(4,' ');
        avl.insert(13,' ');
        avl.insert(12,' ');
        avl.insert(8,' ');
        avl.insert(60,' ');
        avl.insert(19,' ');
        avl.insert(16,' ');
        avl.insert(20,' ');

        // simple case
        avl.delete(53);
        String expected =
                "AVLTree:    size = 12\n" +
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
        expected =
                "AVLTree:    size = 11\n" +
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
        avl.insert(1,'a');

        // simple case
        avl.delete(1);
        String expected = "AVLTree:    size = 1\n" + "leaf";
        assertNull("\nDeletion cannot handle case 1: delete a leaf node." +
                        "\nYour AVL tree should look like: \n" + expected + "\nBut it actually looks like: " + avl
                , avl.root.key);

        // advanced case
        avl.insert(14,' ');
        avl.insert(17,' ');
        avl.insert(11,' ');
        avl.insert(7,' ');
        avl.insert(53,' ');
        avl.insert(4,' ');
        avl.insert(13,' ');
        avl.insert(12,' ');
        avl.insert(8,' ');
        avl.insert(60,' ');
        avl.insert(19,' ');
        avl.insert(16,' ');
        avl.insert(20,' ');
        avl.delete(14);
        expected =
                "AVLTree:    size = 12\n" +
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

    @Test(timeout = 1000)
    public void searchByKeyTest() {
        AVLTree<String,Integer> avlTree = new AVLTree<>();
        avlTree.insert("a",4);
        avlTree.insert("b",1);
        avlTree.insert("c",7);
        avlTree.insert("d",2);
        avlTree.insert("e",0);
        avlTree.insert("e",3);
        int result = avlTree.find("d").get(0);
        assertEquals("The AVL tree did not find correct value by the key",2,result);
        result = avlTree.find("e").get(1);
        assertEquals("The AVL tree did not find correct value by the key",3,result);
    }

    @Test(timeout = 1000)
    public void searchByNotExistKeyTest() {
        AVLTree<String,Integer> avlTree = new AVLTree<>();
        avlTree.insert("a",4);
        avlTree.insert("b",1);
        avlTree.insert("c",7);
        avlTree.insert("d",2);
        avlTree.insert("e",0);
        avlTree.insert("e",3);
        assertNull("Searching key which is not stored in the tree should not return any values:",avlTree.find("f"));
    }

}