package test.com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.parser.ParserFactory;
import com.github.evanquan.parsely.parser.VerbGnosticParser;
import com.github.evanquan.parsely.util.CollectionUtils;
import com.github.evanquan.parsely.words.ObjectPhrase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Evan Quan
 */
public class VerbGnosticParser_getObjectPhrase_Test {

    public static ArrayList<String> tokens;
    public static ObjectPhrase expected;
    public static ObjectPhrase actual;

    public static void testGetObjectPhrase(String[] array) {
        tokens = CollectionUtils.getArrayList(array);
        actual = ((VerbGnosticParser) ParserFactory.getParser(ParserFactory.ParserType.VERB_GNOSTIC)).getObjectPhrase(tokens);

    }

    public static void testGetObjectPhraseEquals(String[] array) {
        testGetObjectPhrase(array);
        assertEquals(expected, actual);
    }

    public static void testGetObjectPhraseEquals(String[] array,
                                                 ObjectPhrase expected) {
        testGetObjectPhrase(array);
        assertEquals(expected, actual);
    }

    @Test
    public void adjective_noun_equals_adjective_noun() {
        expected.setAdjectives(new String[]{"b"});
        expected.setNoun("noun");
        testGetObjectPhraseEquals(new String[]{"b", "noun"});
    }

    @Test
    public void adjectives2_noun_equals_adjectives2_noun() {
        expected.setAdjectives(new String[]{"b", "c"});
        expected.setNoun("noun");
        testGetObjectPhraseEquals(new String[]{"b", "c", "noun"});
    }

    @Test
    public void article_adjectives2_noun_equals_article_adjectives2_noun() {
        expected.setDeterminer("that");
        expected.setAdjectives(new String[]{"b", "c"});
        expected.setNoun("noun");
        testGetObjectPhraseEquals(new String[]{"that", "b", "c", "noun"});
    }

    @Test
    public void article_equals_determiner() {
        expected.setDeterminer("the");
        testGetObjectPhraseEquals(new String[]{"the"});
    }

    /**
     * An empty
     */
    @Test
    public void empty_equals_null() {
        testGetObjectPhraseEquals(new String[]{}, null);
    }

    @Test
    public void noun_article_equals_adjective_noun() {
        expected.setAdjectives(new String[]{"noun"});
        expected.setNoun("the");
        testGetObjectPhraseEquals(new String[]{"noun", "the"});
    }

    @Test
    public void noun_equals_noun() {
        expected.setNoun("noun");
        testGetObjectPhraseEquals(new String[]{"noun"});
    }

    @Test
    public void noun_quantifier_equals_adjective_noun() {
        expected.setAdjectives(new String[]{"noun"});
        expected.setNoun("0");
        testGetObjectPhraseEquals(new String[]{"noun", "0"});
    }

    @Test
    public void quantifier_equals_quanfier() {
        expected.setDeterminer("0");
        testGetObjectPhraseEquals(new String[]{"0"});
    }

    @Test
    public void owner1() {
        expected.setNoun("b");
        expected.setPreposition("of");
        ObjectPhrase owner = new ObjectPhrase();
        owner.setNoun("c");
        expected.setOwner(owner);
        testGetObjectPhraseEquals(new String[]{"b", "of", "c"});
    }

    @Test
    public void ownerDeterminerAdjective1() {
        expected.setNoun("b");
        expected.setPreposition("of");
        ObjectPhrase owner = new ObjectPhrase();
        owner.setDeterminer("the");
        owner.setAdjectives(new String[]{"c"});
        owner.setNoun("d");
        expected.setOwner(owner);
        testGetObjectPhraseEquals(new String[]{"b", "of", "the", "c", "d"});
    }

    @Test
    public void owner2() {
        expected.setNoun("b");
        expected.setPreposition("of");
        ObjectPhrase owner2 = new ObjectPhrase();
        owner2.setNoun("d");
        ObjectPhrase owner = new ObjectPhrase();
        owner.setNoun("c");
        owner.setPreposition("of");
        owner.setOwner(owner2);
        expected.setOwner(owner);
        testGetObjectPhraseEquals(new String[]{"b", "of", "c", "of", "d"});
    }

    @Test
    public void belongingPrepositionWithoutOwner() {
        expected.setNoun("b");
        expected.setPreposition("of");
        testGetObjectPhraseEquals(new String[]{"b", "of"});
    }

    @Before
    public void setUp() {
        expected = new ObjectPhrase();
        expected.setAdjectives(new ArrayList<>());
    }
}
