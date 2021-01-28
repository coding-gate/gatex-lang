package org.gatex.lang.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.process.OsProcess;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PythonHelper extends UnittestHelper {
    private final ObjectMapper mapper;
    private final OsProcess osProcess;


    public PythonHelper(@Value("${py.answer.file.path}") String answerPathTmpl,
                        @Qualifier("pythonProcess") OsProcess osProcess,
                        ObjectMapper mapper) {
        super.ANSWER_PATH_TMPL = answerPathTmpl;
        this.osProcess = osProcess;
        this.mapper=mapper;
    }

    private String formatOutputMsg(String outputMsg) throws JsonProcessingException {
        List<Object[]> printResult=new ArrayList<>();

        String[] lines=outputMsg.split(System.lineSeparator());

        for (String line : lines) {
            String resut=line.trim();
            if(resut.length()>0){
                log.info("Processing "+resut);
                Object[] element=new Object[2];
                element[0]=resut.substring(0,resut.indexOf(" "));
                element[1]=resut.substring(resut.lastIndexOf(" ")+1).equals("ok");
                printResult.add(element);
            }else {
                break;
            }
        }

        return mapper.writeValueAsString(printResult);
    }

    public CmdOutput execCommand(String dirName) throws IOException {
        Process process;
        InputStream in = null;
        CmdOutput cmdOutput = new CmdOutput();
        int status=0;
        try {
            process = osProcess.start(dirName);
            in = process.getErrorStream();
            String progOut = getOutput(in);
            cmdOutput.setOutputMsg(formatOutputMsg(progOut));
            cmdOutput.setStatus(status);
            status = process.waitFor();

        } catch (InterruptedException e) {
            cmdOutput.setStatus(status);
            cmdOutput.setExceptionMsg(e.getMessage());
        }
        finally {
            if (in != null)
                in.close();
        }
        return cmdOutput;
    }

}
