package com.github.evanquan.parsely.parser.condition;

import com.github.evanquan.parsely.words.Action;

class Always implements Condition {

    private static Always instance = new Always();

    public static Always getInstance() {
        return instance;
    }

    /**
     * @param action to check if its form conforms with this status
     * @return true if the specified action's form conforms with this status's
     * specifications.
     */
    @Override
    public boolean isMet(Action action) {
        return true;
    }
}
