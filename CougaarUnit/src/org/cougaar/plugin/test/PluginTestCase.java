package org.cougaar.plugin.test;

import org.cougaar.core.plugin.ComponentPlugin;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public abstract class PluginTestCase extends ComponentPlugin {
    private String description;

    /**
     * keep a static reference to the latest instance of this class
     */
    public PluginTestCase() {
        this.description = this.getClass().getName();  //initialize the description to the class name
        PluginTestResult.setTestName(this.getClass().getName());
    }

    public void setDescription(String s) {
        this.description = s;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Used for testing subscriptions
     * Will not affect the state of the blackboard
     * @paran obj - The object to publish to the blackboard
     * @param expectedResult - Indicates whether this test is expected to pass or fail
            true - pass
            false - fail
            */
    public void subscriptionAssert(Object obj, boolean expectedResult) {
        boolean result = ((BlackboardServiceProxy)blackboard).testSubscriptions(obj);
        PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_SUBSCRIPTION, PluginTestResult.COMMAND_SUBSCRIPTION_ASSERT, !(result^expectedResult), obj.getClass().getName());
    }

    /**
     * This method can be used to test that a source plugin's initial set of publications
     * is what is expected.
     * @param bbs The BlackBoardDeltaState object that defines the initial set of publications
     * by the source plugin
     * @param waitTime length of time to wait before testing the inital state
     * @param expectedResult Indicates whether this test is expected to pass or fail
     */
    public void assertInitialState(BlackboardDeltaState bds, long waitTime, boolean expectedResult) {
        if (bds != null) {
            try {
                Thread.currentThread().sleep(waitTime > 0?waitTime:0);   //wait the designated amount of time
                BlackboardDeltaState currentBDS = ((BlackboardServiceProxy)blackboard).getCurrentBlackboardDeltaState();
                boolean result = bds.compare(currentBDS, expectedResult);
                PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_EXECUTION, PluginTestResult.INITIAL_STATE, result, "Initial State", bds, currentBDS);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Publishes obj to the blackboard and blocks waitTime milliseconds until the bbs is achieved.
     * A timeout indicates an error.
     * @param obj - The object to publish to the blackbaord
     * @param bbs -The blackboard state to be achieved.  If this value is null then no state will be tested.  Passing
     * null for this parameter can be useful if the test requires making several publications to the blackboard
     * before checking the blackboard state.
     * @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.
     * 	-1 - wait indefinitely (not recommended)
            0 - do no waut
            >0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
            * @param expectedResult - Indicates whether this test is expected to pass or fail
            true - pass
            false - fail
     * @param expectedResult  what the expected outcome should be
     * @param ordered whether the comparison of the BlackboardDeltaState object should be order dependednt
            */
    public void assertPublishAdd(Object obj, BlackboardDeltaState bds, long waitTime, boolean expectedResult, boolean ordered) {
        blackboard.openTransaction();
        ((BlackboardServiceProxy)blackboard).publishAdd(obj, true);
        blackboard.closeTransaction();
        if (bds != null) {
            try {
                Thread.currentThread().sleep(waitTime > 0?waitTime:0);   //wait the designated amount of time
                BlackboardDeltaState currentBDS = ((BlackboardServiceProxy)blackboard).getCurrentBlackboardDeltaState();
                boolean result = bds.compare(currentBDS, ordered);
                PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_EXECUTION, PluginTestResult.COMMAND_ASSERT_PUBLISH_ADD, !(result^expectedResult), obj.getClass().getName(), bds, currentBDS);
                ((BlackboardServiceProxy)blackboard).resetBlackboardDeltaState();   //reset the blackboard state
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Helper method for asserting objects that don't require any state evaluation
     * @param obj
     */
    public void assertPublishAdd(Object obj) {
        assertPublishAdd(obj, null, 0, false, false);
    }

    /**
     * Publishes an object change  to the blackboard and blocks waitTime milliseconds until the bbs is achieved.
     * A timeout indicates an error.
     * @param obj - The changed object to publish to the blackbaord
     * @param bbs -The blackboard state to be achieved,  If this value is null then no state will be tested.  Passing
     * null for this parameter can be useful if the test requires making several publications to the blackboard
     * before checking the blackboard state.
     * @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.
     * 	-1 - wait indefinitely (not recommended)
            0 - do no waut
            >0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
            * @param expectedResult - Indicates whether this test is expected to pass or fail
            true - pass
            false - fail
     * @param expectedResult  what the expected outcome should be
     * @param ordered whether the comparison of the BlackboardDeltaState object should be order dependednt

            */
    public void assertPublishChange(Object obj, BlackboardDeltaState bds, long waitTime, boolean expectedResult, boolean ordered) {
        blackboard.openTransaction();
        ((BlackboardServiceProxy)blackboard).publishChange(obj, true);
        blackboard.closeTransaction();
        if (bds != null) {
            try {
                Thread.currentThread().sleep(waitTime > 0?waitTime:0);   //wait the designated amount of time
                BlackboardDeltaState currentBDS = ((BlackboardServiceProxy)blackboard).getCurrentBlackboardDeltaState();
                boolean result = bds.compare(currentBDS, ordered);
                PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_EXECUTION, PluginTestResult.COMMAND_ASSERT_PUBLISH_CHANGE, result, obj.getClass().getName(), bds, currentBDS);
                ((BlackboardServiceProxy)blackboard).resetBlackboardDeltaState();   //reset the blackboard state
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Helper method for asserting objects that don't require any state evaluation
     * @param obj
     */
    public void assertPublishChange(Object obj) {
        assertPublishChange(obj, null, 0, false, false);
    }


    /**
     * Removes obj from the blackboard and blocks waitTime milliseconds until the bbs is achieved.
     * A timeout indicates an error.
     * @param obj - The object to remove from the blackbaord
     * @param bbs -The blackboard state to be achieved.  If this value is null then no state will be tested.  Passing
     * null for this parameter can be useful if the test requires making several publications to the blackboard
     * before checking the blackboard state.
     * @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.
     * 	-1 - wait indefinitely (not recommended)
            0 - do no waut
            >0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
            * @param expectedResult - Indicates whether this test is expected to pass or fail
            true - pass
            false - fail
     * @param expectedResult  what the expected outcome should be
     * @param ordered whether the comparison of the BlackboardDeltaState object should be order dependednt
            */
    public void assertPublishRemove(Object obj, BlackboardDeltaState bds, long waitTime, boolean expectedResult, boolean ordered) {
        blackboard.openTransaction();
        ((BlackboardServiceProxy)blackboard).publishRemove(obj, true);
        blackboard.closeTransaction();
        if (bds != null) {
            try {
                Thread.currentThread().sleep(waitTime > 0?waitTime:0);   //wait the designated amount of time
                BlackboardDeltaState currentBDS = ((BlackboardServiceProxy)blackboard).getCurrentBlackboardDeltaState();
                boolean result = bds.compare(currentBDS, ordered);
                PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_EXECUTION, PluginTestResult.COMMAND_ASSERT_PUBLISH_REMOVE, !(result^expectedResult), obj.getClass().getName(), bds, currentBDS);
                ((BlackboardServiceProxy)blackboard).resetBlackboardDeltaState();   //reset the blackboard state
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Helper method for asserting objects that don't require any state evaluation
     * @param obj
     */
    public void assertPublishRemove(Object obj) {
        assertPublishRemove(obj, null, 0, false, false);
    }

    /**
     * reset the state of the BlackboardServiceProxy.  This clears whatever PublishAction objects it currently
     * is tracking.
     */
    public void resetBlackboardDeltaState() {
        ((BlackboardServiceProxy)this.blackboard).resetBlackboardDeltaState();
    }

     /**
     * Subclasses use this method to implement assertions to test the subscriptions of the target plugin
     */
    abstract public void validateSubscriptions();

    /**
     * Subclasses use this method to implement assertions to test the blackboard state during execution
     */
    abstract public void validateExecution();


    /**
     *
     * @return
     */
    abstract public String getPluginClass();

    /**
     * @todo - this method will probably probably need to run in a separate thread
     */
    public void startTests() {
        System.out.println("STARTING TESTS...");
        //determine the report format

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {Thread.currentThread().sleep(5000);} catch (Exception e){}  //for now we add a delay to let the source plugin start
                validateSubscriptions();
                validateExecution();
                String reportFormat = System.getProperty("org.cougaar.plugin.test.output.format");
                if (reportFormat == null) reportFormat = "text";
                if (reportFormat.equals("text")) {
                  System.out.println(PluginTestResult.getReportAsString());  //print the test results to stdout
                }
                else if (reportFormat.equals("xml")) {
                  System.out.println(PluginTestResult.getReportAsXML());  //print the test results to stdout as xml
                }
                else {
                  System.out.println(PluginTestResult.getReportAsString());  //print the test results to stdout
                }
                System.exit((PluginTestResult.getOverallResult())?0:1);  //exit code = 0 if all tests passed, otherwise 1
            }
        });
        t.start();
    }

    protected void setupSubscriptions(){}
    protected void execute(){}
}