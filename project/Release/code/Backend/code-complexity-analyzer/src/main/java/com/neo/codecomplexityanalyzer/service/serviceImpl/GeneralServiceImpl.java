/*
-------------------------------------------------------------------------------------------------------
--  Date        Sign    History
--  ----------  ------  --------------------------------------------------------------------------------
--  2019-08-06  Sathira  185817, Created General Service Impl.
--  ----------  ------  --------------------------------------------------------------------------------
*/


package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GeneralServiceImpl implements GeneralService{
    private String sourceCode;
    private Long sourceCodeLength;
    private Scanner scanner = null;

    public Long getSourceCodeLength(){
        if (sourceCodeLength == null)
            sourceCodeLength = Long.valueOf(sourceCode.length());
        return (sourceCodeLength);
    }

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

    public int countOccurences(String str, String word) {
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

    public int getLogicalCount(String subString){
        int logicalCount = 0;
        if ((subString.contains("&&") || subString.contains("||") || subString.contains("&") || subString.contains("|"))) {
            int andOccourences = this.countOccurences(subString, "&&");
            int orOccourences = this.countOccurences(subString, "||");
            int bitAndOccourences = this.countOccurences(subString, "&");
            int bitOrOccourences = this.countOccurences(subString, "|");
            logicalCount += andOccourences + orOccourences + bitAndOccourences + bitOrOccourences;
        }
        return logicalCount;
    }
}

