package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.words.Action;

/**
 * Specifies how necessary an {@link Action} component is.
 *
 * @author Evan Quan
 */
public enum Status {

    /**
     * Required with no exceptions.
     */
    MANDATORY,

    /**
     * Completely optional under all contexts.
     */
    OPTIONAL,

    /**
     * Cannot have under any circumstance.
     */
    FORBIDDEN,
}
