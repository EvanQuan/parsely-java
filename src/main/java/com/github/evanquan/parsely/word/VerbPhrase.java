package com.github.evanquan.parsely.word;

import com.github.evanquan.parsely.util.FuncUtils;

import java.util.ArrayList;

/**
 * Verb phrases describe a verb. It is composed of any number of adverbs, ending
 * a verb. Verb phrases conform to the following grammar:
 * <br><br>
 * Adverb* Verb?
 *
 * @author Evan Quan
 */
public class VerbPhrase {

    /**
     * A list of words that describe the verb.
     */
    private ArrayList<String> adverbs;

    /**
     * A word that describes an action.
     */
    private String verb;

    /**
     * Default constructor. Initializes an empty adverbs ArrayList.
     */
    public VerbPhrase() {
        adverbs = new ArrayList<>();
    }

    /**
     * Constructor to set the verb for this {@link VerbPhrase}.
     *
     * @param verb to set
     */
    public VerbPhrase(String verb) {
        this();
        this.verb = verb;
    }

    /**
     * @param other phrase to compare equality with.
     * @return true if the article, adjective, and noun are equal for both
     * object phrases.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof VerbPhrase) {
            return hasSameAdverbs((VerbPhrase) other)
                    && hasSameVerb((VerbPhrase) other);
        }
        return false;
    }

    /**
     * @return the list of adverbs this verb phrase has.
     */
    public ArrayList<String> getAdverbs() {
        return this.adverbs;
    }

    /**
     * @param adverbs to set for this verb phrase.
     */
    public void setAdverbs(ArrayList<String> adverbs) {
        this.adverbs = adverbs;
    }

    /**
     * @return the verb that this verb phrase represents.
     */
    public String getVerb() {
        return this.verb;
    }

    /**
     * @param verb to set for this verb phrase.
     */
    public void setVerb(String verb) {
        this.verb = verb;
    }

    /**
     * @return true if this verb phrase has at least one adverb.
     */
    public boolean hasAdverbs() {
        return !this.adverbs.isEmpty();
    }

    /**
     * @return true if this verb phrase has a verb.
     */
    public boolean hasVerb() {
        return this.verb != null;
    }

    /**
     * @param other to compare
     * @return true if the other {@link VerbPhrase} has the same adverbs as this
     * phrase.
     */
    public boolean hasSameAdverbs(VerbPhrase other) {
        return FuncUtils.nullablesEqual(this.adverbs, other.getAdverbs());
    }

    /**
     * @param other to compare
     * @return ture if the other {@link VerbPhrase} has the same verb as this
     * phrase.
     */
    public boolean hasSameVerb(VerbPhrase other) {
        return FuncUtils.nullablesEqual(this.verb, other.getVerb());
    }

    /**
     * @return true if this verb phrase does not have a verb.
     */
    public boolean isEmpty() {
        return !hasAdverbs() && !hasVerb();
    }

    /**
     * @return the string representation of this command in terms of all its
     * components.
     */
    @Override
    public String toString() {
        return "[" + String.join(" ",
                (hasAdverbs() ? "adverbs: " + adverbs : ""),
                (hasVerb() ? "verb: " + verb : "")
        )
                + "]";
    }
}
