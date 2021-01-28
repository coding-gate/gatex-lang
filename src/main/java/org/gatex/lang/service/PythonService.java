package org.gatex.lang.service;

import lombok.extern.slf4j.Slf4j;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.helper.PythonHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;



@Service
@Slf4j
public class PythonService {

	private final PythonHelper pythonHelper;
	private final String answerFileName;
	private final String unitTestFileName;

	public PythonService(@Value("${py.answer.file.name}") String answerFileName,
						 @Value("${py.unittest.file.name}") String unitTestFileName,
						 PythonHelper pythonHelper) {
		this.answerFileName = answerFileName;
		this.unitTestFileName = unitTestFileName;
		this.pythonHelper = pythonHelper;
	}
	
	
	

	public CmdOutput runUnittest(UnitTestAndAnswerDTO unitTestAns) throws IOException {
		CmdOutput cmdOutput = null;
		String dirName = UUID.randomUUID().toString();
		try {
			pythonHelper.createDir(dirName);
			pythonHelper.saveFile(dirName, answerFileName, unitTestAns.getAnswerCode());
			pythonHelper.saveFile(dirName, unitTestFileName, unitTestAns.getUnitTestCode());
			cmdOutput= pythonHelper.execCommand(dirName);
		}finally {
		   pythonHelper.deleteDir(dirName);
		}
		return cmdOutput;
	}
	

}
