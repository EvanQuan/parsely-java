package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.Action;

import java.util.HashSet;

/**
 * Specifies to a {@link Parser} the rules in which a given {@link Action}
 * is to be used. If the {@link Status}s are not met, then the
 * {@link Action} is invalid.
 *
 * @author Evan Quan
 */
class ActionType {

    private String primaryVerb;

    private HashSet<String> synonyms;

    private Requirement directObjectRequirement;
    private Requirement prepositionRequirement;
    private Requirement indirectObjectRequirement;


    ActionType(String primaryVerb,
               HashSet<String> synonyms,

               Requirement directObjectRequirement,
               Requirement prepositionRequirement,
               Requirement indirectObjectRequirement
    ) {
        this.primaryVerb = primaryVerb;
        this.synonyms = synonyms;

        this.directObjectRequirement = directObjectRequirement;
        this.prepositionRequirement = prepositionRequirement;
        this.indirectObjectRequirement = indirectObjectRequirement;
    }


    String getPrimaryVerb() {
        return primaryVerb;
    }

    HashSet<String> getSynonyms() {
        return new HashSet<>(synonyms);
    }

    public Requirement getDirectObjectRequirement() {
        return directObjectRequirement;
    }

    public Requirement getPrepositionRequirement() {
        return prepositionRequirement;
    }

    public Requirement getIndirectObjectRequirement() {
        return indirectObjectRequirement;
    }
}
