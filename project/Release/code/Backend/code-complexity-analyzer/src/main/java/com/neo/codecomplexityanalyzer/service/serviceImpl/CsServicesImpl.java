package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.neo.codecomplexityanalyzer.service.ICsServices;

public class CsServicesImpl implements ICsServices{

	private static final String VOID = "void";
	private static final String SHORT = "short";
	private static final String DOUBLE = "double";
	private static final String FLOAT = "float";
	private static final String BYTE = "byte";
	private static final String CHAR = "char";
	private static final String LONG = "long";
	private static final String INT = "int";
	private static final String BOOLEAN = "boolean";
	private static final String SINGLE_SPACE_CHARACTOR = " ";

	private static final String CLASS_KEYWORD = "class";
	private static final String EXTENDS_KEYWORD = "extends";
	
	// Constants
	private static final String SPLIT_REG_EXPRESSION_01 = "\\t|,|;|\\.|:|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/|\\s|\\n|=|<|>";
	private static final String SPLIT_REG_EXPRESSION_02 = "\t|;|.|,|:|[|(|{|}|*|/|=|<|>";
	private static final String SPLIT_REG_EXPRESSION_03 = "\\t|\\{|\\}| |\n|\r";
	private static final String SPLIT_REG_EXPRESSION_04 = "\\t| |\n|\r";

	// Keywords 
	String [] highKeywords = {"new", "delete",	"throw", "throws"} ; 
	String [] lowSimpleKeywords = {"abstract", BOOLEAN, BYTE, CHAR, "class", "const", "default",
									DOUBLE, "enum","extends", "final", "finally", FLOAT,
									"goto", "implements","import", "instanceof", INT, "interface",
									LONG,	"native", "package","private", "protected",	"public",
									SHORT, "static", "strictfp", "synchronized", "transient", VOID,"volatile", 
								  } ;
	
	String [] lowComplexKeywords = {"assert", "break", "case", "catch", "continue", "do", "else", "for", 
			                         "if", "return", "super", "switch", "this", "try", "while"} ;
	
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
		HashSet<String> initialIdentifierSet = new HashSet<String>();
		ArrayList<String> tokenList = new ArrayList<String>();
		
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
		initialIdentifierSet = initalIDPass(codeWithoutDoubleQuotedText); 
		for (int i = 0; i < codeWithoutDoubleQuotedText.length; i++) { 
			csValueArray[i] = identifierComplexity(codeWithoutDoubleQuotedText[i], initialIdentifierSet);
		}

