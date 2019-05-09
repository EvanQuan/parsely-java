package com.github.evanquan.parsely.parser;

class Requirement {

    private Status statusIfConditionMet;
    private Condition condition;
    private Status statusDefault;

    Requirement(Status statusIfConditionMet,
                Condition condition,
                Status statusDefault) {
        this.statusIfConditionMet = statusIfConditionMet;
        this.condition = condition;
        this.statusDefault = statusDefault;
    }

    public Status getStatusIfConditionMet() {
        return statusIfConditionMet;
    }

    public Condition getCondition() {
        return condition;
    }

    public Status getStatusDefault() {
        return statusDefault;
    }
}
