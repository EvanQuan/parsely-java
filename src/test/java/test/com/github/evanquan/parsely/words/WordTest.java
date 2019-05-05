package test.com.github.evanquan.parsely.words;

import com.github.evanquan.parsely.words.Word;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WordTest {

    @Test
    public void isArticle_empty_false() {
        assertFalse(Word.isArticle(""));
    }

    @Test
    public void isArticle_the_true() {
        assertTrue(Word.isArticle("the"));
    }

    @Test
    public void isDeterminer_0_true() {
        assertTrue(Word.isDeterminer("0"));
    }

    @Test
    public void isDeterminer_empty_false() {
        assertFalse(Word.isDeterminer(""));
    }

    @Test
    public void isDeterminer_this_true() {
        assertTrue(Word.isDeterminer("this"));
    }

    @Test
    public void isQuantifier_0_0_true() {
        assertTrue(Word.isQuantifier("0.0"));
    }

    @Test
    public void isQuantifier_0_00_true() {
        assertTrue(Word.isQuantifier("0.00"));
    }

    @Test
    public void isQuantifier_0_01_true() {
        assertFalse(Word.isQuantifier("0.01"));
    }

    @Test
    public void isQuantifier_0_true() {
        assertTrue(Word.isQuantifier("0"));
    }

    @Test
    public void isQuantifier_all_true() {
        assertTrue(Word.isQuantifier("all"));
    }

    @Test
    public void isQuantifier_empty_false() {
        assertFalse(Word.isQuantifier(""));
    }

    @Test
    public void isObjectPhraseSeparatingPreposition_joining_true() {
        assertTrue(Word.isObjectPhraseSeparatingPreposition("with"));
    }

    @Test
    public void isObjectPhraseSeparatingPreposition_excluding_true() {
        assertTrue(Word.isObjectPhraseSeparatingPreposition("but"));
    }

    @Test
    public void isObjectPhraseSeparatingPreposition_directional_true() {
        assertTrue(Word.isObjectPhraseSeparatingPreposition("over"));
    }

    @Test
    public void isObjectPhraseSeparatingPreposition_movement_true() {
        assertTrue(Word.isObjectPhraseSeparatingPreposition("to"));
    }

    @Test
    public void isObjectPhraseSeparatingPreposition_belonging_false() {
        assertFalse(Word.isObjectPhraseSeparatingPreposition("of"));
    }

    @Test
    public void isBelongingPreposition_of_true() {
        assertTrue(Word.isBelongingPreposition("of"));
    }

    @Test
    public void isMovementPreposition_to_true() {
        assertTrue(Word.isMovementPreposition("to"));
    }

    @Test
    public void isDirectionalPreposition_across_true() {
        assertTrue(Word.isDirectionalPreposition("across"));
    }

    @Test
    public void isJoiningPreposition_across_true() {
        assertTrue(Word.isJoiningPreposition("with"));
    }

}
