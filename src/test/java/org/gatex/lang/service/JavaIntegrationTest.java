package org.gatex.lang.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.gatex.lang.dto.AnswerDTO;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.util.FileUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class JavaIntegrationTest {

    private UnitTestAndAnswerDTO testAns=new UnitTestAndAnswerDTO();

    @Autowired
    JavaService javaService;

    @Before
    public void makeTestData() throws IOException  {
        testAns.setAnswerCode(FileUtil.readFile("./TestData/Answer.java"));
        testAns.setUnitTestCode(FileUtil.readFile("./TestData/UnitTest.java"));
    }


    @Test
    public void compileTest() throws Exception{
        CmdOutput output = javaService.compileAll(testAns);
        assertEquals(0, (int)output.getStatus());
    }

    @Test
    public void runTest() throws Exception{

        CmdOutput output = javaService.runUnittest(testAns);
        assertTrue(output.getOutputMsg().contains("true"));
    }

    @Test
    public void runTestWhenBadAnswerCode() throws Exception{

        testAns.setAnswerCode("public clas Answer {\r\n" +
                "	\r\n" +
                "	public int findLength(String str){\r\n" +
                "		return str.length()\r\n" +
                "	}\r\n" +
                "\r\n" +
                "}");

        CmdOutput output = javaService.runUnittest(testAns);
        assertEquals(1, (int)output.getStatus());

    }

    @Test
    public void compileAnsTestCompilationFail() throws Exception{

        AnswerDTO ans =new AnswerDTO();
        ans.setAnswerCode("public class"
                + " Answer {");

        CmdOutput output = javaService.compileAns(ans);
        assertEquals(1, (int)output.getStatus());

    }

    @Test
    public void compileAnsTestCompilationSuccess() throws Exception{

        AnswerDTO ans =new AnswerDTO();
        ans.setAnswerCode("public class"
                + " Answer { }");

        ObjectMapper mapper =new ObjectMapper();
        System.out.println(mapper.writeValueAsString(ans));

        CmdOutput output = javaService.compileAns(ans);
        assertEquals(0, (int)output.getStatus());

    }


}
