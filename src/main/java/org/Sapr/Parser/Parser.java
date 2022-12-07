package org.Sapr.Parser;

import org.Sapr.Consts.LexemTypes;
import org.Sapr.Models.Lexem;
import org.Sapr.Models.Variable;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Parser {
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private BufferedReader reader;
    private LookUpService look = new LookUpService();
    private ArrayList<Lexem> lexems = new ArrayList<>();
    private ArrayList<Variable> variables = new ArrayList<>();
    private int currentVariableId = 0;
    private String[] prevBuf = new String[2];

    public Parser(String fileName) throws FileNotFoundException {
        initializeBufferedReader(fileName);
    }

    private void initializeBufferedReader(String filename) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(new File(filename)));
    }

    public void parse() throws IOException {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            for (String podString : currentLine.split(" ")) {
                if (!podString.isBlank()) {
                    if (look.isType(podString)) {
                        addLexem(LexemTypes.DataType, podString);
                        prevBuf[0] = "dataType";
                        prevBuf[1] = podString;
                    } else if (look.isOperation(podString)) {
                        addLexem(LexemTypes.Operation, podString);
                        prevBuf[0] = "operation";
                    } else if (look.isIdentifier(podString)) {
                        addLexem(LexemTypes.Identifier, podString);
                        prevBuf[0] = "identifier";
                    } else if (look.isDelimiter(podString)) {
                        addLexem(LexemTypes.Delimiter, podString);
                        prevBuf[0] = "delimiter";
                    } else if (isNumeric(podString)) {
                        addLexem(LexemTypes.Constant, podString);
                        prevBuf[0] = "constant";
                    } else {
                        if (prevBuf[0].equals("dataType")) addVariable(prevBuf[1], podString);
                        if (!prevBuf[0].equals("dataType") && !prevBuf[0].equals("delimiter"))
                            addLexem(LexemTypes.ParsingError, podString);
                    }
                }
            }
        }

        reader.close();
    }

    private void addLexem(LexemTypes lexemType, String input) {
        int lexemId = -1;
        String lexemValue = "-1";
        Map<Integer, String> result = null;

        switch (lexemType) {
            case DataType:
                result = look.findType(input);
            break;

            case Delimiter:
                result = look.findDelimiter(input);
            break;

            case Identifier:
                result = look.findIdentifier(input);
            break;

            case Constant:
                result = Map.of(0, input);
            break;

            case Operation:
                result = look.findOperation(input);
            break;

            default:
                result = Map.of(-1, input);
            break;
        }

        lexemId = result.keySet().stream().findFirst().get();
        lexemValue = result.values().stream().findFirst().get();

        lexems.add(new Lexem(lexemType, lexemId, lexemValue));
    }


    private void addVariable(String variableDataType, String variableName) {
        variables.add(new Variable(variableDataType, currentVariableId, variableName));
        currentVariableId++;
    }

    public void dump(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(fileName));

        lexems.stream().forEachOrdered(i -> {
            try {
                fileWriter.write(i.toString() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        fileWriter.write("\n");

        variables.stream().forEachOrdered(i -> {
            try {
                fileWriter.write(i.toString() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        fileWriter.close();
    }

    public boolean isNumeric(String input) {
        if (input == null) return false;
        return pattern.matcher(input).matches();
    }

    public boolean isInVariables(String input) {
        boolean flag = false;
        for (Variable variable : variables) {
            if (Objects.equals(variable.variableName(), input)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
