package org.cougaar.plugin.test;

public class TestPluginSample extends PluginTestCase {
    public void validateSubscriptions() {
        System.out.println("Validating Subscriptions....");
        /*publishes this object to the blackboard and validates that the
        * plugin's subscription model passes the object through
        */
        subscriptionAssert(new Object(), true);

        /*publishes this object to the blackboard and validates that the
        *plugin's subscription model filters out the object
        */
        subscriptionAssert(new Object(), false);

        /**
         *Also consider publishRemove and publishChange
         */
    }

    public void validateExecution() {
        System.out.println("Validating Execution...");
        /*
        These three lines construct a blackboard state object that is used to validate the state if the
        blackboard after the asserPublishX statement. In this case, the assertPublishAdd() method will
        publish a FooObject1 object to the blackboard and validate that it results in a FooObject2 object
        being added to to the blackboard.
        */
        BlackboardState bbs = new BlackboardState();
        bbs.add(new PublishAction(PublishAction.ADD, Object.class));
        assertPublishAdd(new Object(), bbs, 5000, true);
        /**
         * PublishAdd, PublishRemove, or PublishChange objects in the blackboard and validate that some
         * resulting blackboard state (i.e., object added/removed/changed)
         */
    }

    public String getPluginClass() {
        return "org.cougaar.plugin.test.SamplePlugin";
    }


}