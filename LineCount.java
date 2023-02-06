package Nonogram; // change this to your package

import java.io.File;
import java.util.Scanner;

public class LineCount {

    public static void main(String args[]) {
        
        String directory = "c:/users/ksucp/desktop/stevenrbrandt-BasicGraphics-08d7e4e/src/Nonogram";
        
        File dir = new File(directory);
        File[] list = dir.listFiles();
        String filesFound = "Scanned these files:\n";
        String linesDisregarded = "";
        int lines = 0;
        for (File file : list) {
            if (file.getName().matches(".*\\.java")) {
                int linesForThisFile = 0;
                int lineNum = 1;
                filesFound += "- " + file.getName() + "\n";
                Scanner in = new Scanner(System.in);
                try {
                    in = new Scanner(file);
                    linesDisregarded += "\nDisregarded lines in " + file.getName() + "\n";
                    while (in.hasNextLine()) {
                        String line = in.nextLine();
                        lineNum++;
                        if (!line.matches("import.*|package.*") && line.matches("(.*;.*)|(.*public.*)|(.*private.*)|(.*if\\s*\\(.*)|(.*\\s*else\\s*.*)|(.*new\\s*.*)|(.*for\\s*\\(.*)")) {
                            lines++;
                            linesForThisFile++;
                            System.out.println(lineNum + " added line" + line);
                        } else {
                            linesDisregarded += lineNum + " " + line + "\n";
                        }
                    }
                    System.out.println("\nlines found in this file (" + file.getName() + ") = " + linesForThisFile + "\n");
                } catch (Exception e) {
                    System.out.println("something went wrong");
                } finally {
                    in.close();
                }
            }
        }
        System.out.println("Disregarded these lines:\n" + linesDisregarded);
        System.out.println(filesFound);
        System.out.println("probably around " + lines + " lines");
    }

}
