package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.Action;

import java.util.HashSet;

/**
 * Specifies to a {@link Parser} the rules in which a given {@link Action}
 * is to be used. If the {@link Requirement}s are not met, then the
 * {@link Action} is invalid.
 *
 * @author Evan Quan
 */
public class ActionType {

    private String primaryVerb;

    private HashSet<String> synonyms;

    private Requirement directObjectPhraseRequirement;
    private Condition directObjectPhraseCondition;
    private Requirement directObjectPhraseDefault;

    private Requirement prepositionRequirement;
    private Condition prepositionCondition;
    private Requirement prepositionDefault;

    private Requirement indirectObjectPhraseRequirement;
    private Condition indirectObjectPhraseCondition;
    private Requirement indirectObjectPhraseDefault;


    ActionType(String primaryVerb,
               HashSet<String> synonyms,
               Requirement directObjectPhraseRequirement,
               Condition directObjectPhraseCondition,
               Requirement directObjectPhraseDefault,

               Requirement prepositionRequirement,
               Condition prepositionCondition,
               Requirement prepositionDefault,

               Requirement indirectObjectPhraseRequirement,
               Condition indirectObjectPhraseCondition,
               Requirement indirectObjectPhraseDefault
    ) {
        this.primaryVerb = primaryVerb;
        this.synonyms = synonyms;

        this.directObjectPhraseRequirement = directObjectPhraseRequirement;
        this.directObjectPhraseCondition = directObjectPhraseCondition;
        this.directObjectPhraseDefault = directObjectPhraseDefault;

        this.prepositionRequirement = prepositionRequirement;
        this.prepositionCondition = prepositionCondition;
        this.prepositionDefault = prepositionDefault;


        this.indirectObjectPhraseRequirement = indirectObjectPhraseRequirement;
        this.indirectObjectPhraseCondition = indirectObjectPhraseCondition;
        this.indirectObjectPhraseDefault = indirectObjectPhraseDefault;
    }


    public String getPrimaryVerb() {
        return primaryVerb;
    }

    public HashSet<String> getSynonyms() {
        return new HashSet<>(synonyms);
    }

    public Requirement getDirectObjectPhraseRequirement() {
        return directObjectPhraseRequirement;
    }

    public Requirement getIndirectObjectPhraseRequirement() {
        return indirectObjectPhraseRequirement;
    }

    public Condition getDirectObjectPhraseCondition() {
        return directObjectPhraseCondition;
    }

    public Requirement getDirectObjectPhraseDefault() {
        return directObjectPhraseDefault;
    }

    public Requirement getPrepositionRequirement() {
        return prepositionRequirement;
    }

    public Condition getPrepositionCondition() {
        return prepositionCondition;
    }

    public Requirement getPrepositionDefault() {
        return prepositionDefault;
    }

    public Condition getIndirectObjectPhraseCondition() {
        return indirectObjectPhraseCondition;
    }

    public Requirement getIndirectObjectPhraseDefault() {
        return indirectObjectPhraseDefault;
    }
}
