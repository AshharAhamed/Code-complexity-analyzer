package com.neo.codecomplexityanalyzer.service.serviceImpl;

import org.junit.Test;

import static org.junit.Assert.*;

public class CTCServiceImplTest {

    @Test
    public void getControlScore() {
        CTCServiceImpl underTest = new CTCServiceImpl("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//If.java");
        int output = underTest.getControlScore();
        assertEquals(20, output);
    }

    @Test
    public void getIterativeControlScore() {
        CTCServiceImpl underTest = new CTCServiceImpl("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//For.java");
        int output = underTest.getIterativeControlScore();
        assertEquals(16, output);
    }

    @Test
    public void getCatchScore() {
        CTCServiceImpl underTest = new CTCServiceImpl("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//TestIF.java");
        int output = underTest.getCatchScore();
        assertEquals(1, output);
    }

    @Test
    public void getSwitchScore() {
        CTCServiceImpl underTest = new CTCServiceImpl("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//Switch.java");
        int output = underTest.getSwitchScore();
        assertEquals(7, output);
    }
    
    @Test
    public void getSwitchScore1() {
        CrServicesImpl underTest = new CrServicesImpl("src/main/resources/sampleData/Recursive.java");
        int output = underTest.getControlScore();
    }

}