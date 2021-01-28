package org.gatex.lang.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.helper.CsharpHelper;
import org.gatex.lang.process.CsharpProcess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CsharpService {

    private final CsharpHelper csharpHelper;
    private final String answerFileName;
    private final String unitTestFileName;

    public CsharpService(@Value("${cs.answer.file.name}") String answerFileName,
                         @Value("${cs.unittest.file.name}") String unitTestFileName,
                         CsharpHelper csharpHelper) {
        this.answerFileName = answerFileName;
        this.unitTestFileName = unitTestFileName;
        this.csharpHelper = csharpHelper;
    }

    public synchronized CmdOutput runUnittest(UnitTestAndAnswerDTO unitTestAns) throws IOException {
        CmdOutput cmdOutput = null;
        String dirName = "testproject";
        try {
            csharpHelper.saveFile(dirName, answerFileName, unitTestAns.getAnswerCode());
            csharpHelper.saveFile(dirName, unitTestFileName, unitTestAns.getUnitTestCode());
            cmdOutput= csharpHelper.execCommand(dirName);
        }finally {
            csharpHelper.deleteFiles(dirName, new String[]{answerFileName,unitTestFileName});
        }
        return cmdOutput;
    }

}
