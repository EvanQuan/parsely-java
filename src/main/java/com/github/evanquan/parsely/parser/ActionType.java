package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.Action;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Specifies to a {@link Parser} the rules in which a given {@link Action}
 * is to be used. If the {@link Requirement}s are not met, then the
 * {@link Action} is invalid.
 *
 * @author Evan Quan
 */
public class ActionType {

    private Requirement[] requirements;

    private String primaryVerb;

    private HashSet<String> synonyms;

    ActionType(String primaryVerb,
               HashSet<String> synonyms,

               Requirement directObjectRequirement,
               Requirement prepositionRequirement,
               Requirement indirectObjectRequirement
    ) {
        this.primaryVerb = primaryVerb;
        this.synonyms = synonyms;

        requirements = new Requirement[]{directObjectRequirement,
                prepositionRequirement, indirectObjectRequirement};
    }

    public Requirement getDirectObjectRequirement() {
        return requirements[RequirementIndex.DIRECT_OBJECT.getValue()];
    }


    String getPrimaryVerb() {
        return primaryVerb;
    }

    HashSet<String> getSynonyms() {
        return new HashSet<>(synonyms);
    }

    public Requirement getPrepositionRequirement() {
        return requirements[RequirementIndex.PREPOSITION.getValue()];
    }

    public Requirement getIndirectObjectRequirement() {
        return requirements[RequirementIndex.INDIRECT_OBJECT.getValue()];
    }

    /**
     * Check if an {@link Action} confirms to the structure of this {@link
     * ActionType}.
     *
     * @param action to check for valid structure
     * @return the list of {@link Requirement}s that were not met. If all
     * requirements are met, the list will be empty.
     */
    public ArrayList<Requirement> checkForValidity(Action action) {
        ArrayList<Requirement> failedRequirements = new ArrayList<>();

        for (Requirement requirement : requirements) {
            if (!requirement.isMet(action)) {
                failedRequirements.add(requirement);
            }
        }

        return failedRequirements;
    }

    private enum RequirementIndex {
        DIRECT_OBJECT(0),
        PREPOSITION(1),
        INDIRECT_OBJECT(2);

        private final int value;

        RequirementIndex(int i) {
            this.value = i;
        }

        int getValue() {
            return value;
        }
    }
}
