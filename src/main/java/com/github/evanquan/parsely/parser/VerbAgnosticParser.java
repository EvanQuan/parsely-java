package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.*;

import java.util.ArrayList;

/**
 * Parses a {@link String} into a {@link Command}. The parser abides by the
 * following grammar rules:
 * <p>
 * 1. The dictionary of all possible verbs, adjectives, direct objects, and
 * indirect objects is not known.<br> - The game handles the validity of these
 * words, not the parser.<br> 2. The first words of the receiveInput is always a
 * verb.<br> - Player {@link Command}s are 2nd person imperative statements.<br>
 * 3. Indirect object phrases are always preceded by a preposition.<br> 4.
 * Direct object phrases are always positioned before indirect object
 * phrases.<br> 5. The dictionary of all possible prepositions is known.<br> 6.
 * The dictionary of all possible determiners is known.<br>
 *
 * <p>
 * <b>Due to not having a verb dictionary, this cannot do the following:</b>
 * </p>
 * Multiple-{@link Action} {@link Command}s, such as:<br> Multiverb
 * commands:
 * (look up, eat pie, go west)<br> Verbsharing {@link Command}s: (eat pie, potato,
 * cake)<br> Object pronouns (this may not be implemented here): (take pie, eat
 * it)<br>
 * <br>
 * With the current implementation, an indeterminism problem arises in trying to
 * parse these kind of {@link Command}s without a dictionary of valid verbs. As a
 * bonus this would allow for verbs to be modified with adverbs<br> For this to
 * be implemented, what needs to be done:<br> - A verb dictionary<br> -
 * lexicalAnalysis() needs to recognize commas at the end of words as their own
 * tokens<br> - syntacticalAnalysis() needs to separate playerActions by
 * separators<br> - incomplete playerActions need to be able to "fill in the
 * gaps" from context of previously parsed playerActions in the same
 * command<br>
 *
 * @author Evan Quan
 */
class VerbAgnosticParser extends Parser {

    private static VerbAgnosticParser instance = null;
    private VerbAgnosticParser() {
    }

    /**
     * Can only be instantiated by {@link ParserFactory}
     */
    static VerbAgnosticParser getInstance() {
        if (instance == null) {
            instance = new VerbAgnosticParser();
        }
        return instance;
    }

    /**
     * Find an objective phrase from a list of tokens. Can be either a direct or
     * indirect object phrase. This modifies the tokens argument (may be changed
     * later if needed).
     *
     * @param tokens to convert to an object phrase
     * @return object phrase that is composed of all token components, or null
     * if tokens is empty
     */
    @Override
    public ObjectPhrase getObjectPhrase(ArrayList<String> tokens) {
        if (tokens.isEmpty()) {
            return null;
        }
        ObjectPhrase objectPhrase = new ObjectPhrase();
        // Scan for an determiner. If one is found, remove it and parse the
        // rest of the receiveInput.
        // NOTE: The preposition must be the first words in the list for it to
        // make sense grammatically. If a determiner is preceded with another
        // words, be it another determiner or not, it will be counted as an
        // adjective.
        if (Word.isDeterminer(tokens.get(0))) {
            objectPhrase.setDeterminer(tokens.remove(0));
        }
        // The last words in the receiveInput is the object. Remove it and parse the
        // rest of the receiveInput.
        if (!tokens.isEmpty()) {
            // If no more tokens remain, then the last words is not a noun
            objectPhrase.setNoun(tokens.remove(tokens.size() - 1));
        }
        // If any receiveInput remains, they are adjectives which modify the object.
        // TODO: This WILL need to change once multiple {@link Command}s separated by commas
        // with a
        // single verb is implemented. Either here, or in syntactical analysis.
        ArrayList<String> adjectives = new ArrayList<>(tokens);
        objectPhrase.setAdjectives(adjectives);
        return objectPhrase;
    }

    /**
     * Parse receiveInput text into words and apply their appropriate meanings
     * and relationships. Accepts only imperative statements.
     *
     * @param input - String to parse into words
     * @return command that represents the player {@link Command}
     */
    @Override
    public Command parse(String input) {
        // Add unaltered receiveInput to Command
        // https://groups.google.com/forum/#!topic/rec.arts.int-fiction/VpsWZdWRnlA
        ArrayList<String> tokens = lexicalAnalysis(input);
        Action action = syntacticalAnalysis(tokens);

        return new Command(input, action);
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
     * indirect objects is <b>not</b> known.<br> 1. The first world of the
     * receiveInput is a always a verb unless<br> - it is a valid determiner,
     * which then the verb phrase is skipped and the indirect object phrase is
     * parsed.<br> - it is a preposition, which then the verb phrase and
     * indirect object phrase is skipped and the preposition and indirect object
     * phrase are parsed.<br> 2. Indirect object phrases are always preceded by
     * a preposition.<br> 3. Direct object phrases are always positioned before
     * indirect object phrases.<br> 4. The dictionary of all possible
     * Prepositions is known.<br> 5. The dictionary of all possible determiners
     * is known.
     *
     * @param tokens to parse for actions
     * @return action parsed from tokens
     */
    private Action syntacticalAnalysis(ArrayList<String> tokens) {

        Action action = new Action();
        if (tokens.isEmpty()) {
            // This happens when the player receiveInput an empty string
            return action;
        }
        // TODO when multi-action {@link Command}s are implemented, make this
        //  part a loop for
        // every separator section


        String first = tokens.get(0);
        if (!Word.isDeterminer(first) && !Word.isObjectPhraseSeparatingPreposition(first)) {
            // 0. The first words is a verb. Remove it and parse the rest of the receiveInput.
            // No adverbs are allowed as it would not be possible to distinguish between the
            // end of the verb phrase and the start of the proceeding indirect/direct object
            // phrase without a dictionary of all possible verbs.
            tokens.remove(0);
            action.setVerbPhrase(new VerbPhrase(first));
        }
        // 1. Scan for a preposition. If one is found, remove it. Parse the receiveInput
        // preceding the preposition as a direct object phrase. Parse the receiveInput
        // following the preposition as an indirect object phrase.
        // For the sake of how the Command will be parsed in the game, the
        // preposition
        // is added to the indirect object phrase.

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

    // /**
    // * <b>Part 3: Translation</b>
    // * <p>
    // * TODO Creates a player command from a list of words. Depending on the
    // relation
    // * between words, the playerAction {@link Verb} and object {@link Noun} are
    // * determined.
    // *
    // * @param receiveInput
    // * - original receiveInput string
    // * @param statement
    // * @return
    // */
    // private static Command translation(String receiveInput, Sentence statement) {
    // // Index tracking
    // int actionIndex = 0;
    // int objectIndex = 0;
    // // 1. The first words of the command should either be a verb, or a shortcut
    // // represents some playerAction
    //
    // // return new Command(command, playerAction, object);
    // return null;
    // }
}
