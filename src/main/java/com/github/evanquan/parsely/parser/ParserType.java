package com.github.evanquan.parsely.parser;

/**
 * Valid {@link Parser} types. The type determines how the {@link Parser}
 * behaves when parsing input, and may depend on varying levels of
 * configuration for what words it knows.
 *
 * @author Evan Quan
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
