/*
 * -----------------------------------------------------------------------------------------------------
 * 								Created By Wellala S. S> IT17009096
 * -----------------------------------------------------------------------------------------------------
 *
 */

package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;

public interface ICiServices {
	public ArrayList<String> getClassNames();

	public int getNumberOfAnsestors(String className);

	public ArrayList<String> getAnsestorClassNames(String className);
}
