package org.Sapr.ReParser;

import org.Sapr.Models.Lexem;
import org.Sapr.Models.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reparser {
    private static ArrayList<Lexem> lexems;
    private static ArrayList<String> resultingFile = new ArrayList<>();
    private int tabCount = 0;
    private ArrayList<Node> nodes = new ArrayList<>();

    public Reparser(ArrayList<Lexem> lexems) {
        this.lexems = lexems;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void reparse() throws IOException {
        ArrayList<Lexem> tempLexems = new ArrayList<>();

        for (Lexem lexem : lexems) {
            tempLexems.add(lexem);
            if (lexem.lexemValue().equals("}") || lexem.lexemValue().equals(";") || lexem.lexemValue().equals("{")) {
                nodes.add(new Node(new ArrayList<>(tempLexems)));
                nodeBuilder(tempLexems);
                tempLexems.clear();
            }
        }
    }

    private void nodeBuilder(List<Lexem> tempLexems) {
        for (int i = 0; i < tempLexems.size(); i++) {
            String tempStore = "";

            for (int j = 0; j < tabCount; j++) {
                tempStore += "\t";
            }

            tempStore += tempLexems.get(i).lexemType() + " Node:\n";

            for (int j = 0; j < tabCount; j++) {
                tempStore += "\t";
            }

            tempStore += tempLexems.get(i).lexemValue();

            resultingFile.add(tempStore);
        }

        String tempStore = "";

        for (int j = 0; j < tabCount; j++) {
            tempStore += "\t";
        }

        tempStore += "|\n";

        for (int j = 0; j < tabCount; j++) {
            tempStore += "\t";
        }

        tempStore += "----------------\n";

        for (int j = 0; j < tabCount + 4; j++) {
            tempStore += "\t";
        }

        tempStore += "|\n";

        resultingFile.add(tempStore);

        tabCount += 4;
    }

    public void dump(String fileName) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(fileName)));

        resultingFile.stream().forEachOrdered(i -> {
            try {
                fileWriter.write(i + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        fileWriter.close();
    }

}
