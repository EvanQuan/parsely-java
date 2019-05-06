package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parses an input string into a {@link Command}. The parser abides by the
 * following grammar rules:
 * <p>
 * 1. The dictionary of all possible verbs, adjectives, direct objects, and
 * indirect objects is not known.<br> - The game handles the validity of these
 * words, not the parser.<br> 2. The first words of the input is always a
 * verb.<br> - Player {@link Command} are 2nd person imperative statements.<br>
 * 3. Indirect object phrases are always preceded by a preposition.<br> 4.
 * Direct object phrases are always positioned before indirect object
 * phrases.<br> 5. The dictionary of all possible prepositions is known.<br> 6.
 * The dictionary of all possible determiners is known.<br> 7. The dictionary of
 * all possible verbs is known.<br>
 *
 * <p>
 * <b>TODO</b>
 * </p>
 * Action multiplying: when multiple direct objects share a single preposition
 * with multiple indirect objects, the verb and preposition is applied to all
 * permutations of direct and indirect object phrases.
 *
 * @author Evan Quan
 */
public class VerbGnosticParser extends Parser {

    /**
     * These determine what count as verbs, as well as recognizing verb
     * synonyms.
     */
    private HashMap<String, String> actionTypes;

    /**
     * Can only be instantiated by ParserFactory
     */
    VerbGnosticParser(HashMap<String, String> actionTypes) {
        this.actionTypes = actionTypes;
    }

    /**
     * Find an objective phrase from a list of tokens. Can be either a direct or
     * indirect object phrase. This modifies the tokens argument (may be changed
     * later if needed).
     *
     * @param tokens to parse for an object phrase
     * @return object phrase that is composed of all token components, or null
     * if tokens is empty. Since this is recursively called for owners, an
     * ObjectPhrase without an owner will have a null owner.
     */
    public ObjectPhrase getObjectPhrase(ArrayList<String> tokens) {
        if (tokens.isEmpty()) {
            return null;
        }
        ObjectPhrase objectPhrase = new ObjectPhrase();
        // Scan for an determiner. If one is found, remove it and parse the
        // rest of the input.
        // NOTE: The preposition must be the first words in the list for it to
        // make sense grammatically. If a determiner is preceded with another
        // words, be it another determiner or not, it will be counted as an
        // adjective.
        if (Word.isDeterminer(tokens.get(0))) {
            objectPhrase.setDeterminer(tokens.remove(0));
        }

        // Scan for a belonging preposition (of), which determines if this
        // object phrase is owned by an other object phrase.
        // thisTokens is composed of tokens to be used for this object phrase
        // and not its owner (if any).
        ArrayList<String> thisTokens = new ArrayList<>();
        int i;
        for (i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (Word.isBelongingPreposition(token)) {
                objectPhrase.setPreposition(token);
                break;
            } else {
                thisTokens.add(token);
            }
        }

        // Add remaining tokens after belonging preposition to be parsed
        // recursively for owner(s).
        ArrayList<String> ownerTokens = new ArrayList<>();
        for (i++; i < tokens.size(); i++) {
            ownerTokens.add(tokens.get(i));
        }
        objectPhrase.setOwner(getObjectPhrase(ownerTokens));

        // The last words in the input is the object. Remove it and parse
        // the rest of the input.
        if (!thisTokens.isEmpty()) {
            // If no more tokens remain, then the last words is not a noun
            objectPhrase.setNoun(thisTokens.remove(thisTokens.size() - 1));
        }
        // If any input remains, they are adjectives which modify the
        // object.
        // TODO: This WILL need to change once multiple {@link Command} separated
        // by commas with a single verb is implemented. Either here, or in
        // syntactical analysis.
        ArrayList<String> adjectives = new ArrayList<>();
        for (i = 0; i < thisTokens.size(); i++) {
            adjectives.add(thisTokens.get(i));
        }
        objectPhrase.setAdjectives(adjectives);
        return objectPhrase;
    }

