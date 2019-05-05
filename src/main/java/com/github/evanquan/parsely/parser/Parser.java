package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Parser {

    // NOTE: For now, only "," as end punctuation will count, as quotes are
    // causing problems with syntactical analysis
    /**
     * Defines the type of punctuation that can exist at the start of a word
     * that will split and count as its own token.
     */
    // public static final char[] START_PUNCTUATION = { '\'', '"' };
    public static final char[] START_PUNCTUATION = {};
    /**
     * Defines the type of punctuation that can exist at the end of a word that
     * will split and count as its own token.
     */
    // public static final char[] END_PUNCTUATION = { '\'', '"', ',' };
    public static final char[] END_PUNCTUATION = {',', '.'};

    public static final String[] VALID_PREPOSITIONS = {};

    /**
     * <b>Step 1: Lexical Analysis</b>
     * <p>
     * Splits the input string into tokens, each representing a word of the
     * command. The tokens are in the same order as they appear in the input
     * string. Each character of punctuation counts as its own token only if it
     * is a single or double quote around a word, or a comma after a word.
     *
     * @param input - input String
     * @return list of all tokens.
     */
    public static ArrayList<String> lexicalAnalysis(String input) {
        // NOTE: There's probably a better way to do this that doesn't use Scanner.
        // aka. Split by spaces, then map reduce.
        Scanner in = new Scanner(input);
        ArrayList<String> tokens = new ArrayList<String>();

        // Add all tokens
        while (in.hasNext()) {
            String token = in.next();
            addToken(tokens, token);
        }
        in.close();
        return tokens;
        // Right, now just using basic split by spaces. May need to change this when
        // things get
        // more complicated
        // return new ArrayList<>(Arrays.asList(input.split(" ")));
    }

    /**
     * Splits token by punctuation and adds punctuation components to
     * tokens.<br> - Double and single quotes at the start or end of words<br> -
     * Commas after a word<br> - Other punctuation and symbols are stripped and
     * ignored.
     *
     * @param tokens
     * @param token
     */
    public static void addToken(ArrayList<String> tokens, String token) {

        char firstChar = token.charAt(0);
        if (CollectionUtils.contains(START_PUNCTUATION, firstChar)) {
            tokens.add(Character.toString(firstChar));
            token = token.substring(1);
        }

        boolean changedLastChar = false;
        String endQuote = "";
        char lastChar = token.charAt(token.length() - 1);
        if (CollectionUtils.contains(END_PUNCTUATION, lastChar)) {
            endQuote = Character.toString(lastChar);
            token = token.substring(0, token.length() - 1);
            changedLastChar = true;
        }

        tokens.add(token);
        // End quote is added after word to preserve token order
        if (changedLastChar) {
            tokens.add(endQuote);
        }
    }

}