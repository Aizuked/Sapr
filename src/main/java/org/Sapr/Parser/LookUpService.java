package org.Sapr.Parser;

import org.Sapr.Consts.LexemConstants;

import java.util.Map;

public class LookUpService {
    public Map<Integer, String> findType(String type) {
        return LexemConstants.types.row(type);
    }

    public Map<Integer, String> findOperation(String operator) {
        return LexemConstants.operators.row(operator);
    }

    public Map<Integer, String> findIdentifier(String identifier) {
        return Map.of(LexemConstants.identifiers.indexOf(identifier), identifier);
    }

    public Map<Integer, String> findDelimiter(String delimiter) {
        return Map.of(LexemConstants.delimiters.indexOf(delimiter), delimiter);
    }

    public boolean isType(String input) {
        return LexemConstants.types.containsRow(input);
    }

    public boolean isOperation(String input) {
        return LexemConstants.operators.containsRow(input);
    }

    public boolean isIdentifier(String input) {
        return LexemConstants.identifiers.contains(input);
    }

    public boolean isDelimiter(String input) {
        return LexemConstants.delimiters.contains(input);
    }
}
