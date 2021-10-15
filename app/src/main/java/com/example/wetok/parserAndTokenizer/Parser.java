package com.example.wetok.parserAndTokenizer;

/**
 * Grammar rule:
 * <exp>    ::=  <term> | <term> '|' <exp>
 * <term>   ::=  <factor> | <factor> '&' <term>
 * <factor> ::=  <tag> | '(' <exp> ')'


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
        if ("&|)".contains(tokenizer.current().getToken())) throw new IllegalProductionException("");
        String term_buffer = "";
        Token current = null;
        int bracket = 0;
        // loop through all, find first valid OR token if exist.
        while (tokenizer.hasNext()) {
            current = tokenizer.current();
            if (bracket == 0) {
                // bracket = 0 but ')' occur
                if (current.getType() == Token.Type.RBRA) throw new IllegalProductionException("");
                // bracket += 1 if '(' occur
                else if (current.getType() == Token.Type.LBRA) bracket += 1;
                // if bracket == 0 and OR token find, break
                else if (current.getType() == Token.Type.OR) break;
            } else {
                // bracket -= 1 if ')' occur
                if (current.getType() == Token.Type.RBRA) bracket -= 1;
                // bracket += 1 if '(' occur
                else if (current.getType() == Token.Type.LBRA) bracket += 1;
            }
            // save tokens before first OR in term_buffer
            term_buffer += current.getToken();
            // next token
            tokenizer.next();
        }
        // check if bracket pair is correct
        if (bracket != 0) throw new IllegalProductionException("");
        // parser for things former then OR
        Parser term_parser = new Parser(new Tokenizer(term_buffer));
        // valid OR not exist
        if (current == null) {
            return term_parser.parseExp();
        } else if (current.getType() == Token.Type.OR) {
            tokenizer.next();
            // parser for things later then OR
            Parser exp_parser = new Parser(tokenizer);
            return new OrExp(term_parser.parseTerm(), exp_parser.parseExp());
        } else if (current.getType() == Token.Type.TAG) {
            return term_parser.parseTerm();
        } else if (current.getType() == Token.Type.RBRA) {
            return term_parser.parseTerm();
        } else throw new IllegalProductionException("");
    }

    /**
     * Adheres to the grammar rule:
     * <term>   ::=  <factor> | <factor> | <factor> '&' <term>
     *
     * @return type: Exp.
     */
    public Exp parseTerm() {
        String factor_buffer = "";
        Token current = null;
        int bracket = 0;
        // loop through all, find first valid AND token if exist.
        while (tokenizer.hasNext()) {
            current = tokenizer.current();
            if (bracket == 0) {
                // bracket = 0 but ')' occur
                if (current.getType() == Token.Type.RBRA) throw new IllegalProductionException("");
                // bracket += 1 if '(' occur
                else if (current.getType() == Token.Type.LBRA) bracket += 1;
                // if bracket == 0 and AND token find, break
                else if (current.getType() == Token.Type.AND) break;
            } else {
                // bracket -= 1 if ')' occur
                if (current.getType() == Token.Type.RBRA) bracket -= 1;
                // bracket += 1 if '(' occur
                else if (current.getType() == Token.Type.LBRA) bracket += 1;
            }
            // save tokens before first OR in term_buffer
            factor_buffer += current.getToken();
            // next token
            tokenizer.next();
        }
        // parser for things former then AND
        Parser factor_parser = new Parser(new Tokenizer(factor_buffer));
        // valid AND not exist
        if (current == null) {
            return factor_parser.parseFactor();
        } else {
            if (current.getType() == Token.Type.AND) {
                tokenizer.next();
                // parser for things later then AND
                Parser term_parser = new Parser(tokenizer);
                return new AndExp(factor_parser.parseFactor(), term_parser.parseTerm());
            } else if (current.getType() == Token.Type.TAG) {
                return factor_parser.parseFactor();
            } else if (current.getType() == Token.Type.RBRA) {
                return factor_parser.parseFactor();
            } else throw new IllegalProductionException("");
        }
    }

    /**
     * Adheres to the grammar rule:
     * <factor> ::= '#'<tag> | '(' <exp> ')'
     *
     * @return type: Exp.
     */
    public Exp parseFactor() {
        Token current = tokenizer.current();
        // if '(', remove correspond ')'
        if (current.getType() == Token.Type.LBRA) {
            int bracket = 1;
            String buffer = "";
            tokenizer.next();
            while (tokenizer.hasNext()) {
                current = tokenizer.current();
                // bracket -= 1 if ')' occur
                if (current.getType() == Token.Type.RBRA) bracket -= 1;
                // bracket += 1 if '(' occur
                else if (current.getType() == Token.Type.LBRA) bracket += 1;
                // match bracket pair!
                if (bracket == 0) break;
                // add token in between '(', ')'
                buffer += current.getToken();
                // next token
                tokenizer.next();
            }
            Parser exp_parser = new Parser(new Tokenizer(buffer));
            return exp_parser.parseExp();
        } else if (current.getType() == Token.Type.TAG) {
            return new TagExp(current.getToken());
        } else throw new IllegalProductionException("");
    }


}
