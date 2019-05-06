package com.github.evanquan.parsely.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Has text utility functions for Collections.
 *
 * @author Evan Quan
 */
public class CollectionUtils {


    /**
     * Cannot instantiate.
     */
    private CollectionUtils() {
    }

    /**
     * Add array to ArrayList<String>
     *
     * @param array to add to list
     * @param list to add to
     */
    public static void addArrayToList(String[] array, ArrayList<String> list) {
        list.addAll(Arrays.asList(array));
    }

    /**
     * Checks if a char[] array contains a specified char key
     *
     * @param array to check if key is contained in it
     * @param key to check if it is contained in array
     * @return true if the array contains the specified char key
     */
    public static boolean contains(char[] array, char key) {
        for (char c : array) {
            if (c == key) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an int[] array contains a specified int key
     *
     * @param array to check if key is contained in it
     * @param key to check if it is contained in array
     * @return true if the array contains the specified int key
     */
    public static boolean contains(int[] array, int key) {
        return Arrays.stream(array).anyMatch(i -> i == key);
    }

    /**
     * Checks if a String[] array contains a specified String key
     *
     * @param array to check if key is contained in it
     * @param key to check if it is contained in array
     * @return true if the array contains the specified String key
     */
    public static boolean contains(Object[] array, Object key) {
        return Arrays.asList(array).contains(key);
    }

    public static ArrayList<String> getArrayList(Object[] array) {
        return getArrayList(getStringArray(array));
    }

    public static ArrayList<String> getArrayList(String word) {
        return getArrayList(new String[]{word});
    }

    /**
     * Convert a String[] to an ArrayList&lt;String&gt; of the same content.
     *
     * @param array to convert
     * @return the ArrayList equivalent
     */
    public static ArrayList<String> getArrayList(String[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    // public static ArrayList<String> getStringArrayList(double doub) {
    // return getStringArrayList(new double[] { doub });
    // }
    //
    // public static ArrayList<String> getStringArrayList(double[] doubleArray) {
    // return getStringArrayList(getDoubleArrayList(doubleArray));
    // }
    //
    // public static ArrayList<String> getStringArrayList(int integer) {
    // return getStringArrayList(getStringArrayList(integer));
    // }
    //
    // public static ArrayList<String> getStringArrayList(int[] intArray) {
    // return getStringArrayList(getIntegerArrayList(intArray));
    // }

    public static ArrayList<Double> getDoubleArrayList(double[] doubleArray) {
        ArrayList<Double> doubleList = new ArrayList<>();
        for (double d : doubleArray) {
            doubleList.add(d);
        }
        return doubleList;
    }

    public static HashSet<String> getHashSet(String[] array) {
        return new HashSet<>(Arrays.asList(array));
    }

    public static ArrayList<Integer> getIntegerArrayList(int[] intArray) {
        ArrayList<Integer> intList = new ArrayList<>();
        for (int i : intArray) {
            intList.add(i);
        }
        return intList;
    }

    /**
     * Convert a String ArrayList to a String array of the same contents.
     *
     * @param list to convert to array
     * @return string array containing the elements of list
     */
    public static String[] getStringArray(ArrayList<String> list) {
        return list.toArray(new String[0]);
    }

    /**
     * Cast an object array to a String array, where the Objects are presumed to
     * be Strings.
     *
     * @param array
     * @return
     */
    public static String[] getStringArray(Object[] array) {
        return Arrays.copyOf(array, array.length, String[].class);
    }

    /**
     * Convert ArrayList\<Integer\> or ArrayList<Double> to ArrayList<String>
     * <p>
     * TODO: This is terrible and needs to be redone.
     *
     * @param inList
     * @return
     */
    @Deprecated
    public static ArrayList<String> getStringArrayList(ArrayList<Number> inList) {
        ArrayList<String> stringList = new ArrayList<>();

        if (inList.isEmpty()) {
            stringList.add("");
        } else if (inList.get(0) instanceof Integer) {
            for (Number number : inList) {
                try {
                    stringList.add(Integer.toString((int) number));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (inList.get(0) instanceof Double) {
            for (Number number : inList) {
                try {
                    stringList.add(Double.toString((double) number));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return stringList;
    }

    @Deprecated
    public static ArrayList<String> getStringArrayListFromDouble(ArrayList<Double> doubleList) {
        ArrayList<String> stringList = new ArrayList<>();
        for (Double doub : doubleList) {
            try {
                stringList.add(Double.toString(doub));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }

    /**
     * TODO: Delete? Is this even used?
     *
     * @param array
     * @param list
     */
    @Deprecated
    public static void setArrayToList(String[] array, ArrayList<String> list) {
        list.clear();
        addArrayToList(array, list);
    }

    /**
     * Sorts list from longest to shortest String length
     *
     * @param list to sort
     */
    public static void sortByLongestFirst(ArrayList<String> list) {
        ArrayList<String> outList = new ArrayList<>();
        while (!list.isEmpty()) {
            String longest = "";
            for (String string : list) {
                if (string.length() > longest.length()) {
                    longest = string;
                }
            }
            outList.add(longest);
            while (list.contains(longest)) {
                list.remove(longest);
            }
        }
        list.addAll(outList);
    }

    /**
     * Sorts array from longest to shortest String length
     *
     * @param array to sort
     * @return sorted array
     */
    public static String[] sortByLongestFirst(String[] array) {
        ArrayList<String> outList = new ArrayList<>(Arrays.asList(array));
        sortByLongestFirst(outList);
        return outList.toArray(new String[0]);
    }

    /**
     * Merge a list of sets into 1 set.
     *
     * @param sets to merge
     * @return a HashSet with the elements of all input hash sets.
     */
    public static HashSet<String> mergeHashSets(HashSet<String>[] sets) {
        HashSet<String> set = new HashSet<>();
        for (HashSet<String> s : sets) {
            set.addAll(s);
        }
        return set;
    }

    /**
     * Join all elements of the list by the specified delimiter
     *
     * @param list      containing elements to join
     * @param delimiter to join list elements
     * @return result joined by delimiter
     */
    public static String join(ArrayList<String> list, String delimiter) {
        StringBuilder builder = new StringBuilder();
        int lastIndex = list.size() - 1;
        for (int i = 0; i < lastIndex; i++) {
            builder.append(list.get(i));
            builder.append(delimiter);
        }
        builder.append(list.get(lastIndex));
        return builder.toString();
    }

}
