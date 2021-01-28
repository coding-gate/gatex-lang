package org.gatex.lang.service;

import lombok.extern.slf4j.Slf4j;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.helper.JavascriptHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class JavascriptService {
	private final JavascriptHelper javascriptHelper;
	private final String answerFileName;
	private final String unitTestFileName;

	public JavascriptService(@Value("${js.answer.file.name}") String answerFileName,
							 @Value("${js.unittest.file.name}") String unitTestFileName,
							 JavascriptHelper javascriptHelper) {
		this.answerFileName = answerFileName;
		this.unitTestFileName = unitTestFileName;
		this.javascriptHelper = javascriptHelper;
	}
	

	public CmdOutput runUnittest(UnitTestAndAnswerDTO unitTestAns) throws IOException {
		CmdOutput cmdOutput = null;
		String dirName = UUID.randomUUID().toString();
		try {
			javascriptHelper.createDir(dirName);
			javascriptHelper.saveFile(dirName, answerFileName, unitTestAns.getAnswerCode());
			javascriptHelper.saveFile(dirName, unitTestFileName, unitTestAns.getUnitTestCode());
			cmdOutput= javascriptHelper.execCommand(dirName);
		}finally {
		   javascriptHelper.deleteDir(dirName);
		}
		return cmdOutput;
	}
	

}
