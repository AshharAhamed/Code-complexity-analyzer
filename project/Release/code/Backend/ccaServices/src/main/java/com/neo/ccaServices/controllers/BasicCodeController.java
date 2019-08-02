package com.neo.ccaServices.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.ccaServices.resources.CCA_Uilities;

@RestController
public class BasicCodeController {
	
	
	// REST service to get the line count 
	@GetMapping(path="/line-count")
	public ResponseEntity<Integer> getLineCount(){
		String code = "" ; 
		int lineCount = 0 ; 
		CCA_Uilities ccaUtil = new CCA_Uilities();

		code = ccaUtil.getSourceCode();
		lineCount = ccaUtil.findSourceCodeLineCount(code); 
		
		return (new ResponseEntity<Integer>(lineCount, HttpStatus.OK));
	}
	
	// REST service to get the source code as an array
	@GetMapping(path="/get-code")
	public ResponseEntity<String[]> getSourceCode(){
		String code = "" ; 
		int lineCount = 0 ; 
		String [] lineArr ; 
		CCA_Uilities ccaUtil = new CCA_Uilities();

		code = ccaUtil.getSourceCode();
		lineCount = ccaUtil.findSourceCodeLineCount(code); 
		lineArr = ccaUtil.collectAllSourceCodeLines(code, lineCount);
		
		return (new ResponseEntity<String[]>(lineArr, HttpStatus.OK));
	}
}
