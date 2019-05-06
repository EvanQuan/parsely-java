package com.github.evanquan.parsely.parser;

/**
 * Retreive
 *
 * @author Evan Quan
 */
final class ValidatorFactory {

    /**
     * Cannot instantiate.
     */
    private ValidatorFactory() {
    }

    /**
     * @param validatorType to get
     * @return the specified {@link Validator}, or null if not configured
     * correctly.
     */
    public static Validator getValidator(ValidatorType validatorType) {
        switch (validatorType) {
            case VERB_AGNOSTIC:
            case VERB_GNOSTIC:
            default:
                return null;
        }
    }

    public enum ValidatorType {
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
