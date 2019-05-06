package test.com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.parser.ParserFactory;
import com.github.evanquan.parsely.util.CollectionUtils;
import com.github.evanquan.parsely.words.ObjectPhrase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * @author Evan Quan
 */
public class VerbAgnosticParser_getObjectPhrase_Test {

    private static ObjectPhrase expected;

    private static void testGetObjectPhraseEquals(String[] array, ObjectPhrase expected) {
        ArrayList<String> tokens = CollectionUtils.getArrayList(array);
        ObjectPhrase actual = Objects.requireNonNull(ParserFactory.getParser(ParserFactory.ParserType.VERB_AGNOSTIC)).getObjectPhrase(tokens);
        assertEquals(expected, actual);
    }

    @Test
    public void adjective_noun_equals_adjective_noun() {
        expected.setAdjectives(new String[]{"b"});
        expected.setNoun("noun");
        testGetObjectPhraseEquals(new String[]{"b", "noun"}, expected);
    }

    @Test
    public void adjectives2_noun_equals_adjectives2_noun() {
        expected.setAdjectives(new String[]{"b", "c"});
        expected.setNoun("noun");
        testGetObjectPhraseEquals(new String[]{"b", "c", "noun"}, expected);
    }

    @Test
    public void article_adjectives2_noun_equals_article_adjectives2_noun() {
        expected.setDeterminer("that");
        expected.setAdjectives(new String[]{"b", "c"});
        expected.setNoun("noun");
        testGetObjectPhraseEquals(new String[]{"that", "b", "c", "noun"}, expected);
    }

    @Test
    public void article_equals_determiner() {
        expected.setDeterminer("the");
        testGetObjectPhraseEquals(new String[]{"the"}, expected);
    }

    @Test
    public void empty_equals_null() {
        testGetObjectPhraseEquals(new String[]{}, null);
    }

    @Test
    public void noun_article_equals_adjective_noun() {
        expected.setAdjectives(new String[]{"noun"});
        expected.setNoun("the");
        testGetObjectPhraseEquals(new String[]{"noun", "the"}, expected);
    }

    @Test
    public void noun_equals_noun() {
        expected.setNoun("noun");
        testGetObjectPhraseEquals(new String[]{"noun"}, expected);
    }

    @Test
    public void noun_quantifier_equals_adjective_noun() {
        expected.setAdjectives(new String[]{"noun"});
        expected.setNoun("0");
        testGetObjectPhraseEquals(new String[]{"noun", "0"}, expected);
    }

    @Test
    public void quantifier_equals_quanfier() {
        expected.setDeterminer("0");
        testGetObjectPhraseEquals(new String[]{"0"}, expected);
    }

    @Before
    public void setUp() {
        expected = new ObjectPhrase();
        expected.setAdjectives(new ArrayList<>());
    }
}
