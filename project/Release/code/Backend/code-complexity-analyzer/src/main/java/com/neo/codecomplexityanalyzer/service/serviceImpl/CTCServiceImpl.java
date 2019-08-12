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


public class CTCServiceImpl{
    private static final int stackSize = 100;
    private static final String errorMessage1 = "Missing } , not properly ended !";
    private static final String errorMessage2 = "Missing ) , not properly ended !";
    private static final String searchIf    = "if";
    private static final String searchFor   = "for";
    private static final String searchWhile = "while";
    private static final String searchSwitch = "switch";
    private static final String searchCase = "case";


    private static final char openBrace  = '(';
    private static final char closeBrace = ')';
    private static final char openSquareBrace  = '{';
    private static final char closeSquareBrace = '}';

    private String sourceCode;
    private String originalSourceCode;
    private long sourceCodeLength;
    private GeneralServiceImpl general_Utils;

    public CTCServiceImpl(String filePath) {
        general_Utils = new GeneralServiceImpl();
        this.sourceCode = general_Utils.getSourceCode(filePath);
        this.sourceCodeLength = general_Utils.getSourceCodeLength();
        this.originalSourceCode = general_Utils.getOriginalSourceCode();
    }

    public String getSourceCode(){
        return this.sourceCode;
    }

    public String getOriginalSourceCode() {
        return originalSourceCode;
    }

    public int getControlScore() {
        int ifStartIndex, ifEndIndex = 0, ifCount = 0, logicalCount = 0;
        do {
            ifStartIndex = sourceCode.indexOf(searchIf, ifEndIndex) + 3;
            if (ifStartIndex == 2)
                break;
            ++ifCount;
            int indexTemp = ifStartIndex;
            Stack s1 = new Stack(stackSize);
            while (true) {
                if (indexTemp >= sourceCodeLength) {
                    System.out.println(errorMessage2);
                    return -1;
                }
                char strTemp = sourceCode.charAt(indexTemp);
                if (strTemp == openBrace)
                    s1.push(openBrace);
                else if (strTemp == closeBrace) {
                    if (!s1.isEmpty()) {
                        s1.pop();
                        if (s1.isEmpty())
                            break;
                    } else {
                        System.out.println(errorMessage2);
                        return -1;
                    }
                }
                ++indexTemp;
            }
            ifEndIndex = indexTemp;
            String subString = sourceCode.substring(ifStartIndex, ifEndIndex);
            logicalCount += general_Utils.getLogicalCount(subString);
        } while (true);
        System.out.println("If Count                         : " + ifCount + " Logical Operator Count         : " + logicalCount);
        return (ifCount + logicalCount);
    }

    private int getIterativeForScore() {
        int forStartIndex, forEndIndex = 0, forCount = 0, logicalCount = 0;
        do {
            forStartIndex = sourceCode.indexOf(searchFor, forEndIndex) + 4;
            if (forStartIndex == 3)
                break;
            ++forCount;
            int indexTemp = forStartIndex;
            Stack s1 = new Stack(stackSize);
            while (true) {
                if (indexTemp >= sourceCodeLength) {
                    System.out.println(errorMessage2);
                    return -1;
                }
                char strTemp = sourceCode.charAt(indexTemp);
                if (strTemp == openBrace)
                    s1.push(openBrace);
                else if ((strTemp == closeBrace)) {
                    if (!s1.isEmpty()) {
                        s1.pop();
                        if (s1.isEmpty())
                            break;
                    } else {
                        System.out.println(errorMessage2);
                        return -1;
                    }
                }
                ++indexTemp;
            }
            forEndIndex = indexTemp;
            String subString = sourceCode.substring(forStartIndex, forEndIndex);
            logicalCount += general_Utils.getLogicalCount(subString);
        } while (true);
        System.out.println("Iterative Operator Count (For)   : " + forCount + " Logical Operator Count (For)   : " + logicalCount);
        return (forCount + logicalCount) * 2;
    }

    private int getIterativeWhileScore() {
        int forStartIndex, forEndIndex = 0, whileCount = 0, logicalCount = 0;
        do {
            forStartIndex = sourceCode.indexOf(searchWhile, forEndIndex) + 5;
            if (forStartIndex == 4)
                break;
            ++whileCount;
            int indexTemp = forStartIndex;
            Stack s1 = new Stack(stackSize);
            while (true) {
                if (indexTemp >= sourceCodeLength) {
                    System.out.println(errorMessage2);
                    return -1;
                }
                char strTemp = sourceCode.charAt(indexTemp);
                if (strTemp == openBrace)
                    s1.push(openBrace);
                else if ((strTemp == closeBrace)) {
                    if (!s1.isEmpty()) {
                        s1.pop();
                        if (s1.isEmpty())
                            break;
                    } else {
                        System.out.println(errorMessage2);
                        return -1;
                    }
                }
                ++indexTemp;
            }
            forEndIndex = indexTemp;
            String subString = sourceCode.substring(forStartIndex, forEndIndex);
            logicalCount += general_Utils.getLogicalCount(subString);
        } while (true);
        System.out.println("Iterative Operator Count (While) : " + whileCount + " Logical Operator Count (While) : " + logicalCount);
        return ((whileCount + logicalCount) * 2);
    }

    public int getIterativeControlScore() {
        return (this.getIterativeForScore() + this.getIterativeWhileScore());
    }

    public int getCatchScore() {
        int forStartIndex, forEndIndex = 0, catchCount = 0;
        do {
            forStartIndex = sourceCode.indexOf("catch", forEndIndex) + 6;
            if (forStartIndex == 5)
                break;
            ++catchCount;
            forEndIndex = forStartIndex;
        } while (true);
        System.out.println("Catch Count                      : " + catchCount);
        return (catchCount);
    }

    public int getSwitchScore() {
        int forStartIndex, forEndIndex = 0, caseCount = 0;
        do {
            forStartIndex = sourceCode.indexOf(searchSwitch, forEndIndex) + 7;
            if (forStartIndex == 6)
                break;
            int indexTemp = forStartIndex;
            Stack s1 = new Stack(stackSize);
            while (true) {
                if (indexTemp >= sourceCodeLength) {
                    System.out.println(errorMessage1);
                    return -1;
                }
                char strTemp = sourceCode.charAt(indexTemp);
                if (strTemp == openSquareBrace)
                    s1.push(openSquareBrace);
                else if ((strTemp == closeSquareBrace)) {
                    if (!s1.isEmpty()) {
                        s1.pop();
                        if (s1.isEmpty())
                            break;
                    } else {
                        System.out.println(errorMessage1);
                        return -1;
                    }
                }
                ++indexTemp;
            }
            forEndIndex = indexTemp;
            String subString = sourceCode.substring(forStartIndex, forEndIndex);
            if ((subString.contains("case"))) {
                int caseOccourences = general_Utils.countOccurences(subString, searchCase);
                caseCount += caseOccourences;
            }
        } while (true);
        System.out.println("Switch Cases Count               : " + caseCount);
        return (caseCount);
    }
}
