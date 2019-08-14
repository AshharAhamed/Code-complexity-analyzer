/*
-------------------------------------------------------------------------------------------------------
--  Date        Sign    History
--  ----------  ------  --------------------------------------------------------------------------------
--  2019-08-06  Sathira  185821, Added the calculating Switch and it's cases score.
--  2019-08-06  Sathira  185833, Added the calculating Iterative Controls' logical operator score.
--  2019-08-06  Sathira  185819, Added the calculating Iterative Controls and it's logical operator score.
--  2019-08-06  Sathira  185820, Added the calculating Catch score.
--  2019-08-06  Sathira  185818, Added the calculating IF's logical operator score.
--  2019-08-05  Sathira  185817, Added the calculating IF and it's logical operator score.
--  2019-08-05  Sathira  185817, Created CTC Service Impl.
--  ----------  ------  --------------------------------------------------------------------------------
*/

package com.neo.codecomplexityanalyzer.service.serviceImpl;


import java.util.HashMap;

public class CTCServiceImpl {
    private static final int stackSize = 100;

    private static final String searchIf = "if";
    private static final String searchFor = "for";
    private static final String searchWhile = "while";
    private static final String searchSwitch = "switch";
    private static final String searchCase = "case";

    private static final char openBrace = '(';
    private static final char closeBrace = ')';
    private static final char openSquareBrace = '{';
    private static final char closeSquareBrace = '}';

    private String sourceCode;
    private long sourceCodeLength;
    private GeneralServiceImpl general_Utils;
    private HashMap<Integer, Integer> lineScore;

    public CTCServiceImpl(String filePath) {
        general_Utils = new GeneralServiceImpl();
        this.sourceCode = general_Utils.getSourceCode(filePath);
        this.sourceCodeLength = general_Utils.getSourceCodeLength();
        this.lineScore = general_Utils.getLineHashMap();
    }

    //calculate score for if conditions and their logical operators
    public int getControlScore() {
        int ifStartIndex, ifEndIndex = 0, ifCount = 0, logicalCount = 0, lineNo, logicalCountTemp;
        do {
            ifStartIndex = searchNextIf(ifEndIndex);
            if (ifStartIndex != 1) {
                ifEndIndex = getLastRoundClosingBrace(ifStartIndex);
                if (ifEndIndex != -1) {
                    ++ifCount;
                    lineNo = general_Utils.getFormattedLineByIndex(ifStartIndex - 1);
                    lineScore.put(lineNo, (lineScore.get(lineNo) + 1));
                    logicalCountTemp = general_Utils.getLogicalCount(sourceCode.substring(ifStartIndex, ifEndIndex + 1));
                    if (logicalCountTemp > 0) {
                        lineNo = general_Utils.getFormattedLineByIndex(ifStartIndex - 1);
                        lineScore.put(lineNo, (lineScore.get(lineNo) + logicalCountTemp));
                    }
                    logicalCount += logicalCountTemp;
                }
            } else
                break;
        } while (ifEndIndex < sourceCodeLength);

        System.out.println("If Count                         : " + ifCount + " Logical Operator Count         : " + logicalCount);
        return (ifCount + logicalCount);
    }

    //calculate score for for loops and their logical operators
    private int getIterativeForScore() {
        int forStartIndex, forEndIndex = 0, forCount = 0, logicalCount = 0, lineNo, logicalCountTemp;
        do {
            forStartIndex = searchNextFor(forEndIndex);
            if (forStartIndex != 2) {
                forEndIndex = getLastRoundClosingBrace(forStartIndex);
                if (forEndIndex != -1) {
                    ++forCount;
                    lineNo = general_Utils.getFormattedLineByIndex(forStartIndex - 2);
                    lineScore.put(lineNo, (lineScore.get(lineNo) + 1));
                    logicalCountTemp = general_Utils.getLogicalCount(sourceCode.substring(forStartIndex, forEndIndex));
                    if (logicalCountTemp > 0) {
                        lineNo = general_Utils.getFormattedLineByIndex(forStartIndex - 2);
                        lineScore.put(lineNo, (lineScore.get(lineNo) + logicalCountTemp));
                    }
                    logicalCount += logicalCountTemp;
                }
            } else
                break;
        } while (true);
        System.out.println("Iterative Operator Count (For)   : " + forCount + " Logical Operator Count (For)   : " + logicalCount);
        return (forCount + logicalCount) * 2;
    }

    //calculate score for do, do-while loops and their logical operators
    private int getIterativeWhileScore() {
        int whileStartIndex, whileEndIndex = 0, whileCount = 0, logicalCount = 0, lineNo, logicalCountTemp;
        do {
            whileStartIndex = searchNextWhile(whileEndIndex);
            if (whileStartIndex != 4) {
                whileEndIndex = getLastRoundClosingBrace(whileStartIndex);
                if (whileEndIndex != -1) {
                    ++whileCount;
                    lineNo = general_Utils.getFormattedLineByIndex(whileStartIndex - 4);
                    lineScore.put(lineNo, (lineScore.get(lineNo) + 1));
                    logicalCountTemp = general_Utils.getLogicalCount(sourceCode.substring(whileStartIndex, whileEndIndex));
                    if (logicalCountTemp > 0) {
                        lineNo = general_Utils.getFormattedLineByIndex(whileStartIndex - 4);
                        lineScore.put(lineNo, (lineScore.get(lineNo) + logicalCountTemp));
                    }
                    logicalCount += logicalCountTemp;
                }
            } else
                break;
        } while (true);
        System.out.println("Iterative Operator Count (While) : " + whileCount + " Logical Operator Count (While) : " + logicalCount);
        return ((whileCount + logicalCount) * 2);
    }

