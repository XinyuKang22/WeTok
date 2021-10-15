package com.example.wetok;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


import com.example.wetok.parserAndTokenizer.Token;
import com.example.wetok.parserAndTokenizer.Tokenizer;

import org.junit.Test;

public class TokenizerTest {

    /*
        We suggest you test your tokenizer by also running it in your own terminal and checking the output.

        We advise you write additional tests to increase the confidence of your implementation. Simply getting these
	    tests correct does not mean your solution is robust enough pass the marking tests.
     */

    private static Tokenizer tokenizer;
    private static final String MID = "#weekend | #time";
    private static final String ADVANCED = "(#weekend | #mood) & #time";
    private static final String AND_AND_OR = "&|";

    @Test(timeout=1000)
    public void testAndToken() {
    	tokenizer = new Tokenizer(AND_AND_OR);

    	// check the type of the first token
        assertEquals("wrong token type", Token.Type.AND, tokenizer.current().getType());

        // check the actual token value"
        assertEquals("wrong token value", "&", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testOrToken() {
    	tokenizer = new Tokenizer(AND_AND_OR);

        // extract next token (just to skip first passCase token)
        tokenizer.next();

        // check the type of the second token
        assertEquals("wrong token type", Token.Type.OR, tokenizer.current().getType());

        // check the actual token value
        assertEquals("wrong token value", "|", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testFirstToken(){
    	tokenizer = new Tokenizer(ADVANCED);
    	
    	// check the type of the first token
        assertEquals("wrong token type", Token.Type.LBRA, tokenizer.current().getType());
        // check the actual token value
        assertEquals("wrong token value", "(", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testMidTokenResult() {
        tokenizer = new Tokenizer(MID);

        // test first token TAG("#weekend")
        assertEquals(new Token("#weekend", Token.Type.TAG), tokenizer.current());

        // test SECOND token OR
        tokenizer.next();
        assertEquals(Token.Type.OR, tokenizer.current().getType());

        // test third token TAG("#time")
        tokenizer.next();
        assertEquals(new Token("#time", Token.Type.TAG), tokenizer.current());

    }

    @Test(timeout=1000)
    public void testAdvancedTokenResult(){
    	tokenizer = new Tokenizer(ADVANCED);
    	
        // test first token (
        assertEquals(new Token("(", Token.Type.LBRA), tokenizer.current());

        // test second token TAG(#weekend)
        tokenizer.next();
        assertEquals(new Token("#weekend", Token.Type.TAG), tokenizer.current());

        // test third token &
        tokenizer.next();
        assertEquals(new Token("|", Token.Type.OR), tokenizer.current());

        // test forth token TAG(#mood)
        tokenizer.next();
        assertEquals(new Token("#mood", Token.Type.TAG), tokenizer.current());

        // test fifth token )
        tokenizer.next();
        assertEquals(new Token(")", Token.Type.RBRA), tokenizer.current());

        // test sixth token |
        tokenizer.next();
        assertEquals(new Token("&", Token.Type.AND), tokenizer.current());

        // test seventh token TAG(#time)
        tokenizer.next();
        assertEquals(new Token("#time", Token.Type.TAG), tokenizer.current());
    }

    @Test(timeout=1000)
    public void testExceptionToken() {
        // Test a series of non-identifiable tokens
        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("Banana and Apples");
            tokenizer.next();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("#tag1+#tag2");
            int i = -1;
            while (i++ < "(1+2.5)/2".length()) {
                tokenizer.next();
            }
        });
    }
}
