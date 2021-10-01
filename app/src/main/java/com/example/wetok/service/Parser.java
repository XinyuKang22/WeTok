package com.example.wetok.service;

/**
 * Grammar rule:
 * <exp>    ::=  <term> | <term> '|' <exp>
 * <term>   ::=  <factor> | <factor> '&' <term>
 * <factor> ::=  '#'<seq> | '(' <exp> ')'
 * <seq>    ::=  <word> | <word>'_'<seq>

 * Parsing, within the context of this lab, is the process of taking a bunch of tokens and
 * evaluating them. You will not need to 'evaluate' them within this class, instead, just
 * return an expression which can be evaluated.
 */
public class Parser {
    /**
     * The following exception should be thrown if the parse is faced with series of tokens that do not
     * correlate with any possible production rule.
     */
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    // The tokenizer (class field) this parser will use.
    Tokenizer tokenizer;

    /**
     * Parser class constructor
     * Simply sets the tokenizer field.
     * **** please do not modify this part ****
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }


    /**
     * Adheres to the grammar rule:
     * <exp>    ::= <term> | <term> '|' <exp>
     *
     * @return type: Exp.
     */
    public Exp parseExp() {
        return null;
    }

    /**
     * Adheres to the grammar rule:
     * <term>   ::=  <factor> | <factor> | <factor> '&' <term>
     *
     * @return type: Exp.
     */
    public Exp parseTerm() {
       return null;
    }

    /**
     * Adheres to the grammar rule:
     * <factor> ::= '#'<seq> | '(' <exp> ')'
     *
     * @return type: Exp.
     */
    public Exp parseFactor() {
        return null;
    }

    /**
     * Adheres to the grammar rule:
     * <seq>    ::=  <english_word> | <english_word>'_'<seq>
     *
     * @return type: Exp.
     */
    public Exp parseSeq() {
        return null;
    }


}