		// Step 06. Remove Identifiers and Pointers from the source code 
		String [] codeWithoutIdentifiers = new String[codeLength]; 
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) { 
			codeWithoutIdentifiers[i] = removeIdentifiers(codeWithoutDoubleQuotedText[i], initialIdentifierSet, gs) ;
		}
		
		// Step 07. Reference and Dereference complexity
					// This is not completed yet 
		
		// Step 08. Manipulators 
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += manipulatorsComplexity(codeWithoutIdentifiers[i]);
		}
		
		// Step 09. Keyword complexity
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += keywordComplexity(codeWithoutIdentifiers[i]);
		}
		
		// Step 10. Arithmetic operator complexity
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += arithmeticOperatorsComplexity(codeWithoutIdentifiers[i]);
		}

		// Step 11. Relation operator complexity
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += realtionOperatorsComplexity(codeWithoutIdentifiers[i]);
		}

		// Step 12. Logical operator complexity
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += logicalOperatorsComplexity(codeWithoutIdentifiers[i]);
		}

		// Step 13. Bitwise operator complexity
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += bitwiseOperatorsComplexity(codeWithoutIdentifiers[i]);
		}

		// Step 14. miscellaneous operator complexity
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += miscOperatorsComplexity(codeWithoutIdentifiers[i]);
		}
		
		// Step 15. assignment operator complexity
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += assignmentOperatorsComplexity(codeWithoutIdentifiers[i]);
		}
		
		// Step 15. Numerical values complexity
		for (int i = 0; i < codeWithoutIdentifiers.length; i++) {
			csValueArray[i] += numericalValuesComplexity(codeWithoutIdentifiers[i], tokenList);
		}

		return csValueArray;
	}
	
	// This is the function to calculate the complexity based on keywords  
	@Override
	public int keywordComplexity(String lineOfCode) {
		int Cs = 0 ;
		boolean found = false; 

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
	
	// =============================== The identifier complexity ==================================
	// This method would return the complexity for identifiers, for a given line of code
	@Override
	public int identifierComplexity(String lineOfCode, HashSet<String> idSet) {
//		String[] idComplexity = new String[2];
		int idComplexity = 0 ; 
//		HashSet<String> initialIdentifierSet = new HashSet<String>(); 
		ArrayList<String> identifiersOnThisLine = new ArrayList<String>();
		int startIndex, endIndex ; 
		
		for (String id : idSet) {
			for (int i = 0; i < lineOfCode.length(); i++) {
				startIndex = lineOfCode.indexOf(id, i) ;
				
				if(startIndex == -1) { // Not Found 
					break ; 
				}else { // Found 
					
					// Validation 
					endIndex = startIndex + id.length() ;
					
					if(endIndex >= lineOfCode.length()) {
						lineOfCode += " " ; // Add a space at the end
					}

					if(checkIfGenuine(id, lineOfCode, startIndex, endIndex)) {
						identifiersOnThisLine.add(id) ; 
						idComplexity++ ; 
						i = startIndex + id.length() ; 
						
						if(i >= lineOfCode.length()) {
							break ; 
						}
					}else {
						i = startIndex + id.length() ; 
						
						if(i >= lineOfCode.length()) {
							break ; 
						}
					}
				}
			}
		}
		
		return idComplexity;
	}
	
	// Function to remove identifiers from the code
	public String removeIdentifiers(String lineOfCode, HashSet<String> idSet, GeneralServiceImpl gs) {
//		String[] idComplexity = new String[2];
//		HashSet<String> initialIdentifierSet = new HashSet<String>(); 
//		ArrayList<String> identifiersOnThisLine = new ArrayList<String>();
		int startIndex, endIndex ; 

		for (String id : idSet) {
			for (int i = 0; i < lineOfCode.length(); i++) {
				startIndex = lineOfCode.indexOf(id, i) ;
				
				if(startIndex == -1) { // Not Found 
					break ; 
				}else { // Found 
					// Validation 
					endIndex = startIndex + id.length() ;
					
					if(endIndex >= lineOfCode.length()) {
						lineOfCode += " " ; // Add a space at the end
					}
					
					if(checkIfGenuine(id, lineOfCode, startIndex, endIndex)) {
						lineOfCode = gs.replaceWithWhiteSpaces(lineOfCode, startIndex, endIndex);
						i = startIndex + id.length() ; 
						
						if(i >= lineOfCode.length()) {
							break ; 
						}
					}else {
						i = startIndex + id.length() ; 
						
						if(i >= lineOfCode.length()) {
							break ; 
						}
					}
				}
			}
		}

		return lineOfCode;
	}
	
	// Function to collect the initial pass of IDs 
	public HashSet<String> initalIDPass(String[] sourceCode) {
		ArrayList<String> definedClassNames = new ArrayList<String>(); 
		ArrayList<String> utilizedClassNames = new ArrayList<String>(); 
		ArrayList<String> variableNames = new ArrayList<String>(); 
		ArrayList<String> methodNames = new ArrayList<String>(); 
		ArrayList<String> objectNames = new ArrayList<String>(); 
		
		HashSet<String> allClassNames = new HashSet<String>();
		HashSet<String> allNonClassIDNames = new HashSet<String>();
		HashSet<String> initialIdentifierSet = new HashSet<String>();
		
		// Collect all Class, method, variable and array names 
		for (String loc : sourceCode) {
			definedClassNames = collectUserDefinedClassNames(loc);
			utilizedClassNames = collectUtilizedClassNames(loc);
			variableNames = collectVariableNames(loc);
			methodNames = collectMethodNames(loc);	
			
			loadHashSet(definedClassNames, allClassNames);
			loadHashSet(utilizedClassNames, allClassNames);
			loadHashSet(variableNames, allNonClassIDNames);
			loadHashSet(methodNames, allNonClassIDNames);
		}
		
		// Collect all object names 
		for (String loc : sourceCode) {
			objectNames = collectObjectNames(allClassNames, loc);
			loadHashSet(objectNames, allNonClassIDNames);
		}

		// Make the final id list 
		for (String cName : allClassNames) {
			initialIdentifierSet.add(cName);
		}
		
		for (String id : allNonClassIDNames) {
			initialIdentifierSet.add(id);
		}
		
		// Return the hash set 
		return initialIdentifierSet ;
	}

	private void loadHashSet(ArrayList<String> idArray, HashSet<String> idHashSet) {
		for (String id : idArray) {
			idHashSet.add(id);
		}
	}
	
	// Function to collect all IDs and replace with whitespace 

	// =============================== The class name identifiers  ==================================
	// Function to collect class names which are user defined and/or extended
	public ArrayList<String> collectUserDefinedClassNames(String lineOfCode) {
		int classKeywordStartIndex, extendsKeywordStartIndex; 
		ArrayList<String> classNames = new ArrayList<String>();

		classKeywordStartIndex = lineOfCode.indexOf(CLASS_KEYWORD);
		if (classKeywordStartIndex != -1) { // If the class keyword is found 
			classNames.add(findClass(lineOfCode, classKeywordStartIndex, 4)); 

			// Now search for extends keyword
			extendsKeywordStartIndex = lineOfCode.indexOf(EXTENDS_KEYWORD);
			if (extendsKeywordStartIndex != -1) {
				classNames.add(findClass(lineOfCode, extendsKeywordStartIndex, 6));
			}
		}
		return classNames ;
	}
	
	// The findClass method to avoid the DRY violation  
	public String findClass(String lineOfCode, int keywordStartIndex, int keyWordLength) {
		int keywordEndIndex;
		int classNameStartIndex = 0; 
		Character character; 
		Character white = new Character(' ');
		Character tab = new Character('\t');
		String tempSubString ;
		String [] tempStringArray ;
		
		keywordEndIndex = keywordStartIndex + keyWordLength;
		character = new Character(lineOfCode.charAt(keywordEndIndex + 1));

		while (character.equals(white) || character.equals(tab)) {
			keywordEndIndex++;
			character = new Character(lineOfCode.charAt(keywordEndIndex));
		}
		
		classNameStartIndex = keywordEndIndex;
		tempSubString = lineOfCode.substring(classNameStartIndex); 
		tempStringArray = tempSubString.split(SPLIT_REG_EXPRESSION_03);
		
		return tempStringArray[0]; 
	}
	
	// Function to collect class names 
	public ArrayList<String> collectUtilizedClassNames(String lineOfCode) {
		ArrayList<String> classNames = new ArrayList<String>();
		String[] codeWords = lineOfCode.split(SPLIT_REG_EXPRESSION_04);
		
		for (String str : codeWords) {
			if(str.length() == 0 || "".equals(str) || "\t".equals(str) || " ".equals(str)) {
				continue ; 
			}else {
				findIdentifierRecursinve(classNames, str, 0); 
			}
		}
		
//		for (int i = 0; i < classNames.size(); i++) {
//			System.out.println(classNames.get(i));
//		}
		
		return classNames;
	}
	
	private void findIdentifierRecursinve(ArrayList<String> idList, String token, int start) {
		String identifier = "" ; 
		int nextJLIndex ; 
		
		if(start <= (token.length()-1)) {
			if(checkJavaClassLetter(token.charAt(start))) { // Starts with a Java letter 
				nextJLIndex = findNexJavaLetterIndex(start, token, false); 
				if(nextJLIndex == -1) {
					if(start == 0) {
						identifier = token ;
					}else {
						identifier = token.substring(start) ;
					}
					 
					idList.add(identifier); 
				}else {
					identifier = token.substring(start, nextJLIndex);
					idList.add(identifier); 
					findIdentifierRecursinve(idList, token, (nextJLIndex + 1)) ;
				}
			}else { // Token does not start with a Java letter 
				nextJLIndex = findNexJavaLetterIndex(start, token, true); 
				
				if(nextJLIndex != -1) {
					if(!checkJavaLetterPlusNumeric(token.charAt(nextJLIndex - 1))) {
						findIdentifierRecursinve(idList, token, nextJLIndex) ;
					}else {
						
						while((nextJLIndex <= token.length()-1) && checkJavaLetterPlusNumeric(token.charAt(nextJLIndex))){
							nextJLIndex++ ; 
						}

						if(nextJLIndex < (token.length()-1)) {
							findIdentifierRecursinve(idList, token, nextJLIndex+1) ;
						}
					}
				}
			}
		}	
	}
	
	private int findNexJavaLetterIndex(int start, String token, boolean mode) { // mode "false" to find an NJL, "true" to find a valid Java Class Letter
		for (int i = start; i < token.length() ; i++) {
			if(mode) {
				if(checkJavaClassLetter(token.charAt(i))) {
					return i; 
				}
			}else {
				if(!checkJavaLetterPlusNumeric(token.charAt(i))) {
					return i; 
				}
			}
		}
		
		return -1 ; 
	}
	
	// =============================== The object name identifiers  ==================================
	// Function to collect object names 
	public ArrayList<String> collectObjectNames(HashSet<String> classNameList, String lineOfCode) {
		ArrayList<String> objectNames = new ArrayList<String>();
		ArrayList<String> classNameInfo = new ArrayList<String>();
		ArrayList<Integer> classStarIndex = new ArrayList<Integer>();
		ArrayList<Integer> classEndIndex = new ArrayList<Integer>();
		int tempStart = 0; 
		int tempEnd = 0 ; 
		int tempObjNameStart, tempObjNameEnd, objSubStringEnd ; 
		int cnStart, cnEnd, onStart, onEnd ; 
		String tempString, objSubString ;
		String tempClassName = "" ;
		String [] tempInfoArray ; 

//		System.out.println(cleanSpecialCases(lineOfCode, 0, '(', ')', true));
		
		// Identifying the occurrences 
		lineOfCode = cleanSpecialCases(lineOfCode, 0, '<', '>', false); // To remove the <> cases
		getIdOccurrenceInfo(classNameList, lineOfCode, classNameInfo);
		
		for (String info : classNameInfo) {
			// process info string and extracting values 
			tempInfoArray = info.split(" ") ; 
			tempClassName = tempInfoArray[0] ; 
			tempStart = Integer.parseInt(tempInfoArray[1]); 
			tempEnd = Integer.parseInt(tempInfoArray[2]); 
			
			// Initial Clean up
			tempString = cleanSpecialCases(lineOfCode, tempEnd, '[', ']', false);
			tempString = cleanSpecialCases(tempString, tempEnd, '(', ')', true);

			objSubString = tempString.substring(tempEnd); 
			objSubStringEnd = objSubString.indexOf('='); 
			if(objSubStringEnd == -1) {
				objSubStringEnd = objSubString.indexOf(';');
			}
			
			if(objSubStringEnd != -1) {
				objSubString = objSubString.substring(0, objSubStringEnd);
				
				// Clean Class Names 
				for (String cn : classNameList) {
					for (int i = 0; i < objSubString.length(); i++) {
						cnStart = objSubString.indexOf(cn, i) ; 

						if(cnStart == -1) {
							break;
						}else {
							cnEnd = cnStart + cn.length() ;
							objSubString += " " ;

							if(checkIfGenuine(cn, objSubString, cnStart, cnEnd)) {
								objSubString = gs.replaceWithWhiteSpaces(objSubString, cnStart, cnEnd);
								i = cnEnd; 
							}else {
								i = cnEnd; 
							}
							
						}
					}
				}
				
				for (int i = 0; i < objSubString.length(); i++) {
					if(checkJavaLetterPlusNumeric(objSubString.charAt(i))) {
						onStart = i ;
						for (int j = onStart; j < objSubString.length(); j++) {
							if(!checkJavaLetterPlusNumeric(objSubString.charAt(j))) {
								onEnd = j ; 
								i = j ; 
								objectNames.add(objSubString.substring(onStart, onEnd)); 
								break ; 
							}else if(j == objSubString.length()-1) {
								objectNames.add(objSubString.substring(onStart));
								i = j ;
								break ; 
							}
						}
					}
				}
			}else {
				simpleObejctNameChack(lineOfCode, objectNames, tempEnd);
			}
		}
		
//		for (String info : objectNames) {
//			System.out.println(info);
//		}
		
		return objectNames ; 
	}

	private void simpleObejctNameChack(String lineOfCode, ArrayList<String> objectNames, int tempEnd) {
		int tempObjNameStart;
		int tempObjNameEnd;
		for (int i = tempEnd; i < lineOfCode.length(); i++) {
			if(!checkJavaLetter(lineOfCode.charAt(i))) {
				continue;
			}else { // it is a Java letter 
				tempObjNameStart = i ; 
				
				while(checkJavaLetterPlusNumeric(lineOfCode.charAt(i))) {
					i++;
				}
				
				tempObjNameEnd = i ;
				objectNames.add(lineOfCode.substring(tempObjNameStart, tempObjNameEnd));
				break; 
			}
		}
	}
	
	// Function to clean the () and <> from the line of code 
	private String cleanSpecialCases(String lineOfCode, int start, char open, char close, boolean keep) {
		ArrayList<Integer> OpenClosePositions = new ArrayList<Integer>();
		int openPos ; 
		int closePos ; 
		
		// Capture Open and Close 
		for (int i = start; i < lineOfCode.length(); i++) {
			openPos = lineOfCode.indexOf(open, i) ;
			
			if(openPos >= 0) { // Found
				closePos = lineOfCode.indexOf(close, i) ;
				
				if(closePos >= 0 ) {
					OpenClosePositions.add(openPos); 
					OpenClosePositions.add(closePos); 
					i = closePos; 
				}else {
					OpenClosePositions.add(openPos); 
					OpenClosePositions.add(lineOfCode.length() - 1);
					break ; 
				}
			}
			
		}
		
		// Cleaning up 
		for (int i = 0; i < ((OpenClosePositions.size())/2); i++) {
			openPos = OpenClosePositions.get(i*2) ;
			closePos = OpenClosePositions.get((2*i)+1) ;
			lineOfCode = gs.replaceWithWhiteSpaces(lineOfCode, openPos, closePos) ; 
			
			if(keep) {
				StringBuilder tempStringToModify = new StringBuilder(lineOfCode);
				tempStringToModify.setCharAt(openPos, open) ; 
				lineOfCode = tempStringToModify.toString() ; 
			}else {
				StringBuilder tempStringToModify = new StringBuilder(lineOfCode);
				tempStringToModify.setCharAt(closePos, ' ') ; 
				lineOfCode = tempStringToModify.toString() ; 
			}
		}

		return lineOfCode;
	}

	// Function to identify the occurrences of all the identifiers, an their positioning 
	private void getIdOccurrenceInfo(HashSet<String> idList, String lineOfCode, ArrayList<String> classNameInfo) {
		int tempStart;
		int tempEnd;
		String tempString;
		for (String cName : idList) {
			for(int i = 0 ; i < lineOfCode.length() ; i++) {
				tempStart = lineOfCode.indexOf(cName, i) ;
				
				if(tempStart >= 0) { // Exists
					tempEnd = tempStart + cName.length() ; 
					
					if(checkIfGenuine(cName, lineOfCode, tempStart, tempEnd)) {
						tempString = cName + " " + Integer.toString(tempStart) + " " + Integer.toString(tempEnd);
						classNameInfo.add(tempString) ; 
					}
					
					i = tempEnd ; 
				}else { // does not exist
					break ; 
				}
				
			}
		}
	}
	
	// The method to check if a given identifier actually exists, of if it is embedded in a different identifier 
	private boolean checkIfGenuine(String id, String lineOfCode, int startIndex, int endIndex) {
		boolean genuine = false ; 
		
		if(startIndex == 0 ) {
			if(!checkJavaLetterPlusNumeric(lineOfCode.charAt(endIndex))) {
				genuine = true ; 
			}
		}else {
			if(!checkJavaLetterPlusNumeric(lineOfCode.charAt(endIndex)) && !checkJavaLetterPlusNumeric(lineOfCode.charAt(startIndex - 1))) {
				genuine = true ; 
			}
		}
		
		return genuine; 
	}
	
	// =============================== The variable name identifiers  ==================================
	// Function to collect variable names 
	public ArrayList<String> collectVariableNames(String lineOfCode) {
		ArrayList<String> variableNames = new ArrayList<String>();
		ArrayList <Integer> positions = new ArrayList<Integer>() ; 
		int tempStart, charStart, varStart, varEnd ; 
		char tempChar; 
		
		String [] keywords = {VOID,SHORT, DOUBLE, FLOAT, BYTE, CHAR, LONG, INT, BOOLEAN};

		// search for the token
		for (String key : keywords) {
			for (int i = 0; i < lineOfCode.length(); i++) {
				tempStart = lineOfCode.indexOf((key), i) ;

				if(tempStart >= i ) { // If found
					charStart = tempStart + key.length();
					
					if(tempStart == 0) {
						if(lineOfCode.charAt(charStart) == ' ' || lineOfCode.charAt(charStart) == '\t' || lineOfCode.charAt(charStart) == '[') {
							positions.add(tempStart + key.length()); 
						}
					}else{
						if(!checkJavaLetter(lineOfCode.charAt(tempStart-1))){
							if(lineOfCode.charAt(charStart) == ' ' || lineOfCode.charAt(charStart) == '\t' || lineOfCode.charAt(charStart) == '[') {
								positions.add(tempStart + key.length()); 
							}
						}
					}
					
					i = charStart ;
					
				}else if(tempStart == -1) {
					break ; 
				}
			}
		}

		// Look for java letter 
		for (Integer i : positions) {
			for (int j = i; j < lineOfCode.length(); j++) {
				if(checkJavaLetter(lineOfCode.charAt(j))) {
					varStart = j ; 
					// Check for a non Java letter except numbers  
					for (int n = j+1; n < lineOfCode.length(); n++) {
						tempChar= lineOfCode.charAt(n);
						if(!checkJavaLetterPlusNumeric(tempChar)) {
							varEnd = n ; 
							variableNames.add(lineOfCode.substring(varStart, varEnd));
							j = recursiveVarNameCheck(variableNames, varEnd, lineOfCode); 
							break ; 
						}
					} 
					break ; 
				}
			}
		}
		
//		for (String var : variableNames) {
//			System.out.println(var);
//		}
		
		return variableNames ; 
	}
	
	// Recursive variable name check 
	private int recursiveVarNameCheck(ArrayList<String> varList, int start, String lineOfCode) {
		int length = lineOfCode.length() ;  
		int temp, varStart, varEnd = start ; 
		char tempChar; 
		
		for (int i = start; i < length; i++) {
			if(lineOfCode.charAt(i) == ',') {
				for (int j = i; j < length; j++) {
					if(checkJavaLetter(lineOfCode.charAt(j))) {
						varStart = j ;
						for (int n = j+1; n < lineOfCode.length(); n++) {
							tempChar= lineOfCode.charAt(n);
							if(!checkJavaLetterPlusNumeric(tempChar)) {
								varEnd = n ; 
								varList.add(lineOfCode.substring(varStart, varEnd));
								j = recursiveVarNameCheck(varList, varEnd, lineOfCode);
								break ; 
							}
						}
					}
				}
			}else if(lineOfCode.charAt(i) == '=') {
				for (int j = i; j < length; j++) {
					if(lineOfCode.charAt(j) == ';' ||  lineOfCode.charAt(j) == ',') {
						varEnd = j ; 
						j = recursiveVarNameCheck(varList, varEnd, lineOfCode); 
						break ; 
					}
				}
			}else if(lineOfCode.charAt(i) == ' ' || lineOfCode.charAt(i) == '\t'){
				continue; 
			}else if(lineOfCode.charAt(i) == '['){
				for (int j = i; j < length; j++) {
					if(lineOfCode.charAt(j) == ';' ||  lineOfCode.charAt(j) == ',' || lineOfCode.charAt(j) == '{') {
						varEnd = j ; 
						j = recursiveVarNameCheck(varList, varEnd, lineOfCode); 
						break ; 
					}
				} 
			}
			else {
				break; 
			}
		}
		return varEnd; 
	}
	
	// =============================== The method name identifiers  ==================================
	// Function to collect method names
	public ArrayList<String> collectMethodNames(String lineOfCode) {
		ArrayList<String> methodNames = new ArrayList<String>();
		ArrayList <Integer> positions = new ArrayList<Integer>() ; 
		int tempEnd, tempStart; 
		
		// Collect positions
		for (int i = 0; i < lineOfCode.length(); i++) {
			if(lineOfCode.charAt(i) == '(') {
				positions.add(i); 
			}
		}
		
		// Collect all the variable names 
		for (Integer i : positions) {
			// if i = 0, it is the beginning 
			if(i == 0) {
				break ; 
			}
			
			// public void newFunction (int i, float j)
			if(lineOfCode.charAt(i-1) == ' ' || lineOfCode.charAt(i-1) == '\t') {
				for (int j = i-1; j >= 0; j--) {
					if(checkJavaLetterPlusNumeric(lineOfCode.charAt(j))) {
						tempEnd = j+1; 
						for (int n = j; n >= 0; n--) {
							if(!checkJavaLetterPlusNumeric(lineOfCode.charAt(n))) {
								tempStart = n+1; 
								if(!checkKeywordMatch(lineOfCode.substring(tempStart, tempEnd))) {
									methodNames.add(lineOfCode.substring(tempStart, tempEnd));
								}
								j = n; 
								break;
							}else if(n == 0){
								tempStart = 0 ;
								if(!checkKeywordMatch(lineOfCode.substring(tempStart, tempEnd))) {
									methodNames.add(lineOfCode.substring(tempStart, tempEnd));
								}
								j = n;
								break;
							}
						}
					}else if(lineOfCode.charAt(j) != ' ' && lineOfCode.charAt(j) != '\t') {
						break ; 
					}
				}
			}
			
			// int someNumber = ((int) manage.converted(newNumber));
			// if the previous is a java character 
			if(checkJavaLetterPlusNumeric(lineOfCode.charAt(i-1))) {
				tempEnd = i; 
				for (int n = i-1; n >= 0; n--) {
					if(!checkJavaLetterPlusNumeric(lineOfCode.charAt(n))) {
						tempStart = n+1; 
						if(!checkKeywordMatch(lineOfCode.substring(tempStart, tempEnd))) {
							methodNames.add(lineOfCode.substring(tempStart, tempEnd));
						}
						break;
					}else if(n == 0){
						tempStart = 0 ;
						if(!checkKeywordMatch(lineOfCode.substring(tempStart, tempEnd))) {
							methodNames.add(lineOfCode.substring(tempStart, tempEnd));
						}
						break;
					}
				}
			}else {
				// if the previous is a non java character
				continue; 
			}
		}
		
//		for (String var : methodNames) {
//			System.out.println(var);
//		}
		
		return methodNames ; 
	}
	
	// Function to collect numerical values complexity 
	@Override
	public int numericalValuesComplexity(String lineOfCode, ArrayList<String> tokenList) {
		
		int nStart, nEnd ; 
		int complexity = 0 ; 
		
		for (int i = 0; i < lineOfCode.length(); i++) {
			if(Character.isDigit(lineOfCode.charAt(i))) {
				nStart = i ; 
				complexity++ ; 
				
				for (int j = nStart; j < lineOfCode.length(); j++) {
					if(!Character.isDigit(lineOfCode.charAt(j))) {
						
						if(lineOfCode.charAt(j) == '.'){
							continue; 
						}
						
						nEnd = j ;
						i = j ; 
						tokenList.add(lineOfCode.substring(nStart, nEnd));
						break;
						
					}else if(j == lineOfCode.length()-1) {
						nEnd = j ;
						i = j ;
						tokenList.add(lineOfCode.substring(nStart));
						break ; 
					}
				}
			}
		}
		
//		for (String info : tokenList) {
//			System.out.println(info);
//		}
		
		return complexity;
	}
	
	public boolean checkKeywordMatch(String token) {
		boolean match = false ; 
		
		if(!match) {
			for (int i = 0; i < highKeywords.length; i++) {
				if(token.equals(highKeywords[i])) {
					match = true; 
					break;
				}
			}
		}
		
		if(!match) {
			for (int i = 0; i < lowSimpleKeywords.length; i++) {
				if(token.equals(lowSimpleKeywords[i])) {
					match = true; 
					break;
				}
			}
		}
		
		if(!match) {
			for (int i = 0; i < lowComplexKeywords.length; i++) {
				if(token.equals(lowComplexKeywords[i])) {
					match = true; 
					break;
				}
			}
		}
		
		return match; 
	}
	
	// Function to check a valid java letter 
	public boolean checkJavaLetter(char ch) {
		boolean positive = false ;  
		Character underScore = new Character('_'); 
		Character dollar = new Character('$'); 
		
		if(Character.isLetter(ch)) {
			positive = true;
		}else if(underScore.equals(ch)) {
			positive = true;
		}else if(dollar.equals(ch)) {
			positive = true;
		}else {
			positive = false ;  
		}
		
		return positive;
	}
	
	// Function to check a valid java letter 
	public boolean checkJavaLetterPlusNumeric(char ch) {
		boolean positive = false;
		Character underScore = new Character('_');
		Character dollar = new Character('$');

		if (Character.isLetter(ch)) {
			positive = true;
		} else if (underScore.equals(ch)) {
			positive = true;
		} else if (dollar.equals(ch)) {
			positive = true;
		} else if (Character.isDigit(ch)){
			positive = true;
		}else {
			positive = false;
		}

		return positive;
	}
	
	// Function to check a valid java letter 
	public boolean checkJavaClassLetter(char ch) {
		boolean positive = false;
		Character underScore = new Character('_');
		Character dollar = new Character('$');

		if (Character.isUpperCase(ch)) {
			positive = true;
		} else if (underScore.equals(ch)) {
			positive = true;
		} else if (dollar.equals(ch)) {
			positive = true;
		} else {
			positive = false;
		}

		return positive;
	}
	
	// Function to check if a given token is a data type keyword 
	public boolean checkDataType(String token) {
		boolean positive = false ; 
		String[] dataTypes = {BOOLEAN, INT, LONG, CHAR, BYTE, FLOAT, DOUBLE, SHORT, VOID};
		
		for (String dType : dataTypes) {
			if(dType.equals(token)) {
				positive = true ;
				break;
			}
		}
		
		return positive;
	}

	
}
