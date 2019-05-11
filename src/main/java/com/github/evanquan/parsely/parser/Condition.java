package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.Action;

/**
 * In order to determine the validity of {@link Action}s, each
 * {@link ActionType} must evaluate all the corresponding {@link Condition}s
 * for proper form.
 *
 * @author Evan Quan
 */
interface Condition {

    /**
     *
     * @param action to check if its form conforms with this status
     * @return true if the specified action's form conforms with this
     * status's specifications.
     */
    boolean isMet(Action action);
}
