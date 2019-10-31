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


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class CTCServiceImpl {
    private static final Logger logger = LogManager.getLogger(CTCServiceImpl.class);

    private static final int STACK_SIZE = 100;

    private static final String SEARCH_IF = "if";
    private static final String SEARCH_FOR = "for";
    private static final String SEARCH_WHILE = "while";
    private static final String SEARCH_SWITCH = "switch";
    private static final String SEARCH_CASE = "case";

    private static final char OPEN_BRACE = '(';
    private static final char CLOSE_BRACE = ')';
    private static final char OPEN_SQUARE_BRACE = '{';
    private static final char CLOSE_SQUARE_BRACE = '}';

    private String sourceCode;
    private long sourceCodeLength;
    private GeneralServiceImpl generalUtils;
    private HashMap<Integer, Integer> lineScore;

    public CTCServiceImpl(String filePath) {
        generalUtils = new GeneralServiceImpl();
        this.sourceCode = generalUtils.getSourceCode(filePath);
        this.sourceCodeLength = generalUtils.getSourceCodeLength();
        this.lineScore = generalUtils.getLineHashMap();
    }

    //calculate score for if conditions and their logical operators
    public int getControlScore() {
        int ifStartIndex;
        int lineNo;
        int logicalCountTemp;
        int ifEndIndex = 0;
        int ifCount = 0;
        int logicalCount = 0;
        do {
            ifStartIndex = searchNextIf(ifEndIndex);
            if (ifStartIndex != 1) {
                ifEndIndex = getLastRoundClosingBrace(ifStartIndex);
                if (ifEndIndex != -1) {
                    ++ifCount;
                    lineNo = generalUtils.getFormattedLineByIndex(ifStartIndex - 1);
                    lineScore.put(lineNo, (lineScore.get(lineNo) + 1));
                    logicalCountTemp = generalUtils.getLogicalCount(sourceCode.substring(ifStartIndex, ifEndIndex + 1));
                    if (logicalCountTemp > 0) {
                        lineNo = generalUtils.getFormattedLineByIndex(ifStartIndex - 1);
                        lineScore.put(lineNo, (lineScore.get(lineNo) + logicalCountTemp));
                    }
                    logicalCount += logicalCountTemp;
                }
            } else
                break;
        } while (ifEndIndex < sourceCodeLength);
        logger.info("If Count                         : {} Logical Operator Count         : {}",  ifCount , logicalCount);
        return (ifCount + logicalCount);
    }

    //calculate score for for loops and their logical operators
    private int getIterativeForScore() {
        int forStartIndex;
        int lineNo;
        int logicalCountTemp;
        int forEndIndex = 0;
        int forCount = 0;
        int logicalCount = 0;
        do {
            forStartIndex = searchNextFor(forEndIndex);
            if (forStartIndex != 2) {
                forEndIndex = getLastRoundClosingBrace(forStartIndex);
                if (forEndIndex != -1) {
                    ++forCount;
                    lineNo = generalUtils.getFormattedLineByIndex(forStartIndex - 2);
                    lineScore.put(lineNo, (lineScore.get(lineNo) + 2));
                    logicalCountTemp = generalUtils.getLogicalCount(sourceCode.substring(forStartIndex, forEndIndex));
                    if (logicalCountTemp > 0) {
                        lineNo = generalUtils.getFormattedLineByIndex(forStartIndex - 2);
                        lineScore.put(lineNo, (lineScore.get(lineNo) + logicalCountTemp));
                    }
                    logicalCount += logicalCountTemp;
                }
            } else
                break;
        } while (true);
        logger.info("Iterative Operator Count (For)   : {}  Logical Operator Count (For)   : {}",  forCount , logicalCount);
        return (forCount + logicalCount) * 2;
    }

    //calculate score for do, do-while loops and their logical operators
    private int getIterativeWhileScore() {
        int whileStartIndex;
        int lineNo;
        int logicalCountTemp;
        int whileEndIndex = 0;
        int whileCount = 0;
        int logicalCount = 0;
        do {
            whileStartIndex = searchNextWhile(whileEndIndex);
            if (whileStartIndex != 4) {
                whileEndIndex = getLastRoundClosingBrace(whileStartIndex);
                if (whileEndIndex != -1) {
                    ++whileCount;
                    lineNo = generalUtils.getFormattedLineByIndex(whileStartIndex - 4);
                    lineScore.put(lineNo, (lineScore.get(lineNo) + 2));
                    logicalCountTemp = generalUtils.getLogicalCount(sourceCode.substring(whileStartIndex, whileEndIndex));
                    if (logicalCountTemp > 0) {
                        lineNo = generalUtils.getFormattedLineByIndex(whileStartIndex - 4);
                        lineScore.put(lineNo, (lineScore.get(lineNo) + logicalCountTemp));
                    }
                    logicalCount += logicalCountTemp;
                }
            } else
                break;
        } while (true);
        logger.info("Iterative Operator Count (While) : {} Logical Operator Count (While) : {}",  whileCount , logicalCount);
        return ((whileCount + logicalCount) * 2);
    }

    //returns value of both for and while loops
    public int getIterativeControlScore() {
        return (this.getIterativeForScore() + this.getIterativeWhileScore());
    }

    //calculate score of catch statements
    public int getCatchScore() {
        int forStartIndex;
        int lineNo;
        int forEndIndex = 0;
        int catchCount = 0;
        do {
            forStartIndex = sourceCode.indexOf("catch", forEndIndex) + 5;
            if (forStartIndex == 4)
                break;
            ++catchCount;
            lineNo = generalUtils.getFormattedLineByIndex(forStartIndex - 4);
            lineScore.put(lineNo, (lineScore.get(lineNo) + 1));
            forEndIndex = forStartIndex;
        } while (true);
        logger.info("Catch Count                      : {}",  catchCount);
        return (catchCount);
    }

    //calculate score of switch statements
    public int getSwitchScore() {
        int switchStartIndex;
        int lineNo;
        int switchEndIndex = 0;
        int caseCount = 0;
        do {
            switchStartIndex = searchNextSwitch(switchEndIndex);
            if (switchStartIndex != 5) {
                switchEndIndex = getLastClosingCurlyBrace(switchStartIndex);
                if (switchEndIndex != -1) {
                    String subString = sourceCode.substring(switchStartIndex, switchEndIndex);
                    if ((subString.contains("case"))) {
                        int caseOccourences = generalUtils.countOccurences(subString, SEARCH_CASE);
                        caseCount += caseOccourences;
                        lineNo = generalUtils.getFormattedLineByIndex(switchStartIndex - 5);
                        lineScore.put(lineNo, (lineScore.get(lineNo) + caseCount));
                    }
                }
            } else
                break;
        } while (true);
        logger.info("Switch Cases Count               : {}",  caseCount);
        return (caseCount);
    }

    //returns the index of next switch
    private int searchNextSwitch(int forEndIndex) {
        int switchStartIndex = sourceCode.indexOf(SEARCH_SWITCH, forEndIndex) + 6;
        if (sourceCode.charAt(switchStartIndex) == ' ' || sourceCode.charAt(switchStartIndex) == '(')
            return switchStartIndex;
        else
            return 5;
    }

    //returns the index of next if
    private int searchNextIf(int ifEndIndex) {
        int ifStartIndex = sourceCode.indexOf(SEARCH_IF, ifEndIndex) + 2;
        if (sourceCode.charAt(ifStartIndex) == ' ' || sourceCode.charAt(ifStartIndex) == '(')
            return ifStartIndex;
        else
            return 1;
    }

    //returns the index of next for
    private int searchNextFor(int forEndIndex) {
        int forStartIndex = sourceCode.indexOf(SEARCH_FOR, forEndIndex) + 3;
        if (sourceCode.charAt(forStartIndex) == ' ' || sourceCode.charAt(forStartIndex) == '(')
            return forStartIndex;
        else
            return 2;
    }

    //returns the index of next while
    private int searchNextWhile(int forEndIndex) {
        int whileStartIndex = sourceCode.indexOf(SEARCH_WHILE, forEndIndex) + 5;
        if (sourceCode.charAt(whileStartIndex) == ' ' || sourceCode.charAt(whileStartIndex) == '(')
            return whileStartIndex;
        else
            return 4;
    }

    //Function returns the last closing curly braces of a parenthesis
    private int getLastClosingCurlyBrace(int startIndex) {
        int indexTemp = startIndex;
        char charTemp;
        Stack s1 = new Stack(STACK_SIZE);
        while (indexTemp < sourceCodeLength) {
            charTemp = sourceCode.charAt(indexTemp);
            if (charTemp == OPEN_SQUARE_BRACE)
                s1.push(OPEN_SQUARE_BRACE);
            else if (charTemp == CLOSE_SQUARE_BRACE) {
                if (Boolean.FALSE.equals(s1.isEmpty())) {
                    s1.pop();
                    if (Boolean.TRUE.equals(s1.isEmpty())) {
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
        Stack s1 = new Stack(STACK_SIZE);
        while (indexTemp < sourceCodeLength) {
            charTemp = sourceCode.charAt(indexTemp);
            if (charTemp == OPEN_BRACE)
                s1.push(OPEN_BRACE);
            else if (charTemp == CLOSE_BRACE) {
                if (Boolean.FALSE.equals(s1.isEmpty())) {
                    s1.pop();
                    if (Boolean.TRUE.equals(s1.isEmpty())) {
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
