package com.github.evanquan.parsely.parser;

import java.util.HashMap;

/**
 * Retrieve parsers.
 *
 * @author Evan Quan
 */
public final class ParserFactory {

    private static HashMap<String, String> actionTypes;

    /**
     * Cannot instantiate.
     */
    private ParserFactory() {
    }

    /**
     * @param parserType to get
     * @return the specified parser, or null if not configured correctly.
     */
    public static Parser getParser(ParserType parserType) {
        switch (parserType) {
            case VERB_AGNOSTIC:
                return VerbAgnosticParser.getInstance();
            case VERB_GNOSTIC:
                return new VerbGnosticParser(actionTypes);
        }
        return null;
    }

    public static void setActionTypes(HashMap<String, String> actions) {
        actionTypes = actions;
    }

    /**
     * Valid parser types
     */
    public enum ParserType {
        /**
         * Is aware of all valid verbs ahead of time. Needs to be configured
         * with valid verbs to work.
         */
        VERB_GNOSTIC,
        /**
         * Is not aware of any valid verbs. Does not need to be configured.
         */
        VERB_AGNOSTIC,
    }
}
