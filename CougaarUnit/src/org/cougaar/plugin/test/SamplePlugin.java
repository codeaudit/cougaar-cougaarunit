package org.cougaar.plugin.test;

import org.cougaar.core.plugin.ComponentPlugin;

public class SamplePlugin extends ComponentPlugin {
    protected void setupSubscriptions() {
        System.out.println("**************SamplePlugin - setupScriptions()");
    }

    protected void execute() {
        System.out.println("*********SamplePlugin - execute()");
    }
}