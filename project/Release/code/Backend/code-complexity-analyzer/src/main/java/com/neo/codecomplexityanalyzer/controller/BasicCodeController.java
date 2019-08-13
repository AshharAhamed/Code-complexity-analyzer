/*
-------------------------------------------------------------------------------------------------------
--  Date        Sign    History
--  ----------  ------  --------------------------------------------------------------------------------
--  2019-08-06  Sathira  185817, Added CTC functions.
--  2019-08-06  Sahan          , Added CiJava Services Functions
--  ----------  ------  --------------------------------------------------------------------------------
*/
package com.neo.codecomplexityanalyzer.controller;

import java.io.FileNotFoundException;
import java.util.*;

import com.neo.codecomplexityanalyzer.service.serviceImpl.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neo.codecomplexityanalyzer.service.ICNCService;

@CrossOrigin(origins = {"http://localhost:1234", "http://localhost:3000"})

@RestController
public class BasicCodeController {

    //--------------------------------------- General End Points ---------------------------------------------------------------
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
        String code;
        int lineCount;
        String[] lineArr;
        GeneralServiceImpl ccaUtil = new GeneralServiceImpl();
        ccaUtil.getSourceCode(FilePath);
        code = ccaUtil.getOriginalSourceCode();
        lineCount = ccaUtil.findSourceCodeLineCount(code);
        lineArr = ccaUtil.collectAllSourceCodeLines(code, lineCount);

