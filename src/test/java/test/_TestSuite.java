package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.com.github.evanquan.parsely.util.CollectionUtilsTest;
import test.com.github.evanquan.parsely.util.FuncUtilsTest;
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
//        LocationTest.class,
//        TestTest.class,
        ActionTest.class,
//        PlayerInputParser_addToken_Test.class,
//        PlayerInputParser_getObjectPhrase_Test.class,
//        PlayerInputParser_lexicalAnalysis_Test.class,
//        PlayerInputParser_parse_Test.class,
//        PlayerInputParser_splitTokensByActions_Test.class,
//        VerbAgnosticPlayerInputParser_lexicalAnalysis_Test.class,
//        VerbAgnosticPlayerInputParser_addToken_Test.class,
//        VerbAgnosticPlayerInputParser_getObjectPhrase_Test.class,
//        VerbAgnosticPlayerInputParser_parse_Test.class,
        ObjectPhraseTest.class,
//        TextUtilsTest.class,
        WordTest.class,
//        ExitTest.class
})
public class _TestSuite {
}
