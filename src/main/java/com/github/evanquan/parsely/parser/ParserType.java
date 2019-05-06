package com.github.evanquan.parsely.parser;

/**
 * Valid parser types
 */
public enum ParserType {
    /**
     * Is aware of all valid verbs ahead of time. Needs to be configured with
     * valid verbs to work.
     */
    VERB_GNOSTIC,
    /**
     * Is not aware of any valid verbs. Does not need to be configured.
     */
    VERB_AGNOSTIC,
}
