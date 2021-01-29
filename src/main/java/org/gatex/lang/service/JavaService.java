package org.gatex.lang.service;


import lombok.extern.slf4j.Slf4j;
import org.gatex.lang.dto.AnswerDTO;
import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.dto.UnitTestAndAnswerDTO;
import org.gatex.lang.helper.JavaHelper;
import org.gatex.lang.util.JavaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class JavaService {

    private final String ANSWER_FILE_NAME;
    private final String UNIT_TEST_FILE_NAME;
    private final String EXECUTOR_UTIL;
    private final JavaUtil javaUtil;
    private final JavaHelper javaHelper;


    public JavaService(@Value("${java.answer.file.name}") String ANSWER_FILE_NAME,
                       @Value("${java.unittest.file.name}") String UNIT_TEST_FILE_NAME,
                       @Value("${java.junit.executor.util}") String EXECUTOR_UTIL,
                       JavaUtil javaUtil,
                       JavaHelper javaHelper) {

        this.ANSWER_FILE_NAME = ANSWER_FILE_NAME;
        this.UNIT_TEST_FILE_NAME = UNIT_TEST_FILE_NAME;
        this.EXECUTOR_UTIL = EXECUTOR_UTIL;
        this.javaUtil = javaUtil;
        this.javaHelper = javaHelper;
    }

    public CmdOutput compileAns(AnswerDTO ans) throws IOException {
        CmdOutput output = null;
        String dirName = UUID.randomUUID().toString();
        try {
            javaHelper.createDir(dirName);
            javaHelper.saveFile(dirName, ANSWER_FILE_NAME, ans.getAnswerCode());
            String cmd = javaUtil.buildCompileString(dirName, ANSWER_FILE_NAME);
            output = javaHelper.compile(cmd, dirName);

        } finally {
            javaHelper.deleteDir(dirName);
        }

        return output;
    }

    public CmdOutput compileAll(UnitTestAndAnswerDTO unitTestAns) throws IOException{
        CmdOutput output = null;
        String dirName = UUID.randomUUID().toString();
        log.info("Directory for compile all {}", dirName);
        try {
            javaHelper.createDir(dirName);
            output=compileAllCode(dirName, unitTestAns);

        } finally {
            javaHelper.deleteDir(dirName);
        }
        log.info("***********CompileAll Ended**********");
        return output;
    }

    private CmdOutput compileAllCode(String dirName, UnitTestAndAnswerDTO unitTestAns) throws IOException {
        CmdOutput output = null;
        String cmd;
        javaHelper.saveFile(dirName, ANSWER_FILE_NAME, unitTestAns.getAnswerCode());
        cmd = javaUtil.buildCompileString(dirName, ANSWER_FILE_NAME);
        output = javaHelper.compile(cmd, dirName);
        if (output.getStatus() == 0) {
            javaHelper.saveFile(dirName, UNIT_TEST_FILE_NAME, unitTestAns.getUnitTestCode());
            cmd = javaUtil.buildCompileString(dirName, UNIT_TEST_FILE_NAME);
            output = javaHelper.compile(cmd, dirName);
        }
        return output;
    }

    public CmdOutput runUnittest(UnitTestAndAnswerDTO unitTestAns) throws IOException{

        String dirName = UUID.randomUUID().toString();
        CmdOutput output = null;
        try {
            javaHelper.createDir(dirName);
            output=compileAllCode(dirName, unitTestAns);
            if (output.getStatus() == 0) {
                String cmd = javaUtil.buildRunString(dirName, EXECUTOR_UTIL);
                output = javaHelper.execCommand(cmd);
                log.debug("Run : {}", output);
            }
        } finally {
            javaHelper.deleteDir(dirName);
        }

        return output;
    }


}
