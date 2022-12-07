package org.Sapr.Models;

import org.Sapr.Consts.LexemTypes;

public record Models(LexemTypes lexemType, int lexemId, String lexemValue) {
    public String toLexemString() {
        return "\tlexem type: " + lexemType + ";\tlexem id: " + lexemId + ";\tvalue: " + lexemValue;
    }
    public String toVariableString() {
        return "\tvariable of type: " +  + ";\tlexem id: " + lexemId + ";\tvalue: " + lexemValue;
    }
}

public record Variable(int asd) {

}