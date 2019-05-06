package com.github.evanquan.parsely.parser;

import java.util.HashSet;

/**
 * Specifies to the parser the rules in which a given action is to be used.
 *
 * @author Evan Quan
 */
public class ActionType {

    private String primaryVerb;

    private HashSet<String> synonyms;

    private ActionTypeRequirement directObjectPhraseActionTypeRequirement;
    private ActionTypeRequirement indirectObjectPhraseActionTypeRequirement;

    public ActionType(String primaryVerb,
                      HashSet<String> synonyms,
                      ActionTypeRequirement directObjectPhraseActionTypeRequirement,
                      ActionTypeRequirement indirectObjectPhraseActionTypeRequirement) {
        this.primaryVerb = primaryVerb;
        this.synonyms = new HashSet<>(synonyms);
        this.directObjectPhraseActionTypeRequirement = directObjectPhraseActionTypeRequirement;
        this.indirectObjectPhraseActionTypeRequirement = indirectObjectPhraseActionTypeRequirement;
    }


    public String getPrimaryVerb() {
        return primaryVerb;
    }

    public HashSet<String> getSynonyms() {
        return synonyms;
    }

    public ActionTypeRequirement getDirectObjectPhraseActionTypeRequirement() {
        return directObjectPhraseActionTypeRequirement;
    }

    public ActionTypeRequirement getIndirectObjectPhraseActionTypeRequirement() {
        return indirectObjectPhraseActionTypeRequirement;
    }

    public void setDirectObjectPhraseCondition(HashSet<String> synonyms) {

    }
}
