package org.gatex.lang.service;

import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.helper.CsharpHelper;
import org.gatex.lang.util.FileUtil;
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
public class CsharpServiceUnittest {

	private UnitTestAndAnswerDTO testAns=new UnitTestAndAnswerDTO();

	CsharpService csharpService;

	@Mock
	CsharpHelper pythonHelper;
	
	@Before
	public void makeTestData() throws IOException {
		CmdOutput cmdOut = new CmdOutput();
		cmdOut.setStatus(0);
		Mockito.when(pythonHelper.execCommand(any(String.class))).thenReturn(cmdOut);
		testAns.setAnswerCode("Mock answer code");
		testAns.setUnitTestCode("Mock Unittest code");
		csharpService =new CsharpService("Answer.cs", "AnswerTest.cs", pythonHelper);
	}
	
	@Test
	public void runTest() throws Exception{
		CmdOutput output = csharpService.runUnittest(testAns);
		System.out.println(output.getOutputMsg());
		assertTrue(output.getStatus().equals(0));
	}

}
