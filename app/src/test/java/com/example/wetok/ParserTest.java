package com.example.wetok;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.wetok.parserAndTokenizer.Exp;
import com.example.wetok.parserAndTokenizer.Parser;
import com.example.wetok.parserAndTokenizer.Tokenizer;

public class ParserTest {

    /*
        We suggest you test your parser by also running it in your own terminal and checking the output.

        We advise you write additional tests to increase the confidence of your implementation. Simply getting these
	    tests correct does not mean your solution is robust enough pass the marking tests.
     */

    private static Tokenizer tokenizer;
    
    private static final String SIMPLE_CASE = "#weekend | #time";
    private static final String MID_CASE = "#weekend & (#mood | #time)";
//    private static final String COMPLEX_CASE = "(10 - 2) * (10 / 2) & 1";

    private static final String[] testExample = new String[]{"#weekend & #time", "#weekend | #time"};
    
    // TODO: need to be modified
    @Test(timeout=1000)
    public void testSimleAnd() {
        Tokenizer searchTokenizer = new Tokenizer(testExample[0]);
        Exp t1 = new Parser(searchTokenizer).parseExp();
        assertEquals(3, t1.evaluate());            
    }

    // TODO: need to be modified
    @Test(timeout=1000)
    public void testSimleMul() {
        Tokenizer searchTokenizer = new Tokenizer(testExample[1]);
        Exp t1 = new Parser(searchTokenizer).parseExp();
        assertEquals(2, t1.evaluate());            
    }


    // TODO: need to be modified
    @Test(timeout=1000)
    public void testSimpleCase(){
        tokenizer = new Tokenizer(SIMPLE_CASE);
        try{
            Exp exp = new Parser(tokenizer).parseExp();
            assertEquals("incorrect display format", "(1 & 2)", exp.show());
            assertEquals("incorrect evaluate value", 3, exp.evaluate());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    // TODO: need to be modified
    @Test(timeout=1000)
    public void testMidCase(){
        tokenizer = new Tokenizer(MID_CASE);
        try{
            Exp exp = new Parser(tokenizer).parseExp();
            assertEquals("incorrect display format", "((12 * 5) - 3)", exp.show());
            assertEquals("incorrect evaluate value", 57, exp.evaluate());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

//    @Test(timeout=1000)
//    public void testComplexCase() {
//        tokenizer = new Tokenizer(COMPLEX_CASE);
//        try{
//            Exp exp = new Parser(tokenizer).parseExp();
//            assertEquals("incorrect display format","(((10 - 2) * (10 / 2)) & 1)", exp.show());
//            assertEquals("incorrect evaluate value", 41, exp.evaluate());
//        }catch (Exception e){
//            fail(e.getMessage());
//        }
//    }

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
            tokenizer = new Tokenizer("#1 & |");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("#1 #5");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("(");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("(#1&#2");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("#1&#2)");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("#1|#2)");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("((#1&#2)|#2");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("#5 | )");
            Exp exp = new Parser(tokenizer).parseExp();
        });

        assertThrows(Parser.IllegalProductionException.class, () -> {
            tokenizer = new Tokenizer("#5 #5 #2 & )");
            Exp exp = new Parser(tokenizer).parseExp();
        });
    }
}