        return (new ResponseEntity<String[]>(lineArr, HttpStatus.OK));
    }

    @RequestMapping(value = "/get-score", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<?> getSourceCodeFormatted(@RequestHeader("file-path") String FilePath) {
        try {

            ResponseClass r1 = new ResponseClass();
            GeneralServiceImpl generalService = new GeneralServiceImpl();
            JavaSyntaxChecker javaSyntaxChecker = new JavaSyntaxChecker(FilePath);
            List<String> errorList = javaSyntaxChecker.check();
            String code = generalService.getSourceCode(FilePath);
            r1.setCode(Arrays.asList(generalService.collectAllSourceCodeLines(code, generalService.findSourceCodeLineCount(code))));

            CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
            int itcScore = cctUtil.getIterativeControlScore();
            int controlScore = cctUtil.getControlScore();
            int catchScore = cctUtil.getCatchScore();
            int switchScore = cctUtil.getSwitchScore();

            HashMap<Integer, Integer> m1 = cctUtil.getLineScore();
            String[] lineScoreArray = new String[m1.size()];
            if (!errorList.isEmpty()) {
                Arrays.fill(lineScoreArray, "-");
                r1.setLineScore(Arrays.asList(lineScoreArray));
                r1.setErrorList(errorList);
                r1.setTotalCtcCount("Error");
                r1.setStatusCode("500");
                r1.setErrorMessage("Please fix the errors in the code !");
                return (new ResponseEntity<>(r1, HttpStatus.OK));
            }

            int i = 0;
            for (Map.Entry<Integer, Integer> entry : m1.entrySet()) {
                int value = entry.getValue();
                lineScoreArray[i] = String.valueOf(value);
                ++i;
            }
            r1.setLineScore(Arrays.asList(lineScoreArray));
            r1.setTotalCtcCount(String.valueOf((itcScore + controlScore + catchScore + switchScore)));
            r1.setStatusCode("200");
            return (new ResponseEntity<>(r1, HttpStatus.OK));
        }catch (IllegalArgumentException e){
            ResponseClass responseClass = new ResponseClass();
            responseClass.setErrorMessage("Invalid file or file path !");
            responseClass.setTotalCtcCount("0");
            responseClass.setStatusCode("501");
            return (new ResponseEntity<>(responseClass, HttpStatus.OK));
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //--------------------------------------- CTC End Points ---------------------------------------------------------------
    @GetMapping(path = "/get-ctc/if")
    public ResponseEntity<Integer> getCTCScore(@RequestHeader("file-path") String FilePath) {
        CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
        return (new ResponseEntity<>(cctUtil.getControlScore(), HttpStatus.OK));
    }

    @GetMapping(path = "/get-ctc/itc")
    public ResponseEntity<Integer> getCTCITCScore(@RequestHeader("file-path") String FilePath) {
        CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
        return (new ResponseEntity<>(cctUtil.getIterativeControlScore(), HttpStatus.OK));
    }

    @GetMapping(path = "/get-ctc/catch")
    public ResponseEntity<Integer> getCTCCatchScore(@RequestHeader("file-path") String FilePath) {
        CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
        return (new ResponseEntity<>(cctUtil.getCatchScore(), HttpStatus.OK));
    }

    @GetMapping(path = "/get-ctc/case")
    public ResponseEntity<Integer> getCTCSwitchScore(@RequestHeader("file-path") String FilePath) {
        CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
        return (new ResponseEntity<>(cctUtil.getSwitchScore(), HttpStatus.OK));
    }

    @GetMapping(path = "/get-ctc")
    public ResponseEntity<HashMap> getCTCTotalScore(@RequestHeader("file-path") String FilePath) {
        HashMap<String, String> hashMap = new HashMap<>();
        CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
        int itcScore = cctUtil.getIterativeControlScore();
        int controlScore = cctUtil.getControlScore();
        int catchScore = cctUtil.getCatchScore();
        int switchScore = cctUtil.getSwitchScore();
        int ctcTotal = itcScore + catchScore + switchScore + controlScore;

        if (controlScore == -1) {
            controlScore = 0;
            hashMap.put("Control Score Error : ", String.valueOf(ctcTotal));
        }

        hashMap.put("ControlScore", String.valueOf(controlScore));
        hashMap.put("ITCScore", String.valueOf(itcScore));
        hashMap.put("CatchScore", String.valueOf(catchScore));
        hashMap.put("SwitchScore", String.valueOf(switchScore));
        hashMap.put("TotalCTCScore", String.valueOf(ctcTotal));

        HashMap<Integer, Integer> m1 = cctUtil.getLineScore();

        return (new ResponseEntity<>(hashMap, HttpStatus.OK));
    }

    @GetMapping(path = "/get-ctc-line-score")
    public ResponseEntity<String[]> getCTCLineScore(@RequestHeader("file-path") String FilePath) {
        CTCServiceImpl cctUtil = new CTCServiceImpl(FilePath);
        cctUtil.getIterativeControlScore();
        cctUtil.getControlScore();
        cctUtil.getCatchScore();
        cctUtil.getSwitchScore();
        HashMap<Integer, Integer> m1 = cctUtil.getLineScore();
        String[] lineScoreArray = new String[m1.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : m1.entrySet()) {
            int value = entry.getValue();
            lineScoreArray[i] = String.valueOf(value);
            ++i;
        }
        return (new ResponseEntity<>(lineScoreArray, HttpStatus.OK));
    }

    //--------------------------------------- Ci End Points ---------------------------------------------------------------

    // This service will give the names of the ancestor class Names when a class
    // Name is given
    @GetMapping(path = "/get-ci/ansestors")
    public ArrayList<String> getNamesOfTheAncestorClasses(@RequestHeader("file-path") String FilePath,
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

    // This service will give the relationship of all the classes with the names
    @GetMapping(path = "/get-ci/class-map")
    public HashMap<String, String> getClassMap(@RequestHeader("file-path") String FilePath) {
        CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
        return ci.getClassMapping();
    }

    // This service is to get all the class Names
    @GetMapping(path = "/get-ci/class-names")
    public ArrayList<String> getClassNames(@RequestHeader("file-path") String FilePath) {
        CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
        return ci.getClassNames();
    }

    // This service is to give the number of ancestor classes of a given class name
    @GetMapping(path = "/get-ci/num-of-ancestors")
    public int getNumberOfAncestors(@RequestHeader("file-path") String FilePath,
                                    @RequestParam String nameOfTheClass) {
        CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
        return ci.getNumberOfAnsestors(nameOfTheClass);
    }

    // This service is to give the complexity of classes separately on a map
    @GetMapping(path = "/get-ci/complexity-map")
    public HashMap<String, Integer> getComplexityOfAllClasses(@RequestHeader("file-path") String FilePath) {
        CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
        return ci.complexityOfAllClassesDueToInheritance();
    }

//	// This service is to give the complexity due to inheritance when given a class
//	// Name
//	@GetMapping(path = "/get-ci/num-of-ancestors")
//	public int getComplexityOfaClass(@RequestHeader("file-path") String FilePath,
//			@RequestParam String nameOfTheClass) {
//		CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
//		return ci.calComplexityDueToInheritance(nameOfTheClass);
//	}

//	// This service is to give the total complexity of a code due to inheritance
//
//	@GetMapping(path = "/get-ci/complexity-map")
//	public int getTotalComplexityDueToInheritance(@RequestHeader("file-path") String FilePath) {
//		CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
//		return ci.calTotalComplexityDueToInheritance();
//	}

    // This method is for future usages
    @GetMapping(path = "/get-ci/conneted-points")
    public void getStronglyConnectedPoints(@RequestHeader("file-path") String FilePath) {
        CiJavaServicesImpl ci = new CiJavaServicesImpl(FilePath);
        ci.identifyStronglyConnectedClasses();
    }


    //--------------------------------------- CNC End Points ---------------------------------------------------------------
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/get-cnc/nested-if")
    public ResponseEntity<Integer> getCNCNestedIfScore(@RequestHeader("file-path") String FilePath) {
        ICNCService cncService = new CNCServiceImpl(FilePath);
        return (new ResponseEntity<Integer>(cncService.getNestedIfControlScore(), HttpStatus.OK));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/get-cnc/nested-for")
    public ResponseEntity<Integer> getCNCNestedForScore(@RequestHeader("file-path") String FilePath) {
        ICNCService cncService = new CNCServiceImpl(FilePath);
        return (new ResponseEntity<Integer>(cncService.getNestedForScore(), HttpStatus.OK));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/get-cnc/nested-While")
    public ResponseEntity<Integer> getCNCNestedWhileScore(@RequestHeader("file-path") String FilePath) {
        ICNCService cncService = new CNCServiceImpl(FilePath);
        return (new ResponseEntity<Integer>(cncService.getNestedWhileScore(), HttpStatus.OK));
    }


    //--------------------------------------- Cs End Points ---------------------------------------------------------------
    @GetMapping(path = "/get-cs")
    public ResponseEntity<int[]> getCsScore(@RequestHeader("file-path") String FilePath) {
        CsServicesImpl cs = new CsServicesImpl();
        GeneralServiceImpl gs = new GeneralServiceImpl();
        String sourceCode = gs.getSourceCode(FilePath);
        int[] csValueArray = cs.getAllCsValues(sourceCode);
        return (new ResponseEntity<int[]>(csValueArray, HttpStatus.OK));
    }

}

class ResponseClass {
    private String totalCtcCount;
    private List<String> errorList;
    private List<String> lineScore;
    private List<String> code;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    private String statusCode;


    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public String getTotalCtcCount() {
        return totalCtcCount;
    }

    public void setTotalCtcCount(String totalCtcCount) {
        this.totalCtcCount = totalCtcCount;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public List<String> getLineScore() {
        return lineScore;
    }

    public void setLineScore(List<String> lineScore) {
        this.lineScore = lineScore;
    }
}