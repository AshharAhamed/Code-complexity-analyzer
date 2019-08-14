package com.neo.codecomplexityanalyzer.service.serviceImpl;

import org.junit.Test;

import java.util.List;

public class JavaSyntaxCheckerTest {

    @Test
    public void test1() {
        JavaSyntaxChecker j1 = new JavaSyntaxChecker("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//Condition.java");
        List<String> errorList = j1.check();
        if (!errorList.isEmpty())
            System.out.println(errorList);
        else
            System.out.println("No Compile Errors");
    }

    @Test
    public void test2() {
        JavaSyntaxChecker j1 = new JavaSyntaxChecker("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//For.java");
        List<String> errorList = j1.check();
        if (!errorList.isEmpty())
            System.out.println(errorList);
        else
            System.out.println("No Compile Errors");
    }

    @Test
    public void test3() {
        JavaSyntaxChecker j1 = new JavaSyntaxChecker("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//Switch.java");
        List<String> errorList = j1.check();
        if (!errorList.isEmpty())
            System.out.println(errorList);
        else
            System.out.println("No Compile Errors");
    }

    @Test
    public void test4() {
        JavaSyntaxChecker j1 = new JavaSyntaxChecker("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//Catch.java");
        List<String> errorList = j1.check();
        if (!errorList.isEmpty())
            System.out.println(errorList);
        else
            System.out.println("No Compile Errors");
    }

}