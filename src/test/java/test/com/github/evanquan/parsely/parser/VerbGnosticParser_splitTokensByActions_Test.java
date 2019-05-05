package test.com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.parser.ParserFactory;
import com.github.evanquan.parsely.parser.VerbGnosticParser;
import com.github.evanquan.parsely.util.CollectionUtils;
import com.github.evanquan.parsely.words.Command;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link Command}.splitTokensByActions()
 *
 * @author Evan Quan
 */
public class VerbGnosticParser_splitTokensByActions_Test {

    private static ArrayList<ArrayList<String>> expected;

    /**
     * Add an action (in token form) to expected results.
     *
     * @param tokens
     */
    private static void addToExpected(String[] tokens) {
        expected.add(CollectionUtils.getArrayList(tokens));
    }

    private static void testSplitTokensByActions(String[] tokens) {
        ArrayList<ArrayList<String>> results =
                ((VerbGnosticParser) ParserFactory.getParser(ParserFactory.ParserType.VERB_GNOSTIC))
                        .splitTokensByActions(CollectionUtils.getArrayList(tokens));
        assertEquals(expected, results);
    }

    @Test
    public void comma() {
        addToExpected(new String[]{});
        addToExpected(new String[]{});
        testSplitTokensByActions(new String[]{","});
    }

    @Test
    public void comma_a() {
        addToExpected(new String[]{});
        addToExpected(new String[]{"a"});
        testSplitTokensByActions(new String[]{",", "a"});
    }

    @Test
    public void a_comma() {
        addToExpected(new String[]{"a"});
        addToExpected(new String[]{});
        testSplitTokensByActions(new String[]{"a", ","});
    }

    @Test
    public void comma2() {
        addToExpected(new String[]{});
        addToExpected(new String[]{});
        addToExpected(new String[]{});
        testSplitTokensByActions(new String[]{",", ","});
    }

    @Test
    public void a_1() {
        addToExpected(new String[]{"a"});
        testSplitTokensByActions(new String[]{"a"});
    }

    @Test
    public void empty_comma_empty_2() {
        addToExpected(new String[]{"a"});
        addToExpected(new String[]{"b"});
        testSplitTokensByActions(new String[]{"a", ",", "b"});
    }

    @Test
    public void empty_then_empty_2() {
        addToExpected(new String[]{"a"});
        addToExpected(new String[]{"b"});
        testSplitTokensByActions(new String[]{"a", "then", "b"});
    }

    @Before
    public void setUp() {
        expected = new ArrayList<>();
    }

    @Test
    public void word_comma_word2_2() {
        addToExpected(new String[]{"a"});
        addToExpected(new String[]{"b", "c"});
        testSplitTokensByActions(new String[]{"a", ",", "b", "c"});
    }

    @Test
    public void word2_comma_word_2() {
        addToExpected(new String[]{"a", "b"});
        addToExpected(new String[]{"c"});
        testSplitTokensByActions(new String[]{"a", "b", ",", "c"});
    }
}
