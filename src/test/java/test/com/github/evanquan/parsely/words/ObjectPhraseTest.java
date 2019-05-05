package test.com.github.evanquan.parsely.words;

import com.github.evanquan.parsely.words.ObjectPhrase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * JUnit 4 tests for {@link ObjectPhrase}
 *
 * @author Evan Quan
 */
public class ObjectPhraseTest {

    public static ObjectPhrase one;
    public static ObjectPhrase two;

    @Test
    public void equals_adjectives_adjectives_true() {
        one.setAdjectives(new ArrayList<>());
        two.setAdjectives(new ArrayList<>());
        assertEquals(one, two);
    }

    @Test
    public void equals_adjectivesEmpty_adjectivesNull_true() {
        one.setAdjectives(new ArrayList<>());
        assertEquals(one, two);
    }

    @Test
    public void equals_all_all_true() {
        one.setDeterminer("");
        two.setDeterminer("");
        one.setAdjectives(new ArrayList<>());
        two.setAdjectives(new ArrayList<>());
        one.setNoun("");
        two.setNoun("");
        assertEquals(one, two);
    }

    @Test
    public void equals_allNull_allNull_true() {
        assertEquals(one, two);
    }

    @Test
    public void equals_determiner_determiner_true() {
        one.setDeterminer("");
        two.setDeterminer("");
        assertEquals(one, two);
    }

    @Test
    public void equals_differentAdjectives_false() {
        one.setDeterminer("");
        two.setDeterminer("");
        one.setAdjectives(new ArrayList<>(Arrays.asList("")));
        two.setAdjectives(new ArrayList<>());
        one.setNoun("");
        two.setNoun("");
        assertNotEquals(one, two);
    }

    @Test
    public void equals_differentDeterminer_false() {
        one.setDeterminer(" ");
        two.setDeterminer("");
        one.setAdjectives(new ArrayList<>());
        two.setAdjectives(new ArrayList<>());
        one.setNoun("");
        two.setNoun("");
        assertNotEquals(one, two);
    }

    @Test
    public void equals_differentNoun_false() {
        one.setDeterminer("");
        two.setDeterminer("");
        one.setAdjectives(new ArrayList<>());
        two.setAdjectives(new ArrayList<>());
        one.setNoun(" ");
        two.setNoun("");
        assertNotEquals(one, two);
    }

    @Test
    public void equals_noun_noun_true() {
        one.setNoun("");
        two.setNoun("");
        assertEquals(one, two);
    }

    @Test
    public void hasSameArticle_allNull_allNull_true() {
        assertTrue(one.hasSameDeterminer(two));
    }

    @Test
    public void hasSamePreposition_allNull_allNull_true() {
        assertTrue(one.hasSamePreposition(two));
    }

    @Test
    public void hasSameOwner_allNull_allNull_true() {
        assertTrue(one.hasSameOwner(two));
    }

    @Test
    public void hasSameOwner_empty_empty_true() {
        ObjectPhrase three = new ObjectPhrase();
        one.setOwner(three);
        two.setOwner(three);
        assertTrue(one.hasSameOwner(two));
    }

    @Test
    public void hasSameOwner_noun_noun_true() {
        ObjectPhrase three = new ObjectPhrase();
        three.setNoun("a");
        three.setPreposition("of");
        one.setOwner(three);
        two.setOwner(three);
        assertTrue(one.hasSameOwner(two));
    }

    @Test
    public void hasSameOwner_differentNoun_false() {
        ObjectPhrase three = new ObjectPhrase();
        ObjectPhrase four = new ObjectPhrase();
        three.setNoun("a");
        one.setOwner(three);
        two.setOwner(four);
        assertFalse(one.hasSameOwner(two));
    }

    @Test
    public void hasSameOwner_differentPreposition_false() {
        ObjectPhrase three = new ObjectPhrase();
        ObjectPhrase four = new ObjectPhrase();
        three.setNoun("a");
        four.setNoun("a");
        three.setPreposition("of");
        one.setOwner(three);
        two.setOwner(four);
        assertFalse(one.hasSameOwner(two));
    }

    @Before
    public void setUp() {
        one = new ObjectPhrase();
        two = new ObjectPhrase();
    }

}
