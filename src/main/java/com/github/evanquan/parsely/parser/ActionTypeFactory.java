package com.github.evanquan.parsely.parser;

import java.util.Collection;
import java.util.HashSet;

/**
 * Creates {@link ActionType}s.
 *
 * @author Evan Quan
 */
final class ActionTypeFactory {

    /**
     * TODO to implement
     *
     * @param type
     * @return
     */
    static ActionType getActionType(DefaultType type) {
        return null;
    }

    /**
     * Common types that are are already configured.
     */
    enum DefaultType {
        GET,
        DROP,
        EAT,
        EXAMINE,
        GO,
        USE,
        GIVE,
    }


    /**
     * Tracks verbs that have already been used in configuring {@link
     * ActionType}s.
     */
    private static HashSet<String> usedVerbs = new HashSet<>();

    /**
     * Cannot instantiate
     */
    private ActionTypeFactory() {
    }

    /**
     * Retrieve an {@link ActionType}.
     *
     * @param primaryVerb                     of the action type. The action
     *                                        will primarily be referred to by
     *                                        this verb.
     * @param synonyms                        of the primary verb. If a synonym
     *                                        is used, it will be treated the
     *                                        same as using the primary verb.
     * @param directObjectPhraseRequirement   to check for validity
     * @param prepositionRequirement          to check for validity
     * @param indirectObjectPhraseRequirement to check for validity
     * @return the configured {@link ActionType}.
     * @throws Exception if the verb is already being used by another {@link
     *                   ActionType}.
     */
    static ActionType getActionType(String primaryVerb,
                                    Collection<String> synonyms,
                                    Requirement directObjectPhraseRequirement,
                                    Requirement prepositionRequirement,
                                    Requirement indirectObjectPhraseRequirement
    ) throws Exception {
        addVerbsToUsed(primaryVerb, synonyms);

        return new ActionType(primaryVerb,
                new HashSet<>(synonyms),
                directObjectPhraseRequirement,
                prepositionRequirement,
                indirectObjectPhraseRequirement
        );
    }

    enum Transitivity {
        /**
         * Requires an
         */
        TRANSTIVE,
        INTRASITIVE,
        AMBITRANSITIVE_,
    }

    /**
     * Add verbs to the used verbs collection. This ensures that there are never
     * duplicate verbs when creating {@link ActionType}s.
     *
     * @param primaryVerb of the {@link ActionType} to add
     * @param synonyms    of the {@link ActionType} to add
     * @throws Exception if the primaryVerb or synonyms contain a verb already
     *                   used by another {@link ActionType}.
     */
    private static void addVerbsToUsed(String primaryVerb,
                                       Collection<String> synonyms) throws Exception {
        String duplicateVerb = null;

        if (!usedVerbs.add(primaryVerb)) {
            duplicateVerb = primaryVerb;
        }

        for (String verb : synonyms) {
            if (!usedVerbs.add(verb)) {
                duplicateVerb = verb;
            }
        }

        if (duplicateVerb != null) {
            throw new Exception("The verb \"" + duplicateVerb + "\" has already" +
                    " been used by another ActionType. Duplicate verbs are " +
                    "not allowed.");
        }
    }

    /**
     * @param actionType to remove
     * @return true if the action was removed.
     */
    private static boolean removeActionType(ActionType actionType) {
        // Bitwise OR prevents short-circuiting, so both evaluate.
        return usedVerbs.remove(actionType.getPrimaryVerb())
                | usedVerbs.removeAll(actionType.getSynonyms());

    }

}
