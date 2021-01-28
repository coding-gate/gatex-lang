package org.gatex.lang.process;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JavaProcess implements OsProcess{

    public Process start(String cmdString) throws IOException {
        return Runtime.getRuntime().exec(cmdString);
    }
}
