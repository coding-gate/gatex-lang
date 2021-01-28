package org.gatex.lang.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.process.CsharpProcess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CsharpHelper extends UnittestHelper{

    private final ObjectMapper mapper;
    private final CsharpProcess csharpProcess;
    private final int SUCCESS_SIGN;


    public CsharpHelper(@Value("${cs.answer.file.path}") String answerPathTmpl,
                        @Value("${cs.success.sign.char}") int successSign,
                        CsharpProcess csharpProcess,
                        ObjectMapper mapper) {
        super.ANSWER_PATH_TMPL = answerPathTmpl;
        this.SUCCESS_SIGN = successSign;
        this.csharpProcess = csharpProcess;
        this.mapper=mapper;
    }


    private String formatUnittestMsg(String outputMsg) throws JsonProcessingException {
        List<Object[]> printResult=new ArrayList<>();

        String[] lines=outputMsg.split(System.lineSeparator());

        List<String> filterLines = Arrays.stream(lines)
                .filter(line -> line.contains("AnswerTest."))
                .map(line -> line.trim())
                .filter(line->line.split(" ").length==3)
                .collect(Collectors.toList());

        for (String line : filterLines) {
            String result=line.trim();
            if(result.length()>0){
                log.info("Processing "+result);
                String[] part = result.split(" ");
                Object[] element=new Object[2];
                element[1]= (part[0].charAt(0)==this.SUCCESS_SIGN);
                element[0]=part[1].substring(result.indexOf(".")-1);
                printResult.add(element);
            }
        }

        return mapper.writeValueAsString(printResult);
    }


    private String formatCompilationMsg(String outputMsg) throws JsonProcessingException {
        String[] lines = outputMsg.split(System.lineSeparator());
        return Arrays.stream(lines)
                .map(line->line.substring(0,line.indexOf("["))+System.lineSeparator())
                .reduce("", String::concat);
    }



    public CmdOutput execCommand(String dirName) throws IOException {
        Process process;
        InputStream in = null;
        CmdOutput cmdOutput = new CmdOutput();
        int status=0;
        try {
            process = csharpProcess.start(dirName);
            in = process.getInputStream();
            String progOut = getOutput(in);
            if(progOut.startsWith("Test run for")){
                cmdOutput.setOutputMsg(formatUnittestMsg(progOut));
                cmdOutput.setStatus(status);
            }else{
                cmdOutput.setOutputMsg(formatCompilationMsg(progOut));
                cmdOutput.setStatus(1);
            }
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
