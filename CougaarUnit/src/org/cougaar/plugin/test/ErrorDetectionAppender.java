package org.cougaar.plugin.test;

import org.apache.log4j.*;
import org.apache.log4j.spi.*;


/**
 * <p>Title: ErrorDetectionAppender</p>
 * <p>Description: Used to detect error messages within the log output and terminate the run if detected</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author unascribed
 * @version 1.0
 */

public class ErrorDetectionAppender extends AppenderSkeleton {
    private static final String[] errorChecks = new String[] {"Failed to add"};

    public ErrorDetectionAppender() {
    }

    private void checkForError(String line) {
        if (line == null) return;

        for (int i=0; i<errorChecks.length; i++) {
            if (line.indexOf(errorChecks[i]) != -1 ) {
               System.exit(-1);
            }
        }
    }

    public boolean requiresLayout() {
        return false;
    }

    protected void append(LoggingEvent parm1) {
        checkForError(parm1.getRenderedMessage());
    }

    public void close() {
    }
}