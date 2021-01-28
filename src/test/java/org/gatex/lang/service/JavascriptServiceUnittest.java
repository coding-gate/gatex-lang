package org.gatex.lang.service;

import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.util.FileUtil;
import org.gatex.lang.helper.JavascriptHelper;
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
public class JavascriptServiceUnittest {

	private UnitTestAndAnswerDTO testAns=new UnitTestAndAnswerDTO();
	JavascriptService javascriptService;

	@Mock
	JavascriptHelper javascriptHelper;
	
	@Before
	public void makeTestData() throws IOException, InterruptedException {
		CmdOutput cmdOut = new CmdOutput();
		cmdOut.setStatus(0);
		Mockito.when(javascriptHelper.execCommand(any(String.class))).thenReturn(cmdOut);
		testAns.setAnswerCode(FileUtil.readFile("./TestData/answer.js"));
		testAns.setUnitTestCode(FileUtil.readFile("./TestData/test.js"));
		javascriptService =new JavascriptService("answer.js", "test.js", javascriptHelper);
	}
	
	@Test
	public void runTest() throws Exception{
		CmdOutput output = javascriptService.runUnittest(testAns);
		System.out.println(output.getOutputMsg());
		assertTrue(output.getStatus().equals(0));
	}

}
