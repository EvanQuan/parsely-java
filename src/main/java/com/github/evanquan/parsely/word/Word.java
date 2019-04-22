package com.github.evanquan.parsely.word;

import com.github.evanquan.parsely.parser.Parser;
import com.github.evanquan.parsely.util.CollectionUtils;
import com.github.evanquan.parsely.util.TextUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a word. A word must contain one or more alphanumeric characters.
 *
 * @author Evan Quan
 */
public class Word {

    /**
     * For commands that refer to a quantity of object phrases, either by
     * explicitly listing them or quantifying "all" of them, there may be some
     * exceptions to the objects to refer to. These prepositions are followed by
     * the indirect object phrases to ignore.
     */
    private static final HashSet<String> EXCLUDING_PREPOSITIONS
            = new HashSet<>(
            Set.of("but", "except", "without")
    );
    /**
     * Situate one object phrase directionally in relation to another object
     * phrase
     */
    private static final HashSet<String> DIRECTIONAL_PREPOSITIONS
            = new HashSet<>(
            Set.of("over", "under", "on", "between", "behind", "in", "across")
    );
    /**
     * Connect 2 object phrases together (in some sense)
     */
    private static final HashSet<String> JOINING_PREPOSITIONS
            = new HashSet<>(
            Set.of("with", "about", "against")
    );
    /**
     * The first object phrase is being "verbed" from the player to the 2nd
     * object phrase.
     */
    private static final HashSet<String> MOVEMENT_PREPOSITIONS
            = new HashSet<>(
            Set.of("to", "at", "through")
    );
    /**
     * First object phrase is "owned by" or "belongs to" the second object
     * phrase.
     */
    private static final HashSet<String> BELONGING_PREPOSITIONS
            = new HashSet<>(
            Set.of("of")
    );
    /**
     * All prepositions used to separate object phrases.
     */
    private static final HashSet<String> OBJECT_PHRASE_SEPARATING_PREPOSITION
            = CollectionUtils.mergeHashSets(
            new HashSet[]{EXCLUDING_PREPOSITIONS,
                    DIRECTIONAL_PREPOSITIONS,
                    JOINING_PREPOSITIONS,
                    MOVEMENT_PREPOSITIONS}
    );
    /**
     * These sort of determiners do not give information about the quantity of
     * objects they refer to.
     */
    private static final HashSet<String> GENERAL_ARTICLES
            = new HashSet<>(
            Set.of("the", "this", "that")
    );
    /**
     * These refer to objects in the player's possession.
     */
    private static final HashSet<String> PLAYER_ARTICLES
            = new HashSet<>(
            Set.of("my")
    );
    /**
     * For creating {@link PlayerCommand}s, all articles are treated the same.
     */
    private static final HashSet<String> ARTICLES
            = CollectionUtils.mergeHashSets(
            new HashSet[]{GENERAL_ARTICLES, PLAYER_ARTICLES}
    );
    /**
     * A type of determiner that quantifies the object phrase. While numerical
     * values directly quantify an object phrase (which is valid), these
     * quantifier words can also give a quantity as well. Example: all is used
     * to signify all objects (in inventory, or room) a/an is equivalent to 1
     * the is excluded because the noun in the object phrase determines the
     * quantity.
     */
    private static final HashSet<String> QUANTIFIERS
            = new HashSet<>(
            Set.of("a", "an", "all")
    );
    /**
     * While the {@link Parser} will treat any word that ends with "ly" as an
     * adverb for parsing strings into {@link PlayerCommand}s, the game will
     * reject all adverbs it doesn't recognize.
     */
    private static final HashSet<String> ADVERBS
            = new HashSet<>(
            Set.of("quickly", "slowly", "sneakily", "loudly", "quietly")
    );
    /**
     * Action verbs that have a direct object phrase attached directly to them,
     * but never an indirect object phrase. Examples: eat cake, take gold
     */
    private static final HashSet<String> NON_INDIRECT_TRANSITIVE_VERBS
            = new HashSet<>(
            Set.of("eat", "take", "get", "drop", "remove", "hit", "examine")
    );
    /**
     * Action verbs that have a direct object phrase attached directly to them,
     * and sometimes optionally an indirect object phrase. Examples: go (to)
     * west, use key (on door)
     */
    private static final HashSet<String> OPTIONALLY_INDIRECT_TRANSITIVE_VERBS
            = new HashSet<>(
            Set.of("go", "use", "move", "walk", "run", "travel")

    );
    /**
     * Transitive verbs that REQUIRE an indirect object phrase for the command
     * to fully to make sense, although without an indirect object phrase, it
     * will still parse correctly. Examples: give gold to guard (give gold ->
     * give gold to who?)
     */
    private static final HashSet<String> MANDATORY_INDIRECT_TRANSITIVE_VERBS
            = new HashSet<>(
            Set.of("give")

    );
    /**
     * Action verbs that cannot attach directly to a direct object phrase to
     * make complete sense. They need the help of an object phrase separating
     * preposition (and thus an indirect object phrase to make sense)
     */
    private static final HashSet<String> NON_TERMINATING_INTRANSITIVE_VERBS
            = new HashSet<>(
            Set.of()
    );
    /**
     * Optionally terminating intransitive verbs can optionally have an attached
     * indirect object phrase, but can also be used as a terminating verb by
     * itself.
     */
    private static final HashSet<String> OPTIONALLY_TERMINATING_INTRANSITIVE_VERBS
            = new HashSet<>(
            Set.of("jump", "quit", "look")
    );
    /**
     * Terminating intransitive verbs cannot have attached direct or indirect
     * object phrases and are actions entirely on their own.
     */
    private static final HashSet<String> TERMINATING_INTRANSITIVE_VERBS
            = new HashSet<>(
            Set.of("die")
    );
    /**
     * Intransitive verbs cannot attached to a direct object phrase.
     */
    private static final HashSet<String> INTRANSITIVE_VERBS
            = CollectionUtils.mergeHashSets(
            new HashSet[]{NON_TERMINATING_INTRANSITIVE_VERBS,
                    OPTIONALLY_TERMINATING_INTRANSITIVE_VERBS,
                    TERMINATING_INTRANSITIVE_VERBS
            }
    );
    /**
     * Verbs are action words.
     */
    private static final HashSet<String> VERBS
            = CollectionUtils.mergeHashSets(
            new HashSet[]{NON_INDIRECT_TRANSITIVE_VERBS,
                    OPTIONALLY_INDIRECT_TRANSITIVE_VERBS,
                    MANDATORY_INDIRECT_TRANSITIVE_VERBS,
                    INTRANSITIVE_VERBS
            }
    );
    /**
     * PlayerAction separators separates {@link PlayerCommand} playerActions.
     * These are used for receiveInput multi-playerAction stringCommands.
     */
    private static final HashSet<String> ACTION_SEPARATORS
            = new HashSet<>(
            Set.of(",", "and", "then", ".")
    );

