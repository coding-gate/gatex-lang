package org.gatex.lang.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.process.CsharpProcess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CsharpHelperTest {

    @Mock
    CsharpProcess csharpProcess;

    @Mock
    Process process;


    CsharpHelper csharpHelper;

    String str="Test run for C:\\pss\\gatex\\gatex-lang\\testproject\\bin\\Debug\\netcoreapp3.1\\testproject.dll(.NETCoreApp,Version=v3.1)\n" +
            "Microsoft (R) Test Execution Command Line Tool Version 16.7.0\n" +
            "Copyright (c) Microsoft Corporation.  All rights reserved.\n" +
            "\n" +
            "Starting test execution, please wait...\n" +
            "\n" +
            "A total of 1 test files matched the specified pattern.\n" +
            "C:\\pss\\gatex\\gatex-lang\\testproject\\bin\\Debug\\netcoreapp3.1\\testproject.dll\n" +
            "[xUnit.net 00:00:00.00] xUnit.net VSTest Adapter v2.4.0 (64-bit .NET Core 3.1.10)\n" +
            "[xUnit.net 00:00:00.41]   Discovering: testproject\n" +
            "[xUnit.net 00:00:00.45]   Discovered:  testproject\n" +
            "[xUnit.net 00:00:00.45]   Starting:    testproject\n" +
            "[xUnit.net 00:00:00.55]       Assert.Equal() Failure\n" +
            "[xUnit.net 00:00:00.55]       Expected: 6\n" +
            "[xUnit.net 00:00:00.55]       Actual:   5\n" +
            "[xUnit.net 00:00:00.55]       Stack Trace:\n" +
            "[xUnit.net 00:00:00.55]         C:\\pss\\gatex\\gatex-lang\\testproject\\AnswerTest.cs(25,0): at AnswerTest.testAdd1()\n" +
            "[xUnit.net 00:00:00.56]   Finished:    testproject\n" +
            "  X AnswerTest.testAdd1 [10ms]\n" +
            "  Error Message:\n" +
            "   Assert.Equal() Failure\n" +
            "Expected: 6\n" +
            "Actual:   5\n" +
            "  Stack Trace:\n" +
            "     at AnswerTest.testAdd1() in C:\\pss\\gatex\\gatex-lang\\testproject\\AnswerTest.cs:line 25\n" +
            "  � AnswerTest.testAdd [1ms]\n" +
            "\n" +
            "Total tests: 2\n" +
            "     Passed: 1\n" +
            "     Failed: 1\n" +
            " Total time: 1.2827 Seconds\n" +
            "C:\\Program Files\\dotnet\\sdk\\3.1.404\\Microsoft.TestPlatform.targets(32,5): error MSB4181: The \"Microsoft.TestPlatform.Build.Tasks.VSTestTask\" task returned false but did not log an error. [C:\\pss\\gatex\\gatex-lang\\testproject\\testproject.csproj]\n";


    @Before
    public void setup() throws IOException {

        Mockito.when(csharpProcess.start(any(String.class))).thenReturn(process);
        Mockito.when(process.getInputStream()).thenReturn(new ByteArrayInputStream(str.getBytes()));
        csharpHelper =new CsharpHelper("./{0}/",65533, csharpProcess, new ObjectMapper());
    }

    @Test
    public void execCommand() throws IOException, InterruptedException {
        CmdOutput output= csharpHelper.execCommand("testproject");
        assertTrue(output.getStatus().equals(0));
    }

}
