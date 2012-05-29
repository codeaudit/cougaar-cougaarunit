package org.cougaar.plugin.test;

import java.util.Vector;
import java.util.Enumeration;

/**
 * <p>Title: PluginTestResult</p>
 * <p>Description: Encapsulates the results for an entire plugin test.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class PluginTestResult {
    public static final int PHASE_TEST_SUBSCRIPTION = 1;
    public static final int PHASE_TEST_EXECUTION = 2;
    public static final int INITIAL_STATE = 0;
    public static final int COMMAND_SUBSCRIPTION_ASSERT = 1;
    public static final int COMMAND_ASSERT_PUBLISH_ADD = 2;
    public static final int COMMAND_ASSERT_PUBLISH_CHANGE = 3;
    public static final int COMMAND_ASSERT_PUBLISH_REMOVE = 4;

    /**
     *
     * <p>Title: TestResultEntry</p>
     * <p>Description: Composite class for containing individual test results</p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: InfoEther, LLC</p>
     * @author David Craine
     * @version 1.0
     */
    static class TestResultEntry {
        int testPhase;
        int testCommand;
        boolean testResult;
        BlackboardDeltaState expectedState = null;
        BlackboardDeltaState actualState = null;
        String description;
        int id =0;
        static int master_id=0;

        /**
         * Constructor
         * @param testPhase
         * @param testCommand
         * @param testResult
         * @param description
         * @param expectedState
         * @param actualState
         */
        public TestResultEntry(int testPhase, int testCommand, boolean testResult, String description, BlackboardDeltaState expectedState, BlackboardDeltaState actualState) {
            this.testPhase = testPhase;
            this.testCommand = testCommand;
            this.testResult = testResult;
            this.description = description;
            this.expectedState = expectedState;
            this.actualState = actualState;
            this.id = ++master_id;
        }
    }

    private static Vector entries = new Vector();
    private static String testName;
    private static String testDescription;


    /**
     * Set the test name
     * @param name
     */
    public static void setTestName(String name) {
        testName = name;
    }

    /**
     * Set the test description
     * @param desc
     */
    public static void setTestDescription(String desc) {
        testDescription = desc;
    }

    /**
     * Add an entry to the test results
     * @param phase
     * @param command
     * @param result
     * @param description
     */
    public static void addEntry(int phase, int command, boolean result, String description) {
      addEntry(phase, command, result, description, null, null);
    }

    /**
     * Add an entry to the test results
     * @param phase
     * @param command
     * @param result
     * @param description
     * @param expectedBDS
     * @param currentBDS
     */
    public static void addEntry(int phase, int command, boolean result, String description, BlackboardDeltaState expectedBDS, BlackboardDeltaState currentBDS) {
        entries.add(new TestResultEntry(phase, command, result, description, expectedBDS, currentBDS));
    }

    /**
     * Returns the overall status if the test results
     * @return true if every test passed, false if at least one test failed
     */
    public static boolean getOverallResult() {
        for (Enumeration enm = entries.elements(); enm.hasMoreElements(); ) {
            if (!(((TestResultEntry)enm.nextElement()).testResult))
                return false;
        }
        return true;
    }

    /**
     * Get the string representation of a test phase
     * @param phaseId
     * @return
     */
    private static String getPhaseAsString(int phaseId) {
        switch (phaseId) {
            case PHASE_TEST_SUBSCRIPTION: return "SUBSCRIPTION";
            case PHASE_TEST_EXECUTION: return "EXECUTION";
        }
        return "";
    }

    /**
     * Get the string representaton for a command
     * @param commandId
     * @return
     */
    private static String getCommandAsString(int commandId) {
        switch (commandId) {
            case COMMAND_SUBSCRIPTION_ASSERT: return   "SUBSCRIPTION ASSERT";
            case COMMAND_ASSERT_PUBLISH_ADD: return    "PUBLISH ADD";
            case COMMAND_ASSERT_PUBLISH_CHANGE: return "PUBLISH CHANGE";
            case COMMAND_ASSERT_PUBLISH_REMOVE: return "PUBLISH REMOVE";
            case INITIAL_STATE: return "INITIAL STATE";
        }
        return "";
    }

    /**
     * Get test result details as a stand-alone string
     * @return
     */
    public static String getReportAsString() {
        StringBuffer result = new StringBuffer();
        result.append("Test Name: " + testName+"\n");
        for (Enumeration enm = entries.elements(); enm.hasMoreElements(); ) {
            TestResultEntry tre = (TestResultEntry)enm.nextElement();
            result.append("ID: " + String.valueOf(tre.id)+
                          "\nPhase: "+ getPhaseAsString(tre.testPhase)+
                          "\nCommand: "+getCommandAsString(tre.testCommand)+
                          "\nResult: "+String.valueOf(tre.testResult)+
                          "\nDescription: "+tre.description+"\n");
        }
        return result.toString();
    }

    /**
     * Get test result details as an xml string
     * @return
     */
    public static String getReportAsXML() {
        StringBuffer result = new StringBuffer();
        result.append("<?xml version=\"1.0\"?>\n");
        result.append("<TEST Name=\""+testName+"\">\n");
        for (Enumeration enm = entries.elements(); enm.hasMoreElements(); ) {
            TestResultEntry tre = (TestResultEntry)enm.nextElement();
            result.append("<ID Value=\""+ String.valueOf(tre.id)+"\">\n");
            result.append("<PHASE>" + getPhaseAsString(tre.testPhase) + "</PHASE>\n");
            result.append("<COMMAND>" +getCommandAsString(tre.testCommand)+"</COMMAND>\n");
            result.append("<DESCRIPTION>"+tre.description+"</DESCRIPTION>\n");
            result.append("<RESULT>" + (tre.testResult?"pass":"fail") + "</RESULT>\n");
            if (tre.expectedState != null && tre.actualState != null) {
              result.append("<EXPECTED_STATE>");
              result.append(tre.expectedState.getXML());
              result.append("</EXPECTED_STATE><ACTUAL_STATE>");
              result.append(tre.actualState.getXML());
              result.append("</ACTUAL_STATE>");
            }
            result.append("</ID>\n");
        }
        result.append("</TEST>");
        return result.toString();
    }
}