    //returns value of both for and while loops
    public int getIterativeControlScore() {
        return (this.getIterativeForScore() + this.getIterativeWhileScore());
    }

    //calculate score of catch statements
    public int getCatchScore() {
        int forStartIndex, forEndIndex = 0, catchCount = 0, lineNo;
        do {
            forStartIndex = sourceCode.indexOf("catch", forEndIndex) + 5;
            if (forStartIndex == 4)
                break;
            ++catchCount;
            lineNo = general_Utils.getFormattedLineByIndex(forStartIndex - 4);
            lineScore.put(lineNo, (lineScore.get(lineNo) + 1));
            forEndIndex = forStartIndex;
        } while (true);
        System.out.println("Catch Count                      : " + catchCount);
        return (catchCount);
    }

    //calculate score of switch statements
    public int getSwitchScore() {
        int switchStartIndex, switchEndIndex = 0, caseCount = 0, lineNo;
        do {
            switchStartIndex = searchNextSwitch(switchEndIndex);
            if (switchStartIndex != 5) {
                switchEndIndex = getLastClosingCurlyBrace(switchStartIndex);
                if (switchEndIndex != -1) {
                    String subString = sourceCode.substring(switchStartIndex, switchEndIndex);
                    if ((subString.contains("case"))) {
                        int caseOccourences = general_Utils.countOccurences(subString, searchCase);
                        caseCount += caseOccourences;
                        lineNo = general_Utils.getFormattedLineByIndex(switchStartIndex - 5);
                        lineScore.put(lineNo, (lineScore.get(lineNo) + caseCount));
                    }
                }
            } else
                break;
        } while (true);
        System.out.println("Switch Cases Count               : " + caseCount);
        return (caseCount);
    }

    //returns the index of next switch
    private int searchNextSwitch(int forEndIndex) {
        int switchStartIndex = sourceCode.indexOf(searchSwitch, forEndIndex) + 6;
        if (sourceCode.charAt(switchStartIndex) == ' ' || sourceCode.charAt(switchStartIndex) == '(')
            return switchStartIndex;
        else
            return 5;
    }

    //returns the index of next if
    private int searchNextIf(int ifEndIndex) {
        int ifStartIndex = sourceCode.indexOf(searchIf, ifEndIndex) + 2;
        if (sourceCode.charAt(ifStartIndex) == ' ' || sourceCode.charAt(ifStartIndex) == '(')
            return ifStartIndex;
        else
            return 1;
    }

    //returns the index of next for
    private int searchNextFor(int forEndIndex) {
        int forStartIndex = sourceCode.indexOf(searchFor, forEndIndex) + 3;
        if (sourceCode.charAt(forStartIndex) == ' ' || sourceCode.charAt(forStartIndex) == '(')
            return forStartIndex;
        else
            return 2;
    }

    //returns the index of next while
    private int searchNextWhile(int forEndIndex) {
        int whileStartIndex = sourceCode.indexOf(searchWhile, forEndIndex) + 5;
        if (sourceCode.charAt(whileStartIndex) == ' ' || sourceCode.charAt(whileStartIndex) == '(')
            return whileStartIndex;
        else
            return 4;
    }

    //Function returns the last closing curly braces of a parenthesis
    private int getLastClosingCurlyBrace(int startIndex) {
        int indexTemp = startIndex;
        char charTemp;
        Stack s1 = new Stack(stackSize);
        while (indexTemp < sourceCodeLength) {
            charTemp = sourceCode.charAt(indexTemp);
            if (charTemp == openSquareBrace)
                s1.push(openSquareBrace);
            else if (charTemp == closeSquareBrace) {
                if (!s1.isEmpty()) {
                    s1.pop();
                    if (s1.isEmpty()) {
                        return (indexTemp + 1);
                    }
                } else {
                    return -1;
                }
            }
            ++indexTemp;
        }
        return -1;
    }

    //Function returns the last closing round braces of a parenthesis
    private int getLastRoundClosingBrace(int startIndex) {
        int indexTemp = startIndex;
        char charTemp;
        Stack s1 = new Stack(stackSize);
        while (indexTemp < sourceCodeLength) {
            charTemp = sourceCode.charAt(indexTemp);
            if (charTemp == openBrace)
                s1.push(openBrace);
            else if (charTemp == closeBrace) {
                if (!s1.isEmpty()) {
                    s1.pop();
                    if (s1.isEmpty()) {
                        return (indexTemp + 1);
                    }
                } else {
                    return -1;
                }
            }
            ++indexTemp;
        }
        return -1;
    }

    public HashMap<Integer, Integer> getLineScore() {
        return lineScore;
    }
}
