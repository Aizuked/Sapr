package org.Sapr;


import org.Sapr.ExceptionsChecker.ExceptionsChecker;
import org.Sapr.Models.Node;
import org.Sapr.Parser.Parser;
import org.Sapr.ReParser.Reparser;
import org.Sapr.Translator.Translator;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser("C:\\Users\\aizyk\\IdeaProjects\\Sapr\\src\\main\\resources\\input.txt");
        parser.parse();
        parser.dump("C:\\Users\\aizyk\\IdeaProjects\\Sapr\\src\\main\\resources\\output.txt");

        Reparser reparser = new Reparser(parser.getLexems());
        reparser.reparse();
        reparser.dump("C:\\Users\\aizyk\\IdeaProjects\\Sapr\\src\\main\\resources\\output2.txt");

        ArrayList<Node> nodes = reparser.getNodes();
        ExceptionsChecker exceptionsChecker = new ExceptionsChecker(reparser.getNodes(), parser.getVariables());
        exceptionsChecker.checkForExceptions();

        if (!exceptionsChecker.isExceptionRaised()) {
            Translator translator = new Translator(reparser.getNodes());
            translator.translate();
            translator.dump("C:\\Users\\aizyk\\IdeaProjects\\Sapr\\src\\main\\resources\\output3.txt");
        }
    }
}