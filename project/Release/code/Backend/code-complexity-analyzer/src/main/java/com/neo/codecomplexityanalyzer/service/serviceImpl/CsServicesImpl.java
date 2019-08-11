package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;

import com.neo.codecomplexityanalyzer.service.ICsServices;

public class CsServicesImpl implements ICsServices{

	// Constants
	private static final String SPLIT_REG_EXPRESSION_01 = "\\t|,|;|\\.|:|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/|\\s|\\n|=|<|>";
	private static final String SPLIT_REG_EXPRESSION_02 = "\t|;|.|,|:|[|(|{|}|*|/|=|<|>";

	// Properties
	private GeneralServiceImpl gs = new GeneralServiceImpl();
	private ArrayList<String> textSet = new ArrayList<String>();
	
	private HashSet<String> classNames = new HashSet<String>();
	private HashSet<String> methodNames = new HashSet<String>();
	private HashSet<String> arrayNames = new HashSet<String>();
	private HashSet<String> variableNames = new HashSet<String>();
	private HashSet<String> objectNames = new HashSet<String>();
	
	// Utility functions ================================================
	// Function to get the textSet array list
	public ArrayList<String> getTextSet() {
		return this.textSet;
	}
	
	// The function to collect class names 
	private HashSet<String> collectClassNames(String [] sourceCode){
		
		HashSet<String> classNamesCollection = new HashSet<String>();
		String keyword = "class";
		int classIndex ;
		String tempString, trimmedString ; 
		String [] splitList; 
		
		for (int i = 0; i < sourceCode.length; i++) {
			classIndex = sourceCode[i].indexOf(keyword); 
			if(classIndex == -1) {
				continue;
			}else {
				tempString = sourceCode[i].substring(classIndex + keyword.length());
				trimmedString = tempString.trim();
				splitList = trimmedString.split(SPLIT_REG_EXPRESSION_01);
				classNamesCollection.add(splitList[0]);
			}
		}
		
		return classNamesCollection;
		
	}
	
	// The function to collect Method names 
	private HashSet<String> collectMethodNames(String[] sourceCode) {

		HashSet<String> methodNamesCollection = new HashSet<String>();
		String [] keywordSet = {"assert", "case", "catch", "do", "for", "if", "return", "super", "switch", "try", "while"} ;
		
		String whiteSpace = " " ;
		int classIndex ;
		boolean keyWord = false ; 
		String tempString, trimmedString, lastToken ; 
		String [] splitList;
		String [] subSplitList; 
		
		// Get each line of code 
		// Split by white space 
		// Detect( 
		// Get the substring 
		// Split by delimiters  . , ; , ( , =, [, ], {, }, <, >
		// Check if this is one of the keywords, if it is, ignore 
		// If not, add to collection 
		
		// If a string starts with (
		// Get the previous element
		// Split by delimiters  . , ; , ( , =, [, ], {, }, <, >
		// Check the last element 
		
		for (int i = 0; i < sourceCode.length; i++) {
			splitList = sourceCode[i].split(whiteSpace);
			
			for (int j = 0; j < splitList.length; j++) {
				if(splitList[i].charAt(0) == '(') { // If substring starts with "("
					if(j != 0) {
						tempString = getLastToken(splitList[j-1]);
						lastToken = tempString.trim();
						for (int k = 0; k < keywordSet.length; k++) {
							if(lastToken.equals(keywordSet[k])) {
								keyWord = true;
								break ; 
							}
						}
						
						if(!keyWord) {
							methodNamesCollection.add(lastToken);
						}
					}else{
						// You need to complete this logic 
					}
				}else { // If substring does not start with "("
					tempString = getLastToken(splitList[j]);
					subSplitList = splitList[i].split(")");
					
					if(subSplitList.length ==0) {
						
					}else {
						
					}
					
					lastToken = tempString.trim();
					for (int k = 0; k < keywordSet.length; k++) {
						if(lastToken.equals(keywordSet[k])) {
							keyWord = true;
							break ; 
						}
					}
				}
			}
		}
		return methodNamesCollection;

	}
	
