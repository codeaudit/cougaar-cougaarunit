package org.cougaar.plugin.test;

import java.util.Iterator;

public abstract class PluginTestSuite {
    /**
     * This method should return an iterator of class objects that represent
     * all the test cases to be tested within this suite
     */
    public abstract Iterator getTestClasses();
}