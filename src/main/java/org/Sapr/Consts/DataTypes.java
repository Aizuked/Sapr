package org.Sapr.Consts;

public enum DataTypes {
    INT("int"),
    LONG("long"),
    STRING("String"),
    BOOL("bool"),
    FLOAT("float"),
    DOUBLE("double");

    private final String dataType;

    DataTypes(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}