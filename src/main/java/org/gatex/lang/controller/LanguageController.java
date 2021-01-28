package org.gatex.lang.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.lang.dto.AnswerDTO;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.service.CsharpService;
import org.gatex.lang.service.JavaService;
import org.gatex.lang.service.JavascriptService;
import org.gatex.lang.service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class LanguageController {
	
	@Autowired
	JavascriptService javascriptService;

	@Autowired
	JavaService javaService;

	@Autowired
	PythonService pythonService;

	@Autowired
	CsharpService csharpService;


	@PostMapping(path="java/unittest", produces="application/json", consumes="application/json")
	public ResponseEntity<CmdOutput> javaUnittest(@RequestBody @Valid UnitTestAndAnswerDTO unitTestAns) throws Exception{
		CmdOutput output = javaService.runUnittest(unitTestAns);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	@PostMapping(path="java/compileAll", produces="application/json", consumes="application/json")
	public ResponseEntity<CmdOutput> javaCompileAll(@RequestBody @Valid UnitTestAndAnswerDTO unitTestAns) throws Exception{
		CmdOutput output = javaService.compileAll(unitTestAns);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	@PostMapping(path="java/compileAns", produces="application/json", consumes="application/json")
	public ResponseEntity<CmdOutput> javaCompileAns(@RequestBody @Valid AnswerDTO ans) throws Exception{
		CmdOutput output = javaService.compileAns(ans);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	@PostMapping(path="javascript/unittest", produces="application/json", consumes="application/json")
	public ResponseEntity<CmdOutput> javascriptUnittest(@RequestBody @Valid UnitTestAndAnswerDTO unitTestAns) throws Exception{
		CmdOutput output = javascriptService.runUnittest(unitTestAns);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	@PostMapping(path="python/unittest", produces="application/json", consumes="application/json")
	public ResponseEntity<CmdOutput> pythonUnittest(@RequestBody @Valid UnitTestAndAnswerDTO unitTestAns) throws Exception{
		CmdOutput output = pythonService.runUnittest(unitTestAns);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	@PostMapping(path="csharp/unittest", produces="application/json", consumes="application/json")
	public ResponseEntity<CmdOutput> CsharpUnittest(@RequestBody @Valid UnitTestAndAnswerDTO unitTestAns) throws Exception{
		CmdOutput output = csharpService.runUnittest(unitTestAns);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}


}
