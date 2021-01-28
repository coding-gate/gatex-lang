package org.gatex.lang.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.gatex.lang.dto.AnswerDTO;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.service.CsharpService;
import org.gatex.lang.service.JavaService;
import org.gatex.lang.service.JavascriptService;
import org.gatex.lang.service.PythonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers= LanguageController.class)
@TestPropertySource(locations="classpath:test.properties")
public class LanguageControllerTest {
	@Autowired
	private MockMvc mockMvc;


	ObjectMapper objectMapper = new ObjectMapper();
	
	@MockBean
	JavascriptService javascriptService;

	@MockBean
	JavaService javaService;

	@MockBean
	PythonService pythonService;

	@MockBean
	CsharpService csharpService;


	
	private UnitTestAndAnswerDTO testAns;
	private AnswerDTO ans;
	private CmdOutput cmdOutput;
	
	@Before
	public void makeTestData() throws IOException  {
		testAns=new UnitTestAndAnswerDTO();
		testAns.setAnswerCode("AnswerCode");
		testAns.setUnitTestCode("UnitTestCode");
		
		ans=new AnswerDTO();
		ans.setAnswerCode("AnswerCode");
		
		cmdOutput=new CmdOutput();
		cmdOutput.setStatus(0);
		cmdOutput.setOutputMsg("Output message");
	}
	
	
	
	
	@Test
	public void runJavascriptTest() throws Exception{
		
		Mockito.when(javascriptService.runUnittest(Mockito.any(UnitTestAndAnswerDTO.class))).thenReturn(cmdOutput);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/javascript/unittest")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testAns))
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(objectMapper.writeValueAsString(cmdOutput), result.getResponse().getContentAsString());
		
	}

	@Test
	public void runJavaTest() throws Exception{

		Mockito.when(javaService.runUnittest(Mockito.any(UnitTestAndAnswerDTO.class))).thenReturn(cmdOutput);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/java/unittest")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testAns))
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(objectMapper.writeValueAsString(cmdOutput), result.getResponse().getContentAsString());

	}

	@Test
	public void runPythonTest() throws Exception{

		Mockito.when(pythonService.runUnittest(Mockito.any(UnitTestAndAnswerDTO.class))).thenReturn(cmdOutput);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/python/unittest")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testAns))
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(objectMapper.writeValueAsString(cmdOutput), result.getResponse().getContentAsString());

	}

	@Test
	public void runCsharpTest() throws Exception{

		Mockito.when(csharpService.runUnittest(Mockito.any(UnitTestAndAnswerDTO.class))).thenReturn(cmdOutput);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/csharp/unittest")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testAns))
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(objectMapper.writeValueAsString(cmdOutput), result.getResponse().getContentAsString());

	}
	

}