    /**
     * Parse input text into words and apply their appropriate meanings and
     * relationships. Accepts only imperative statements.
     *
     * @param input - String to parse into words
     * @return command that represents the player {@link Command}
     */
    @Override
    public Command parse(String input) {
        // https://groups.google.com/forum/#!topic/rec.arts.int-fiction/VpsWZdWRnlA
        ArrayList<String> tokens = lexicalAnalysis(input);
        ArrayList<ArrayList<String>> tokenListsPerAction =
                splitTokensByActions(tokens);

        ArrayList<Action> actions = new ArrayList<>();
        for (ArrayList<String> actionTokens : tokenListsPerAction) {
            Action action = syntacticalAnalysis(actionTokens);
            if (!action.isEmpty()) {
                actions.add(action);
            }
        }

        syntacticalCleanup(actions);

        return new Command(input, actions);
    }

    /**
     * For multi-{@link Action} {@link Command}, {@link Action} separators define the
     * number of playerActions that are present in a command. Single
     * syntacticalAnalysis() assumes an ArrayList of tokens is a single
     * {@link Action}, we need to make an ArrayList of ArrayLists (playerActions).
     * Separators are not included in any token array.
     * <p>
     * TODO: Optimization: redo this so it doesn't need to traverse the tokens
     * twice
     *
     * @param tokens to split
     * @return list of token lists, where each token list corresponds to an
     * action
     */
    public ArrayList<ArrayList<String>> splitTokensByActions(
            ArrayList<String> tokens) {
        // Each ArrayList sublist separated by separators counts as its own
        // {@link Action}
        // Find the number of playerActions and track what index the
        // playerActions are separated by.
        ArrayList<Integer> separatorIndices = new ArrayList<>();
        ArrayList<ArrayList<String>> actionTokens = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (Word.isActionSeparator(tokens.get(i))) {
                separatorIndices.add(i);
            }
        }
        // Separate each {@link Action}, defined by separatorIndices, into its
        // own ArrayList of tokens. Action separators are not included
        // in the arrays.
        int startIndex = 0;
        int endIndex;
        for (int i : separatorIndices) {
            endIndex = i; // excludes separator token
            actionTokens.add(new ArrayList<>(tokens.subList(startIndex, endIndex)));
            startIndex = endIndex + 1; // skips over separator token
        }
        // Add remaining tokens until end of tokens.
        endIndex = tokens.size();
        actionTokens.add(new ArrayList<>(tokens.subList(startIndex, endIndex)));

