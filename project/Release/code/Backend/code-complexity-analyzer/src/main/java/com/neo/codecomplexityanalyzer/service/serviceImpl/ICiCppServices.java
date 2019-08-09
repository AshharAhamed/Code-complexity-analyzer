package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICiCppServices {
	public ArrayList<String> getAllClassNames();
	
	public HashMap<String,String>getClassMapping();

	public ArrayList<String> getAncestorClasses(String childClass);

	public int getNumberOfAncestorClasses(String childClass);

}
