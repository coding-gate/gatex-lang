package org.gatex.lang.service;

import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.util.FileUtil;
import org.gatex.lang.helper.PythonHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class PythonServiceUnittest {

	private UnitTestAndAnswerDTO testAns=new UnitTestAndAnswerDTO();
	PythonService pythonService;

	@Mock
	PythonHelper pythonHelper;
	
	@Before
	public void makeTestData() throws IOException {
		CmdOutput cmdOut = new CmdOutput();
		cmdOut.setStatus(0);
		Mockito.when(pythonHelper.execCommand(any(String.class))).thenReturn(cmdOut);
		testAns.setAnswerCode(FileUtil.readFile("./TestData/answer.py"));
		testAns.setUnitTestCode(FileUtil.readFile("./TestData/test.py"));
		pythonService =new PythonService("answer.py", "test.py", pythonHelper);
	}
	
	@Test
	public void runTest() throws Exception{
		CmdOutput output = pythonService.runUnittest(testAns);
		System.out.println(output.getOutputMsg());
		assertTrue(output.getStatus().equals(0));
	}

}
