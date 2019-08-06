package com.neo.codecomplexityanalyzer.service.serviceImpl;

public class CTC_Utilities {
    private String sourceCode;
    private General_Utilities general_Utils;

    public CTC_Utilities(String filePath) {
        this.sourceCode = new General_Utilities().getSourceCode(filePath);
    }

    public int getControlScore() {
        int classNameStartIndex = 0, classNameEndIndex = 0, ifCount = 0, logicalCount = 0;
        String subString;
        char strTemp;
        int startTemp;
        while (true) {
            startTemp = 0;
            classNameStartIndex = sourceCode.indexOf("if", classNameEndIndex) + 3;
            if (classNameStartIndex == 2)
                break;
            ++ifCount;
            startTemp = classNameStartIndex;
            Stack s1 = new Stack(100);
            while (true) {
                strTemp = sourceCode.charAt(startTemp);
                if (strTemp == '(')
                    s1.push("(");
                else if (strTemp == ')' && !s1.isEmpty()) {
                    s1.pop();
                } else if (s1.isEmpty()) {
                    break;
                }
                ++startTemp;
            }
            classNameEndIndex = startTemp;
            subString = sourceCode.substring(classNameStartIndex, classNameEndIndex);
            if ((subString.contains("&&") || subString.contains("||") || subString.contains("&") || subString.contains("|"))) {
                int andOccourences = General_Utilities.countOccurences(subString, "&&");
                int orOccourences = General_Utilities.countOccurences(subString, "||");
                int bitAndOccourences = General_Utilities.countOccurences(subString, "&");
                int bitOrOccourences = General_Utilities.countOccurences(subString, "|");
                logicalCount += andOccourences + orOccourences + bitAndOccourences + bitOrOccourences;
            }
        }
        System.out.println("If Count : " + ifCount + "Logical Count : " + logicalCount);
        return (ifCount + logicalCount);
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
