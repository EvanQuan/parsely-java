package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.Action;

public class Never implements Condition {

    private static Never instance = new Never();

    private Never() {
    }

    public static Never getInstance() {
        return instance;
    }

    /**
     * @param action to check if its form conforms with this status
     * @return true if the specified action's form conforms with this status's
     * specifications.
     */
    @Override
    public boolean isMet(Action action) {
        return false;
    }
}
