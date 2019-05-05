package test.com.github.evanquan.parsely.words;

import com.github.evanquan.parsely.words.Action;
import com.github.evanquan.parsely.words.ObjectPhrase;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Tests for {@link Action}
 *
 * @author Evan Quan
 */
public class ActionTest {

    public static Action one;
    public static Action two;
    public static ObjectPhrase onedo;
    public static ObjectPhrase oneio;
    public static ObjectPhrase twodo;
    public static ObjectPhrase twoio;

    @Test
    public void Action() {
        assertNotEquals(one, "");
    }

    @Test
    public void equals_directObjectPhrase_true() {
        onedo.setNoun("a");
        twodo.setNoun("a");
        one.setDirectObjectPhrase(onedo);
        two.setDirectObjectPhrase(twodo);
        assertEquals(one, two);
    }

    @Test
    public void equals_empty_true() {
        assertEquals(one, two);
    }

    @Test
    public void equals_indirectObjectPhrase_true() {
        oneio.setNoun("a");
        twoio.setNoun("a");
        one.setIndirectObjectPhrase(oneio);
        two.setIndirectObjectPhrase(twoio);
        assertEquals(one, two);
    }

    @Test
    public void equals_verb_false() {
        one.setVerbPhrase("a");
        two.setVerbPhrase("b");
        assertNotEquals(one, two);
    }

    @Test
    public void equals_verb_true() {
        one.setVerbPhrase("a");
        two.setVerbPhrase("a");
        assertEquals(one, two);
    }

    @Test
    public void hasDirectObjectPhrase_empty_false() {
        one.setDirectObjectPhrase(onedo);
        assertFalse(one.hasDirectObjectPhrase());
    }

    @Test
    public void hasDirectObjectPhrase_nonempty_true() {
        onedo.setNoun("");
        one.setDirectObjectPhrase(onedo);
        assertTrue(one.hasDirectObjectPhrase());
    }

    @Test
    public void hasDirectObjectPhrase_null_false() {
        assertFalse(one.hasDirectObjectPhrase());
    }

    @Test
    public void hasDirectObjectPhrase_null_fase() {
        assertFalse(one.hasDirectObjectPhrase());
    }

    @Test
    public void hasIndirectObjectPhrase_empty_false() {
        one.setIndirectObjectPhrase(oneio);
        assertFalse(one.hasIndirectObjectPhrase());
    }

    @Test
    public void hasIndirectObjectPhrase_nonempty_true() {
        oneio.setNoun("");
        one.setIndirectObjectPhrase(oneio);
        assertTrue(one.hasIndirectObjectPhrase());
    }

    @Test
    public void hasIndirectObjectPhrase_null_false() {
        assertFalse(one.hasIndirectObjectPhrase());
    }

    @Test
    public void hasPreposition_false() {
        assertFalse(one.hasPreposition());
    }

    @Test
    public void hasPreposition_true() {
        one.setPreposition("");
        assertTrue(one.hasPreposition());
    }

    @Before
    public void setUp() {
        one = new Action();
        two = new Action();
        onedo = new ObjectPhrase();
        oneio = new ObjectPhrase();
        twodo = new ObjectPhrase();
        twoio = new ObjectPhrase();
    }

}
