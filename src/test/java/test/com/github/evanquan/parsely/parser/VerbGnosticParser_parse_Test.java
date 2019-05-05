package test.com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.parser.ParserFactory;
import com.github.evanquan.parsely.words.Action;
import com.github.evanquan.parsely.words.Command;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * @author Evan Quan
 */
public class VerbGnosticParser_parse_Test {

    private static Command command;
    private static Action action;
    private static ArrayList<Action> actions;

    /**
     * Test parse by parsing an input string and conveniently saving its
     * components in global variables.
     *
     * @param string to parse
     */
    private void testParse(String string) {
        testParse(string, false);
    }

    /**
     * Test parse by parsing an input string and conveniently saving its
     * components in global variables. Can print the command to see all the
     * components. Useful for when test case fails.
     *
     * @param string to parse
     * @param print true if should print to standard output
     */
    private void testParse(String string, boolean print) {
        command =
                Objects.requireNonNull(ParserFactory.getParser(ParserFactory.ParserType.VERB_GNOSTIC)).parse(string);
        actions = command.getActions();
        if (print) {
            System.out.println(command);
        }
        if (actions.isEmpty()) {
            action = null;
        } else {
            action = actions.get(0);
        }
    }

    /**
     * Test that an empty string parses into an empty command with no actions.
     */
    @Test
    public void word0_empty() {
        testParse("");

        assertFalse(command.hasActions());
        assertTrue(command.isEmpty());
    }

