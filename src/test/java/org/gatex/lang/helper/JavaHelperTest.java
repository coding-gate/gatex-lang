package org.gatex.lang.helper;

import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.process.OsProcess;
import org.gatex.lang.util.FileUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class JavaHelperTest {

    @Mock
    OsProcess osProcess;

    @Mock
    Process process;


    JavaHelper javaHelper;

    @Before
    public void setup() throws IOException {
        javaHelper =new JavaHelper("./{0}/",  osProcess );
    }

    @Test(expected=IOException.class)
    public void saveFileIOExceptionTestE() throws Exception {
        javaHelper.saveFile("TempDir", "Answer.txt", "Dummy content");
    }

    @Test
    public void saveFileTest() throws Exception {

        javaHelper.createDir("UserId");
        javaHelper.saveFile("UserId", "Answer.java", "Dummy content");
        assertEquals("Dummy content", FileUtil.readFile("./UserId/Answer.java"));
        javaHelper.deleteDir("UserId");

    }


    @Test
    public void runUnittest() throws IOException, InterruptedException {
        String str="[[\"failureTest\",false],[\"sucessTest\",true],[\"nullPointerTest\",true],[\"performanceTest\",true]]";
        Mockito.when(osProcess.start(any(String.class))).thenReturn(process);
        Mockito.when(process.getInputStream()).thenReturn(new ByteArrayInputStream(str.getBytes()));

        CmdOutput output= javaHelper.execCommand("command");
        assertTrue(output.getOutputMsg().contains("true"));
    }

    @Test
    public void compilationFailTest() throws Exception{

        String str=".\\9797440d-49fe-42aa-a872-f7acec330ac7\\Answer.java:4: error: ';' expected\t\treturn str.length()\t\t                   ^1 error";

        Mockito.when(osProcess.start(any(String.class))).thenReturn(process);
        Mockito.when(process.getErrorStream()).thenReturn(new ByteArrayInputStream(str.getBytes()));
        CmdOutput output= javaHelper.compile("command");

        assertEquals(0, (int)output.getStatus());

    }

}
