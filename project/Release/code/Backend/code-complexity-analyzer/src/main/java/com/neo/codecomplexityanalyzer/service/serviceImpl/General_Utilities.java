package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class General_Utilities {
    private String sourceCode;
    private Scanner scanner = null;

    public String getSourceCode(String path) {
        File file = new File(path);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNextLine()) {
            sourceCode += (scanner.nextLine() + "\n");
        }
        return this.sourceCode;
    }

    public ArrayList<String> getSourceCodeLinesUpto(int lineNumber) {
        ArrayList<String> sourceCodeLines = new ArrayList<String>();
        BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(this.sourceCode));
        String str;
        try {
            for (int i = 0; ((str = sourceCodeTemp.readLine()) != null) && (i <= lineNumber); ++i) {
                sourceCodeLines.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceCodeLines;
    }

    public ArrayList<String> getSourceCodeLine(int lineNumber) {
        ArrayList<String> sourceCodeLines = new ArrayList<String>();
        BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(this.sourceCode));
        String str;
        try {
            for (int i = 0; ((str = sourceCodeTemp.readLine()) != null); ++i) {
                if (i == lineNumber) {
                    sourceCodeLines.add(str);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceCodeLines;
    }

    public static int countOccurences(String str, String word) {
        // split the string by spaces in a
        String a[] = str.split(" ");

        // search for pattern in a
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            // if match found increase count
            if (word.equals(a[i]))
                count++;
        }
        return count;
    }

}

