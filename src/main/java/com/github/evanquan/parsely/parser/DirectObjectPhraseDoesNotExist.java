package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.Action;

public class DirectObjectPhraseDoesNotExist implements DirectObjectPhraseCondition {

    private static DirectObjectPhraseDoesNotExist instance =
            new DirectObjectPhraseDoesNotExist();

    private DirectObjectPhraseDoesNotExist() {
    }

    public static DirectObjectPhraseDoesNotExist getInstance() {
        return instance;
    }

    /**
     * @param action to check if its form conforms with this status
     * @return true if the specified action's form conforms with this status's
     * specifications.
     */
    @Override
    public boolean isMet(Action action) {
        return !action.hasDirectObjectPhrase();
    }
}
