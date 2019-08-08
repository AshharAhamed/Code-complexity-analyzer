package com.neo.codecomplexityanalyzer.service.serviceImpl;

public class DemoMainApp {
	public static void main(String[] args) {

		CiJavaServicesImpl ci = new CiJavaServicesImpl(
				"/home/sahan/Documents/My Documents/SLIIT/SPM/Code-complexity-analyzer/project/Release/code/Backend/code-complexity-analyzer/src/main/resources/sampleData/InheritanceSample.java");

		
		for (String string : ci.getAnsestorClassNames("SportsCar")) {
			System.out.println(string);
		}
		// System.out.println("Count Returned : " + count);
	}

}
