package com.neo.codecomplexityanalyzer.service.serviceImpl;

import org.junit.Test;

public class CrServiceTest {

	@Test
    public void getSwitchScore1() {
        CrServicesImpl underTest = new CrServicesImpl("src/main/resources/sampleData/Recursive.java");
        int output = underTest.getControlScore();
    }
}
