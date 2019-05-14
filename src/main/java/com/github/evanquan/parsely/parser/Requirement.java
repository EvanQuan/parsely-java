package com.github.evanquan.parsely.parser;

import com.github.evanquan.parsely.parser.condition.Condition;
import com.github.evanquan.parsely.words.Action;

import java.util.ArrayList;

/**
 * @author Evan Quan
 */
class Requirement {

    private Condition conditionIfConditionsMet;
    private ArrayList<Condition> conditions;
    private Condition conditionDefault;

    Requirement(Condition conditionIfConditionsMet,
                ArrayList<Condition> conditions,
                Condition conditionDefault) {
        this.conditionIfConditionsMet = conditionIfConditionsMet;
        this.conditions = conditions;
        this.conditionDefault = conditionDefault;
    }

    Condition getConditionIfConditionsMet() {
        return conditionIfConditionsMet;
    }

    ArrayList<Condition> getConditions() {
        return conditions;
    }

    Condition getConditionDefault() {
        return conditionDefault;
    }

    boolean isMet(Action action) {
        Condition condition = getStatus(action);
        return condition.isMet(action);
    }

    private Condition getStatus(Action action) {
        for (Condition condition : conditions) {
            if (!condition.isMet(action)) {
                return conditionDefault;
            }
        }
        return conditionIfConditionsMet;
    }
}
