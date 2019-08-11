package com.neo.codecomplexityanalyzer.service.serviceImpl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.neo.codecomplexityanalyzer.service.serviceImpl.CsServicesImpl;

public class CsPrimaryKeywordsTest {
	@Test
	public void test() {
		CsServicesImpl cs = new CsServicesImpl();
		
		String primaryKeywordTestCode01 = "FileReader f = new FileReader();";
		String primaryKeywordTestCode02 = "catch (FileNotFoundException e) {";
		String primaryKeywordTestCode03 = "static void accessFiles() throws FileNotFoundException{";
		String primaryKeywordTestCode04 = "FileReader f = new FileReader();catch (FileNotFoundException e) {}";
		String primaryKeywordTestCode05 = "FileReader f = new FileReader(); catch (FileNotFoundException e) { static void accessFiles() throws FileNotFoundException{}}";
		String primaryKeywordTestCode06 = "public class MyException {";
		String primaryKeywordTestCode07 = "void accessFiles() throws FileNotFoundException{";
		String primaryKeywordTestCode08 = "public static void main(String[] args) ";
		String primaryKeywordTestCode09 = "boolean isActive = true ;";
		String primaryKeywordTestCode10 = "for ( int i = 0; i < 100; i++)";
		String primaryKeywordTestCode11 = "case 1 : System.out.println(\"SUNDAY\");";
		String primaryKeywordTestCode12 = "public default void myDefaultMethod()";
		String primaryKeywordTestCode13 = "class SubClass extends SuperClass";
		String primaryKeywordTestCode14 = "final class FinalClass";
		String primaryKeywordTestCode15 = "if (a instanceof A) {";
		
		int result01 = cs.keywordComplexity(primaryKeywordTestCode01);
		int result02 = cs.keywordComplexity(primaryKeywordTestCode02);
		int result03 = cs.keywordComplexity(primaryKeywordTestCode03);
		int result04 = cs.keywordComplexity(primaryKeywordTestCode04);
		int result05 = cs.keywordComplexity(primaryKeywordTestCode05);
		int result06 = cs.keywordComplexity(primaryKeywordTestCode06);
		int result07 = cs.keywordComplexity(primaryKeywordTestCode07);
		int result08 = cs.keywordComplexity(primaryKeywordTestCode08);
		int result09 = cs.keywordComplexity(primaryKeywordTestCode09);
		int result10 = cs.keywordComplexity(primaryKeywordTestCode10);
		int result11 = cs.keywordComplexity(primaryKeywordTestCode11);
		int result12 = cs.keywordComplexity(primaryKeywordTestCode12);
		int result13 = cs.keywordComplexity(primaryKeywordTestCode13);
		int result14 = cs.keywordComplexity(primaryKeywordTestCode14);
		int result15 = cs.keywordComplexity(primaryKeywordTestCode15);
		
		assertTrue(result01 == 2);
		assertTrue(result02 == 1);
		assertTrue(result03 == 4);
		assertTrue(result04 == 3);
		assertTrue(result05 == 7);
		assertTrue(result06 == 2);
		assertTrue(result07 == 3);
		assertTrue(result08 == 3);
		assertTrue(result09 == 1);
		assertTrue(result10 == 2);
		assertTrue(result11 == 1);
		assertTrue(result12 == 3);
		assertTrue(result13 == 2);
		assertTrue(result14 == 2);
		assertTrue(result15 == 2);
	}

}
