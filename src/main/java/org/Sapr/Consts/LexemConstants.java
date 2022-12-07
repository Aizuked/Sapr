package org.Sapr.Consts;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.List;

public class LexemConstants {

    public static final Table<String, Integer, String> types = ImmutableTable.<String, Integer, String> builder()
            .put("int", 0, "32-bit integer")
            .put("long", 1, "64-bit integer")
            .put("String", 2, "string of chars")
            .put("bool", 3, "boolean value")
            .put("float", 4, "32-bit floating point value")
            .put("double", 5, "64-bit floating point value")
            .build();

    public static final Table<String, Integer, String> operators = ImmutableTable.<String, Integer, String> builder()
            .put("=", 0, "assign_operation")
            .put("+", 1, "sum_operation")
            .put("-", 2, "subtract_operation")
            .put("*", 3, "multiply_operation")
            .put("/", 4, "divide_operation")
            .put("+=", 5, "add_amount_operation")
            .put("-=", 6, "subtract_amount_operation")
            .put("==", 7, "are_equal_operation")
            .put(">", 8, "more_operation")
            .put("<", 9, "less_operation")
            .put("++", 10, "increment_operation")
            .put("--", 11, "decrement_operation")
            .put("%", 12, "modulo_operation")
            .build();

    public static final ArrayList<String> identifiers = new ArrayList<>(List.of("class", "public",
            "private", "for", "return", "if", "else", "while", "do", "switch", "case", "static", "void", "package"));

    public static final ArrayList<String> delimiters = new ArrayList<>
            (List.of(";", ",", "(", ")", "[", "]", "{", "}"));

}
