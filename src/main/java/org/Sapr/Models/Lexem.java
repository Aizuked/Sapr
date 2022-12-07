package org.Sapr.Models;

import org.Sapr.Consts.LexemTypes;

public record Lexem(LexemTypes lexemType, int lexemId, String lexemValue) {
    public String toString() {
        return "lexem type:" + lexemType + ";lexem id:" + lexemId + ";value:" + lexemValue;
    }
}
