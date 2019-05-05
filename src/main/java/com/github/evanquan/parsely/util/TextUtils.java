package com.github.evanquan.parsely.util;

/**
 * Has utility functions for text
 *
 * @author Evan Quan
 */
public class TextUtils {

    /**
     * Cannot instantiate.
     */
    private TextUtils() {
    }

    /**
     * @param string to check
     * @return true if string matches the form of an integer, or a floating
     * point number that can be expressed by an integer. Assumes decimal
     * format.
     */
    public static boolean isInteger(String string) {
        return string.matches("\\d+((.)?0+)?");
    }

    /**
     * Split words by upper case letters
     *
     * @param string to split
     * @return an array of which each element is a words split by camel case
     */
    public static String[] splitCamelCase(String string) {
        return string.split("(?<!^)(?=[A-Z])");
    }

    /**
     * Split words by upper case letters by spaces
     *
     * @param string to split
     * @return a string of words separated by spaces by camel case
     */
    public static String splitCamelCaseToString(String string) {
        return String.join(" ", splitCamelCase(string));
    }

    /**
     * @param string to check
     * @return true if string starts with a vowel
     */
    public static boolean startsWithVowel(String string) {
        switch (Character.toLowerCase(string.charAt(0))) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }

    /**
     * Removes extension from file name, noted by a '.'
     *
     * @param fileName to to strip
     * @return fileName without extension
     */
    public static String stripExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        return fileName;
    }

    /**
     * Converts to lower case before title case Important for convert upper case
     * or mixed case to title case
     *
     * @param string to be converted
     * @return title case string
     */
    public static String toLowerTitleCase(String string) {
        return toTitleCase(string.toLowerCase());
    }

    /**
     * Converts beginning of each words in string to upper case
     *
     * @param string to change to title case
     * @return title case string
     */
    public static String toTitleCase(String string) {
        String[] words = string.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1);
        }
        return String.join(" ", words);
    }
}
