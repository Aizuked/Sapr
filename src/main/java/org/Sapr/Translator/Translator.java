package org.Sapr.Translator;

import org.Sapr.Consts.LexemConstants;
import org.Sapr.Consts.LexemTypes;
import org.Sapr.Models.Lexem;
import org.Sapr.Models.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Translator {
    private ArrayList<Node> nodes;
    private ArrayList<String> output = new ArrayList<>();
    private final String space = " ";

    public Translator(List<Node> nodes) {
        this.nodes = (ArrayList<Node>) nodes;
    }

    public void translate() {
        int currentTabs = 0;
        for (Node node : nodes) {
            boolean prevVar = false;
            boolean firstTimer = true;
            StringBuilder currentNode = new StringBuilder();
            for (Lexem lexem : node.lexemList()) {
                switch (lexem.lexemType()) {
                    case DataType: {
                        if (lexem.lexemValue().equals("boolean value")) {
                            if (firstTimer) {
                                for (int i = 0; i < currentTabs; i++)
                                    currentNode.append("\t");
                                firstTimer = false;
                            }
                            currentNode.append("bool ");
                        } else {
                            if (firstTimer) {
                                for (int i = 0; i < currentTabs; i++)
                                    currentNode.append("\t");
                                firstTimer = false;
                            }
                            currentNode.append(findTypeByDescription(lexem.lexemId())).append(" ");
                        }
                    } break;
                    case Identifier: {
                        if (!(lexem.lexemValue().equals("public") || lexem.lexemValue().equals("class") || lexem.lexemValue().equals("void") || lexem.lexemValue().equals("static"))) {
                            if (firstTimer) {
                                for (int i = 0; i < currentTabs; i++)
                                    currentNode.append("\t");
                                firstTimer = false;
                            }
                            currentNode.append(lexem.lexemValue()).append(" ");
                        } else if (lexem.lexemValue().equals("public")) {
                            if (firstTimer) {
                                for (int i = 0; i < currentTabs; i++)
                                    currentNode.append("\t");
                                firstTimer = false;
                            }
                        } else if (lexem.lexemValue().equals("class")) {
                            if (firstTimer) {
                                for (int i = 0; i < currentTabs; i++)
                                    currentNode.append("\t");
                                firstTimer = false;
                            }
                            currentNode.append("namespace ");
                        } else if (lexem.lexemValue().equals("void")) {
                            if (firstTimer) {
                                for (int i = 0; i < currentTabs; i++)
                                    currentNode.append("\t");
                                firstTimer = false;
                            }
                            currentNode.append("int ");
                        } else if (lexem.lexemValue().equals("static")) {
                            if (firstTimer) {
                                for (int i = 0; i < currentTabs; i++)
                                    currentNode.append("\t");
                                firstTimer = false;
                            }
                            currentNode.append("");
                        }
                    } break;
                    case Delimiter: {
                        if (lexem.lexemValue().equals("{"))
                            currentTabs++;
                        else if (lexem.lexemValue().equals("}"))
                            currentTabs--;
                        if (firstTimer) {
                            for (int i = 0; i < currentTabs; i++)
                                currentNode.append("\t");
                            firstTimer = false;
                        }
                        currentNode.append(lexem.lexemValue()).append(" ");

                    } break;
                    case Operation: {
                        if (firstTimer) {
                            for (int i = 0; i < currentTabs; i++)
                                currentNode.append("\t");
                            firstTimer = false;
                        }
                        String operationById = findOperatorByDescription(lexem.lexemId());
                        currentNode.append(operationById).append(" ");
                    } break;
                    default: {
                        if (lexem.lexemType().equals(LexemTypes.Variable) && !prevVar) {
                            prevVar = true;
                            if (firstTimer) {
                                for (int i = 0; i < currentTabs; i++)
                                    currentNode.append("\t");
                                firstTimer = false;
                            }
                            currentNode.append(lexem.lexemValue()).append(" ");
                        } else if (lexem.lexemType().equals(LexemTypes.Variable) && prevVar) {
                            prevVar = false;
                        } else {
                            if (firstTimer) {
                            for (int i = 0; i < currentTabs; i++)
                                currentNode.append("\t");
                            firstTimer = false;
                        }
                            currentNode.append(lexem.lexemValue()).append(" ");
                        }
                    } break;
                }
            }
            output.add(currentNode.toString());
        }
    }

    public String findOperatorByDescription(int id) {
        return (String) LexemConstants.operators.column(id).keySet().toArray()[0];
    }

    public String findTypeByDescription(int id) {
        return (String) LexemConstants.types.column(id).keySet().toArray()[0];
    }

    public void dump(String fileName) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(fileName)));

        output.stream().forEachOrdered(i -> {
            try {
                fileWriter.write(i + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        fileWriter.close();
    }

}