    /**
     * Cannot instantiate.
     */
    private Word() {
    }

    /**
     * Action separators are words (or characters) that split a input string
     * from a {@link PlayerCommand} into multiple {@link PlayerAction}s.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid action
     * separator.
     */
    public static boolean isActionSeparator(String word) {
        return ACTION_SEPARATORS.contains(word.toLowerCase());
    }

    /**
     * An article specify a noun.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid article.
     */
    public static boolean isArticle(String word) {
        return ARTICLES.contains(word.toLowerCase());
    }

    /**
     * A determiner is either an article, or a quantifier.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid determiner,
     * which is either an article or quantifier.
     */
    public static boolean isDeterminer(String word) {
        return isArticle(word.toLowerCase()) || isQuantifier(word.toLowerCase());
    }

    /**
     * @param word to check
     * @return true if the specified word is recognized as a valid directional
     * preposition.
     */
    public static boolean isDirectionalPreposition(String word) {
        return DIRECTIONAL_PREPOSITIONS.contains(word.toLowerCase());
    }

    /**
     * @param word to check
     * @return true if the specified word is recognized as a valid joining
     * preposition.
     */
    public static boolean isJoiningPreposition(String word) {
        return JOINING_PREPOSITIONS.contains(word.toLowerCase());
    }

    /**
     * @param word to check
     * @return true if the specified word is recognized as a valid movement
     * preposition.
     */
    public static boolean isMovementPreposition(String word) {
        return MOVEMENT_PREPOSITIONS.contains(word.toLowerCase());
    }

    /**
     * These types of prepositions signify separating direct and indirect object
     * phrases. This excludes belonging prepositions.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid preposition
     * for separating object phrases.
     */
    public static boolean isObjectPhraseSeparatingPreposition(String word) {
        return OBJECT_PHRASE_SEPARATING_PREPOSITION.contains(word.toLowerCase());
    }

