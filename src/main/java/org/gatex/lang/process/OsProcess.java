package org.gatex.lang.process;

import java.io.IOException;

public interface OsProcess {
    Process start(String dirName) throws IOException;
}
