package com.neo.codecomplexityanalyzer.service.serviceImpl;

public class CTC_Utilities {
    private String sourceCode;
    private General_Utilities general_Utils;

    public CTC_Utilities(String filePath) {
        general_Utils = new General_Utilities();
        this.sourceCode = general_Utils.getSourceCode(filePath);
    }

    public int getControlScore() {
        int ifEndIndex = 0, ifStartIndex, ifCount = 0, logicalCount = 0;
        do {
            ifStartIndex = sourceCode.indexOf("if", ifEndIndex) + 3;
            if (ifStartIndex == 2)
                break;
            ++ifCount;
            int startIndexTemp = ifStartIndex;
            Stack s1 = new Stack(100);
            while (true) {
                char strTemp = sourceCode.charAt(startIndexTemp);
                if (strTemp == '(')
                    s1.push("(");
                else if (strTemp == ')' && !s1.isEmpty()) {
                    s1.pop();
                } else if (s1.isEmpty()) {
                    break;
                }
                ++startIndexTemp;
            }
            ifEndIndex = startIndexTemp;
            String subString = sourceCode.substring(ifStartIndex, ifEndIndex);
            if ((subString.contains("&&") || subString.contains("||") || subString.contains("&") || subString.contains("|"))) {
                int andOccourences = General_Utilities.countOccurences(subString, "&&");
                int orOccourences = General_Utilities.countOccurences(subString, "||");
                int bitAndOccourences = General_Utilities.countOccurences(subString, "&");
                int bitOrOccourences = General_Utilities.countOccurences(subString, "|");
                logicalCount += andOccourences + orOccourences + bitAndOccourences + bitOrOccourences;
            }
        } while (true);
        System.out.println("If Count : " + ifCount + " Logical Count : " + logicalCount);
        final int totalScore = ifCount + logicalCount;
        return totalScore;
    }


//	public int getControlStructCount() {
//		BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(this.sourceCode));
//		String str;
//		int ControlStructCount = 0;
//		try {
//			for (int i = 0; ((str = sourceCodeTemp.readLine()) != null); ++i) {
//				if (str.contains("for") || str.contains("while") || str.contains("do-while")) {
//					++ControlStructCount;
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return ControlStructCount;
//	}
//
//	public int getCatchCount() {
//		BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(this.sourceCode));
//		String str;
//		int catchCount = 0;
//		try {
//			for (; ((str = sourceCodeTemp.readLine()) != null);) {
//				if (str.contains("catch")) {
//					++catchCount;
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return catchCount;
//	}
//
//	public int getSwitchCount() {
//		BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(this.sourceCode));
//		String str;
//		int switchCount = 0;
//		try {
//			for (int i = 0; ((str = sourceCodeTemp.readLine()) != null); ++i) {
//				if (str.contains("switch")) {
//					++switchCount;
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return switchCount;
//	}
}
