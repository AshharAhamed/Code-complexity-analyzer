package com.neo.codecomplexityanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.neo.codecomplexityanalyzer.service.serviceImpl.CsServicesImpl;
import com.neo.codecomplexityanalyzer.service.serviceImpl.GeneralServiceImpl;

@SpringBootApplication
public class CodeComplexityAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeComplexityAnalyzerApplication.class, args);
	}

}


