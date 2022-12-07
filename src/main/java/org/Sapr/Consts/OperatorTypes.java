package org.Sapr.Consts;

public enum OperatorTypes {
    EQ("="),
    PL("+"),
    MI("-"),
    MU("*"),
    DI("/"),
    PLEQ("+="),
    MIEQ("-="),
    EQEQ("=="),
    MO(">"),
    LE("<"),
    PLPL("++"),
    MIMI("--"),
    MOD("%");

    private final String operatorType;

    OperatorTypes(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorType() {
        return operatorType;
    }
}
