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

    public PluginTestCase() {
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
        boolean result = ((TestBlackboardService)blackboard).testSubscriptions(obj);
        PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_SUBSCRIPTION, PluginTestResult.COMMAND_SUBSCRIPTION_ASSERT, result);
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
            */
    public void assertPublishAdd(Object obj, BlackboardState bbs, long waitTime, boolean expectedResult) {
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
            */
    public void assertPublishChange(Object obj, BlackboardState bbs, long waitTime, boolean expectedResult) {
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
            */
    public void assertPublishRemove(Object obj, BlackboardState bbs, long waitTime, boolean expectedResult) {
    }

    /**
     * reset the state of the TestBlackboardService.  This clears whatever PublishAction objects it currently
     * is tracking.
     */
    public void resetBlackboardState() {
        ((TestBlackboardService)this.blackboard).resetBlackboardState();
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
        Thread t = new Thread(new Runnable() {
            public void run() {
                validateSubscriptions();
                validateExecution();
            }
        });
        t.start();
    }

    protected void setupSubscriptions(){}
    protected void execute(){}
}