        return actionTokens;
    }

    /**
     * <b>Step 2: Syntactical Analysis</b>
     * <p>
     * Takes a sequence of tokens and sees whether the sequences matches a known
     * correct sentence structure.
     * <p>
     * <b>Grammar Rules</b><br>
     * <p>
     * 0. The dictionary of all possible verbs, adjectives, direct objects, and
     * indirect objects is <b>not</b> known.<br> 1. The first world of the input
     * is a always a verb unless<br> - it is a valid determiner, which then the
     * verb phrase is skipped and the indirect object phrase is parsed.<br> - it
     * is a preposition, which then the verb phrase and indirect object phrase
     * is skipped and the preposition and indirect object phrase are parsed.<br>
     * 2. Indirect object phrases are always preceded by a preposition.<br> 3.
     * Direct object phrases are always positioned before indirect object
     * phrases.<br> 4. The dictionary of all possible Prepositions is known.<br>
     * 5. The dictionary of all possible determiners is known.<br>
     *
     * @param tokens to parse for action
     * @return action parsed from tokens
     */
    private Action syntacticalAnalysis(ArrayList<String> tokens) {

        Action action = new Action();
        if (tokens.isEmpty()) {
            // This happens when the player input string an empty string
            // This is also the base case to stop ObjectPhrase owner recursion
            return action;
        }

        // Get adverbs
        VerbPhrase verbPhrase = new VerbPhrase();
        ArrayList<String> adverbs = new ArrayList<>();
        while (!tokens.isEmpty() && Word.isAdverb(tokens.get(0))) {
            adverbs.add(tokens.get(0));
            tokens.remove(0);
        }
        verbPhrase.setAdverbs(adverbs);
        if (tokens.isEmpty()) {
            // If the action is only adverbs, then return early
            action.setVerbPhrase(verbPhrase);
            return action;
        }
        if (isVerb(tokens.get(0))) {
            // 0. The first words is a verb. Remove it and parse the rest of the
            // input. No adverbs are allowed as it would not be
            // possible to distinguish between the end of the verb phrase and
            // the start of the proceeding indirect/direct object
            // phrase without a dictionary of all possible verbs.
            verbPhrase.setVerb(tokens.get(0));
            tokens.remove(0);
        }
        action.setVerbPhrase(verbPhrase);
        // 1. Scan for a preposition. If one is found, remove it.
        // Parse the input preceding the preposition as a direct object
        // phrase. Parse the input following the preposition as an
        // indirect object phrase. For the sake of how the Command will
        // be parsed in the game, the preposition is added to the indirect
        // object phrase.

        // Add first tokens before preposition (if any) to direct tokens.
        // If there is a preposition, store it by itself.
        ArrayList<String> directTokens = new ArrayList<>();
        int i;
        for (i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (Word.isObjectPhraseSeparatingPreposition(token)) {
                action.setPreposition(token);
                break;
            } else {
                directTokens.add(token);
            }
        }
        // Add remaining tokens after preposition (if any) to indirect tokens.
        ArrayList<String> indirectTokens = new ArrayList<>();
        for (i++; i < tokens.size(); i++) {
            indirectTokens.add(tokens.get(i));
        }

        // Create the object phrases from the token lists
        action.setDirectObjectPhrase(getObjectPhrase(directTokens));
        action.setIndirectObjectPhrase(getObjectPhrase(indirectTokens));

        return action;
    }

    /**
     * Modify all actions in a {@link Command} so that inferred components
     * can be added to each of them. In more detail, this ensures that all valid
     * multi-actions work by ensuring each one has the verb phrases,
     * prepositions, direct and indirect object phrases necessary.
     *
     * @param actions to fix
     */
    private void syntacticalCleanup(ArrayList<Action> actions) {
        if (actions.isEmpty()) {
            return;
        }

        // Backwards fix must be applied after forward fix because forward
        // fix fixes some direct object phrases to indirect object
        // phrases
        fixSyntaxForward(actions);
        fixSyntaxBackwards(actions);
    }


    /**
     * Copy verb phrase forward if it has one. If a new verb phrase is found
     * with an accompanying direct object phrase, set that as the new verb to
     * copy forward. The accompanying direct object phrase is needed because
     * otherwise, the verb is being treated as an intransitive verb, which
     * cannot transfer onto a list of direct object verbs.
     * <p>
     * Prepositions are also added if the initial verb has a preposition. Direct
     * object phrases are changed in indirect object phrases to have the
     * preposition refer to the object phrase. If a new preposition is found,
     * switch to the new preposition to copy.
     *
     * @param actions to fix
     */
    private void fixSyntaxForward(ArrayList<Action> actions) {
        VerbPhrase verbPhraseToCopy = null;
        ObjectPhrase directObjectToCopy = null;
        String prepositionToCopy = null;
        for (Action action : actions) {
            verbPhraseToCopy = getForwardVerbPhraseToCopy(action, verbPhraseToCopy);
            directObjectToCopy = getForwardDirectObjectToCopy(action,
                    verbPhraseToCopy,
                    directObjectToCopy);
            prepositionToCopy = getForwardPrepositionToCopy(action, verbPhraseToCopy,
                    prepositionToCopy);

            setForwardVerbPhraseToCopy(action, verbPhraseToCopy);
            setForwardPrepositionToCopy(action, prepositionToCopy);
            moveForwardDirectToIndirect(action, prepositionToCopy);
            setForwardDirectObjectToCopy(action, directObjectToCopy);
        }
    }

    private VerbPhrase getForwardVerbPhraseToCopy(Action action,
                                                  VerbPhrase previousVerbPhraseToCopy) {
        if (action.hasVerbPhrase()
                && (action.hasDirectObjectPhrase() || action.hasIndirectObjectPhrase())) {
            return action.getVerbPhrase();
        }
        if (action.hasVerbPhrase()) {
            // Standalone verb phrases signify terminating intransitive verbs.
            // These verbs alone are their own actions and so don't transfer
            // to future listed direct or indirect object phrases.
            return null;
        }
        // At this point, an action with a direct or indirect object phrase
        // without a corresponding verb phrase is found.
        // This means that the previously established verb phrase to copy
        // should be copied to this action.
        return previousVerbPhraseToCopy;
    }

    /**
     * A direct object phrase is identified to start copying over if a phrase
     * has a verb, direct object phrase, preposition and indirect object
     * phrase.
     * <p>
     * A direct object phrase continues to copy over until it reaches an action
     * with a direct object, and (preposition or indirect object phrase), where
     * the phrase to copy is updated.
     * <p>
     * A direct object phrase stops copying completely if it reaches an action
     * where a new verb is found without a (preposition or indirect object
     * phrase).
     *
     * @param action to fix
     * @param verbPhraseToCopy forward
     * @param previousDirectObjectToCopy forward
     * @return object phrase that was fixed
     */
    private ObjectPhrase getForwardDirectObjectToCopy(Action action,
                                                      VerbPhrase verbPhraseToCopy,
                                                      ObjectPhrase previousDirectObjectToCopy) {
        if (verbPhraseToCopy != null) {
            if (action.hasDirectObjectPhrase()
                    && (action.hasPreposition() || action.hasIndirectObjectPhrase())) {
                // Cannot just be a direct object phrase alone.
                // If it is, then it will be transferred to an indirect object
                // phrase.
                // This updates the direct object phrase to copy, but
                // continues to copy for this action and future actions.
                return action.getDirectObjectPhrase();
            }
            // An action that is copying, but does not have a direct object
            // phrase means that it was previously moved to indirect, or is
            // empty so it can be filled with the direct object to copy.
            return previousDirectObjectToCopy;
        }
        return null;
    }

    /**
     * @param action to fix
     * @param verbPhraseToCopy forward
     * @param previousPrepositionToCopy forward
     * @return preposition to copy
     */
    private String getForwardPrepositionToCopy(Action action,
                                               VerbPhrase verbPhraseToCopy,
                                               String previousPrepositionToCopy) {
        if (verbPhraseToCopy != null) {
//            if (!action.hasDirectObjectPhrase()
            // Testing to see if removing the lack of direct object phrase
            // condition has negative effects
//                && action.hasPreposition()
            if (action.hasPreposition()
                    && action.hasIndirectObjectPhrase()) {
                return action.getPreposition();
            }
            if (action.hasVerbPhrase()) {
                return null;
            } // else continue copying same preposition
        } else {
            // stop copying prepositions since a null verbPhraseToCopy
            // signifies that copying has stopped
            return null;
        }
        return previousPrepositionToCopy;
    }

    /**
     * If there is a verb to copy forward, then copy it to the action.
     *
     * @param action to fix
     * @param verbPhraseToCopy forward
     */
    private void setForwardVerbPhraseToCopy(Action action,
                                            VerbPhrase verbPhraseToCopy) {
        if (verbPhraseToCopy != null) {
            action.setVerbPhrase(verbPhraseToCopy);
        }
    }

    /**
     * If there is a preposition to copy, then copy it to the action.
     *
     * @param action to fix
     * @param prepositionToCopy forward
     */
    private void setForwardPrepositionToCopy(Action action,
                                             String prepositionToCopy) {

        if (prepositionToCopy != null) {
            action.setPreposition(prepositionToCopy);
        }
    }

    /**
     * If there is a direct object phrase to copy, then copy it to the action.
     *
     * @param action to fix
     * @param directObjectToCopy forward
     */
    private void setForwardDirectObjectToCopy(Action action,
                                              ObjectPhrase directObjectToCopy) {
        if (directObjectToCopy != null) {
            action.setDirectObjectPhrase(directObjectToCopy);
        }
    }

    /**
     * If a preposition was copied and the action has a direct object phrase,
     * and not an indirect object phrase, then move the direct object phrase to
     * become indirect.
     *
     * @param action to fix
     * @param prepositionToCopy forward
     */
    private void moveForwardDirectToIndirect(Action action,
                                             String prepositionToCopy) {
        if (prepositionToCopy != null) {
            ObjectPhrase directToCopy = action.getDirectObjectPhrase();
            if (directToCopy != null && !action.hasIndirectObjectPhrase()) {
                action.setIndirectObjectPhrase(action.getDirectObjectPhrase());
                action.setDirectObjectPhrase(null);
            }
        }
    }

    /**
     * Copy preposition and indirect object phrase backwards. If a new
     * preposition or indirect object phrase is found, set that as the new
     * preposition or indirect object phrase to copy backwards.
     *
     * @param actions to fix
     */
    private void fixSyntaxBackwards(ArrayList<Action> actions) {
        VerbPhrase lastVerbPhrase = actions.get(0).hasVerbPhrase() ?
                actions.get(0).getVerbPhrase() : null;
        String prepositionToCopy = null;
        ObjectPhrase indirectToCopy = null;
        Action action;
        for (int i = actions.size() - 1; i >= 0; i--) {
            action = actions.get(i);

            prepositionToCopy = getBackwardPrepositionToCopyBefore(action,
                    lastVerbPhrase, prepositionToCopy);
            indirectToCopy = getBackwardsIndirectToCopyBefore(action,
                    lastVerbPhrase, indirectToCopy);

            setBackwardsPrepositionToCopy(action, prepositionToCopy);
            setBackwardsIndirectToCopy(action, indirectToCopy);

//            prepositionToCopy = getBackwardPrepositionToCopyAfter(action,
//                    prepositionToCopy);
//            indirectToCopy = getBackwardsIndirectToCopyAfter(action,
//                    indirectToCopy);

            lastVerbPhrase = action.hasVerbPhrase() ?
                    action.getVerbPhrase() : null;
        }

    }

    /**
     * If a preposition is found, copy it backwards. If a new preposition is
     * found, then change to that preposition to copy backwards. Otherwise, if
     * an action with a verb phrase
     *
     * @param action to fix
     * @param prepositionToCopy backwards
     * @return preposition to copy
     */
    private String getBackwardPrepositionToCopyBefore(Action action,
                                                      VerbPhrase lastVerbPhrase,
                                                      String prepositionToCopy) {
        if (action.hasVerbPhrase() && action.getVerbPhrase().equals(lastVerbPhrase)) {
            if (action.hasPreposition()) {
                // Same verb, different preposition means update to new
                // preposition to copy
                return action.getPreposition();
            }
            // There is no preposition, so copy the preposition already defined
            return prepositionToCopy;
        }
        return null; // stop copying as verb has changed
    }

    private ObjectPhrase getBackwardsIndirectToCopyBefore(Action action,
                                                          VerbPhrase lastVerbPhrase,
                                                          ObjectPhrase indirectToCopy) {
        if (action.hasVerbPhrase() && action.getVerbPhrase().equals(lastVerbPhrase)) {
            if (action.hasIndirectObjectPhrase()) {
                return action.getIndirectObjectPhrase();
            }
            return indirectToCopy;
        }
        return null;

    }

    /**
     * After a verb has been reached, stop copying prepositions for future
     * actions.
     *
     * @param action to fix
     * @param prepositionToCopy backwards
     */
    private String getBackwardPrepositionToCopyAfter(Action action,
                                                     String prepositionToCopy) {

        if (action.hasVerbPhrase()) {
            return null;
        }
        return prepositionToCopy;
    }

    /**
     * After a verb has been reached, stop copying indirect object phrases for
     * future actions.
     *
     * @param action to fix
     * @param indirectToCopy backwards
     * @return object phrase to copy backwards
     */
    private ObjectPhrase getBackwardsIndirectToCopyAfter(Action action,
                                                         ObjectPhrase indirectToCopy) {
        if (action.hasVerbPhrase()) {
            return null;
        }
        return indirectToCopy;
    }

    /**
     * @param action to fix
     * @param prepositionToCopy backwards
     */
    private void setBackwardsPrepositionToCopy(Action action,
                                               String prepositionToCopy) {
        if (prepositionToCopy != null) {
            action.setPreposition(prepositionToCopy);
        }
    }

    /**
     * @param action to fix
     * @param indirectToCopy backwards
     */
    private void setBackwardsIndirectToCopy(Action action,
                                            ObjectPhrase indirectToCopy) {
        if (indirectToCopy != null) {
            action.setIndirectObjectPhrase(indirectToCopy);
        }
    }

    // /**
    // * <b>Part 3: Translation</b>
    // * <p>
    // * TODO Creates a player command from a list of words. Depending on the
    // relation
    // * between words, the {@link Action} {@link Verb} and object {@link Noun} are
    // * determined.
    // *
    // * @param input
    // * - original input string
    // * @param statement
    // * @return
    // */
    // private Command translation(String input, Sentence statement) {
    // // Index tracking
    // int actionIndex = 0;
    // int objectIndex = 0;
    // // 1. The first words of the command should either be a verb, or a shortcut
    // // represents some {@link Action}
    //
    // // return new Command(command, {@link Action}, object);
    // return null;
    // }
    private boolean isVerb(String word) {
        return actionTypes != null && actionTypes.containsKey(word.toLowerCase());
    }
}
