package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

public class CrServicesImpl {

	private GeneralServiceImpl general_Utils;
	private String sourceCode;
	private long sourceCodeLength;
	private String originalSourceCode;
//	private int recursionCountCpp;

	private static final int stackSize = 100;
	private static final String searchPublic = "public";
	private static final char openSquareBrace = '{';
	private static final char closeSquareBrace = '}';
	private static final char openBrace = '(';
	private static final String errorMessage2 = "Missing ) , not properly ended !";
	
	private HashMap<Integer, Integer> linesOfRecursionMethod;
	private HashMap<Integer, Integer> linesOfRecursionMethodForJava;
	private HashMap<Integer, Integer> linesOfRecursionMethodForCpp;

	public CrServicesImpl(String filePath) {
		general_Utils = new GeneralServiceImpl();
		this.sourceCode = general_Utils.getSourceCode(filePath);
		this.sourceCodeLength = general_Utils.getSourceCodeLength();
		this.originalSourceCode = general_Utils.getOriginalSourceCode();
		this.linesOfRecursionMethod = new HashMap<Integer, Integer>();
		this.linesOfRecursionMethodForCpp = general_Utils.getLineHashMap();
		this.linesOfRecursionMethodForJava = general_Utils.getLineHashMap();
	}

	public HashMap<Integer, Integer>/*int*/ getControlScore() {
		int publicStartIndex, endIndex = 0, methodNameStartIndex = 0, recursionCount = 0, startLine,endLine,tempRecursiveCount;
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
//					return -1;
					return null;
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
//						return -1;
						return null;
					}
				}
				++indexTemp;
			}
			endIndex = indexTemp;
			String subString = sourceCode.substring(publicStartIndex, endIndex);
			tempRecursiveCount = recursionCount;
//			startLine = general_Utils.getFormattedLineByIndex(publicStartIndex);
//			endLine = general_Utils.getFormattedLineByIndex(endIndex);
			recursionCount += general_Utils.countRecursiveOccurrences(subString, methodName);
//			linesOfRecursionMethod.put(startLine, endLine);
			
			if(tempRecursiveCount<recursionCount) {
				startLine = general_Utils.getFormattedLineByIndex(publicStartIndex);
				endLine = general_Utils.getFormattedLineByIndex(endIndex+1);
//				linesOfRecursionMethod.put(startLine, endLine);
				ArrayList<Integer> listOfLine = this.getLineNumbersBy(publicStartIndex, endIndex);
				for(int Lno:listOfLine) {
					linesOfRecursionMethodForJava.put(Lno, 1);
				}
//				linesOfRecursionMethodForJava.put(key, value)
				
			}
			System.out.println(subString);
			System.out.println("*****************************************************************************************************************");
			
		} while (true);
		System.out.println("Recursion Count : " + recursionCount);
		return (linesOfRecursionMethodForJava);
