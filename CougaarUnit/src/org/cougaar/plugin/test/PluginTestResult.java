package org.cougaar.plugin.test;

import java.util.Vector;
import java.util.Enumeration;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PluginTestResult {
    public static final int PHASE_TEST_SUBSCRIPTION = 1;
    public static final int PHASE_TEST_EXECUTION = 2;
    public static final int COMMAND_SUBSCRIPTION_ASSERT = 1;
    public static final int COMMAND_ASSERT_PUBLISH_ADD = 2;
    public static final int COMMAND_ASSERT_PUBLISH_CHANGE = 3;
    public static final int COMMAND_ASSERT_PUBLISH_REMOVE = 4;

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
    private static String testName;


    public static void setTestName(String name) {
        testName = name;
    }

    public static void addEntry(int phase, int command, boolean result) {
        entries.add(new TestResultEntry(phase, command, result));
    }

    /**
     * Returns the overall status if the test results
     * @return true if every test passed, false if at least one test failed
     */
    public static boolean getOverallResult() {
        for (Enumeration enum = entries.elements(); enum.hasMoreElements(); ) {
            if (!(((TestResultEntry)enum.nextElement()).testResult))
                return false;
        }
        return true;
    }

    private static String getPhaseAsString(int phaseId) {
        switch (phaseId) {
            case PHASE_TEST_SUBSCRIPTION: return "SUBSCRIPTION PHASE";
            case PHASE_TEST_EXECUTION: return "EXECUTION PHASE";
        }
        return "";
    }

    private static String getCommandAsString(int commandId) {
        switch (commandId) {
            case COMMAND_SUBSCRIPTION_ASSERT: return "COMMAND SUBSCRIPTION ASSERT";
            case COMMAND_ASSERT_PUBLISH_ADD: return "COMMAND PUBLISH ADD";
            case COMMAND_ASSERT_PUBLISH_CHANGE: return "COMMAND PUBLISH CHANGE";
            case COMMAND_ASSERT_PUBLISH_REMOVE: return "COMMAND PUBLISH REMOVE";
        }
        return "";
    }

    public static String getReportasString() {
        StringBuffer result = new StringBuffer();
        for (Enumeration enum = entries.elements(); enum.hasMoreElements(); ) {
            TestResultEntry tre = (TestResultEntry)enum.nextElement();
            result.append("TEST RESULTS FOR: " + testName);
            result.append("PHASE\tTEST\tRESULT\n");
            result.append("-----\t----\t------\n");
            result.append(getPhaseAsString(tre.testPhase)+"\t"+getCommandAsString(tre.testCommand)+"\t"+String.valueOf(tre.testResult)+"\n");
        }
        return result.toString();
    }
}