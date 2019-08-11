/*
-------------------------------------------------------------------------------------------------------
--  Date        Sign    History
--  ----------  ------  --------------------------------------------------------------------------------
--  2019-08-06  Sathira  185817, Added CTC functions.
--  ----------  ------  --------------------------------------------------------------------------------
*/
package com.neo.codecomplexityanalyzer.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neo.codecomplexityanalyzer.service.serviceImpl.GeneralServiceImpl;
import com.neo.codecomplexityanalyzer.service.serviceImpl.CTCServiceImpl;
import com.neo.codecomplexityanalyzer.service.serviceImpl.CiJavaServicesImpl;
import com.neo.codecomplexityanalyzer.service.serviceImpl.CsServicesImpl;

import org.apache.commons.io.FilenameUtils;

@CrossOrigin(origins = { "http://localhost:1234", "http://localhost:3000" })

@RestController
public class BasicCodeController {

	// REST service to get the line count
	@GetMapping(path = "/line-count")
	public ResponseEntity<Integer> getLineCount(@RequestHeader("file-path") String FilePath) {
		String code = "";
		int lineCount = 0;
		GeneralServiceImpl ccaUtil = new GeneralServiceImpl();

		code = ccaUtil.getSourceCode(FilePath);
		lineCount = ccaUtil.findSourceCodeLineCount(code);

		return (new ResponseEntity<Integer>(lineCount, HttpStatus.OK));
	}

	// REST service to get the file type
	@GetMapping(path = "/file-type")
	public ResponseEntity<String> getFileType(@RequestHeader("file-path") String FilePath) {
		GeneralServiceImpl ccaUtil = new GeneralServiceImpl();
		String type = ccaUtil.getSourceCodeType(FilePath);
		return (new ResponseEntity<String>(type, HttpStatus.OK));
	}
	
	// REST service to get the source code as an array
	@GetMapping(path = "/get-code")
	public ResponseEntity<String[]> getSourceCode(@RequestHeader("file-path") String FilePath) {
		String code = "";
		int lineCount = 0;
		String[] lineArr;
		GeneralServiceImpl ccaUtil = new GeneralServiceImpl();
		ccaUtil.getSourceCode(FilePath);
		code = ccaUtil.getOriginalSourceCode();
		lineCount = ccaUtil.findSourceCodeLineCount(code);
		lineArr = ccaUtil.collectAllSourceCodeLines(code, lineCount);

		return (new ResponseEntity<String[]>(lineArr, HttpStatus.OK));
	}

//--------------------------------------- CTC End Points ---------------------------------------------------------------
	@GetMapping(path = "/get-ctc/if")
	public ResponseEntity<Integer> getCTCScore(@RequestHeader("file-path") String FilePath) {
		CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
		return (new ResponseEntity<Integer>(cctUtil.getControlScore(), HttpStatus.OK));
	}

	@GetMapping(path = "/get-ctc/itc")
	public ResponseEntity<Integer> getCTCITCScore(@RequestHeader("file-path") String FilePath) {
		CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
		return (new ResponseEntity<Integer>(cctUtil.getIterativeControlScore(), HttpStatus.OK));
	}

	@GetMapping(path = "/get-ctc/catch")
	public ResponseEntity<Integer> getCTCCatchScore(@RequestHeader("file-path") String FilePath) {
		CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
		return (new ResponseEntity<Integer>(cctUtil.getCatchScore(), HttpStatus.OK));
	}

	@GetMapping(path = "/get-ctc/case")
	public ResponseEntity<Integer> getCTCSwitchScore(@RequestHeader("file-path") String FilePath) {
		CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
		return (new ResponseEntity<Integer>(cctUtil.getSwitchScore(), HttpStatus.OK));
	}

	@GetMapping(path = "/get-ctc")
	public ResponseEntity<HashMap> getCTCTotalScore(@RequestHeader("file-path") String FilePath) {
		HashMap<String, String> hashMap = new HashMap<>();
		CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
		int ctcTotal = cctUtil.getControlScore() + cctUtil.getIterativeControlScore() + cctUtil.getCatchScore() + cctUtil.getSwitchScore();
		hashMap.put("SourceCode", cctUtil.getSourceCode());
		hashMap.put("ControlScore", String.valueOf(cctUtil.getControlScore()));
		hashMap.put("ITCScore", String.valueOf(cctUtil.getIterativeControlScore()));
		hashMap.put("CatchScore", String.valueOf(cctUtil.getCatchScore()));
		hashMap.put("SwitchScore", String.valueOf(cctUtil.getSwitchScore()));
		hashMap.put("TotalCTCScore", String.valueOf(ctcTotal));
		return (new ResponseEntity<HashMap>(hashMap, HttpStatus.OK));
	}

	/*
	 * -----------------------------------------------------------------------------
	 * Ci Service End Points
	 * -----------------------------------------------------------------------------
	 * 
	 */

	@GetMapping(path = "/get-ci/ansestors")
	public ArrayList<String> getAnsestorClassNames(@RequestHeader("file-path") String FilePath,
			@RequestParam String nameOfTheClass) {
		CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
		ArrayList<String> classNames = ci.getAnsestorClassNames(nameOfTheClass);
		return classNames;
	}

	// This service is giving a console log as a briefing of all the details
	@GetMapping(path = "/get-ci/class-level-data")
	public void getClassLevelDetails(@RequestHeader("file-path") String FilePath) {
		CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
		ci.getClassLevelDetails();
	}

	@GetMapping(path = "/get-ci/class-map")
	public HashMap<String, String> getClassMap(@RequestHeader("file-path") String FilePath) {
		CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
		return ci.getClassMapping();
	}

	@GetMapping(path = "/get-ci/class-names")
	public ArrayList<String> getClassNames(@RequestHeader("file-path") String FilePath) {
		CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
		return ci.getClassNames();
	}

	@GetMapping(path = "/get-ci/num-of-ancestors")
	public int getNumberOfAnsestorClasses(@RequestHeader("file-path") String FilePath,
			@RequestParam String nameOfTheClass) {
		CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
		return ci.getNumberOfAnsestors(nameOfTheClass);
	}

	//This method is for future usages
	@GetMapping(path = "/get-ci/conneted-points")
	public void getStronglyConnectedPoints(@RequestHeader("file-path") String FilePath) {
		CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
		ci.identifyStronglyConnectedClasses();
	}

	/*
	 * -----------------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------------
	 */
	 /*
	 * -----------------------------------------------------------------------------
	 * CNC Service End Points
	 * -----------------------------------------------------------------------------
	 * 
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/get-ctc/nested-if")
	public ResponseEntity<Integer> getCNCNestedIfScore(@RequestHeader("file-path") String FilePath) {
		CNCService cncService = new CNCServiceImpl(FilePath);
		return (new ResponseEntity<Integer>(cncService.getNestedIfControlScore(), HttpStatus.OK));
	}
	
	//--------------------------------------- Cs service End Points ------------------------------------------------------
	@GetMapping(path = "/get-cs")
	public ResponseEntity<int[]> getCsScore(@RequestHeader("file-path") String FilePath) {
		CsServicesImpl cs = new CsServicesImpl();
		GeneralServiceImpl gs = new GeneralServiceImpl();
		String sourceCode = gs.getSourceCode(FilePath);
		int [] csValueArray = cs.getAllCsValues(sourceCode);
		return (new ResponseEntity<int[]>(csValueArray, HttpStatus.OK));
	}

}
