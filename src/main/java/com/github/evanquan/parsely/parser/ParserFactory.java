package com.github.evanquan.parsely.parser;

import java.util.HashMap;

/**
 * Abstract {@link Parser} factory.
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
     * @return the specified {@link Parser}, or null if not configured
     * correctly.
     */
    public static Parser getParser(ParserType parserType) {
        switch (parserType) {
            case VERB_AGNOSTIC:
                return VerbAgnosticParser.getInstance();
            case VERB_GNOSTIC:
                return new VerbGnosticParser(actionTypes);
            default:
                return null;
        }
    }

    public static void setActionTypes(HashMap<String, String> actions) {
        actionTypes = actions;
    }

}
