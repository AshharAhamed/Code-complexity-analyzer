/*
 * -----------------------------------------------------------------------------------------------------
 * 								Created By Wellala S. S> IT17009096
 * -----------------------------------------------------------------------------------------------------
 *
 */

package com.neo.codecomplexityanalyzer.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICiJavaServices {
	public ArrayList<String> getClassNames();

	public int getNumberOfAnsestors(String className);

	public ArrayList<String> getAnsestorClassNames(String className);

	public int calComplexityDueToInheritance(String className);

	public int calTotalComplexityDueToInheritance();

	public HashMap<String, Integer> getClassWithTheNumberOfAnsestors();

	public HashMap<String, Integer> complexityOfAllClassesDueToInheritance();

}
