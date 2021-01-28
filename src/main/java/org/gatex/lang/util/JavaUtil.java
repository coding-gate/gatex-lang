package org.gatex.lang.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Slf4j
@Component
public class JavaUtil {
    //:
    private String SEPARATOR;
    // "/tempfiles/{0}/"
    private String ANSWER_PATH_TMPL;
    // "/tmp/junitlib/*"
    private String LIB_PATH;

    public JavaUtil( @Value("${java.classpath.separator}") String SEPARATOR,
                     @Value("${java.answer.file.path}") String ANSWER_PATH_TMPL,
                     @Value("${java.junit.path}") String LIB_PATH) {
        this.SEPARATOR = SEPARATOR;
        this.ANSWER_PATH_TMPL = ANSWER_PATH_TMPL;
        this.LIB_PATH = LIB_PATH;
    }

    private String buildClassPathString(String srcPath) {
        StringBuilder sb = new StringBuilder();
        sb.append(srcPath);
        sb.append(SEPARATOR);
        sb.append(LIB_PATH);
        sb.append(" ");
        return sb.toString();
    }


    public String buildCompileString(String dirName, String javaFileName) {

        StringBuilder cmplStr = new StringBuilder("javac -cp ");
        String srcPath = getDirPath(dirName);
        cmplStr.append(buildClassPathString(srcPath));
        cmplStr.append(srcPath);
        cmplStr.append(javaFileName);
        log.info("Building compile string  as {}", cmplStr.toString());
        return cmplStr.toString();

    }
    public String buildRunString(String dirName, String javaFileName) {

        StringBuilder cmplStr = new StringBuilder("java -cp ");
        String srcPath = getDirPath(dirName);
        cmplStr.append(buildClassPathString(srcPath));
        cmplStr.append(javaFileName);
        log.info("Building run string  as {}", cmplStr.toString());
        return cmplStr.toString();

    }


    public String getDirPath(String dirName) {
        return MessageFormat.format(ANSWER_PATH_TMPL, dirName);
    }
}
