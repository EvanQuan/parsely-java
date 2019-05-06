package com.github.evanquan.parsely.parser;

public enum ActionTypeRequirement {

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

    /**
     * Can have if and only if condition is met.
     */
    CONDITIONAL,
}
