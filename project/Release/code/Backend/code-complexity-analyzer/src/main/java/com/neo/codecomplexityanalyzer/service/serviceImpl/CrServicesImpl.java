package com.neo.codecomplexityanalyzer.service.serviceImpl;

public class CrServicesImpl {

	private GeneralServiceImpl general_Utils;
	private String sourceCode;
	private long sourceCodeLength;
	private String originalSourceCode;

	private static final int stackSize = 100;
	private static final String searchPublic = "public";
	private static final char openSquareBrace = '{';
	private static final char closeSquareBrace = '}';
	private static final char openBrace = '(';
	private static final String errorMessage2 = "Missing ) , not properly ended !";

	public CrServicesImpl(String filePath) {
		general_Utils = new GeneralServiceImpl();
		this.sourceCode = general_Utils.getSourceCode(filePath);
		this.sourceCodeLength = general_Utils.getSourceCodeLength();
		this.originalSourceCode = general_Utils.getOriginalSourceCode();
	}

	public int getControlScore() {
		int publicStartIndex, endIndex = 0, methodNameStartIndex = 0, recursionCount = 0;
		String methodName = "";
		do {
			publicStartIndex = sourceCode.indexOf(searchPublic, endIndex) + 7;
			if (publicStartIndex == 6)
				break;
			else {
				methodNameStartIndex = sourceCode.indexOf(openBrace, publicStartIndex);
				if (methodNameStartIndex != -1) {
					methodName = sourceCode.substring(publicStartIndex, methodNameStartIndex);
					int i;
					for (i = methodName.length() - 1; i >= 0; --i) {
						if (methodName.charAt(i) == ' ') {
							break;
						}
					}
					methodName = methodName.substring(i);
				}

				publicStartIndex = sourceCode.indexOf(openSquareBrace, publicStartIndex);
				if (publicStartIndex == -1)
					break;
			}

			int indexTemp = publicStartIndex - 1;
			Stack s1 = new Stack(stackSize);
			while (true) {
				if (indexTemp >= sourceCodeLength) {
					System.out.println(errorMessage2);
					return -1;
				}
				char strTemp = sourceCode.charAt(indexTemp);
				if (strTemp == openSquareBrace)
					s1.push(openSquareBrace);
				else if (strTemp == closeSquareBrace) {
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
			endIndex = indexTemp;
			String subString = sourceCode.substring(publicStartIndex, endIndex);
			
			recursionCount += general_Utils.countRecursiveOccurrences(subString, methodName);

			
		} while (true);
		System.out.println("Recursion Count : " + recursionCount);
		return (recursionCount);
	}
}
