/*
-------------------------------------------------------------------------------------------------------
--  Date        Sign    History
--  ----------  ------  --------------------------------------------------------------------------------
--  2019-08-08  Sathira  185834, modified the getSourceCode.
--  2019-08-07  Indunil  185834, Created functions to remove all comments from the source code
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

import org.apache.commons.io.FilenameUtils;

public class GeneralServiceImpl {
    private StringBuilder sourceCode = new StringBuilder();
    private String originalSourceCode;
    //    private String sourceCode="";
    private Long sourceCodeLength;
    private Scanner scanner = null;
    private boolean multipleLineComment = false;
    private boolean multipleLineCommentAtTheEnd = false;

    private boolean isMultipleLineCommentAtTheEnd() {
        return multipleLineCommentAtTheEnd;
    }

    private void setMultipleLineCommentAtTheEnd(boolean multipleLineCommentAtTheEnd) {
        this.multipleLineCommentAtTheEnd = multipleLineCommentAtTheEnd;
    }

    /* Detect the Source Code type
     * return 1 - .java 
     * return 2 - .cpp
     * return 0 - others  
     */
	public String getSourceCodeType(String path) { 
		String ext = FilenameUtils.getExtension(path);
		
		if("java".equalsIgnoreCase(ext)) {
			return "java";
		}else if("cpp".equalsIgnoreCase(ext)) {
			return "cpp";
		}else {
			return "other";
		}
	}
    
    // Get Multiple line comment status
    private boolean isMultipleLineComment() {
        return multipleLineComment;
    }

    // Set multiple line comment status
    private void setMultipleLineComment(boolean multipleLineComment) {
        this.multipleLineComment = multipleLineComment;
    }

    // Get Source Code character length
    public Long getSourceCodeLength() {
        if (sourceCodeLength == null)
            sourceCodeLength = (long) sourceCode.length();
        return (sourceCodeLength);
    }

    // Function to read the file and return the source code as a String
    // Comments are removed from the source code
    public String getSourceCode(String path) {
        File file = new File(path);
        String tempLine;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNextLine()) {
            tempLine = scanner.nextLine() + "\n";
            sourceCode.append(tempLine);
        }
        String[] sourceCodeArray = new String[this.findSourceCodeLineCount(sourceCode.toString())];
        originalSourceCode = String.valueOf(sourceCode);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (scanner.hasNextLine()) {
            sourceCodeArray[i] = (scanner.nextLine() + "\n");
            ++i;
        }

        StringBuilder resultSourceCode = new StringBuilder();
        String[] sourceCodeArrayresult = this.removeCommentsFromTheCode(sourceCodeArray);
        for (String line : sourceCodeArrayresult) {
            resultSourceCode.append(line);
        }
        String finalCode = removeDoubleQuotedText(String.valueOf(resultSourceCode));
        return finalCode;
    }

    // Get Lines of Source Code upto a certain line
    public ArrayList<String> getSourceCodeLinesUpto(int lineNumber) {
        ArrayList<String> sourceCodeLines = new ArrayList<>();
        BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(this.sourceCode.toString()));
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

    // Get a specific line of code
    public ArrayList<String> getSourceCodeLine(int lineNumber) {
        ArrayList<String> sourceCodeLines = new ArrayList<>();
        BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(this.sourceCode.toString()));
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

    // Get occurrences of a word in a String
    public int countOccurences(String str, String word) {
        String[] wordArray = str.split(" ");
        int count = 0;
        for (String temp : wordArray) {
            if (word.equals(temp))
                ++count;
        }
        return count;
    }

    public int getLogicalCount(String subString) {
        int logicalCount = 0;
        if ((subString.contains("&&") || subString.contains("||") || subString.contains("&")
                || subString.contains("|"))) {
            int andOccourences = this.countOccurences(subString, "&&");
            int orOccourences = this.countOccurences(subString, "||");
            int bitAndOccourences = this.countOccurences(subString, "&");
            int bitOrOccourences = this.countOccurences(subString, "|");
            logicalCount += andOccourences + orOccourences + bitAndOccourences + bitOrOccourences;
        }
        return logicalCount;
    }

    // Function to find the line count. i.e, the LOC

    public int findSourceCodeLineCount(String sourceCode) {
        int lineCount = 0;
        BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(sourceCode));

        try {
            while (sourceCodeTemp.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineCount;
    }

    // Function to return all lines of code as an array
    // Only to be used by the REACT application to display the source code on the
    // page

    public String[] collectAllSourceCodeLines(String sourceCode, int lineCount) {
        String[] sourceCodeLines = new String[lineCount];
        BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(sourceCode));
        String str;
        int i = 0;

        try {
            while ((str = sourceCodeTemp.readLine()) != null) {
                sourceCodeLines[i] = str;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sourceCodeLines;
    }

    // Function to remove comments from the source code
    // Removes comments and return the source as an array of strings


    public String[] removeCommentsFromTheCode(String[] sourceCode) {
        int ArrayLength = sourceCode.length;
        String tempCodeLine;
        String[] outputSourceCode = new String[sourceCode.length];
        int singleLineCommentStartingPoint;
        int multiLineCommentEntryPoint, multiLineCommentExitPoint;

        for (int i = 0; i < sourceCode.length; i++) {
            tempCodeLine = sourceCode[i];
            singleLineCommentStartingPoint = tempCodeLine.indexOf("//");
            if (singleLineCommentStartingPoint == -1) {
                outputSourceCode[i] = removeMultipleLineComments(tempCodeLine);
            } else {
                tempCodeLine = tempCodeLine.substring(0, singleLineCommentStartingPoint);
                outputSourceCode[i] = removeMultipleLineComments(tempCodeLine);
            }
        }
        return outputSourceCode;
    }

    public String getOriginalSourceCode() {
        return originalSourceCode;
    }

    // Recursive method to remove multiple line comments
    public String removeMultipleLineComments(String lineOfCode) {
        int multiLineCommentEntryPoint, multiLineCommentExitPoint;
        String stringAfterRemoval;

        if (isMultipleLineComment()) {
            if (isMultipleLineCommentAtTheEnd()) {
                multiLineCommentEntryPoint = -1;
            } else {
                multiLineCommentEntryPoint = 0;
            }
        } else {
            multiLineCommentEntryPoint = lineOfCode.indexOf("/*");
        }

        if (multiLineCommentEntryPoint == -1) {
            /*
             * This is the base condition Return the line of code as it is since there is no
             * multiple line comment entry point here
             */
            if (isMultipleLineCommentAtTheEnd()) {
                setMultipleLineCommentAtTheEnd(false);
            } else {
                setMultipleLineComment(false);
            }
            return lineOfCode;
        } else {
            /*
             * This is where the recursive call is This code unit runs when there is a
             * multiple line comment starter is in the code
             */
            multiLineCommentExitPoint = lineOfCode.indexOf("*/");

            if (multiLineCommentExitPoint == -1) {
                // No Closing literal. Means the comment continues to the next line
                setMultipleLineComment(true);
                if (multiLineCommentEntryPoint == 0) {
                    return replaceWithWhiteSpaces(lineOfCode, multiLineCommentEntryPoint, lineOfCode.length());
                } else {
                    stringAfterRemoval = replaceWithWhiteSpaces(lineOfCode, multiLineCommentEntryPoint,
                            lineOfCode.length());
                    setMultipleLineCommentAtTheEnd(true);
                    return removeMultipleLineComments(stringAfterRemoval);
                }
            } else {
                // There is a closing literal, means that some bugger has put a multiple line
                // comment within a single line
                setMultipleLineComment(false);
                stringAfterRemoval = replaceWithWhiteSpaces(lineOfCode, multiLineCommentEntryPoint,
                        multiLineCommentExitPoint + 2);
                return removeMultipleLineComments(stringAfterRemoval);
            }
        }
    }

    // This function place white spaces for all the comment characters
    public String replaceWithWhiteSpaces(String code, int entry, int exit) {
        String stringWithComment;
        String remainingString;
        String stringWithCommentRemoved;
        String stringAfterRemoval;

        remainingString = code.substring(exit); // The String segment after the comment
        stringWithComment = code.substring(0, exit);

        // Replace the comment with white spaces
        StringBuilder tempString = new StringBuilder(stringWithComment);
        int commentLength = exit - entry;

        for (int i = 0; i < commentLength; i++) {
            tempString.setCharAt((i + entry), ' ');
        }
        stringWithCommentRemoved = tempString.toString();
        stringAfterRemoval = stringWithCommentRemoved + remainingString;

        return stringAfterRemoval;
    }

    // Function to detect if the file is .cpp or .java
    // Returns integer value 1, if the file has a .cpp extension, means it is a C++
    // file
    // Returns integer value 2, if the file has a .java extension, means it is a
    // Java file
    // Returns integer value 0, if the file has any other extension, means it is an
    // unsupported file type

    public int checkExtension() {
        // Implementation to be done here

        return 2;
    }

    // This function would remove the double quoted text from a given line of code

    public String removeDoubleQuotedText(String lineOfCode) {
        int textEntryPoint, textExitPoint;
        String stringAfterRemoval, tempstring;

        textEntryPoint = lineOfCode.indexOf("\"");
        if (textEntryPoint == -1) { // Base condition 01
            // No double quoted text, return as it is
            return lineOfCode;
        } else if ((lineOfCode.substring(textEntryPoint + 1)).indexOf("\"") == -1) { // Base condition 2, some bugger
            // has an unclosed double
            // quotes.
            // This is just for error handling
            stringAfterRemoval = replaceWithWhiteSpaces(lineOfCode, textEntryPoint, lineOfCode.length());
            return (removeDoubleQuotedText(stringAfterRemoval));
        } else {
            textExitPoint = lineOfCode.indexOf("\"", (lineOfCode.indexOf("\"") + 1));
            stringAfterRemoval = replaceWithWhiteSpaces(lineOfCode, textEntryPoint, textExitPoint + 1);
            return (removeDoubleQuotedText(stringAfterRemoval));
        }
    }

    /*
     * This function would accept any String token such as "for", "cout", "+", "+=",
     * ">>>" etc .... This can be anything And return the number of occurrences of
     * that token in the given line of code
     */

    public int findNumberOfCoccurences(String lineOfCode, String token) {
        int tokenEntryPoint, tokenExitPoint;
        String tempstring;
        int tokenLength = token.length();
        tokenEntryPoint = lineOfCode.indexOf(token);

        if (tokenEntryPoint == -1) { // Base condition, No token found, return 0
            System.out.println(lineOfCode);
            return 0;
        } else {
            tokenExitPoint = lineOfCode.indexOf(token, (lineOfCode.indexOf(token) + 1));
            if (tokenExitPoint == -1) {
                tempstring = "";
            } else {
                tempstring = lineOfCode.substring(tokenExitPoint);
            }
            return (findNumberOfCoccurences(tempstring, token) + 1);
        }
    }

}
