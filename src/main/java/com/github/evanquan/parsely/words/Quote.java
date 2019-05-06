package com.github.evanquan.parsely.words;

import com.github.evanquan.parsely.util.CollectionUtils;

import java.util.ArrayList;

/**
 * Quoted nondelimitedText.
 *
 * @author Evan Quan
 */
public class Quote {

    /**
     * Text without quotation delimiters.
     */
    private String nonDelimitedText;
    /**
     * Text broken up into space-delimited tokens. Internal punctuation is
     * ignored.
     */
    private ArrayList<String> tokens;
    /**
     * Text with quotation delimiters.
     */
    private String delimitedText;

    /**
     * Quote delimiter
     */
    private String delimiter;

    /**
     * Signifies the quote was closed by a delimiter.
     */
    private boolean closedOff;

    /**
     * @param tokens    contained in the quote, without delimiters
     * @param delimiter surrounding the non-delimited text
     */
    public Quote(ArrayList<String> tokens, String delimiter,
                 boolean closedOff) {
        this.tokens = new ArrayList<>(tokens);
        this.nonDelimitedText = CollectionUtils.join(this.tokens, " ");
        this.delimitedText = delimiter
                + nonDelimitedText
                + (closedOff ? delimiter : "");
        this.delimiter = delimiter;
        this.closedOff = closedOff;
    }


    /**
     * @return delimiters surrounding text
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * @return text surrounded by delimiters
     */
    public String getDelimitedText() {
        return delimitedText;
    }

    /**
     * @return text without surrounding delimiters
     */
    public String getNonDelimitedText() {
        return nonDelimitedText;
    }

    /**
     * @return space-delimited tokens contained between the delimiters
     */
    public ArrayList<String> getTokens() {
        return tokens;
    }

    /**
     * @return true if the quote was closed off by a delimiter.
     */
    public boolean isClosedOff() {
        return closedOff;
    }
}
