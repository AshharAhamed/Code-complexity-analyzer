package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;

public class CsServicesImpl implements ICsServices{

	/*
	 * This is the main method to call all other methods and 
	 * come up with the Cs values for each line code
	 * This function returns all Sc values for each line of code as an array
	 * The REACT from end would take this array and map it in an HTML table
	 */
	@Override
	public int[] getAllCsValues(String sourceCode) {
		GeneralServiceImpl gs = new GeneralServiceImpl();
		
		int codeLength = gs.findSourceCodeLineCount(sourceCode);
		int [] csValueArray = new int[codeLength];
		String [] linesOfCodeArray = gs.collectAllSourceCodeLines(sourceCode, codeLength);
		String[] codeWithoutComments = gs.removeCommentsFromTheCode(linesOfCodeArray);
		
		// Text within quotes complexity
		for (int i = 0; i < codeWithoutComments.length; i++) {
			csValueArray[i] += textWithinQuotesComplexity(codeWithoutComments[i]);
		}
		
//		// Keyword complexity
//		for (int i = 0; i < codeWithoutComments.length; i++) {
//			csValueArray[i] += keywordsComplexity(codeWithoutComments[i]);
//		}
		
		return csValueArray;
	}
	
	// This is the function to calculate the complexity based on keywords  
	@Override
	public int keywordsComplexity(String lineOfCode) {
		int Cs = 0 ;
		boolean found = false; 
		
		String [] highKeywords = {"new", "delete",	"throw", "throws",} ; 
		String [] lowSimpleKeywords = {"abstract", "boolean", "byte", "char", "class", "const", "default",
										"double", "enum","extends", "final", "finally", "float",
										"goto", "implements","import", "instanceof", "int", "interface",
										"long",	"native", "package","private", "protected",	"public",
										"short", "static", "strictfp", "synchronized", "transient", "void","volatile", 
									  } ;
		
		String [] lowComplexeKeywords = {"assert", "break", "case", "catch", "continue", "do", "else", "for", 
				                         "if", "return", "super", "switch", "this", "try", "while"} ;
		
		String[] codeWords = lineOfCode.split(" ");
		ArrayList<String> codewordsList = new ArrayList<String>();
		
		// Load the array list
		for (int i = 0; i < codeWords.length; i++) {
			codewordsList.add(codeWords[i]);
		}
		
		// Check for high keywords, which are simple to search
		for (int i = 0; i < codewordsList.size(); i++) {
			for (int j = 0; j < highKeywords.length; j++) {
				if (codewordsList.get(i).equals(highKeywords[j])) {
					Cs += 2;
					codewordsList.remove(i);
					i--;
					break;
				}
			}
		}
		
		// Check for keywords which are simple to search and weight is just 1
		for (int i = 0; i < codewordsList.size(); i++) {
			for (int j = 0; j < lowSimpleKeywords.length; j++) {
				if (codewordsList.get(i).equals(lowSimpleKeywords[j])) {
					Cs++;
					codewordsList.remove(i);
					i--;
					break;
				}
			}
		}
		
		// Check for keywords which are complex to search and weight is just 1
		for (int i = 0; i < codewordsList.size(); i++) {
			for (int j = 0; j < lowComplexeKeywords.length; j++) {
				if (codewordsList.get(i).equals(lowComplexeKeywords[j])) {
					Cs++;
					codewordsList.remove(i);
					i--;
					break;
				}
			}
		}
		
		return Cs;
	}

	
	// This function would return the complexity for double quoted text, for a given line of code 
	@Override
	public int textWithinQuotesComplexity(String lineOfCode) {
		int textEntryPoint, textExitPoint;
		String tempstring;
		textEntryPoint = lineOfCode.indexOf("\"");
		
		if(textEntryPoint == -1) { // Base condition 01
			// No double quoted text, return 0 
			return 0; 
		}else if((lineOfCode.substring(textEntryPoint+1)).indexOf("\"") == -1){ // Base condition 2, some bugger has an unclosed double quotes.
			return 1;
		}else {
			textExitPoint = lineOfCode.indexOf("\"", (lineOfCode.indexOf("\"")+1));
			tempstring = lineOfCode.substring(textExitPoint+1);
			return (textWithinQuotesComplexity(tempstring) + 1);
		}
	}

	
	// This function would return the complexity for double quoted text, for a given line of code 
	@Override
	public int manipulatorsComplexity(String lineOfCode, GeneralServiceImpl gs) {
		int manipulatorComplexity = 0 ; 
		String originalLine = lineOfCode;
		String lineWithoutText = gs.removeDoubleQuotedText(lineOfCode); 
		
		
		
		return manipulatorComplexity;
	}
	
}