	// This function would split the token by the delimiters and return the last token
	private String getLastToken(String subString) {
		String [] subSplitList; 
		subSplitList = subString.split(SPLIT_REG_EXPRESSION_02);
		return subSplitList[subSplitList.length - 1];
	}
	
	// The function to collect Array names 
	private HashSet<String> collectArrayNames(String[] sourceCode) {

		HashSet<String> arraydNamesCollection = new HashSet<String>();
		
		
		
		return arraydNamesCollection;

	}

	// The function to collect variable names
	private HashSet<String> collectVariableNames(String[] sourceCode) {

		HashSet<String> variableNamesCollection = new HashSet<String>();
		String [] xeywordSet = {"abstract", "boolean", "byte", "char", "class", "const", "default",
				"double", "enum","extends", "final", "finally", "float",
				"goto", "implements","import", "instanceof", "int", "interface",
				"long",	"native", "package","private", "protected",	"public",
				"short", "static", "strictfp", "synchronized", "transient", "void","volatile", 
				"new", "delete",	"throw", "throws", "assert", "break", "case", "catch", "continue", "do", "else", "for", 
                "if", "return", "super", "switch", "this", "try", "while"
			  } ;
		
		return variableNamesCollection;

	}

	// The function to collect Method names
	private HashSet<String> collectObjectNames(String[] sourceCode) {

		HashSet<String> objectNamesCollection = new HashSet<String>();

		return objectNamesCollection;

	}
	
	// ==================================================================

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
		
		// Step 01. Remove all the comments 
		String[] codeWithoutComments = gs.removeCommentsFromTheCode(linesOfCodeArray); 
		
		// Step 02. Text within double quotes complexity
		for (int i = 0; i < codeWithoutComments.length; i++) {
			csValueArray[i] += textWithinQuotesComplexity(codeWithoutComments[i]);
		}
		
		// Step 03. Escape Character Complexity 
		for (int i = 0; i < codeWithoutComments.length; i++) {
			csValueArray[i] += escapeCharactersComplexity(codeWithoutComments[i]);
		}
		
