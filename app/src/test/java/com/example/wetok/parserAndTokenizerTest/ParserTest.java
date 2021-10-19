package com.example.wetok.parserAndTokenizerTest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import com.example.wetok.bean.Post;
import com.example.wetok.parserAndTokenizer.AndExp;
import com.example.wetok.parserAndTokenizer.Exp;
import com.example.wetok.parserAndTokenizer.Parser;
import com.example.wetok.parserAndTokenizer.Token;
import com.example.wetok.parserAndTokenizer.Tokenizer;
import com.example.wetok.searchTree.Search;

public class ParserTest {

    /*
        We suggest you test your parser by also running it in your own terminal and checking the output.

        We advise you write additional tests to increase the confidence of your implementation. Simply getting these
	    tests correct does not mean your solution is robust enough pass the marking tests.
     */

    private static Tokenizer tokenizer;

    List<Post> result;

    private static final String SIMPLE_CASE = "#mood & #time & #weekend";
    private static final String MID_CASE = "#weekend & (#mood | #time)";
    private static final String[] testExample = new String[]{"#weekend & #time", "#weekend | #time"};
    Post p1 = new Post(Arrays.asList("#weekend","#mood"), "nice day!","1");
    Post p2 = new Post(Arrays.asList("#weekend"), "nice day!","2");
    Post p3 = new Post(Arrays.asList("#time"), "nice day!","3");
    Post p4 = new Post(Arrays.asList("#weekend","#time"), "nice day!","4");
    Post p5 = new Post(Arrays.asList("#time","#mood"), "nice day!","5");
    List<Post> posts = Arrays.asList(p1,p2,p3,p4,p5);
    Search s = new Search();


    @Test(timeout=1000)
    public void testSingleTag() {
        s.buildIndexTrees(posts);
        Tokenizer searchTokenizer = new Tokenizer("#weekend");
        Exp t1 = new Parser(searchTokenizer).parseExp();
        result = Arrays.asList(p1,p2,p4);
        assertEquals(result, t1.evaluate(s));
    }

    @Test(timeout=1000)
    public void testSimleAnd() {
        s.buildIndexTrees(posts);
        Tokenizer searchTokenizer = new Tokenizer(testExample[0]);
        Exp t1 = new Parser(searchTokenizer).parseExp();
        result = Arrays.asList(p4);
        assertEquals(result, t1.evaluate(s));
    }

    @Test(timeout=1000)
    public void testSimleMul() {
        s.buildIndexTrees(posts);
        Tokenizer searchTokenizer = new Tokenizer(testExample[1]);
        Exp t1 = new Parser(searchTokenizer).parseExp();
        // check length of intersection
        assertEquals(AndExp.postIntersection(posts,t1.evaluate(s)).size(), posts.size());
    }

    @Test(timeout=1000)
    public void testSimpleCase(){
        s.buildIndexTrees(posts);
        tokenizer = new Tokenizer(SIMPLE_CASE);
        try{
            Exp exp = new Parser(tokenizer).parseExp();
            assertEquals("incorrect display format", "(#mood & (#time & #weekend))", exp.show());
            assertEquals("incorrect evaluate value", new ArrayList<>(), exp.evaluate(s));
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test(timeout=1000)
    public void testMidCase(){
        s.buildIndexTrees(posts);
        tokenizer = new Tokenizer(MID_CASE);
        try{
            Exp exp = new Parser(tokenizer).parseExp();
            result = Arrays.asList(p1,p4);
            assertEquals("incorrect display format", "(#weekend & (#mood | #time))", exp.show());
            assertEquals("incorrect evaluate value", AndExp.postIntersection(result,exp.evaluate(s)).size(), result.size());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test(timeout=1000)
    public void testIllegalProductionException() {
        // Provide a series of tokens that should invoke this exception
        assertThrows(Parser.IllegalProductionException.class, () -> {
           tokenizer = new Tokenizer("&&");
           Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("|&");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("(");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("#1 & |");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("#1 #5");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("(#1&#2");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("#1&#2)");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("#1|#2)");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("((#1&#2)|#2");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("#5 | )");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("#5 #5 #2 & )");
            Exp exp = new Parser(tokenizer).parseExp();
        });
    }
}


