package com.neo.codecomplexityanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.neo.codecomplexityanalyzer.configs.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class })
public class CodeComplexityAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeComplexityAnalyzerApplication.class, args);
	}

}