    /**
     * Quantifiers specify a quantity of a noun. They can be as specific as a
     * number, or specify "all" of an noun.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid quantifier.
     */
    public static boolean isQuantifier(String word) {
        return QUANTIFIERS.contains(word.toLowerCase())
                || TextUtils.isInteger(word.toLowerCase());
    }

    /**
     * This is used for separating object phrases from their owners.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid belonging
     * preposition.
     */
    public static boolean isBelongingPreposition(String word) {
        return BELONGING_PREPOSITIONS.contains(word.toLowerCase());
    }

    /**
     * All words that end with "ly" are treated as adverbs for parsing
     * purposes.
     *
     * @param word to check
     * @return true if the specified word is recognized as following the
     * structure of an adverb.
     */
    public static boolean isAdverb(String word) {
        return word.toLowerCase().endsWith("ly");
    }

    /**
     * Known adverbs are adverbs that the game recognizes as valid and can be
     * used in commands.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid adverb.
     */
    public static boolean isKnownAdverb(String word) {
        return ADVERBS.contains(word.toLowerCase());
    }

    /**
     * Verbs are words that specify an action.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid verb.
     */
    public static boolean isVerb(String word) {
        return VERBS.contains(word.toLowerCase());
    }

    /**
     * Non-indirect transitive verbs must have a direct object phrase attached
     * directly to them, but never an indirect object phrase in order to make
     * sense.
     *
     * @param word to check
     * @return true if the specified word is recognized as a valid
     */
    public static boolean isNonIndirectTransitiveVerb(String word) {
        return NON_INDIRECT_TRANSITIVE_VERBS.contains(word.toLowerCase());
    }

    /**
     * Indirect transitive verbs must have a direct object and indirect object
     * phrase attached to them in order to make sense.
     *
     * @param word to check
     * @return true if the specified word is recognized as an indirect
     * transitive verb.
     */
    public static boolean isIndirectTransitiveVerb(String word) {
        return MANDATORY_INDIRECT_TRANSITIVE_VERBS.contains(word.toLowerCase());
    }

    /**
     * Optionally-indirect transitive verbs must have a direct object phrase
     * attached directly to the, and optionally an indirect object phrase to
     * make sense. The specific context that the verb is being used and what the
     * direct object phrase is determines whether the indirect object phrase is
     * needed to make sense.
     *
     * @param word to check
     * @return true fi the specified word is recognized as an optionally
     * indirect transitive verb.
     */
    public static boolean isOptionallyIndirectTransitiveVerb(String word) {
        return OPTIONALLY_INDIRECT_TRANSITIVE_VERBS.contains(word.toLowerCase());
    }

    /**
     * Intransitive verbs cannot attach directly to a direct object phrase to
     * make sense. They need the help of an object phrase separating preposition
     * and an indirect object phrase to make sense.
     *
     * @param word to check
     * @return true if the specified word is an intransitive verb.
     */
    public static boolean isIntransitiveVerb(String word) {
        return INTRANSITIVE_VERBS.contains(word.toLowerCase());
    }

    /**
     * Optionally terminating intransitive verbs can optionally have an attached
     * indirect object phrase, but can also be used as a terminating verb by
     * itself.
     *
     * @param word to check
     * @return true if the specified word is recognized as an optionally
     * terminating intransitive verb.
     */
    public static boolean isOptionallyTerminatingIntransitiveVerb(String word) {
        return OPTIONALLY_TERMINATING_INTRANSITIVE_VERBS.contains(word.toLowerCase());
    }

    /**
     * Action verbs that cannot attach directly to a direct object phrase to
     * make complete sense. They need the help of an object phrase separating
     * preposition (and thus an indirect object phrase to make sense)
     *
     * @param word to check
     * @return true if the specified word is recognized as a terminating
     * intransitive verb.
     */
    public static boolean isTerminatingIntransitiveVerb(String word) {
        return TERMINATING_INTRANSITIVE_VERBS.contains(word.toLowerCase());
    }

    /**
     * Terminating intransitive verbs cannot have attached direct or indirect
     * object phrases and are actions entirely on their own.
     *
     * @param word to check
     * @return true if the specified word is recognized as a non-terminating
     * intransitive verb.
     */
    public static boolean isNonTerminatingIntransitiveVerb(String word) {
        return NON_TERMINATING_INTRANSITIVE_VERBS.contains(word.toLowerCase());
    }
}