    /**
     * Test that a single determiner skips the verb parsing.
     */
    @Test
    public void word1_directDeterminer() {
        testParse("the");

        assertFalse(action.hasVerbPhrase());

        assertTrue(action.hasDirectObjectPhrase());
        assertEquals("the", action.getDirectObjectPhrase().getDeterminer());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Check that a single preposition skips the verb and direct object phrase
     * input.
     */
    @Test
    public void word1_preposition() {
        testParse("to");

        assertFalse(action.hasVerbPhrase());

        assertFalse(action.hasDirectObjectPhrase());

        assertTrue(action.hasPreposition());
        assertEquals("to", action.getPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that a single non-verb word counts as a noun. Other action
     * components are null.
     */
    @Test
    public void word1_noun() {
        testParse("b");

        assertFalse(action.hasVerbPhrase());

        assertTrue(action.hasDirectObjectPhrase());
        assertEquals("b", action.getDirectObjectPhrase().getNoun());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that an adverb suffix is treated as an adverb.
     */
    @Test
    public void word1_adverbSuffix() {
        testParse("ly");

        assertEquals(1, actions.size());

        assertTrue(action.hasVerbPhrase());
        assertTrue(action.getVerbPhrase().hasAdverbs());
        assertEquals(1, action.getVerbPhrase().getAdverbs().size());
        assertEquals("ly", action.getVerbPhrase().getAdverbs().get(0));
        assertFalse(action.getVerbPhrase().hasVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that an adverb is treated as an adverb.
     */
    @Test
    public void word1_adverbQuickly() {
        testParse("quickly");

        assertEquals(1, actions.size());

        assertTrue(action.hasVerbPhrase());
        assertTrue(action.getVerbPhrase().hasAdverbs());
        assertEquals(1, action.getVerbPhrase().getAdverbs().size());
        assertEquals("quickly",
                action.getVerbPhrase().getAdverbs().get(0));
        assertFalse(action.getVerbPhrase().hasVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    @Test
    public void word1_indirectTransitiveVerb_verb() {
        testParse("give");

        assertEquals(1, actions.size());

        assertTrue(action.hasVerbPhrase());
        assertEquals("give", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    @Test
    public void word1_nonIndirectTransitiveVerb_verb() {
        testParse("eat");

        assertEquals(1, actions.size());

        assertTrue(action.hasVerbPhrase());
        assertEquals("eat", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    @Test
    public void word1_optionallyIndirectTransitiveVerb_verb() {
        testParse("use");

        assertEquals(1, actions.size());

        assertTrue(action.hasVerbPhrase());
        assertEquals("use", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    @Test
    public void word2_adverbVerb() {
        testParse("quickly run");

        assertEquals(1, actions.size());

        assertTrue(action.hasVerbPhrase());
        assertTrue(action.getVerbPhrase().hasAdverbs());
        assertEquals(1, action.getVerbPhrase().getAdverbs().size());
        assertEquals("quickly",
                action.getVerbPhrase().getAdverbs().get(0));
        assertTrue(action.getVerbPhrase().hasVerb());
        assertEquals("run", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that the last word is treated is a noun, and preceding word is
     * treated as an adjective.
     */
    @Test
    public void word2_directAdjectiveNoun() {
        testParse("b c");

        assertFalse(action.hasVerbPhrase());

        assertTrue(action.hasDirectObjectPhrase());
        assertEquals(1, action.getDirectObjectPhrase().getAdjectives().size());
        assertEquals("b", action.getDirectObjectPhrase().getAdjectives().get(0));
        assertEquals("c", action.getDirectObjectPhrase().getNoun());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that a noun can be parsed have a known verb.
     */
    @Test
    public void word2_nonIndirectTransitiveVerb_directNoun() {
        testParse("eat b");

        assertTrue(action.hasVerbPhrase());
        assertEquals("eat", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertFalse(action.getDirectObjectPhrase().hasDeterminer());
        assertFalse(action.getDirectObjectPhrase().hasAdjectives());
        assertEquals("b", action.getDirectObjectPhrase().getNoun());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that a known preposition can be parsed have a known verb.
     */
    @Test
    public void word2_optionallyIndirectTransitiveVerb_preposition() {
        testParse("go to");

        assertTrue(action.hasVerbPhrase());
        assertEquals("go", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertTrue(action.hasPreposition());
        assertEquals("to", action.getPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Check that an adverb is not removed by forward or backwards syntax fix.
     */
    @Test
    public void word3_adverbVerbPrepositionIndirect() {
        testParse("quickly run behind wall");

        assertEquals(1, actions.size());

        assertTrue(action.hasVerbPhrase());
        assertTrue(action.getVerbPhrase().hasAdverbs());
        assertEquals(1, action.getVerbPhrase().getAdverbs().size());
        assertEquals("quickly",
                action.getVerbPhrase().getAdverbs().get(0));
        assertTrue(action.getVerbPhrase().hasVerb());
        assertEquals("run", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertTrue(action.hasPreposition());
        assertEquals("behind", action.getPreposition());

        assertTrue(action.hasIndirectObjectPhrase());
        assertEquals("wall", action.getIndirectObjectPhrase().getNoun());
    }

    /**
     * Test that 1 adjective can be parsed between a verb and noun
     */
    @Test
    public void word3_nonIndirectTransitiveVerb_directAdjective1Noun() {
        testParse("eat c d");

        assertTrue(action.hasVerbPhrase());
        assertEquals("eat", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertFalse(action.getDirectObjectPhrase().hasDeterminer());
        assertEquals(1, action.getDirectObjectPhrase().getAdjectives().size());
        assertEquals("c", action.getDirectObjectPhrase().getAdjectives().get(0));
        assertEquals("d", action.getDirectObjectPhrase().getNoun());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that the object phrase after the preposition is parsed as an
     * indirect object phrase.
     */
    @Test
    public void word3_indirectTransitiveVerb_preposition_indirectNoun() {
        testParse("give to c");

        assertTrue(action.hasVerbPhrase());
        assertEquals("give", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertTrue(action.hasPreposition());
        assertEquals("to", action.getPreposition());

        assertTrue(action.hasIndirectObjectPhrase());
        assertFalse(action.getIndirectObjectPhrase().hasDeterminer());
        assertFalse(action.getIndirectObjectPhrase().hasAdjectives());
        assertTrue(action.getIndirectObjectPhrase().hasNoun());
        assertEquals("c", action.getIndirectObjectPhrase().getNoun());
    }

    /**
     * Test that multiple adjectives can be parsed before a noun for a direct
     * object phrase.
     */
    @Test
    public void word4_nonIndirectTransitiveVerb_directAdjective2Noun() {
        testParse("eat c d e");

        assertTrue(action.hasVerbPhrase());
        assertEquals("eat", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertFalse(action.getDirectObjectPhrase().hasDeterminer());
        assertEquals(2, action.getDirectObjectPhrase().getAdjectives().size());
        assertEquals("c", action.getDirectObjectPhrase().getAdjectives().get(0));
        assertEquals("d", action.getDirectObjectPhrase().getAdjectives().get(1));
        assertEquals("e", action.getDirectObjectPhrase().getNoun());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that a known determiner is parsed not as an adjective for a direct
     * object phrase.
     */
    @Test
    public void word4_nonIndirectTransitiveVerb_directDeterminerAdjectiveNoun() {
        testParse("eat a c d");

        assertTrue(action.hasVerbPhrase());
        assertEquals("eat", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertTrue(action.getDirectObjectPhrase().hasDeterminer());
        assertEquals("a", action.getDirectObjectPhrase().getDeterminer());
        assertTrue(action.getDirectObjectPhrase().hasAdjectives());
        assertEquals(1, action.getDirectObjectPhrase().getAdjectives().size());
        assertEquals("c", action.getDirectObjectPhrase().getAdjectives().get(0));
        assertTrue(action.getDirectObjectPhrase().hasNoun());
        assertEquals("d", action.getDirectObjectPhrase().getNoun());
    }

    /**
     * Test that a direct and indirect object phrase is is parsed around a
     * preposition.
     */
    @Test
    public void word4_indirectTransitiveVerb_directNoun_preposition_indirectNoun() {
        testParse("give c to d");

        assertTrue(action.hasVerbPhrase());
        assertEquals("give", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertFalse(action.getDirectObjectPhrase().hasDeterminer());
        assertFalse(action.getDirectObjectPhrase().hasAdjectives());
        assertTrue(action.getDirectObjectPhrase().hasNoun());
        assertEquals("c", action.getDirectObjectPhrase().getNoun());

        assertTrue(action.hasPreposition());
        assertEquals("to", action.getPreposition());

        assertTrue(action.hasIndirectObjectPhrase());
        assertFalse(action.getIndirectObjectPhrase().hasDeterminer());
        assertEquals("d", action.getIndirectObjectPhrase().getNoun());
        assertFalse(action.getIndirectObjectPhrase().hasAdjectives());
    }

    /**
     * TODO rename this
     */
    @Test
    public void word8_indirectTransitiveVerb_complete() {
        testParse("give all of my delicious funnel cakes of deliciousness"
                + " to the big angry demon of doom");

        assertTrue(action.hasVerbPhrase());
        assertEquals("give", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertEquals("all",
                action.getDirectObjectPhrase().getDeterminer());
        assertFalse(action.getDirectObjectPhrase().hasNoun());

        assertEquals("of", action.getDirectObjectPhrase().getPreposition());

        assertTrue(action.getDirectObjectPhrase().hasOwner());
        assertEquals("my",
                action.getDirectObjectPhrase().getOwner().getDeterminer());
        assertTrue(action.getDirectObjectPhrase().getOwner().hasAdjectives());
        assertEquals(2,
                action.getDirectObjectPhrase().getOwner().getAdjectives().size());
        assertEquals("delicious",
                action.getDirectObjectPhrase().getOwner().getAdjectives().get(0));
        assertEquals("funnel",
                action.getDirectObjectPhrase().getOwner().getAdjectives().get(1));
        assertEquals("cakes",
                action.getDirectObjectPhrase().getOwner().getNoun());


        assertEquals("of",
                action.getDirectObjectPhrase().getOwner().getPreposition());

        assertTrue(action.getDirectObjectPhrase().getOwner().hasOwner());
        assertEquals("deliciousness",
                action.getDirectObjectPhrase().getOwner().getOwner().getNoun());

        assertTrue(action.hasPreposition());
        assertEquals("to", action.getPreposition());

        assertTrue(action.hasIndirectObjectPhrase());
        assertEquals("the", action.getIndirectObjectPhrase().getDeterminer());
        assertEquals(2,
                action.getIndirectObjectPhrase().getAdjectives().size());
        assertEquals("big",
                action.getIndirectObjectPhrase().getAdjectives().get(0));
        assertEquals("angry",
                action.getIndirectObjectPhrase().getAdjectives().get(1));
        assertEquals("demon",
                action.getIndirectObjectPhrase().getNoun());

        assertTrue(action.getIndirectObjectPhrase().hasPreposition());
        assertEquals("of", action.getIndirectObjectPhrase().getPreposition());

        assertTrue(action.getIndirectObjectPhrase().hasOwner());
        assertEquals("doom",
                action.getIndirectObjectPhrase().getOwner().getNoun());
    }

    /**
     * Test that the verb "eat" is copied all the way through to following
     * actions.
     */
    @Test
    public void multiAction_fixSyntaxForward_singleTransitiveVerbToAll() {
        testParse("eat b, c, d");

        assertEquals(3, actions.size());

        assertEquals("eat", actions.get(0).getVerbPhrase().getVerb());
        assertEquals("b", actions.get(0).getDirectObjectPhrase().getNoun());

        assertEquals("eat", actions.get(1).getVerbPhrase().getVerb());
        assertEquals("c", actions.get(1).getDirectObjectPhrase().getNoun());

        assertEquals("eat", actions.get(2).getVerbPhrase().getVerb());
        assertEquals("d", actions.get(2).getDirectObjectPhrase().getNoun());
    }

    /**
     * Test that the verb "look" and preposition "at" is copied all the way
     * through to following actions.
     */
    @Test
    public void multiAction_fixSyntaxForward_singleIntransitiveVerbToAll() {
        testParse("look at b, c, d");

        assertEquals(3, actions.size());

        assertEquals("look", actions.get(0).getVerbPhrase().getVerb());
        assertFalse(actions.get(0).hasDirectObjectPhrase());
        assertEquals("at", actions.get(0).getPreposition());
        assertEquals("b", actions.get(0).getIndirectObjectPhrase().getNoun());

        assertEquals("look", actions.get(1).getVerbPhrase().getVerb());
        assertFalse(actions.get(1).hasDirectObjectPhrase());
        assertEquals("at", actions.get(1).getPreposition());
        assertEquals("c", actions.get(1).getIndirectObjectPhrase().getNoun());

        assertEquals("look", actions.get(2).getVerbPhrase().getVerb());
        assertFalse(actions.get(2).hasDirectObjectPhrase());
        assertEquals("at", actions.get(2).getPreposition());
        assertEquals("d", actions.get(2).getIndirectObjectPhrase().getNoun());
    }

    /**
     * Test that the verb "look" is copied all the way through and the
     * preposition to copy is changed from "at" to "through".
     */
    @Test
    public void multiAction_fixSyntaxForward_changePrepositions() {
        testParse("look at b, c, through d, e");

        assertEquals(4, actions.size());

        assertEquals("look", actions.get(0).getVerbPhrase().getVerb());
        assertFalse(actions.get(0).hasDirectObjectPhrase());
        assertEquals("at", actions.get(0).getPreposition());
        assertEquals("b", actions.get(0).getIndirectObjectPhrase().getNoun());

        assertEquals("look", actions.get(1).getVerbPhrase().getVerb());
        assertFalse(actions.get(1).hasDirectObjectPhrase());
        assertEquals("at", actions.get(1).getPreposition());
        assertEquals("c", actions.get(1).getIndirectObjectPhrase().getNoun());

        assertEquals("look", actions.get(2).getVerbPhrase().getVerb());
        assertFalse(actions.get(2).hasDirectObjectPhrase());
        assertEquals("through", actions.get(2).getPreposition());
        assertEquals("d", actions.get(2).getIndirectObjectPhrase().getNoun());

        assertEquals("look", actions.get(3).getVerbPhrase().getVerb());
        assertFalse(actions.get(3).hasDirectObjectPhrase());
        assertEquals("through", actions.get(3).getPreposition());
        assertEquals("e", actions.get(3).getIndirectObjectPhrase().getNoun());
    }

    /**
     * Test that "walk e" does not turn into "walk through e".
     */
    @Test
    public void multiAction_fixSyntaxForward_changeVerbAndPreposition() {
        testParse("look at b, c, through d, walk e");

        assertEquals(4, actions.size());

        assertEquals("look", actions.get(0).getVerbPhrase().getVerb());
        assertFalse(actions.get(0).hasDirectObjectPhrase());
        assertEquals("at", actions.get(0).getPreposition());
        assertEquals("b", actions.get(0).getIndirectObjectPhrase().getNoun());

        assertEquals("look", actions.get(1).getVerbPhrase().getVerb());
        assertFalse(actions.get(1).hasDirectObjectPhrase());
        assertEquals("at", actions.get(1).getPreposition());
        assertEquals("c", actions.get(1).getIndirectObjectPhrase().getNoun());

        assertEquals("look", actions.get(2).getVerbPhrase().getVerb());
        assertFalse(actions.get(2).hasDirectObjectPhrase());
        assertEquals("through", actions.get(2).getPreposition());
        assertEquals("d", actions.get(2).getIndirectObjectPhrase().getNoun());

        assertEquals("walk", actions.get(3).getVerbPhrase().getVerb());
        assertEquals("e", actions.get(3).getDirectObjectPhrase().getNoun());
        assertFalse(actions.get(3).hasPreposition());
        assertFalse(actions.get(3).hasIndirectObjectPhrase());
    }

    /**
     * Test that direct verb phrase "hammer" and preposition "on" copy over, and
     * that (initially) direct object phrase "wall" gets converts to an indirect
     * object phrase, such that the second action becomes "use hammer on wall".
     */
    @Test
    public void multiAction_fixSyntaxForward_copyDirectAndPrepositionChangeDirect() {
        testParse("use hammer on door and wall");

        assertEquals(2, actions.size());

        assertEquals("use", actions.get(0).getVerbPhrase().getVerb());
        assertEquals("hammer",
                actions.get(0).getDirectObjectPhrase().getNoun());
        assertEquals("on", actions.get(0).getPreposition());
        assertEquals("door",
                actions.get(0).getIndirectObjectPhrase().getNoun());

        assertEquals("use", actions.get(1).getVerbPhrase().getVerb());
        assertEquals("hammer",
                actions.get(1).getDirectObjectPhrase().getNoun());
        assertEquals("on", actions.get(1).getPreposition());
        assertEquals("wall",
                actions.get(1).getIndirectObjectPhrase().getNoun());

    }

    /**
     * Test that the preposition to copy "on" changes to "against" while the
     * direct object phrase "hammer" continues to copy forward.
     */
    @Test
    public void multiAction_fixSyntaxForward_copyDirectAndPrepositionChangeDirectChangePreposition() {
        testParse("use hammer on door and wall and against box");

        assertEquals(3, actions.size());

        assertEquals("use", actions.get(0).getVerbPhrase().getVerb());
        assertEquals("hammer",
                actions.get(0).getDirectObjectPhrase().getNoun());
        assertEquals("on", actions.get(0).getPreposition());
        assertEquals("door",
                actions.get(0).getIndirectObjectPhrase().getNoun());

        assertEquals("use", actions.get(1).getVerbPhrase().getVerb());
        assertEquals("hammer",
                actions.get(1).getDirectObjectPhrase().getNoun());
        assertEquals("on", actions.get(1).getPreposition());
        assertEquals("wall",
                actions.get(1).getIndirectObjectPhrase().getNoun());

        assertEquals("use", actions.get(2).getVerbPhrase().getVerb());
        assertEquals("hammer",
                actions.get(2).getDirectObjectPhrase().getNoun());
        assertEquals("against", actions.get(2).getPreposition());
        assertEquals("box",
                actions.get(2).getIndirectObjectPhrase().getNoun());
    }

    /**
     * Test that the verb to copy "eat" changes from "eat" to "go" and continues
     * to copy all the way through.
     */
    @Test
    public void multiAction_fixSyntaxForward_twoVerbsChangeVerbToAll() {
        testParse("eat b, go c, d");

        assertEquals(3, actions.size());

        assertTrue(actions.get(0).hasVerbPhrase());
        assertEquals("eat", actions.get(0).getVerbPhrase().getVerb());
        assertTrue(actions.get(0).hasDirectObjectPhrase());
        assertEquals("b", actions.get(0).getDirectObjectPhrase().getNoun());
        assertFalse(actions.get(0).hasPreposition());
        assertFalse(actions.get(0).hasIndirectObjectPhrase());

        assertTrue(actions.get(1).hasVerbPhrase());
        assertEquals("go", actions.get(1).getVerbPhrase().getVerb());
        assertEquals("c", actions.get(1).getDirectObjectPhrase().getNoun());
        assertFalse(actions.get(1).hasPreposition());
        assertFalse(actions.get(1).hasIndirectObjectPhrase());

        assertTrue(actions.get(2).hasVerbPhrase());
        assertEquals("go", actions.get(2).getVerbPhrase().getVerb());
        assertEquals("d", actions.get(2).getDirectObjectPhrase().getNoun());
        assertFalse(actions.get(2).hasPreposition());
        assertFalse(actions.get(2).hasIndirectObjectPhrase());
    }

    /**
     * Once stopping intransitive verb "look" is arrived at, stop copying verbs.
     * Action "d" should be unaltered.
     */
    @Test
    public void multiAction_fixSyntaxForward_stopCopyingAtStoppingIntransitiveVerb() {
        testParse("eat b, c, look, d");

        assertEquals(4, actions.size());

        assertEquals("eat", actions.get(0).getVerbPhrase().getVerb());
        assertEquals("b", actions.get(0).getDirectObjectPhrase().getNoun());
        assertFalse(actions.get(0).hasPreposition());
        assertFalse(actions.get(0).hasIndirectObjectPhrase());

        assertEquals("eat", actions.get(1).getVerbPhrase().getVerb());
        assertEquals("c", actions.get(1).getDirectObjectPhrase().getNoun());
        assertFalse(actions.get(1).hasPreposition());
        assertFalse(actions.get(1).hasIndirectObjectPhrase());

        assertEquals("look", actions.get(2).getVerbPhrase().getVerb());
        assertFalse(actions.get(2).hasDirectObjectPhrase());
        assertFalse(actions.get(2).hasPreposition());
        assertFalse(actions.get(2).hasIndirectObjectPhrase());

        assertFalse(actions.get(3).hasVerbPhrase());
        assertEquals("d", actions.get(3).getDirectObjectPhrase().getNoun());
        assertFalse(actions.get(3).hasPreposition());
        assertFalse(actions.get(3).hasIndirectObjectPhrase());
    }

    /**
     * Test that preposition "to" and indirect object phrase "e" of direct
     * object "c" to direct object "b".
     */
    @Test
    public void multiAction_fixSyntaxBackwards_prepositionIndirectToAll() {
        testParse("give b, c to e");

        assertEquals(2, actions.size());

        assertEquals("give", actions.get(0).getVerbPhrase().getVerb());
        assertEquals("b", actions.get(0).getDirectObjectPhrase().getNoun());
        assertEquals("to", actions.get(0).getPreposition());
        assertEquals("e", actions.get(0).getIndirectObjectPhrase().getNoun());

        assertEquals("give", actions.get(1).getVerbPhrase().getVerb());
        assertEquals("c", actions.get(1).getDirectObjectPhrase().getNoun());
        assertEquals("to", actions.get(1).getPreposition());
        assertEquals("e", actions.get(1).getIndirectObjectPhrase().getNoun());
    }

    /**
     * Test that a direct object "b" and preposition "to" can copy to action "d"
     * and correct move "d" to the indirect object.
     */
    @Test
    public void multiAction_fixSyntaxForwards_transferDirectAndPrepositionForward() {
        testParse("give b to c, d");

        assertEquals(2, actions.size());

        assertEquals("give",
                actions.get(0).getVerbPhrase().getVerb());
        assertEquals("b",
                actions.get(0).getDirectObjectPhrase().getNoun());
        assertEquals("to",
                actions.get(0).getPreposition());
        assertEquals("c",
                actions.get(0).getIndirectObjectPhrase().getNoun());

        assertEquals("give",
                actions.get(1).getVerbPhrase().getVerb());
        assertEquals("b",
                actions.get(1).getDirectObjectPhrase().getNoun());
        assertEquals("to",
                actions.get(1).getPreposition());
        assertEquals("d",
                actions.get(1).getIndirectObjectPhrase().getNoun());
    }


    @Test
    public void excludingPrepositions_all_except_1() {
        testParse("drop all except b");

        assertEquals(1, actions.size());

        assertEquals("drop", actions.get(0).getVerbPhrase().getVerb());
        assertEquals("all", actions.get(0).getDirectObjectPhrase().getDeterminer());
        assertFalse(actions.get(0).getDirectObjectPhrase().hasNoun());
        assertEquals("except", actions.get(0).getPreposition());
        assertEquals("b", actions.get(0).getIndirectObjectPhrase().getNoun());
    }

    @Test
    public void excludingPrepositions_2_except_1() {
        testParse("drop b, c except b", true);

        assertEquals(2, actions.size());

        assertEquals("drop", actions.get(0).getVerbPhrase().getVerb());
        assertEquals("b", actions.get(0).getDirectObjectPhrase().getNoun());
        assertEquals("except", actions.get(0).getPreposition());
        assertEquals("b", actions.get(0).getIndirectObjectPhrase().getNoun());

        assertEquals("drop", actions.get(1).getVerbPhrase().getVerb());
        assertEquals("c", actions.get(1).getDirectObjectPhrase().getNoun());
        assertEquals("except", actions.get(1).getPreposition());
        assertEquals("b", actions.get(1).getIndirectObjectPhrase().getNoun());
    }

    /**
     * Check that actions can multiply if multiple direct objects share a single
     * preposition with multiple indirect objects.
     * <p>
     * This requires that action multiplication is implemented. Alternatively, a
     * restriction for multiple indirect objects could work if there are
     * multiple direct objects.
     */
    @Test
    public void multiplyActions_2x2() {
//        testParse("give b, c, to d, e", true);
//
//        assertEquals(4, actions.size());
//
//        assertEquals("give",
//                actions.get(0).getVerbPhrase().getVerb());
//        assertEquals("b",
//                actions.get(0).getDirectObjectPhrase().getNoun());
//        assertEquals("to",
//                actions.get(0).getPreposition());
//        assertEquals("d",
//                actions.get(0).getIndirectObjectPhrase().getNoun());
//
//        assertEquals("give",
//                actions.get(1).getVerbPhrase().getVerb());
//        assertEquals("c",
//                actions.get(1).getDirectObjectPhrase().getNoun());
//        assertEquals("to",
//                actions.get(1).getPreposition());
//        assertEquals("d",
//                actions.get(1).getIndirectObjectPhrase().getNoun());
//
//        assertEquals("give",
//                actions.get(2).getVerbPhrase().getVerb());
//        assertTrue(actions.get(2).hasDirectObjectPhrase());
//        assertEquals("b",
//                actions.get(2).getDirectObjectPhrase().getNoun());
//        assertEquals("to",
//                actions.get(2).getPreposition());
//        assertEquals("e",
//                actions.get(2).getIndirectObjectPhrase().getNoun());
//
//        assertEquals("give",
//                actions.get(3).getVerbPhrase().getVerb());
//        assertTrue(actions.get(3).hasDirectObjectPhrase());
//        assertEquals("c",
//                actions.get(3).getDirectObjectPhrase().getNoun());
//        assertEquals("to",
//                actions.get(3).getPreposition());
//        assertEquals("e",
//                actions.get(3).getIndirectObjectPhrase().getNoun());

    }

    /**
     * This is tricky what the expected results should be because it is
     * ambiguous how f should be treated.
     * <p>
     * If f is ignored in forward fix, then the backwards fix will changed it to
     * "give f at h", but messes with other test cases. This gets more
     * complicated if/when action multiplying gets implemented.
     */
    @Test
    public void multiplyActions_2x2_1() {
//        testParse("give b, c to e, f, g at h", true);
//
//        assertEquals(4, actions.size());
//
//        assertEquals("give", actions.get(0).getVerbPhrase().getVerb());
//        assertEquals("b", actions.get(0).getDirectObjectPhrase().getNoun());
//        assertEquals("to", actions.get(0).getPreposition());
//        assertEquals("e", actions.get(0).getIndirectObjectPhrase().getNoun());
//
//        assertEquals("give", actions.get(1).getVerbPhrase().getVerb());
//        assertEquals("c", actions.get(1).getDirectObjectPhrase().getNoun());
//        assertEquals("to", actions.get(1).getPreposition());
//        assertEquals("e", actions.get(1).getIndirectObjectPhrase().getNoun());
//
//        assertEquals("give", actions.get(2).getVerbPhrase().getVerb());
//        assertEquals("f", actions.get(2).getDirectObjectPhrase().getNoun());
//        assertEquals("at", actions.get(2).getPreposition());
//        assertEquals("h", actions.get(2).getIndirectObjectPhrase().getNoun());
//
//        assertEquals("give", actions.get(3).getVerbPhrase().getVerb());
//        assertEquals("g", actions.get(3).getDirectObjectPhrase().getNoun());
//        assertEquals("at", actions.get(3).getPreposition());
//        assertEquals("h", actions.get(3).getIndirectObjectPhrase().getNoun());
    }
}
