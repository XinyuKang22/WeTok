package com.example.wetok.parserAndTokenizer;

public class Tokenizer {
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.

    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and save it to currentToken
     */
    public Tokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }

    /**
     * This function will find and extract a next token from {@code _buffer} and
     * save the token to {@code currentToken}.
     */
    public void next() {
        buffer = buffer.trim();     // remove whitespace
        char firstChar = buffer.charAt(0);
        if (firstChar == '&')
            currentToken = new Token("+", Token.Type.AND);
        else if (firstChar == '|')
            currentToken = new Token("-", Token.Type.OR);
        else if (firstChar == '_')
            currentToken = new Token("*", Token.Type.UNDERLINE);
        else if (firstChar == '#')
            currentToken = new Token("/", Token.Type.HASH);
        else if (firstChar == '(')
            currentToken = new Token("(", Token.Type.LBRA);
        else if (firstChar == ')')
            currentToken = new Token(")", Token.Type.RBRA);
        else if (Character.isLetter(firstChar)) {
            String res = "" + firstChar;
            for (int i = 1; i < buffer.length(); i++) {
                if (Character.isLetter(buffer.charAt(i))) {
                    res += buffer.charAt(i);
                } else {
                    break;
                }
            }
            currentToken = new Token(res, Token.Type.WORD);
        }
        else throw new Token.IllegalTokenException("Illegal Token");
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted by {@code next()}
     *
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Check whether there still exists another tokens in the buffer or not
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}