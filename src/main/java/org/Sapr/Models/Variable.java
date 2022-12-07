package org.Sapr.Models;



public record Variable(String variableDataType, int variableId, String variableName) {
    public String toString() {
        return "<" + variableId + ">variable of type<" + variableDataType + ">with name:" + variableName;
    }
}
