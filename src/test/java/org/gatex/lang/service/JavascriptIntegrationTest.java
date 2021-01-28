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
public class JavascriptIntegrationTest {



    private UnitTestAndAnswerDTO testAns=new UnitTestAndAnswerDTO();

    @Autowired
    JavascriptService javascriptService;

    @Before
    public void makeTestData() throws IOException  {
        testAns.setAnswerCode(FileUtil.readFile("./TestData/answer.js"));
        testAns.setUnitTestCode(FileUtil.readFile("./TestData/test.js"));
    }



    @Test
    public void runTest() throws Exception{
        CmdOutput output = javascriptService.runUnittest(testAns);
        System.out.println(output.getOutputMsg());
        assertTrue(output.getStatus().equals(0));

    }

}
