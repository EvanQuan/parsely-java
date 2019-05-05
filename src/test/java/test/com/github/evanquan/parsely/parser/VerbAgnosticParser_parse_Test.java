package test.com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.parser.ParserFactory;
import com.github.evanquan.parsely.words.Action;
import com.github.evanquan.parsely.words.Command;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Evan Quan
 */
public class VerbAgnosticParser_parse_Test {

    private static Command command;
    private static Action action;

    private void testParse(String string) {
        command =
                ParserFactory.getParser(ParserFactory.ParserType.VERB_AGNOSTIC).parse(string);
        ArrayList<Action> actions = command.getActions();
        if (actions.isEmpty()) {
            action = null;
        } else {
            action = actions.get(0);
        }
    }

    @Test
    public void word0_empty() {
        testParse("");

        assertFalse(command.hasActions());
    }

    /**
     * Check that a single determiner skips the verb receiveInput.
     */
    @Test
    public void word1_directDeterminer() {
        testParse("the");

        assertFalse(action.hasVerbPhrase());

        assertTrue(action.hasDirectObjectPhrase());
        assertEquals("the", action.getDirectObjectPhrase().getDeterminer());
    }

    /**
     * Check that a single preposition skips the verb and direct object phrase
     * receiveInput.
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
     * Check that a single non-preposition word counts as a verb. Other action
     * components are null.
     */
    @Test
    public void word1_verb() {
        testParse("b");

        assertTrue(action.hasVerbPhrase());
        assertEquals("b", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that a noun can be parsed
     */
    @Test
    public void word2_verb_directNoun() {
        testParse("b c");

        assertTrue(action.hasVerbPhrase());
        assertEquals("b", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertFalse(action.getDirectObjectPhrase().hasDeterminer());
        assertFalse(action.getDirectObjectPhrase().hasAdjectives());
        assertEquals("c", action.getDirectObjectPhrase().getNoun());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    @Test
    public void word2_verb_preposition() {
        testParse("b to");

        assertTrue(action.hasVerbPhrase());
        assertEquals("b", action.getVerbPhrase().getVerb());

        assertFalse(action.hasDirectObjectPhrase());

        assertTrue(action.hasPreposition());
        assertEquals("to", action.getPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    /**
     * Test that 1 adjective can be parsed
     */
    @Test
    public void word3_verb_directAdjective1Noun() {
        testParse("b c d");

        assertTrue(action.hasVerbPhrase());
        assertEquals("b", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertFalse(action.getDirectObjectPhrase().hasDeterminer());
        assertEquals(1, action.getDirectObjectPhrase().getAdjectives().size());
        assertEquals("c", action.getDirectObjectPhrase().getAdjectives().get(0));
        assertEquals("d", action.getDirectObjectPhrase().getNoun());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    @Test
    public void word3_verb_preposition_indirectNoun() {
        testParse("b to c");

        assertTrue(action.hasVerbPhrase());
        assertEquals("b", action.getVerbPhrase().getVerb());

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
     * Test that multiple adjectives can be parsed
     */
    @Test
    public void word4_verb_directAdjective2Noun() {
        testParse("b c d e ");

        assertTrue(action.hasVerbPhrase());
        assertEquals("b", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertFalse(action.getDirectObjectPhrase().hasDeterminer());
        assertEquals(2, action.getDirectObjectPhrase().getAdjectives().size());
        assertEquals("c", action.getDirectObjectPhrase().getAdjectives().get(0));
        assertEquals("d", action.getDirectObjectPhrase().getAdjectives().get(1));
        assertEquals("e", action.getDirectObjectPhrase().getNoun());

        assertFalse(action.hasPreposition());

        assertFalse(action.hasIndirectObjectPhrase());
    }

    @Test
    public void word4_verb_directDeterminerAdjectiveNoun() {
        testParse("b a c d");

        assertTrue(action.hasVerbPhrase());
        assertEquals("b", action.getVerbPhrase().getVerb());

        assertTrue(action.hasDirectObjectPhrase());
        assertTrue(action.getDirectObjectPhrase().hasDeterminer());
        assertEquals("a", action.getDirectObjectPhrase().getDeterminer());
        assertTrue(action.getDirectObjectPhrase().hasAdjectives());
        assertEquals(1, action.getDirectObjectPhrase().getAdjectives().size());
        assertEquals("c", action.getDirectObjectPhrase().getAdjectives().get(0));
        assertTrue(action.getDirectObjectPhrase().hasNoun());
        assertEquals("d", action.getDirectObjectPhrase().getNoun());
    }

    @Test
    public void word4_verb_directNoun_preposition_indirectNoun() {
        testParse("b c to d");

        assertTrue(action.hasVerbPhrase());
        assertEquals("b", action.getVerbPhrase().getVerb());

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

}
