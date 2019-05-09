package com.github.evanquan.parsely.parser;

class Requirement {

    private Status statusForCondition;
    private Condition condition;
    private Status statusDefault;

    Requirement(Status statusForCondition,
                Condition condition,
                Status statusDefault) {
        this.statusForCondition = statusForCondition;
        this.condition = condition;
        this.statusDefault = statusDefault;
    }

    public Status getStatusForCondition() {
        return statusForCondition;
    }

    public Condition getCondition() {
        return condition;
    }

    public Status getStatusDefault() {
        return statusDefault;
    }
}
