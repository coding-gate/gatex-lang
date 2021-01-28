package org.gatex.lang.helper;


import lombok.extern.slf4j.Slf4j;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.process.OsProcess;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class JavaHelper extends UnittestHelper{
    private final OsProcess osProcess;

    public JavaHelper(@Value("${java.answer.file.path}") String ANSWER_PATH_TMPL,
                      @Qualifier("javaProcess") OsProcess osProcess) {
        this.ANSWER_PATH_TMPL =ANSWER_PATH_TMPL;
        this.osProcess = osProcess;
    }


    public CmdOutput compile(String cmdString) throws IOException {
        Process process;
        InputStream in = null;
        CmdOutput cmdOutput = new CmdOutput();
        log.info("Executing command {}", cmdString);

        try {
            process = osProcess.start(cmdString);
            in = process.getErrorStream();
            String progErrOut = getOutput(in);
            String errorMsg = removeFilePathFromErrorMsg(progErrOut);
            cmdOutput.setErrorMsg(errorMsg);
            int exitCode = process.waitFor();
            cmdOutput.setStatus(exitCode);
        }catch (InterruptedException e) {
            log.info("InterruptedException occurred:  {}", e.getMessage());
            cmdOutput.setExceptionMsg(e.getMessage());
        } finally {
            if (in != null)
                in.close();
        }
        return cmdOutput;
    }


    public CmdOutput execCommand(String cmdString) throws IOException {
        Process process;
        InputStream in = null;
        CmdOutput cmdOutput = new CmdOutput();
        log.info("Executing command {}", cmdString);

        try {
            process = osProcess.start(cmdString);
            in = process.getInputStream();
            String progOut = getOutput(in);
            int lastIndex=progOut.lastIndexOf("[[\"");
            if(lastIndex>-1) {
                progOut=progOut.substring(lastIndex); //to remove all System.out.println
            }
            cmdOutput.setOutputMsg(progOut);
            int exitCode = process.waitFor();
            cmdOutput.setStatus(exitCode);

        } catch (InterruptedException e) {
            log.info("InterruptedException occurred:  {}", e.getMessage());
            cmdOutput.setExceptionMsg(e.getMessage());
        } finally {
            if (in != null)
                in.close();
        }
        return cmdOutput;
    }


    private String removeFilePathFromErrorMsg(String progErrOut) {
        String[] lines = progErrOut.split(System.lineSeparator());
        StringBuilder sb = new StringBuilder();
        for(String line:lines){
            if(line.contains("Answer.java:")){
                sb.append(line.substring(line.indexOf("Answer.java:")));
            }else if(line.contains("UnitTest.java:")){
                sb.append(line.substring(line.indexOf("UnitTest.java:")));
            }else{
                sb.append(line);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