//		return (recursionCount);
	}
	
	public HashMap<Integer, Integer>/*int*/ getControlScoreInCpp() {
		int typeStartIndex, endIndex = 0, methodNameStartIndex = 0, recursionCountCpp = 0, startLine,endLine,lineNo;
		String methodName = "", methStringLine = "";
		do {
//			typeStartIndex = sourceCode.indexOf(searchPublic, endIndex) + 7;
			methodNameStartIndex = sourceCode.indexOf(openBrace, endIndex);
			lineNo = general_Utils.getFormattedLineByIndex(methodNameStartIndex);
			methStringLine = this.getLineAsString(lineNo);
			System.out.println("code line 102 : "+methStringLine);
			if(methStringLine!=null) {
				
				if((methStringLine.contains("int")) && (methStringLine.contains("(")) && ((methStringLine.contains("{")||this.getLineAsString(lineNo+1).contains("{")))) {
					typeStartIndex = sourceCode.indexOf("int", endIndex) + 3;
					if (typeStartIndex == 2)
						break;
					else {
						methodNameStartIndex = sourceCode.indexOf(openBrace, typeStartIndex);
						if (methodNameStartIndex != -1) {
							methodName = sourceCode.substring(typeStartIndex, methodNameStartIndex);
							System.out.println("code line 113 : methode name = "+ methodName);
							/*int i;
							for (i = methodName.length() - 1; i >= 0; --i) {
								if (methodName.charAt(i) == ' ') {
									break;
								}
							}
							methodName = methodName.substring(i);*/
						}
	
						typeStartIndex = sourceCode.indexOf(openSquareBrace, typeStartIndex);
						if (typeStartIndex == -1)
							break;
					}
				}else if((methStringLine.contains("double")) && (methStringLine.contains("(")) && ((methStringLine.contains("{")||this.getLineAsString(lineNo+1).contains("{")))) {
					typeStartIndex = sourceCode.indexOf("double", endIndex) + 6;
					if (typeStartIndex == 5)
						break;
					else {
						methodNameStartIndex = sourceCode.indexOf(openBrace, typeStartIndex);
						if (methodNameStartIndex != -1) {
							methodName = sourceCode.substring(typeStartIndex, methodNameStartIndex);
							System.out.println("code line 135 : methode name = "+ methodName);
							/*int i;
							for (i = methodName.length() - 1; i >= 0; --i) {
								if (methodName.charAt(i) == ' ') {
									break;
								}
							}
							methodName = methodName.substring(i);*/
						}
	
						typeStartIndex = sourceCode.indexOf(openSquareBrace, typeStartIndex);
						if (typeStartIndex == -1)
							break;
					}
				}else if((methStringLine.contains("float")) && (methStringLine.contains("(")) && ((methStringLine.contains("{")||this.getLineAsString(lineNo+1).contains("{")))) {
					typeStartIndex = sourceCode.indexOf("float", endIndex) + 5;
					if (typeStartIndex == 4)
						break;
					else {
						methodNameStartIndex = sourceCode.indexOf(openBrace, typeStartIndex);
						if (methodNameStartIndex != -1) {
							methodName = sourceCode.substring(typeStartIndex, methodNameStartIndex);
							System.out.println("code line 135 : methode name = "+ methodName);
							/*int i;
							for (i = methodName.length() - 1; i >= 0; --i) {
								if (methodName.charAt(i) == ' ') {
									break;
								}
							}
							methodName = methodName.substring(i);*/
						}
	
						typeStartIndex = sourceCode.indexOf(openSquareBrace, typeStartIndex);
						if (typeStartIndex == -1)
							break;
					}
				}else if((methStringLine.contains("bool")) && (methStringLine.contains("(")) && ((methStringLine.contains("{")||this.getLineAsString(lineNo+1).contains("{")))) {
					typeStartIndex = sourceCode.indexOf("bool", endIndex) + 4;
					if (typeStartIndex == 3)
						break;
					else {
						methodNameStartIndex = sourceCode.indexOf(openBrace, typeStartIndex);
						if (methodNameStartIndex != -1) {
							methodName = sourceCode.substring(typeStartIndex, methodNameStartIndex);
							System.out.println("code line 135 : methode name = "+ methodName);
							/*int i;
							for (i = methodName.length() - 1; i >= 0; --i) {
								if (methodName.charAt(i) == ' ') {
									break;
								}
							}
							methodName = methodName.substring(i);*/
						}
	
						typeStartIndex = sourceCode.indexOf(openSquareBrace, typeStartIndex);
						if (typeStartIndex == -1)
							break;
					}
				}else if((methStringLine.contains("wchar_t")) && (methStringLine.contains("(")) && ((methStringLine.contains("{")||this.getLineAsString(lineNo+1).contains("{")))) {
					typeStartIndex = sourceCode.indexOf("wchar_t", endIndex) + 7;
					if (typeStartIndex == 6)
						break;
					else {
						methodNameStartIndex = sourceCode.indexOf(openBrace, typeStartIndex);
						if (methodNameStartIndex != -1) {
							methodName = sourceCode.substring(typeStartIndex, methodNameStartIndex);
							System.out.println("code line 135 : methode name = "+ methodName);
							/*int i;
							for (i = methodName.length() - 1; i >= 0; --i) {
								if (methodName.charAt(i) == ' ') {
									break;
								}
							}
							methodName = methodName.substring(i);*/
						}
	
						typeStartIndex = sourceCode.indexOf(openSquareBrace, typeStartIndex);
						if (typeStartIndex == -1)
							break;
					}
				}else if((methStringLine.contains("long")) && (methStringLine.contains("(")) && ((methStringLine.contains("{")||this.getLineAsString(lineNo+1).contains("{")))) {
					typeStartIndex = sourceCode.indexOf("long", endIndex) + 4;
					if (typeStartIndex == 3)
						break;
					else {
						methodNameStartIndex = sourceCode.indexOf(openBrace, typeStartIndex);
						if (methodNameStartIndex != -1) {
							methodName = sourceCode.substring(typeStartIndex, methodNameStartIndex);
							System.out.println("code line 135 : methode name = "+ methodName);
							/*int i;
							for (i = methodName.length() - 1; i >= 0; --i) {
								if (methodName.charAt(i) == ' ') {
									break;
								}
							}
							methodName = methodName.substring(i);*/
						}
	
						typeStartIndex = sourceCode.indexOf(openSquareBrace, typeStartIndex);
						if (typeStartIndex == -1)
							break;
					}
				}else {
					break;
				}
			}else {
//				typeStartIndex=1000000000;
				break;
			}
//			if (typeStartIndex == 6)
//				break;
//			else {
////				methodNameStartIndex = sourceCode.indexOf(openBrace, typeStartIndex);
//				if (methodNameStartIndex != -1) {
//					methodName = sourceCode.substring(typeStartIndex, methodNameStartIndex);
//					int i;
//					for (i = methodName.length() - 1; i >= 0; --i) {
//						if (methodName.charAt(i) == ' ') {
//							break;
//						}
//					}
//					methodName = methodName.substring(i);
//				}
//
//				typeStartIndex = sourceCode.indexOf(openSquareBrace, typeStartIndex);
//				if (typeStartIndex == -1)
//					break;
//			}

			int indexTemp = typeStartIndex - 1;
			Stack s1 = new Stack(stackSize);
			while (true) {
				if (indexTemp >= sourceCodeLength) {
					System.out.println(errorMessage2);
//					return -1;
					return null;
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
//						return -1;
						return null;
					}
				}
				++indexTemp;
			}
			endIndex = indexTemp;
			String subString = sourceCode.substring(typeStartIndex, endIndex+1);
			methodName = methodName.replaceAll("\\s+","");
			recursionCountCpp += general_Utils.countRecursiveOccurrences(subString, methodName);
			System.out.println("*"+methodName+"*");
			System.out.println("occurense of recursive : "+subString.contains(methodName));
			if(subString.contains(methodName)) {
				startLine = general_Utils.getFormattedLineByIndex(typeStartIndex);
				endLine = general_Utils.getFormattedLineByIndex(endIndex);
//				linesOfRecursionMethod.put(startLine, endLine);
				ArrayList<Integer> listOfLine = this.getLineNumbersBy(typeStartIndex, endIndex);
				for(int Lno:listOfLine) {
					linesOfRecursionMethodForCpp.put(Lno, 1);
				}
//				linesOfRecursionMethodForJava.put(key, value)
			}
			
			
			/*if () {
				
			}*/
			
			System.out.println(subString);
			System.out.println("*****************************************************************************************************************");
			System.out.println("code Line 210: recursion methode : "+methodName);
			System.out.println("code Line 211: recursion count : "+recursionCountCpp);
			System.out.println("*****************************************************************************************************************");
			
		} while (true);
		System.out.println("Recursion Count : " + recursionCountCpp);
		return (linesOfRecursionMethodForCpp);
