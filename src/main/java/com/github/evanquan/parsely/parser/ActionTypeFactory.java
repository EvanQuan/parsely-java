package com.github.evanquan.parsely.parser;

import java.util.Collection;
import java.util.HashSet;

/**
 * Creates {@link ActionType}s.
 */
final class ActionTypeFactory {

    private static ActionTypeFactory instance = null;

    /**
     * Tracks verbs that have already been used in configuring {@link
     * ActionType}s.
     */
    private HashSet<String> usedVerbs;

    private ActionTypeFactory() {
        usedVerbs = new HashSet<>();
    }


    public static ActionTypeFactory getInstance() {
        if (instance == null) {
            instance = new ActionTypeFactory();
        }
        return instance;
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
     * @param directObjectPhraseCondition     to check for validity
     * @param directObjectPhraseDefault       for check for validity
     * @param indirectObjectPhraseRequirement for check for validity
     * @param indirectObjectPhraseCondition   for check for validity
     * @param indirectObjectPhraseDefault     for check for validity
     * @return the configured {@link ActionType}.
     * @throws Exception if the verb is already being used by another {@link
     *                   ActionType}.
     */
    public ActionType getActionType(String primaryVerb,
                                    Collection<String> synonyms,
                                    Requirement directObjectPhraseRequirement,
                                    Condition directObjectPhraseCondition,
                                    Requirement directObjectPhraseDefault,

                                    Requirement prepositionRequirement,
                                    Condition prepositionCondition,
                                    Requirement prepositionDefault,

                                    Requirement indirectObjectPhraseRequirement,
                                    Condition indirectObjectPhraseCondition,
                                    Requirement indirectObjectPhraseDefault
    ) throws Exception {
        addVerbsToUsed(primaryVerb, synonyms);

        return new ActionType(primaryVerb,
                new HashSet<>(synonyms),
                directObjectPhraseRequirement,
                directObjectPhraseCondition,
                directObjectPhraseDefault,

                prepositionRequirement,
                prepositionCondition,
                prepositionDefault,

                indirectObjectPhraseRequirement,
                indirectObjectPhraseCondition,
                indirectObjectPhraseDefault
        );
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
    private void addVerbsToUsed(String primaryVerb,
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
    private boolean removeActionType(ActionType actionType) {

        boolean removed = usedVerbs.remove(actionType.getPrimaryVerb());

        removed =
                usedVerbs.removeAll(actionType.getSynonyms()) || removed;

        return removed;

    }

}
