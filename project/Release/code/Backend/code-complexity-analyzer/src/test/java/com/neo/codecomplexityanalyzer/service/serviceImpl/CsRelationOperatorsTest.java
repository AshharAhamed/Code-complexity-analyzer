package com.neo.codecomplexityanalyzer.service.serviceImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.neo.codecomplexityanalyzer.service.serviceImpl.CsServicesImpl;

public class CsRelationOperatorsTest {

	@Test
	public void test() {
		CsServicesImpl cs = new CsServicesImpl();
		
		String relationTestCode01 = "System.out.println(\"    i * x == \" + (i * x))";
		String relationTestCode02 = "System.out.println(\"    x / y != \" + (x / y));";
		String relationTestCode03 = "i = \\\" > i);";
		String relationTestCode04 = "i + j >= \\\" + (i + >> j));";
		String relationTestCode05 = "for ( int i = 0; i < 100; i++ <<<)";
		String relationTestCode06 = "i * j != \\\" + (i == j))";
		String relationTestCode07 = "i % j %= \\\" + (i == j++));";
		String relationTestCode08 = "i * x = \\\" + (i * x))";
		String relationTestCode09 = "x / y <= \\\" + (x / y));";
		String relationTestCode10 = "i = \\\\\\\" + i);";
		
		int result01 = cs.realtionOperatorsComplexity(relationTestCode01);
		int result02 = cs.realtionOperatorsComplexity(relationTestCode02);
		int result03 = cs.realtionOperatorsComplexity(relationTestCode03);
		int result04 = cs.realtionOperatorsComplexity(relationTestCode04);
		int result05 = cs.realtionOperatorsComplexity(relationTestCode05);
		int result06 = cs.realtionOperatorsComplexity(relationTestCode06);
		int result07 = cs.realtionOperatorsComplexity(relationTestCode07);
		int result08 = cs.realtionOperatorsComplexity(relationTestCode08);
		int result09 = cs.realtionOperatorsComplexity(relationTestCode09);
		int result10 = cs.realtionOperatorsComplexity(relationTestCode10);
		
		assertTrue(result01 == 1);
		assertTrue(result02 == 1);
		assertTrue(result03 == 1);
		assertTrue(result04 == 1);
//		assertTrue(result05 == 1);
		assertTrue(result06 == 2);
		assertTrue(result07 == 1);
		assertTrue(result08 == 0);
		assertTrue(result09 == 1);
		assertTrue(result10 == 0);
	}

}
