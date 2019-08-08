package com.neo.codecomplexityanalyzer.service.serviceImpl;

public interface ICsServices {
	public int[] getAllCsValues(String sourceCode);
//	public int refAndDerefComplexity(String lineOfCode);
	
	public int keywordsComplexity(String lineOfCode);

//	public int arithmeticOperatorsComplexity(String lineOfCode);
//	public int realtionOperatorsComplexity(String lineOfCode);
//	public int logicalOperatorsComplexity(String lineOfCode);
//	public int bitwiseOperatorsComplexity(String lineOfCode);
//	public int miscOperatorsComplexity(String lineOfCode);
//	public int assignmentOperatorsComplexity(String lineOfCode);
	public int manipulatorsComplexity(String lineOfCode, GeneralServiceImpl gs);
	public int textWithinQuotesComplexity(String lineOfCode);
//	public int identifierComplexity(String lineOfCode);
//	public int numericalValuesComplexity(String lineOfCode);
}
