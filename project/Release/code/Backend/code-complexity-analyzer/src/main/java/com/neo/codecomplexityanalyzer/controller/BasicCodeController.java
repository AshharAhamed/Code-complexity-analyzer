package com.neo.codecomplexityanalyzer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neo.codecomplexityanalyzer.service.serviceImpl.CCA_Uilities;
import com.neo.codecomplexityanalyzer.service.serviceImpl.CTC_Utilities;

@CrossOrigin(origins = {"http://localhost:3000"})

@RestController
public class BasicCodeController {

    // REST service to get the line count
    @GetMapping(path = "/line-count")
    public ResponseEntity<Integer> getLineCount() {
        String code = "";
        int lineCount = 0;
        CCA_Uilities ccaUtil = new CCA_Uilities();

        code = ccaUtil.getSourceCode();
        lineCount = ccaUtil.findSourceCodeLineCount(code);

        return (new ResponseEntity<Integer>(lineCount, HttpStatus.OK));
    }

    // REST service to get the source code as an array
    @GetMapping(path = "/get-code")
    public ResponseEntity<String[]> getSourceCode() {
        String code = "";
        int lineCount = 0;
        String[] lineArr;
        CCA_Uilities ccaUtil = new CCA_Uilities();

        code = ccaUtil.getSourceCode();
        lineCount = ccaUtil.findSourceCodeLineCount(code);
        lineArr = ccaUtil.collectAllSourceCodeLines(code, lineCount);

        return (new ResponseEntity<String[]>(lineArr, HttpStatus.OK));
    }

    @GetMapping(path = "/get-ctc/")
    public ResponseEntity<Integer> getCTCScore(@RequestHeader("file-path") String FilePath) {
        CTC_Utilities cctUtil = new CTC_Utilities(FilePath);
        return (new ResponseEntity<Integer>(cctUtil.getControlScore(), HttpStatus.OK));
    }
}
