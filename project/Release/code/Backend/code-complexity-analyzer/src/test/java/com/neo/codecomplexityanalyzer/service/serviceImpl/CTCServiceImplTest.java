package com.neo.codecomplexityanalyzer.service.serviceImpl;

import com.neo.codecomplexityanalyzer.controller.BasicCodeController;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CTCServiceImplTest {

    @Test
    public void getControlScore() {
        CTCServiceImpl underTest = new CTCServiceImpl("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//Condition.java");
        int output = underTest.getControlScore();
        assertEquals(22, output);
    }

    @Test
    public void getIterativeControlScore() {
        CTCServiceImpl underTest = new CTCServiceImpl("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//For.java");
        int output = underTest.getIterativeControlScore();
        assertEquals(14, output);
    }

    @Test
    public void getCatchScore() {
        CTCServiceImpl underTest = new CTCServiceImpl("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//Catch.java");
        int output = underTest.getCatchScore();
        assertEquals(2, output);
    }

    @Test
    public void getSwitchScore() {
        CTCServiceImpl underTest = new CTCServiceImpl("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//Switch.java");
        int output = underTest.getSwitchScore();
        assertEquals(7, output);
    }

    @Test
    public void getTotalCTCScore() {
        BasicCodeController underTest = new BasicCodeController();
        ResponseEntity<HashMap> output = underTest.getCTCTotalScore("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//Recursive.java");
    }

    @Test
    public void getTotalCTCScore2() {
        BasicCodeController underTest = new BasicCodeController();
        ResponseEntity<HashMap> output = underTest.getCTCTotalScore("C://Student//SPM//Code-complexity-analyzer//project//Release//code//Backend//code-complexity-analyzer//src//main//resources//sampleData//NonRecursive.java");
    }
}