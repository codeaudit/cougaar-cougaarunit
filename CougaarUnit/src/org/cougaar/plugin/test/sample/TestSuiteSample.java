package org.cougaar.plugin.test.sample;

import java.util.Iterator;
import org.cougaar.plugin.test.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TestSuiteSample extends PluginTestSuite {
    public Class[] getTestClasses() {
        return new Class[] {TestPluginSample.class};
    }
}