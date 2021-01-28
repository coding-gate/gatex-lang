package org.gatex.lang.process;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.MessageFormat;

@Component
public class CsharpProcess {

    private final String ANSWER_PATH_TMPL; // "/tempfiles/{0}/"
    private final String TEST_CMD; // "dotnet"
    private final String TEST_ARG; // "test"

    public CsharpProcess(@Value("${cs.answer.file.path}") String ANSWER_PATH_TMPL,
                         @Value("${cs.unittest.cmd.name}") String TEST_CMD,
                         @Value("${cs.unittest.cmd.arg}") String TEST_ARG) {
        this.ANSWER_PATH_TMPL = ANSWER_PATH_TMPL;
        this.TEST_CMD = TEST_CMD;
        this.TEST_ARG = TEST_ARG;
    }

    public Process start(String dirName) throws IOException {
        String strPath = MessageFormat.format(ANSWER_PATH_TMPL, dirName);
        return new ProcessBuilder(this.TEST_CMD, this.TEST_ARG, "-l", "console;verbosity=detailed")
                .directory(Paths.get(strPath).toFile()).start();
    }
}