//		return (recursionCountCpp);
	}
	
	public String getLineAsString(int lineNumber) {
		int startIndex, endIndex = 0;
		int startLine, endLine;
		int indexCounter = 0;
		int lineCount = 0;

		String line;

		do {
			if (indexCounter >= sourceCode.length())
				break;
			startIndex = sourceCode.indexOf("\n", endIndex) + 1;
			if (startIndex == 0)
				break;
			++lineCount;
			if (lineCount == lineNumber) {
				line = sourceCode.substring(endIndex, startIndex);
				return line;

			}
			endIndex = startIndex;
		} while (true);
		return null;
	}
	
	public ArrayList<Integer> getLineNumbersBy(int startindex1, int endIndex1) {
		int startIndex, endIndex = 0;
		int startLine, endLine;
		int indexCounter = 0;
		int lineCount = 0;
		ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();

		startLine = general_Utils.getFormattedLineByIndex(startindex1);
		endLine = general_Utils.getFormattedLineByIndex(endIndex1);
		String line;

		do {
			if (indexCounter >= sourceCode.length())
				break;
			startIndex = sourceCode.indexOf("\n", endIndex) + 1;
			if (startIndex == 0)
				break;
			++lineCount;
			if (lineCount >= startLine && lineCount < endLine) {
				line = sourceCode.substring(endIndex, startIndex);
				if ((line.length() != 2) || !(line.contains("}"))) {
					lineNumberArr1.add(lineCount);
				}
			}

			/*
			 * for(int i = 0 ; i < line.length() ; ++i){ if(index == indexCounter){ return
			 * lineCount; } ++indexCounter; }
			 */
			endIndex = startIndex;
		} while (lineCount < endLine);
		return lineNumberArr1;
	}
}
