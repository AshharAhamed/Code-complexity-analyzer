package com.neo.ccaServices.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class CCA_Uilities {
	
	// Function to read the file and return the source code as a String 
	public String getSourceCode() {
		File file = new File("D:\\02_Mech_Mango\\01_SLIIT\\Semester_06\\SPM\\03_10th_August_Sprint_01\\01_Testing\\01_Based_on_Size\\src\\utilities\\FibonacciMain.java"); 
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
