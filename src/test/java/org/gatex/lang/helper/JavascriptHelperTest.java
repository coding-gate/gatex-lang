package org.gatex.lang.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.process.OsProcess;
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
public class JavascriptHelperTest {

    @Mock
    OsProcess osProcess;

    @Mock
    Process process;


    JavascriptHelper javascriptHelper;

    String str="\n" +
            "\n" +
            "  √ test add 2+3 \n" +
            "  √ test add 2+0 \n" +
            "  √ test add 2-1 \n" +
            "  1) test add 3+3 \n" +
            "\n" +
            "  3 passing (16ms)\n" +
            "  1 failing\n" +
            "\n" +
            "  1) test add 3+3 :\n" +
            "\n" +
            "      AssertionError [ERR_ASSERTION]: 7 == 6\n" +
            "      + expected - actual\n" +
            "\n" +
            "      -7\n" +
            "      +6\n" +
            "      \n" +
            "      at Context.<anonymous> (test.js:17:10)\n" +
            "\n" +
            "\n" +
            "\n";


    @Before
    public void setup() throws IOException {

        Mockito.when(osProcess.start(any(String.class))).thenReturn(process);
        Mockito.when(process.getInputStream()).thenReturn(new ByteArrayInputStream(str.getBytes()));
        javascriptHelper =new JavascriptHelper("./{0}/", 8730, osProcess ,new ObjectMapper());
    }

    @Test
    public void execCommand() throws IOException, InterruptedException {
        CmdOutput output= javascriptHelper.execCommand("tempdir");
        assertTrue(output.getStatus().equals(0));
    }

}
