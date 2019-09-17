package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.neo.codecomplexityanalyzer.model.CiResultModel;

public class DemoMainApp {
	public static void main(String[] args) {

		CiCppServicesImpl ci = new CiCppServicesImpl(
				"/home/sahan/Documents/My Documents/SLIIT/SPM/Code-complexity-analyzer/project/Release/code/Backend/code-complexity-analyzer/src/main/resources/sampleData/CppInheritanceSample.cpp");

		CiJavaServicesImpl ciJ = new CiJavaServicesImpl(
				"/home/sahan/Documents/My Documents/SLIIT/SPM/Code-complexity-analyzer/project/Release/code/Backend/code-complexity-analyzer/src/main/resources/sampleData/InheritanceSample.java");

//	HashMap<Integer,CiResultModel> list =ciJ.getClassNameIndexByLineNumber();
//	Iterator<Integer> itr = list.keySet().iterator();
//
//	while (itr.hasNext()) 
//	{
//	    Integer key = itr.next();
//	    CiResultModel value = list.get(key);
//	    
//	    System.out.println("The key is :: " + key + ", and value is :: " + value.getClassHierachy() );
//	}
//		 System.out.println(ciJ.calComplexityDueToInheritance("SportsCar"));
//		ci.identifyClassStructure();
//		System.out.println(ci.getNumberOfAncestorClasses("TA"));
//		ci.identifyClassStructure();
		
		System.out.println(ci.getCiCppDetailsWithLineNumbers());
		GeneralServiceImpl gs = new GeneralServiceImpl();
		gs.getSourceCode("/home/sahan/Documents/My Documents/SLIIT/SPM/Code-complexity-analyzer/project/Release/code/Backend/code-complexity-analyzer/src/main/resources/sampleData/CppInheritanceSample.cpp");
	System.out.println(gs.getLineByIndex(143));
//		System.out.println(ciJ.getClassNames());
//		//ciJ.complexityOfAllClassesDueToInheritance();
//		for (String string : ci.getAncestorClasses("TA")) {
//			System.out.print(string);
//		}

//		for (String string : ciJ.getAnsestorClassNames("SportsCar")) {
//			System.out.println(string);
//		}

//		HashMap<String, String> map = ci.getClassMapping();
//		for (String name : map.keySet()) {
//			String key = name.toString();
//			String value = map.get(name).toString();
//			System.out.println(key + " " + value);
//		}
		// System.out.println("Count Returned : " + count);
//	System.out.println(ciJ.calTotalComplexityDueToInheritance());
//	
	}

}
