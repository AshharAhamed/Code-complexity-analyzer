package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class CCA_Uilities {
	
	// Function to read the file and return the source code as a String 
	public String getSourceCode() {
		File file = new File("/home/sahan/Documents/My Documents/SLIIT/SPM/Code-complexity-analyzer/project/Release/code/Backend/ccaServices/src/main/java/com/neo/ccaServices/resources/FibonacciMain.java"); 
	    Scanner sc = null;
	    String sourceCode  = "" ; 
	    
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
		while (sc.hasNextLine()) {
			sourceCode += (sc.nextLine() + "\n") ; 
		} 
		
		return sourceCode ; 
	}
	
	// Function to find the line count 
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
	public String[] collectAllSourceCodeLines(String sourceCode, int lineCount) {
		String[] sourceCodeLines = new String[lineCount];
		BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(sourceCode));
		String str;
		int i = 0;

		try {
			while ((str = sourceCodeTemp.readLine()) != null) {
				sourceCodeLines[i] = str;
				i++ ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sourceCodeLines;
	}
}
