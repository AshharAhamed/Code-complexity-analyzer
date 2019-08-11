package com.neo.codecomplexityanalyzer.service.serviceImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.neo.codecomplexityanalyzer.service.serviceImpl.CsServicesImpl;

public class CsArithmeticOperatorsTest {

	@Test
	public void test() {
		CsServicesImpl cs = new CsServicesImpl();
		
		String arithmeticTestCode01 = "System.out.println(\"    i = \" + i);";
		String arithmeticTestCode02 = "System.out.println(\"    i + j = \" + (i + j));";
		String arithmeticTestCode03 = "System.out.println(\"    i * j *= \" + (i * j))";
		String arithmeticTestCode04 = "System.out.println(\"    i / j = \" + (i-- / j));";
		String arithmeticTestCode05 = "System.out.println(\"    i % j %= \" + (i % j++));";
		String arithmeticTestCode06 = "System.out.println(\"    i * x = \" + (i * x))";
		String arithmeticTestCode07 = "System.out.println(\"    x / y += \" + (x / y));";
		String arithmeticTestCode08 = "i = \\\" + i);";
		String arithmeticTestCode09 = "i + j = \\\" + (i + j));";
		String arithmeticTestCode10 = "for ( int i = 0; i < 100; i++)";
		String arithmeticTestCode11 = "i * j *= \\\" + (i * j))";
		String arithmeticTestCode12 = "i % j %= \\\" + (i % j++));";
		String arithmeticTestCode13 = "i * x = \\\" + (i * x))";
		String arithmeticTestCode14 = "x / y += \\\" + (x / y));";
		String arithmeticTestCode15 = "i = \\\\\\\" + i);";
		
		int result01 = cs.arithmeticOperatorsComplexity(arithmeticTestCode01);
		int result02 = cs.arithmeticOperatorsComplexity(arithmeticTestCode02);
		int result03 = cs.arithmeticOperatorsComplexity(arithmeticTestCode03);
		int result04 = cs.arithmeticOperatorsComplexity(arithmeticTestCode04);
		int result05 = cs.arithmeticOperatorsComplexity(arithmeticTestCode05);
		int result06 = cs.arithmeticOperatorsComplexity(arithmeticTestCode06);
		int result07 = cs.arithmeticOperatorsComplexity(arithmeticTestCode07);
		int result08 = cs.arithmeticOperatorsComplexity(arithmeticTestCode08);
		int result09 = cs.arithmeticOperatorsComplexity(arithmeticTestCode09);
		int result10 = cs.arithmeticOperatorsComplexity(arithmeticTestCode10);
		int result11 = cs.arithmeticOperatorsComplexity(arithmeticTestCode11);
		int result12 = cs.arithmeticOperatorsComplexity(arithmeticTestCode12);
		int result13 = cs.arithmeticOperatorsComplexity(arithmeticTestCode13);
		int result14 = cs.arithmeticOperatorsComplexity(arithmeticTestCode14);
		int result15 = cs.arithmeticOperatorsComplexity(arithmeticTestCode15);
		
		assertTrue(result01 == 1);
		assertTrue(result02 == 3);
		assertTrue(result03 == 3);
		assertTrue(result04 == 4);
		assertTrue(result05 == 4);
		assertTrue(result06 == 3);
		assertTrue(result07 == 3);
		assertTrue(result08 == 1);
		assertTrue(result09 == 3);
		assertTrue(result10 == 1);
		assertTrue(result11 == 3);
		assertTrue(result12 == 4);
		assertTrue(result13 == 3);
		assertTrue(result14 == 3);
		assertTrue(result15 == 1);
	}

}
