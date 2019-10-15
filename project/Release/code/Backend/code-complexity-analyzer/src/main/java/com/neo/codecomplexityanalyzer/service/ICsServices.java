package com.neo.codecomplexityanalyzer.service;

import java.util.ArrayList;
import java.util.HashSet;

public interface ICsServices {
	public int[] getAllCsValues(String sourceCode);
//	public int refAndDerefComplexity(String lineOfCode);
	public int keywordComplexity(String lineOfCode);
	public int arithmeticOperatorsComplexity(String lineOfCode);
	public int realtionOperatorsComplexity(String lineOfCode);
	public int logicalOperatorsComplexity(String lineOfCode);
	public int bitwiseOperatorsComplexity(String lineOfCode);
	public int miscOperatorsComplexity(String lineOfCode);
	public int assignmentOperatorsComplexity(String lineOfCode);
	public int manipulatorsComplexity(String lineOfCode);
	public int escapeCharactersComplexity(String lineOfCode);
	public int textWithinQuotesComplexity(String lineOfCode);
	public int identifierComplexity(String lineOfCode, HashSet<String> idList);
        public int numericalValuesComplexity(String lineOfCode, ArrayList<String> tokenList);
}
