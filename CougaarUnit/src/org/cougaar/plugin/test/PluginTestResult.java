package org.cougaar.plugin.test;

import java.util.Vector;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PluginTestResult {
    public static int PHASE_TEST_SUBSCRIPTION = 1;
    public static int PHASE_TEST_EXECUTION = 2;
    public static int COMMAND_SUBSCRIPTION_ASSERT = 1;
    public static int COMMAND_ASSERT_PUBLISH_ADD = 2;
    public static int COMMAND_ASSERT_PUBLISH_CHANGE = 3;
    public static int COMMAND_ASSERT_PUBLISH_REMOVE = 4;

    static class TestResultEntry {
        int testPhase;
        int testCommand;
        boolean testResult;

        public TestResultEntry(int testPhase, int testCommand, boolean testResult) {
            this.testPhase = testPhase;
            this.testCommand = testCommand;
            this.testResult = testResult;
        }
    }

    private static Vector entries = new Vector();



    public static void addEntry(int phase, int command, boolean result) {
        entries.add(new TestResultEntry(phase, command, result));
    }
}