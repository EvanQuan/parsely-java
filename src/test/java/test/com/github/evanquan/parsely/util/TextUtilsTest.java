package test.com.github.evanquan.parsely.util;

import com.github.evanquan.parsely.util.TextUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test class for the TextUtil class
 *
 * @author Evan Quan
 */

public class TextUtilsTest {

    @Test
    public void isInteger_0_true() {
        assertTrue(TextUtils.isInteger("0"));
    }

    @Test
    public void isInteger_0123456789_true() {
        assertTrue(TextUtils.isInteger("0123456789"));
    }

    @Test
    public void isInteger_1_float_false() {
        assertFalse(TextUtils.isInteger("1.1"));
    }

    @Test
    public void isInteger_1_float_true() {
        assertTrue(TextUtils.isInteger("1.0"));
    }

    @Test
    public void isInteger_a_false() {
        assertFalse(TextUtils.isInteger("a"));
    }

    @Test
    public void isInteger_empty_false() {
        assertFalse(TextUtils.isInteger(""));
    }

    @Test
    public void testSplitCamelCase() {
        String input = "thisIsTheInputString";
        String[] expected = {"this", "Is", "The", "Input", "String"};
        String[] result = TextUtils.splitCamelCase(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testSplitCamelCaseToString() {
        String input = "thisIsTheInputString";
        String expected = "this Is The Input String";
        String result = TextUtils.splitCamelCaseToString(input);
        assertEquals(expected, result);
    }

    /**
     * Check if startsWithVowel works
     */
    @Test
    public void testStartsWithVowel() {
        assertTrue(TextUtils.startsWithVowel("ab"));
        assertTrue(TextUtils.startsWithVowel("eb"));
        assertTrue(TextUtils.startsWithVowel("ib"));
        assertTrue(TextUtils.startsWithVowel("ob"));
        assertTrue(TextUtils.startsWithVowel("ub"));
        assertFalse(TextUtils.startsWithVowel("yb"));
    }

    /**
     * Check if strip extension works
     */
    @Test
    public void testStripExtention() {
        String input1 = "foo.exe";
        String input2 = "bar.txt";
        String input3 = "bar.asdf.qwerty";
        String expected1 = "foo";
        String expected2 = "bar";
        String expected3 = "bar.asdf";
        String result1 = TextUtils.stripExtension(input1);
        String result2 = TextUtils.stripExtension(input2);
        String result3 = TextUtils.stripExtension(input3);
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
        assertEquals(expected3, result3);
    }

    /**
     * Check if all caps strings are converted to lower title case
     */
    @Test
    public void testToLowerTitleCase() {
        String input = "THIS IS THE INPUT STRING";
        String expected = "This Is The Input String";
        String result = TextUtils.toLowerTitleCase(input);
        assertEquals(expected, result);
    }

    /**
     * Check if apostrophes are not counted as new words
     */
    @Test
    public void testToTitleCaseWithApostrophes() {
        String input = "he's ish'kafel";
        String expected = "He's Ish'kafel";
        String result = TextUtils.toTitleCase(input);
        assertEquals(expected, result);
    }

    @Test
    public void testToTitleCaseWithLowerCaseInput() {
        String input = "this is the input string";
        String expected = "This Is The Input String";
        String result = TextUtils.toTitleCase(input);
        assertEquals(expected, result);
    }

    /**
     * Check if letters follow the first are ignored In other words, all caps
     * strings are not changed
     */
    @Test
    public void testToTitleCaseWithUpperCaseInput() {
        String input = "THIS IS THE INPUT STRING";
        String expected = "THIS IS THE INPUT STRING";
        String result = TextUtils.toTitleCase(input);
        assertEquals(expected, result);
    }
}
