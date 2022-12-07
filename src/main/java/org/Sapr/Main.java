package org.Sapr;


import org.Sapr.Parser.Parser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser("C:\\Users\\aizyk\\IdeaProjects\\Sapr\\src\\main\\resources\\input.txt");
        parser.parse();
        parser.dump("C:\\Users\\aizyk\\IdeaProjects\\Sapr\\src\\main\\resources\\output.txt");
    }
}