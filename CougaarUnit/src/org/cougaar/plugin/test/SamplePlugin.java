package org.cougaar.plugin.test;

import org.cougaar.core.plugin.ComponentPlugin;
import java.net.URLClassLoader;
import java.net.URL;
import org.cougaar.bootstrap.XURLClassLoader;
import org.cougaar.util.UnaryPredicate;
import org.cougaar.core.blackboard.IncrementalSubscription;
import java.util.Iterator;
import java.util.Collection;

public class SamplePlugin extends ComponentPlugin {
    private IncrementalSubscription testIS;

    UnaryPredicate testUP = new UnaryPredicate() {
        public boolean execute (Object o) {
            if (o instanceof TestBBMessageObject)
                return true;
            return false;
        }
    };;

    protected void setupSubscriptions() {
        testIS = (IncrementalSubscription)getBlackboardService().subscribe(testUP);
    }

    protected void execute() {
        Collection messages = testIS.getAddedCollection();
        if (!messages.isEmpty()) {
            for (Iterator iter=messages.iterator(); iter.hasNext(); ) {
                System.out.println("added: " + iter.next());
            }
            getBlackboardService().publishAdd(new TestBBMessageObject2());
        }

        messages = testIS.getChangedCollection();
        if (!messages.isEmpty()) {
            for (Iterator iter=messages.iterator(); iter.hasNext(); ) {
                System.out.println("changed: " + iter.next());
            }
            getBlackboardService().publishAdd(new TestBBMessageObject3());
        }

        messages = testIS.getRemovedCollection();
        if (!messages.isEmpty()) {
            for (Iterator iter=messages.iterator(); iter.hasNext(); ) {
                System.out.println("removed: " + iter.next());
            }
            getBlackboardService().publishAdd(new TestBBMessageObject4());
        }

    }
}