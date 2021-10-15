package com.example.wetok.parserAndTokenizer;

import java.util.Objects;

/**
 * Token class to save extracted token from tokenizer.
 * Each token has its surface form saved in {@code token}
 * and type saved in {@code type} which is one of the predefined type in Type enum.
 * The following are the different types of tokens:
 * HASH: #
 * WORD: plain english word
 * UNDERLINE: _
 * AND: &
 * OR: |
 * LBRA: (
 * RBRA: )
 */
public class Token {
    public enum Type {TAG, AND, OR, LBRA, RBRA}

    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    /**
     * The following exception should be thrown if a tokenizer attempts to tokenize something that is not of one
     * of the types of tokens.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String msg) {
            super(msg);
        }
    }

    @Override
    public String toString() {
        if (type == Type.AND) {
            return " && ";
        } else if (type == Type.OR) {
            return " || ";
        } else {
            return token + "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Token)) return false;
        return this.type == ((Token) other).getType() && this.token.equals(((Token) other).getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type);
    }
}
