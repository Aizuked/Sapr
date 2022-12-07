package org.Sapr.ReParser;

import java.io.*;

public class Reparser {
    BufferedReader reader;

    public Reparser(String filename) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(new File(filename)));
    }

    public void reparse() throws IOException {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            for (String podString : currentLine.split(" ")) {
                if (!podString.isBlank()) {
                    
                }
            }
        }
    }


}