		// Step 04. Remove double quotes from all the source code 
		String [] codeWithoutDoubleQuotedText = new String[codeLength]; 
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			codeWithoutDoubleQuotedText[i] = gs.removeDoubleQuotedText(codeWithoutComments[i]);
		}
		
		// Step 05. Identifier complexity 
			// This has been moved to sprint 2
		
		// Step 06. Reference and Dereference complexity
			// This has been moved to sprint 2
		
		// Step 07. Remove Identifiers and Pointers from the source code 
			// This has been moved to sprint 2
		
		// Step 08. Manipulators 
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			csValueArray[i] += manipulatorsComplexity(codeWithoutDoubleQuotedText[i]);
		}
		
		// Step 09. Keyword complexity
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			csValueArray[i] += keywordComplexity(codeWithoutDoubleQuotedText[i]);
		}
		
		// Step 10. Arithmetic operator complexity
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			csValueArray[i] += arithmeticOperatorsComplexity(codeWithoutDoubleQuotedText[i]);
		}

		// Step 11. Relation operator complexity
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			csValueArray[i] += realtionOperatorsComplexity(codeWithoutDoubleQuotedText[i]);
		}

		// Step 12. Logical operator complexity
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			csValueArray[i] += logicalOperatorsComplexity(codeWithoutDoubleQuotedText[i]);
		}

		// Step 13. Bitwise operator complexity
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			csValueArray[i] += bitwiseOperatorsComplexity(codeWithoutDoubleQuotedText[i]);
		}

		// Step 14. miscellaneous operator complexity
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			csValueArray[i] += miscOperatorsComplexity(codeWithoutDoubleQuotedText[i]);
		}
		
		// Step 15. miscellaneous operator complexity
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) {
			csValueArray[i] += assignmentOperatorsComplexity(codeWithoutDoubleQuotedText[i]);
		}

		return csValueArray;
	}
	
	// This is the function to calculate the complexity based on keywords  
	@Override
	public int keywordComplexity(String lineOfCode) {
		int Cs = 0 ;
		boolean found = false; 

		String [] highKeywords = {"new", "delete",	"throw", "throws"} ; 
		String [] lowSimpleKeywords = {"abstract", "boolean", "byte", "char", "class", "const", "default",
										"double", "enum","extends", "final", "finally", "float",
										"goto", "implements","import", "instanceof", "int", "interface",
										"long",	"native", "package","private", "protected",	"public",
										"short", "static", "strictfp", "synchronized", "transient", "void","volatile", 
									  } ;
		
		String [] lowComplexKeywords = {"assert", "break", "case", "catch", "continue", "do", "else", "for", 
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
		for (int i = 0; i < lowComplexKeywords.length; i++) {
			Cs += gs.findNumberOfCoccurences(lineOfCode, lowComplexKeywords[i]);
		}
		
		return Cs;
	}
	
	// This function would return the complexity for double quoted text, for a given line of code 
	// Additionally this would load the textSet array list with all the double quoted text segments in the given line of code
	@Override
	public int textWithinQuotesComplexity(String lineOfCode) {
		int textEntryPoint, textExitPoint;
		String tempstring;
		textEntryPoint = lineOfCode.indexOf("\"");
		
		if(textEntryPoint == -1) { // Base condition 01
			// No double quoted text, return 0 
			return 0; 
		}else if((lineOfCode.substring(textEntryPoint+1)).indexOf("\"") == -1){ // Base condition 2, some bugger has an unclosed double quotes.
			textSet.add(lineOfCode.substring(textEntryPoint, lineOfCode.length()));
			return 1;
		}else {
			textExitPoint = lineOfCode.indexOf("\"", (lineOfCode.indexOf("\"")+1));
			textSet.add(lineOfCode.substring(textEntryPoint, textExitPoint));
			tempstring = lineOfCode.substring(textExitPoint+1);
			return (textWithinQuotesComplexity(tempstring) + 1);
		}
	}

	// This function would return the complexity for escape characters inside double quoted text, for a given line of code
	// This is the first part of manipulator complexity
	@Override
	public int escapeCharactersComplexity(String lineOfCode) {
		int escapeCharacterComplexity = 0 ; 
		String [] escapeCharacters = {"\\b", "\\n", "\\t", "\\r", "\\f", "\\\"", "\\\\"}; 
		String tempString;
		
		// Clear the Array list
		textSet.clear();
		
		// Collect the strings and number of strings 
		textWithinQuotesComplexity(lineOfCode);
		for (int i = 0; i < textSet.size(); i++) {
			tempString = textSet.get(i);
			for (int j = 0; j < escapeCharacters.length; j++) {
				escapeCharacterComplexity += gs.findNumberOfCoccurences(tempString, escapeCharacters[j]);
			}
		}

		return escapeCharacterComplexity;
	}
		
	// This function would return the complexity for double quoted text, for a given line of code 
	@Override
	public int manipulatorsComplexity(String lineOfCode) {
		int manipulatorComplexity = 0 ; 
		String [] manipulators = {"endl", "ends", "flush", "dec", "hex", "oct"};
		for (int i = 0; i < manipulators.length; i++) {
			manipulatorComplexity += gs.findNumberOfCoccurences(lineOfCode, manipulators[i]);
		}

		return manipulatorComplexity;
	}

	// This method would return the complexity for identifiers, for a given line of code
	@Override
	public int identifierComplexity(String lineOfCode) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	// This function would return the complexity for arithmetic operators, for a given line of code 
	@Override
	public int arithmeticOperatorsComplexity(String lineOfCode) {
		int arithmeticOperatorComplexity = 0 ; 
		
		int tempCount, issueCount; 
		
		String [] arithmeticOperators = {"+", "-", "*", "/", "%", "++", "--"};
		String [] plusIssue = {"++", "+="};
		String [] minusIssue = {"--", "-=", "->"};
		String [] multiplyIssue = {"*="};
		String [] devisionIssue = {"/="};
		String [] modulusIssue = {"%="}; 
		
		for (int i = 0; i < arithmeticOperators.length; i++) {
			
			if(i == 0) { // Count for "+"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, arithmeticOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, plusIssue[0]);
				tempCount = (tempCount - (issueCount*2));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, plusIssue[1]);
				tempCount = (tempCount - issueCount);
				arithmeticOperatorComplexity += tempCount;
			}else if(i == 1) { // // Count for "-"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, arithmeticOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, minusIssue[0]);
				tempCount = (tempCount - (issueCount*2));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, minusIssue[1]);
				tempCount = (tempCount - issueCount);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, minusIssue[2]);
				tempCount = (tempCount - issueCount);
				arithmeticOperatorComplexity += tempCount;
			}else if(i == 2) { // // Count for "-"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, arithmeticOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, multiplyIssue[0]);
				tempCount = (tempCount - issueCount);
				arithmeticOperatorComplexity += tempCount;
			}else if(i == 3) { // // Count for "-"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, arithmeticOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, devisionIssue[0]);
				tempCount = (tempCount - issueCount);
				arithmeticOperatorComplexity += tempCount;
			}else if(i == 4) { // // Count for "-"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, arithmeticOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, modulusIssue[0]);
				tempCount = (tempCount - issueCount);
				arithmeticOperatorComplexity += tempCount;
			}else if(i == 5) { // // Count for "-"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, arithmeticOperators[i]);
				arithmeticOperatorComplexity += tempCount;
			}else if(i == 6) { // // Count for "-"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, arithmeticOperators[i]);
				arithmeticOperatorComplexity += tempCount;
			}
		}

		return arithmeticOperatorComplexity;
	}

	
	// This function would return the complexity for relation operators, for a given line of code
	@Override
	public int realtionOperatorsComplexity(String lineOfCode) {
	
		int relationOperatorComplexity = 0 ; 
		int tempCount, issueCount; 
		
		String [] relationOperators = {"==", "!=", ">", "<"};
		String [] rightIssue = {">>", ">>>", ">>>=", ">>=", "->"};
		String [] leftIssue = {"<<", "<<=", "<<<"};
		
		for (int i = 0; i < relationOperators.length; i++) {
			
			if(i == 0) { // Count for "+"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, relationOperators[i]);
				relationOperatorComplexity += tempCount;
			}else if(i == 1) { // // Count for "-"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, relationOperators[i]);
				relationOperatorComplexity += tempCount;
			}else if(i == 2) { // // Count for ">"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, relationOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, rightIssue[0]);
				tempCount = (tempCount - (issueCount*2));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, rightIssue[1]);
				tempCount = (tempCount - (issueCount*3));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, rightIssue[2]);
				tempCount = (tempCount - (issueCount*3));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, rightIssue[3]);
				tempCount = (tempCount - (issueCount*2));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, rightIssue[4]);
				tempCount = (tempCount - issueCount);
				relationOperatorComplexity += tempCount;
			}else if(i == 3) { // // Count for "<"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, relationOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, leftIssue[0]);
				tempCount = (tempCount - (issueCount*2));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, leftIssue[1]);
				tempCount = (tempCount - (issueCount*2));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, leftIssue[2]);
				tempCount = (tempCount - (issueCount*3));
				relationOperatorComplexity += tempCount;
			}
		}

		return relationOperatorComplexity;
	}

	
	// This function would return the complexity for logical operators, for a given line of code
	@Override
	public int logicalOperatorsComplexity(String lineOfCode) {
		int logicalOperatorComplexity = 0 ; 
		int tempCount, issueCount; 
		
		String [] logicalOperators = {"&&", "||", "!"};
		String [] notIssue = {"!="};
		
		for (int i = 0; i < logicalOperators.length; i++) {
			
			if(i == 0) { // Count for "&&"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, logicalOperators[i]);
				logicalOperatorComplexity += tempCount;
			}else if(i == 1) { // // Count for "||"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, logicalOperators[i]);
				logicalOperatorComplexity += tempCount;
			}else if(i == 2) { // // Count for "!"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, logicalOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, notIssue[0]);
				tempCount = (tempCount - issueCount);
				logicalOperatorComplexity += tempCount;
			}
		}

		return logicalOperatorComplexity;
	}

	
	// This function would return the complexity for bitwise operators, for a given line of code
	@Override
	public int bitwiseOperatorsComplexity(String lineOfCode) {
		int bitwiseOperatorComplexity = 0 ; 
		int tempCount, issueCount; 
		
		String [] bitwiseOperators = {"|", "^", "~", "<<", ">>"};
		String [] pipeIssue = {"||", "|="};
		String [] upIssue = {"^="};
		String [] leftIssue = {"<<="};
		String [] rightIssue = {">>=", ">>>="};
		
		for (int i = 0; i < bitwiseOperators.length; i++) {
			
			if(i == 0) { // Count for "|"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, bitwiseOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, pipeIssue[0]);
				tempCount = (tempCount - (issueCount*2));
				issueCount = gs.findNumberOfCoccurences(lineOfCode, pipeIssue[1]);
				tempCount = (tempCount - issueCount);
				bitwiseOperatorComplexity += tempCount;
			}else if(i == 1) { // // Count for "^"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, bitwiseOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, upIssue[0]);
				tempCount = (tempCount - issueCount);
				bitwiseOperatorComplexity += tempCount;
			}else if(i == 2) { // // Count for "~"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, bitwiseOperators[i]);
				bitwiseOperatorComplexity += tempCount;
			}else if(i == 3) { // // Count for "<<" and "<<<"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, bitwiseOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, leftIssue[0]);
				tempCount = (tempCount - issueCount);
				bitwiseOperatorComplexity += tempCount;
			}else if(i == 4) { // // Count for ">>" and ">>>"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, bitwiseOperators[i]);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, rightIssue[0]);
				tempCount = (tempCount - issueCount);
				issueCount = gs.findNumberOfCoccurences(lineOfCode, rightIssue[1]);
				tempCount = (tempCount - issueCount);
				bitwiseOperatorComplexity += tempCount;
			}
		}

		return bitwiseOperatorComplexity;
	}
	
	// This function would return the complexity for miscellaneous operators, for a given line of code 

	@Override
	public int miscOperatorsComplexity(String lineOfCode) {
		int miscOperatorComplexity = 0 ; 
		int tempCount;
		
		String [] miscOperators = {",", "->", ".", "::"};
		
		for (int i = 0; i < miscOperators.length; i++) {
			
			if(i == 0) { // Count for ","
				tempCount = gs.findNumberOfCoccurences(lineOfCode, miscOperators[i]);
				miscOperatorComplexity += tempCount;
			}else if(i == 1) { // // Count for "->"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, miscOperators[i]);
				miscOperatorComplexity += tempCount;
			}else if(i == 2) { // // Count for "."
				tempCount = gs.findNumberOfCoccurences(lineOfCode, miscOperators[i]);
				miscOperatorComplexity += tempCount;
			}else if(i == 3) { // // Count for "::"
				tempCount = gs.findNumberOfCoccurences(lineOfCode, miscOperators[i]);
				miscOperatorComplexity += tempCount;
			}
		}
		
		return miscOperatorComplexity;
	}

	
	// This function would return the complexity for assignment operators, for a given line of code 
	@Override
	public int assignmentOperatorsComplexity(String lineOfCode) {
		int assignmentOperatorComplexity = 0 ; 
//		int tempCount, issueCount; 
		
//		String [] assignmentOperators = {"+=", "-=", "*=", "/=", "%=", "&=", "^=", ">>>=", "|=", "<<=", ">>=", "="};
//		String [] pipeIssue = {"||", "|="};
//		String [] upIssue = {"^="};
//		String [] leftIssue = {"<<="};
//		String [] rightIssue = {">>=", ">>>="};
		
		int assignemtnOprCount = gs.findNumberOfCoccurences(lineOfCode, "=");
		int eq = gs.findNumberOfCoccurences(lineOfCode, "==");
		int nq = gs.findNumberOfCoccurences(lineOfCode, "!=");
		int correction01 = eq + nq; 
		
		int lteq = gs.findNumberOfCoccurences(lineOfCode, "<=");
		int sl = gs.findNumberOfCoccurences(lineOfCode, "<<=");
		int correction02 = lteq - sl ; 
		
		int gteq = gs.findNumberOfCoccurences(lineOfCode, ">=");
		int sr = gs.findNumberOfCoccurences(lineOfCode, ">>=");
		int correction03 = gteq - sr ;

		assignmentOperatorComplexity = assignemtnOprCount - (correction01 + correction02 + correction03);

		return assignmentOperatorComplexity;
	}
}
