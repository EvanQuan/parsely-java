package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.com.github.evanquan.parsely.parser.*;
import test.com.github.evanquan.parsely.util.CollectionUtilsTest;
import test.com.github.evanquan.parsely.util.FuncUtilsTest;
import test.com.github.evanquan.parsely.util.TextUtilsTest;
import test.com.github.evanquan.parsely.words.ActionTest;
import test.com.github.evanquan.parsely.words.ObjectPhraseTest;
import test.com.github.evanquan.parsely.words.WordTest;

/**
 * Runs all test classes under JUnit 4
 *
 * @author Evan Quan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CollectionUtilsTest.class,
        FuncUtilsTest.class,
        ActionTest.class,
        Parser_addToken_Test.class,
        VerbGnosticParser_getObjectPhrase_Test.class,
        Parser_lexicalAnalysis_Test.class,
        VerbGnosticParser_parse_Test.class,
        VerbGnosticParser_splitTokensByActions_Test.class,
        VerbAgnosticParser_getObjectPhrase_Test.class,
        VerbAgnosticParser_parse_Test.class,
        ObjectPhraseTest.class,
        TextUtilsTest.class,
        WordTest.class,
})
public class _TestSuite {
}
