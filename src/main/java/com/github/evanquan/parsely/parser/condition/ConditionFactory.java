package com.github.evanquan.parsely.parser.condition;

public class ConditionFactory {

    public static DirectObjectPhraseCondition getDirectObjectPhraseCondition(ObjectPhraseType type) {
        switch (type) {
            case EXISTS:
                return DirectObjectPhraseDoesNotExist.getInstance();
            case DOES_NOT_EXIST:
            default:
                return DirectObjectPhraseExists.getInstance();
        }
    }

    public static IndirectObjectPhraseCondition getIndirectObjectPhraseCondition(ObjectPhraseType type) {
        switch (type) {
            case EXISTS:
                return IndirectObjectPhraseExists.getInstance();
            case DOES_NOT_EXIST:
            default:
                return IndirectObjectPhraseDoesNotExist.getInstance();
        }
    }

    public static PrepositionCondition getPrepositionCondition(PrepositionType type) {
        return null;
    }

    public enum ObjectPhraseType {
        EXISTS,
        DOES_NOT_EXIST,
    }

    public enum PrepositionType {
        DIRECTIONAL,
    }
}
