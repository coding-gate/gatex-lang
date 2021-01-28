package org.gatex.lang.service;


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

import static org.junit.Assert.assertTrue;


@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class CsharpIntegrationTest {

    private UnitTestAndAnswerDTO testAns=new UnitTestAndAnswerDTO();

    @Autowired
    CsharpService csharpService;

    @Before
    public void makeTestData() throws IOException  {
        testAns.setAnswerCode(FileUtil.readFile("./TestData/Answer.cs"));
        testAns.setUnitTestCode(FileUtil.readFile("./TestData/AnswerTest.cs"));
    }


    @Test
    public void runTest() throws Exception{
        CmdOutput output = csharpService.runUnittest(testAns);
        System.out.println(output.getOutputMsg());
        assertTrue(output.getStatus().equals(0));
    }

}
