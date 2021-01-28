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
public class PythonHelperTest {

    @Mock
    OsProcess osProcess;

    @Mock
    Process process;


    PythonHelper pythonHelper;

    String str="test_avg_2_3 (__main__.TestUM) ... FAIL\n" +
            "test_avg_2_4 (__main__.TestUM) ... ok\n" +
            "\n" +
            "======================================================================\n" +
            "FAIL: test_avg_2_3 (__main__.TestUM)\n" +
            "----------------------------------------------------------------------\n" +
            "Traceback (most recent call last):\n" +
            "  File \"test.py\", line 13, in test_avg_2_3\n" +
            "    self.assertEqual( avg(2,3), 5)\n" +
            "AssertionError: 2.5 != 5\n" +
            "\n" +
            "----------------------------------------------------------------------\n" +
            "Ran 2 tests in 0.001s\n" +
            "\n" +
            "FAILED (failures=1)\n";


    @Before
    public void setup() throws IOException {

        Mockito.when(osProcess.start(any(String.class))).thenReturn(process);
        Mockito.when(process.getErrorStream()).thenReturn(new ByteArrayInputStream(str.getBytes()));
        pythonHelper =new PythonHelper("./{0}/", osProcess , new ObjectMapper());
    }

    @Test
    public void execCommand() throws IOException, InterruptedException {
        CmdOutput output= pythonHelper.execCommand("tempdir");
        assertTrue(output.getStatus().equals(0));
    }

}
