package com.github.evanquan.parsely.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Has utility functions for objects, and higher order functions.
 *
 * @author Evan Quan
 */
public class FuncUtils {

    /**
     * Cannot instantiate.
     */
    private FuncUtils() {
    }

    /**
     * Filter a predicate over a String ArrayList and return the result as a new
     * String ArrayList.
     *
     * @param predicate
     * @param list
     * @return
     */
    public static ArrayList<String> filter(Predicate<? super String> predicate, ArrayList<String> list) {
        return CollectionUtils.getArrayList(filter(predicate, CollectionUtils.getStringArray(list)));
    }

    /**
     * Filter a predicate over a String array and return the result as a new
     * String array.
     *
     * @param predicate
     * @param array
     * @return
     */
    public static String[] filter(Predicate<? super String> predicate, String[] array) {
        return Arrays.stream(array).filter(predicate).toArray(String[]::new);
    }

    /**
     * Map a function over a String ArrayList and return the result as a new
     * String ArrayList.
     *
     * @param func
     * @param list
     * @return
     */
    public static ArrayList<String> map(Function<? super String, ? super String> func, ArrayList<String> list) {
        return CollectionUtils.getArrayList(map(func, CollectionUtils.getStringArray(list)));
    }

    /**
     * Map a function over a String array and return the result as a new String
     * array.
     *
     * @param func
     * @param array
     * @return
     */
    public static String[] map(Function<? super String, ? super String> func, String[] array) {
        return Arrays.stream(array).map(func).toArray(String[]::new);
    }

    /**
     * @param one
     * @param two
     * @return true if both objects equal each other, or if both are null
     */
    public static boolean nullablesEqual(Object one, Object two) {
        return (one == null && two == null) || (one != null && two != null && one.equals(two));
    }

    /**
     * Reduce a String ArrayList to a single string using a binary operator.
     * Identity element is the empty string.
     *
     * @param operator
     * @param list
     * @return
     */
    public static String reduce(BinaryOperator<String> operator, ArrayList<String> list) {
        return list.stream().reduce("", operator);
    }

    /**
     * Reduce a String[] array to a single string using a binary operator.
     * Identity element is the empty string.
     *
     * @param operator
     * @param array
     * @return
     */
    public static String reduce(BinaryOperator<String> operator, String[] array) {
        return Arrays.stream(array).reduce("", operator);
    }

}
