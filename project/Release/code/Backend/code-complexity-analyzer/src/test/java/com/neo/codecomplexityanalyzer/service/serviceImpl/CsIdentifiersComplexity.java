package com.neo.codecomplexityanalyzer.service.serviceImpl;

import static org.junit.Assert.*;

import org.junit.Test;

public class CsIdentifiersComplexity {

	@Test
	public void test() {
		CsServicesImpl cs = new CsServicesImpl();
		
		String lineOfCode1 = "class StyleSheet{" ;  
		String lineOfCode2 = "class StyleSheet extends StyleClass	{" ;  
		String lineOfCode3 = "public class StyleSheet {" ;  
		String lineOfCode4 = "public class StyleSheet extends StyleClass{" ;  
		String lineOfCode5 = "public class StyleSheet extends StyleClass     {" ;  
		String lineOfCode6 = "public     class StyleSheet      extends StyleClass{" ;  
		String lineOfCode7 = "public 		class    	StyleSheet			 extends   	StyleClass		{" ;  
		
		// For Classes 
		String classlLineOfCode1 = "class StyleSheet{" ;  
		String classlLineOfCode2 = "class StyleSheet extends StyleClass	{" ;  
		String classlLineOfCode3 = "public class StyleSheet {" ;  
		String classlLineOfCode4 = "public class StyleSheet extends StyleClass{" ;  
		String classlLineOfCode5 = "public class StyleSheet extends StyleClass     {" ;  
		String classlLineOfCode6 = "public     class StyleSheet      extends StyleClass{" ;  
		String classlLineOfCode7 = "public 		class    	StyleSheet			 extends   	StyleClass		{" ;  
		String classlLineOfCode8 = "public 		class    	Style_Sheet			 extends   	$StyleClass		{" ; 
		String classlLineOfCode9 = "public 		class    	_StyleSheet			 extends   	$$$StyleClass		{" ;  
		String classlLineOfCode10 = "public 		class    	_$_StyleSheet			 extends   	___StyleClass		{" ;  
		String classlLineOfCode11 = "public 		class    	Style12Sheet			 extends   	Styl234eC64lass		{" ;  
		String classlLineOfCode12 = "public 		class    	(StyleSheet)			 extends   	().StyleClass.wre()		{" ;  
		String classlLineOfCode13 = "public 		class    	==StyleSheet			 extends   	StyleClass;		{" ;  
		String classlLineOfCode14 = "public 		class    	*StyleSheet.out()			 extends   	=StyleClass		{" ;  
		String classlLineOfCode16 = "public 		class    	ctyleSheet123			 extends   	Style Class		{" ;  
		String classlLineOfCode17 = "public 		class    	.ctyleSheet123			 extends   	>Style Class		{" ; 
		String classlLineOfCode18 = "private void findIdentifierRecursinve(ArrayList<String> idList, >String token, int start) {  =public 		;class    	.ctyleSheet123			 .extends   	>Style Class		{" ;  
		String classlLineOfCode19 = "findIdentifier.Recursinve(ArrayList<String>" ;
		String classlLineOfCode20 = "findIdentifier4Recursinve(ArrayList<String>" ;
		String classlLineOfCode21 = "findIdentifier_Recursinve(ArrayList<String>" ;
		
		// For Variables 
		String variableLineOfCode1 = "int someNumber = 10;" ;  
		String variableLineOfCode2 = "byte someNumberfloat, anotherNoumber;" ; 
		String variableLineOfCode3 = "byte someNumber; float anotherNoumber; long longNumber;" ;
		String variableLineOfCode4 = "float someNumber = 10, anotherDoubleNumber = 12;" ; 
		String variableLineOfCode5 = "char someNumberdouble = 10;" ; 
		String variableLineOfCode6 = "char characterfloat123 = 'c' \n" ; 
		String variableLineOfCode7 = "long some$$$numberlong = 1234 ;" ; 
		String variableLineOfCode8 = "boolean some234Conditionbyte = true ;" ; 
		String variableLineOfCode9 = "int _someNumber = 10;" ;
		String variableLineOfCode10 = "float _longsomeNumber = 10;" ;
		String variableLineOfCode11 = "int __someNumber = 10;" ;
		String variableLineOfCode12 = "byte _some_charNumber = 10;" ;
		String variableLineOfCode13 = "long $$someNumber = 10;" ;
		String variableLineOfCode14 = "boolean somebooleanNumber=10;" ; 
		String variableLineOfCode15 = "int someNumber = 10;float num = 2.3;int number=10, anotherNumber;" ; 
		String variableLineOfCode16 = "int[] someNumberlong ;" ;
		String variableLineOfCode17 = "byte [] someNumberlong ;" ;
		String variableLineOfCode18 = "long 	[][] someNumberlong ;" ;
		String variableLineOfCode19 = "double []someNumberlong ;" ;
		String variableLineOfCode20 = "float [][]someNumberlong ;" ;
		String variableLineOfCode21 = "char [][][] someNumberlong ;" ;
		String variableLineOfCode22 = "byte[][] someNumberlong, [] num ;" ;
		String variableLineOfCode23 = "int someNumberlong[], some[] ;;;" ;
		String variableLineOfCode24 = "long someNumberlong[][], somme[][][] ;" ;
		String variableLineOfCode25 = "boolean someNumberlong [] = {true, false, true},condition; long [] longArr;int munberfloat,longNumber, bytenumber;" ;
		String variableLineOfCode26 = "boolean someNumberlong 			[][] ; boolean sn[][]; boolean [] bn ;" ;
		
		// method names
		String methodLineOfCode1 = "public static void main(String[] args) {" ;  
		String methodLineOfCode2 = "cs.collectVariableNames(methodLineOfCode1);" ; 
		String methodLineOfCode3 = "ArrayList<String> tests = new ArrayList<String>(); " ;
		String methodLineOfCode4 = "String methodLineOfCode4 = \"}else if(lineOfCode.charAt(i) == '=') {\" ;" ; 
		String methodLineOfCode5 = "for (int j = i; j < len.size(); j++) {" ;
		String methodLineOfCode6 = "public void newFunction(int i, float j)" ;
		String methodLineOfCode7 = "public void newFunction (int i, float j)" ;
		String methodLineOfCode8 = "public void newFunction	(int i, float j)" ;
		String methodLineOfCode9 = "public void newFunction 			 (int i, float j)" ;
		String methodLineOfCode10 = "int someNumber = ((int) manage.converted(newNumber));" ;
		String methodLineOfCode11 = "int (someNumber.make()) = ((int) manage.converted(newNumber));" ;
		String methodLineOfCode12 = "int someNumber = ((int) manage.converted(newNumber));" ;
		String methodLineOfCode13 = "int someNumber = ((int) manage.converted(newNumber));" ;
		String methodLineOfCode14 = "int someNumber = ((int) manage.converted(newNumber));" ;
		String methodLineOfCode15 = "int someNumber = ((int) manage.converted(newNumber));" ;
		
		// Object Names 
		String objLineOfCode1 = "catch(Exception e)" ;
		String objLineOfCode2 = "CsServicesImpl csString = new CsServicesImpl<String>();" ;
		String objLineOfCode3 = "String s1ArrayList;" ;
		String objLineOfCode4 = "String s1;CsServicesImpl cs;" ;
		String objLineOfCode5 = "String s1; NullPointerException ne;CsServicesImpl cs = new CsServicesImpl();" ;
		String objLineOfCode6 = "String sCsServicesImpl1, s2, s3 ;" ;
		String objLineOfCode7 = "<some>CsServicesImpl cs1, cs2, cs3 = new CsServicesImpl(), cs4,  cs5 = new CsServicesImpl(arg1, arg2, agr3), cs6;" ;
		String objLineOfCode8 = "ArrayList<String> variableExceptionNames = new ArrayList<String>();" ;
		String objLineOfCode9 = "String [] s1 = {\\\"asd\\\", \\\"asd\\\", \\\"asd\\\", }" ;
		String objLineOfCode10 = "ArrayList[] s1 = {\\\"asd\\\", \\\"asd\\\", \\\"asd\\\", }" ;
		String objLineOfCode11 = "Exception []s1 = {\\\"asd\\\", \\\"aCsServicesImplsd\\\", \\\"asd\\\", }" ;
		String objLineOfCode12 = "String s1[] = {\\\"asd\\\", \\\"asd\\\", \\\"asd\\\", }" ;
		String objLineOfCode13 = "ArrayList s1 []= {\\\"asd\\\", \\\"asArrayListd\\\", \\\"asd\\\", }" ;
		String objLineOfCode14 = "String s1 [] = {\\\"asd\\\", \\\"asd\\\", \\\"asd\\\", }" ;
		String objLineOfCode15 = "String [][] 	s1 = {\\\"asd\\\", \\\"asd\\\", \\\"asd\\\", }" ;
		String objLineOfCode16 = "ArrayList[][] 	s1 = {\\\"asd\\\", \\\"asd\\\", \\\"asdNullPointerException\\\", }" ;
		String objLineOfCode17 = "Exception [][]s1 = {\\\"asd\\\", \\\"asd\\\", \\\"asd\\\", }" ;
		String objLineOfCode18 = "String s1[][] = {\\\"asd\\\", \\\"asd\\\", \\\"asd\\\", }" ;
		String objLineOfCode19 = "CsServicesImpl s1 [][]= {\\\"asd\\\", \\\"aNullPointerExceptionsd\\\", \\\"asd\\\", }" ;
		String objLineOfCode20 = "Exception sNullPointerException1	 [][] = {\\\"asd\\\", \\\"asd\\\", \\\"asd\\\", }" ;
		String objLineOfCode21 = "String s1[], s2[];";
		String objLineOfCode22 = "CsServicesImpl s1[],	 s2 = new CsServicesImpl(arg1, arg2, agrg3);";
		String objLineOfCode23 = "ArrayList [] s1[], sCsServicesImpl2[];";
		String objLineOfCode24 = "ArrayList [][] s1[], s2[];";
		String objLineOfCode25 = "String s1 , sNullPointerException2[];";
		String objLineOfCode26 = "CsServicesImpl [][][] 	s1[], sCsServicesImpl2[];";
		String objLineOfCode27 = "CsServicesImpl cs = CsServicesImpl.getInstance();" ;
		String objLineOfCode28 = "CsServicesImpl cs 			= new CsServicesImpl();" ;
		String objLineOfCode29 = "CsServicesImpl 		cs = new CsServicesImpl();" ;
		String objLineOfCode30 = "try(NullPointerException 	e)" ;
		String objLineOfCode31 = "try(NullPointerException 	e	)" ;
		String objLineOfCode32 = "try(	NullPointerException 	e	)" ;
		String objLineOfCode33 = "try( NullPointerException 	eNullPointerException	)" ;
		String objLineOfCode34 = "try(		NullPointerException 	  	e			)" ;
		
		String numericLineOfCode1 = "int i = 203" ;
		String numericLineOfCode2 = "double d = 123.623" ;
		String numericLineOfCode3 = "float f = 4.7333434f;" ;
		String numericLineOfCode4 = "for (i = 0; i < 5; i++)"; 
		String numericLineOfCode5 = "int a[5] = { 1, 2, 3, 4, 5 }; ";
		String numericLineOfCode6 = "struct se s = { 15, 98.9, \"Some Text\" };";
		String numericLineOfCode7 = "if (str == null || str.length() == 0) {";
		String numericLineOfCode8 = "if ( !Character.isDigit( str.charAt( 5 ) ) && str.charAt( 8 ) != localeMinusSign ) {";
		String numericLineOfCode9 = "for (int i = 0; i < 10000000; i++)";
		String numericLineOfCode10 = "final static int a=1,b=2,c=3,d=4,e=5,f=8,g=3,h=5,i=1,j=1,k=2,l=3,m=4,n=5,o=7,p=8,q=1,r=2,s=3,t=4,u=6,v=6,w=6,x=5,y=1,z=7;";
		String numericLineOfCode11 = "{adder = 2 + adder ; }";
		String numericLineOfCode12 = "hm.put('a', 1);     hm.put('b', 2);     hm.put('c', 3);     hm.put('d', 4);";
		
//		fail("Not yet implemented");
	}

}
