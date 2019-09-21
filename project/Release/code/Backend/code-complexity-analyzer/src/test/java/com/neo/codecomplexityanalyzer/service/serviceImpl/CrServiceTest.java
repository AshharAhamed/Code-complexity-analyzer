package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.HashMap;

import org.junit.Test;

public class CrServiceTest {

	@Test
    public void getSwitchScore1() {
        CrServicesImpl underTest = new CrServicesImpl("src/main/resources/sampleData/Recursive.java");
        HashMap<Integer, Integer> output = underTest.getControlScore();
    }
}
