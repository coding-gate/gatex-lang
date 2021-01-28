package org.gatex.lang.service;

import org.gatex.lang.dto.AnswerDTO;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.helper.JavaHelper;
import org.gatex.lang.util.JavaUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class JavaServiceUnittest {

	JavaService javaService;
	@Mock
	JavaUtil javaUtil;
	@Mock
	JavaHelper javaHelper;
	
	@Before
	public void makeTestData() throws IOException {

		javaService = new JavaService("Answer.java", "UnitTest.java", "RunTestCase", javaUtil, javaHelper);
	}
	
	@Test
	public void compileAnsTest() throws IOException {
		CmdOutput output=new CmdOutput();
		output.setStatus(0);
		Mockito.when(javaUtil.buildCompileString(any(String.class), any(String.class))).thenReturn(" ");
		Mockito.when(javaHelper.compile(any(String.class))).thenReturn(output);
		CmdOutput result = javaService.compileAns(new AnswerDTO());
		assertEquals(new Long(0), new Long(result.getStatus()));
	}

	@Test
	public void runTest() throws IOException {
		CmdOutput output=new CmdOutput();
		output.setStatus(0);
		Mockito.when(javaUtil.buildCompileString(any(String.class), any(String.class))).thenReturn(" ");
		Mockito.when(javaUtil.buildRunString(any(String.class), any(String.class))).thenReturn(" ");
		Mockito.when(javaHelper.execCommand(any(String.class))).thenReturn(output);
		Mockito.when(javaHelper.compile(any(String.class))).thenReturn(output);
		UnitTestAndAnswerDTO unitTestAns=new UnitTestAndAnswerDTO();
		CmdOutput result = javaService.runUnittest(unitTestAns);
		assertEquals(new Long(0), new Long(result.getStatus()));
	}

	@Test
	public void compileAll() throws IOException {
		CmdOutput output=new CmdOutput();
		output.setStatus(0);
		Mockito.when(javaUtil.buildCompileString(any(String.class), any(String.class))).thenReturn(" ");
		Mockito.when(javaHelper.compile(any(String.class))).thenReturn(output);
		UnitTestAndAnswerDTO unitTestAns=new UnitTestAndAnswerDTO();
		CmdOutput result = javaService.compileAll(unitTestAns);
		assertEquals(new Long(0), new Long(result.getStatus()));
	}

}
