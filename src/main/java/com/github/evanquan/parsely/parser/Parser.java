package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.util.CollectionUtils;
import com.github.evanquan.parsely.words.Command;
import com.github.evanquan.parsely.words.ObjectPhrase;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Parses a {@link String} into a {@link Command}.
 *
 * @author Evan Quan
 */
public abstract class Parser {

    // NOTE: For now, only "," as end punctuation will count, as quotes are
    // causing problems with syntactical analysis
    /**
     * Defines the type of punctuation that can exist at the start of a words
     * that will split and count as its own token.
     */
    // public static final char[] START_PUNCTUATION = { '\'', '"' };
    private static final char[] START_PUNCTUATION = {};
    /**
     * Defines the type of punctuation that can exist at the end of a words that
     * will split and count as its own token.
     */
    // public static final char[] END_PUNCTUATION = { '\'', '"', ',' };
    private static final char[] END_PUNCTUATION = {',', '.'};

    public static final String[] VALID_PREPOSITIONS = {};

    /**
     * <b>Step 1: Lexical Analysis</b>
     * <p>
     * Splits the input string into tokens, each representing a words of the
     * command. The tokens are in the same order as they appear in the input
     * string. Each character of punctuation counts as its own token only if it
     * is a single or double quote around a words, or a comma after a words.
     *
     * @param input - input String
     * @return list of all tokens.
     */
    public static ArrayList<String> lexicalAnalysis(String input) {
        // NOTE: There's probably a better way to do this that doesn't use Scanner.
        // aka. Split by spaces, then map reduce.
        Scanner in = new Scanner(input);
        ArrayList<String> tokens = new ArrayList<>();

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
     * Commas after a words<br> - Other punctuation and symbols are stripped and
     * ignored.
     *
     * @param tokens to add token to
     * @param token to add to tokens
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

        if (!token.isEmpty()) {
            // If the token is empty, then the token was punctuation on its own.
            // We don't want empty tokens, as that will create take strange
            // behaviour for parsing {@link Command} components. In other words,
            // we don't want empty strings to become nouns, adjectives etc.
            tokens.add(token);
        }
        // End quote is added after words to preserve token order
        if (changedLastChar) {
            tokens.add(endQuote);
        }
    }

    /**
     * Parse receiveInput text into words and apply their appropriate meanings
     * and relationships. Accepts only imperative statements.
     *
     * @param input - String to parse into words
     * @return command that represents the player {@link Command}
     */
    public abstract Command parse(String input);

    /**
     * Find an objective phrase from a list of tokens. Can be either a direct or
     * indirect object phrase. This modifies the tokens argument (may be changed
     * later if needed).
     *
     * @param tokens to convert to an object phrase
     * @return object phrase that is composed of all token components, or null
     * if tokens is empty
     */
    public abstract ObjectPhrase getObjectPhrase(ArrayList<String> tokens);

}
