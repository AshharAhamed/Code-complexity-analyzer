package com.neo.codecomplexityanalyzer.service.serviceImpl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CiJavaServicesTest {
	@Test
	public void getNumberOfAnsestorClasses() {
		CiJavaServicesImpl ci = new CiJavaServicesImpl(
				"/home/sahan/Documents/My Documents/SLIIT/SPM/Code-complexity-analyzer/project/Release/code/Backend/code-complexity-analyzer/src/main/resources/sampleData/InheritanceSample.java");
		int output = ci.getNumberOfAnsestors("SportsCar");
		int expected = 2;
		assertEquals(expected, output);
	}
}
