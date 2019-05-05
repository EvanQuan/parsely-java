package com.github.evanquan.parsely.parser;

import javax.naming.ConfigurationException;
import java.util.HashMap;

public final class ParserFactory {

    private static HashMap<String, String> actionTypes;

    /**
     * Cannot instantiate.
     */
    private ParserFactory() {
    }

    /**
     * @param parserType to get
     * @return the specified parser
     * @throws ConfigurationException if the parser was not configured
     *                                beforehand.
     */
    public static Parser getParser(ParserType parserType) throws ConfigurationException {
        switch (parserType) {
            case VERB_AGNOSTIC:
                return VerbAgnosticParser.getInstance();
            case VERB_GNOSTIC:
                if (isConfigured()) {
                    return new VerbGnosticParser(actionTypes);
                }
                throw new ConfigurationException("Cannot make verb gnostic " +
                        "parser without action types.");
        }
        return null;
    }

    /**
     * @return true if the parsers are configured.
     */
    public static boolean isConfigured() {
        return null != actionTypes;
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
