package org.cougaar.cougaarunit;

import java.util.Enumeration;
import java.util.Vector;

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
    public static class TestResultEntry {
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
		/**
		 * @return Returns the actualState.
		 */
		public BlackboardDeltaState getActualState() {
			return actualState;
		}

		/**
		 * @param actualState The actualState to set.
		 */
		public void setActualState(BlackboardDeltaState actualState) {
			this.actualState = actualState;
		}

		/**
		 * @return Returns the description.
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description The description to set.
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return Returns the expectedState.
		 */
		public BlackboardDeltaState getExpectedState() {
			return expectedState;
		}

		/**
		 * @param expectedState The expectedState to set.
		 */
		public void setExpectedState(BlackboardDeltaState expectedState) {
			this.expectedState = expectedState;
		}

		/**
		 * @return Returns the id.
		 */
		public int getId() {
			return id;
		}

		/**
		 * @param id The id to set.
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * @return Returns the testCommand.
		 */
		public int getTestCommand() {
			return testCommand;
		}

		/**
		 * @param testCommand The testCommand to set.
		 */
		public void setTestCommand(int testCommand) {
			this.testCommand = testCommand;
		}

		/**
		 * @return Returns the testPhase.
		 */
		public int getTestPhase() {
			return testPhase;
		}

		/**
		 * @param testPhase The testPhase to set.
		 */
		public void setTestPhase(int testPhase) {
			this.testPhase = testPhase;
		}

		/**
		 * @return Returns the testResult.
		 */
		public boolean isTestResult() {
			return testResult;
		}

		/**
		 * @param testResult The testResult to set.
		 */
		public void setTestResult(boolean testResult) {
			this.testResult = testResult;
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
        for (Enumeration e = entries.elements(); e.hasMoreElements(); ) {
            if (!(((TestResultEntry)e.nextElement()).testResult))
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
        for (Enumeration e = entries.elements(); e.hasMoreElements(); ) {
            TestResultEntry tre = (TestResultEntry)e.nextElement();
            result.append("ID: " + String.valueOf(tre.id)+
                          "\nPhase: "+ getPhaseAsString(tre.testPhase)+
                          "\nCommand: "+getCommandAsString(tre.testCommand)+
                          "\nResult: "+String.valueOf(tre.testResult)+
                          "\nDescription: "+tre.description+"\n");
        }
        return result.toString();
    }

   
	/**
	 * @return Returns the entries.
	 */
	public static Vector getEntries() {
		return entries;
	}

	/**
	 * @return Returns the testDescription.
	 */
	public static String getTestDescription() {
		return testDescription;
	}

	/**
	 * @return Returns the testName.
	 */
	public static String getTestName() {
		return testName;
	}

}