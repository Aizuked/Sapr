package org.Sapr.ExceptionsChecker;

import org.Sapr.Consts.LexemTypes;
import org.Sapr.Models.Lexem;
import org.Sapr.Models.Node;
import org.Sapr.Models.Variable;
import org.Sapr.Parser.LookUpService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ExceptionsChecker {
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Variable> variables = new ArrayList<>();
    private final LookUpService look = new LookUpService();
    private int currentLine;
    private boolean exceptionRaised = false;

    public boolean isExceptionRaised() {
        return exceptionRaised;
    }

    public ExceptionsChecker(ArrayList<Node> nodes, List<Variable> variables) {
        this.nodes = nodes;
        this.variables = (ArrayList<Variable>) variables;
    }

    public void checkForExceptions() {
        int forFlag = 0;
        for (Node node : nodes) {
            if (forFlag == 0) currentLine++;

            boolean dataDeclaration = false;
            boolean dataEq = false;
            boolean operationFlag = false;
            boolean variableFlag1 = false;
            boolean variableFlag2 = false;
            boolean constantFlag = false;
            boolean parsingErrorFlag = false;
            boolean ppMM = false;
            Lexem tempLexem1 = null;
            Lexem tempLexem2 = null;

            for (Lexem lexem : node.lexemList()) {
                switch (lexem.lexemType()) {
                    case DataType: {
                        dataDeclaration = true;
                    } break;
                    case Variable: {
                        if (variableFlag1) {
                            variableFlag2 = true;
                            tempLexem2 = lexem;
                        }
                        else {
                            variableFlag1 = true;
                            tempLexem1 = lexem;
                        }
                    } break;
                    case Operation: {
                        if (lexem.lexemValue().equals("assign_operation"))
                            dataEq = true;
                        else if (!(lexem.lexemValue().equals("increment_operation") || lexem.lexemValue().equals("decrement_operation")))
                            operationFlag = true;
                        else
                            ppMM = true;

                    } break;
                    case Constant: {
                        constantFlag = true;
                        tempLexem2 = lexem;
                    } break;
                    case ParsingError: {
                        parsingErrorFlag = true;
                    } break;
                    case Identifier: {
                        if (lexem.lexemValue().equals("for"))
                            forFlag = 3;
                    } break;
                }
            }

            if (dataEq && parsingErrorFlag) {
                System.out.println("Illegal eq with unknown variable: " + currentLine);
            } else if (dataEq && dataDeclaration && constantFlag) {
                declarationVerifier(tempLexem1, tempLexem2, currentLine);
            } else if (dataEq) {
                leftRightVerifier(tempLexem1, tempLexem2, currentLine);
            } else if (operationFlag) {
                leftRightVerifier(tempLexem1, tempLexem2, currentLine);
            }

            if (forFlag != 0)
                forFlag--;
        }
    }

    private void declarationVerifier(Lexem lexemT, Lexem lexemV, int currentLine) {
        try {
            switch (getTypeByDeclaredName(lexemT.lexemValue())) {
                case "int" -> {
                    try {
                        Integer.parseInt(lexemV.lexemValue());
                    } catch (Exception e) {
                        System.out.println("Illegal type eq declaration on line: " + currentLine);
                    }
                }
                case "long" -> {
                    try {
                        Long.parseLong(lexemV.lexemValue());
                    } catch (Exception e) {
                        System.out.println("Illegal type eq declaration on line: " + currentLine);
                    }
                }
                case "String" -> {
                    if (lexemV.lexemValue().split("\"").length < 2) {
                        System.out.println("Illegal type eq declaration on line: " + currentLine);
                    }
                }
                case "boolean" -> {
                    if (!(lexemV.lexemValue().equals("true") || lexemV.lexemValue().equals("True")
                            || lexemV.lexemValue().equals("false") || lexemV.lexemValue().equals("False")))
                        System.out.println("Illegal type eq declaration on line: " + currentLine);
                }
                case "float" -> {
                    try {
                        Float.parseFloat(lexemV.lexemValue());
                    } catch (Exception e) {
                        System.out.println("Illegal type eq declaration on line: " + currentLine);
                    }
                }
                case "double" -> {
                    try {
                        Double.parseDouble(lexemV.lexemValue());
                    } catch (Exception e) {
                        System.out.println("Illegal type eq declaration on line: " + currentLine);
                    }
                }
            }
        } catch (Exception ignored) {
        }

    }

    private void leftRightVerifier(Lexem lexemL, Lexem lexemR, int currentLine) {
        try {
            if (!(lexemR.lexemType() == LexemTypes.Identifier)) {
                if (lexemR.lexemType() != LexemTypes.Constant) {
                    if (!Objects.equals(lexemL.lexemType(), lexemR.lexemType())) //Variable type lookup
                        System.out.println("Illegal operation on line: " + currentLine);
                } else {
                    switch (getTypeByDeclaredName(lexemL.lexemValue())) {
                        case "int" -> {
                            try {
                                Integer.parseInt(lexemR.lexemValue());
                            } catch (Exception e) {
                                System.out.println("Illegal operation on line: " + currentLine);
                            }
                        }
                        case "long" -> {
                            try {
                                Long.parseLong(lexemR.lexemValue());
                            } catch (Exception e) {
                                System.out.println("Illegal operation on line: " + currentLine);
                            }
                        }
                        case "String" -> {
                            if (lexemR.lexemValue().split("\"").length < 2) {
                                System.out.println("Illegal operation on line: " + currentLine);
                            }
                        }
                        case "boolean" -> {
                            if (!(lexemR.lexemValue().equals("true") || lexemR.lexemValue().equals("True")
                                    || lexemR.lexemValue().equals("false") || lexemR.lexemValue().equals("False")))
                                System.out.println("Illegal operation on line: " + currentLine);
                        }
                        case "float" -> {
                            try {
                                Float.parseFloat(lexemR.lexemValue());
                            } catch (Exception e) {
                                System.out.println("Illegal operation on line: " + currentLine);
                            }
                        }
                        case "double" -> {
                            try {
                                Double.parseDouble(lexemR.lexemValue());
                            } catch (Exception e) {
                                System.out.println("Illegal operation on line: " + currentLine);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTypeByDeclaredName(String varName) {
        AtomicReference<String> type = new AtomicReference<>("");
        variables.forEach(i -> {
            if (i.variableName().equals(varName) && type.get().equals("")) type.set(i.variableDataType());
        });

        return type.get();
    }
}
