package org.cougaar.plugin.test;

import org.cougaar.core.service.BlackboardService;
import org.cougaar.core.blackboard.Subscriber;
import org.cougaar.core.blackboard.Subscription;
import org.cougaar.util.UnaryPredicate;
import java.util.Collection;
import org.cougaar.core.blackboard.SubscriberException;
import org.cougaar.core.blackboard.SubscriptionWatcher;
import org.cougaar.core.persist.PersistenceNotEnabledException;
import org.cougaar.core.persist.Persistence;
import java.util.Vector;
import java.util.Enumeration;

/**
 * <p>Title: TestBlackboardService</p>
 * <p>Description: This class acts as a wrapper or delegate to the actual BlackboardService.  The reason for this is that
 * we need to monitor the subscriptions requests and the publication requests.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TestBlackboardService implements BlackboardService {

    BlackboardService actualBlackboardService;
    Vector predicates = new Vector();



    public TestBlackboardService(BlackboardService actualBlackboardService) {
        this.actualBlackboardService = actualBlackboardService;
    }

    public boolean testSubscriptions(Object obj) {
        boolean ret = false;
        for (Enumeration enum = predicates.elements(); enum.hasMoreElements(); ) {
            UnaryPredicate up = (UnaryPredicate)enum.nextElement();
            ret = up.execute(obj);
            if (ret) break;
        }

        return ret;
    }

    public Subscriber getSubscriber() {
        return actualBlackboardService.getSubscriber();
    }

    public Subscription subscribe(UnaryPredicate parm1) {
        //System.out.println("DETECTED SUBSCRIPTION: " + parm1);
        predicates.add(parm1);
        return actualBlackboardService.subscribe(parm1);
    }

    public Subscription subscribe(UnaryPredicate parm1, Collection parm2) {
        predicates.add(parm1);
        return actualBlackboardService.subscribe(parm1, parm2);
    }

    public Subscription subscribe(UnaryPredicate parm1, boolean parm2) {
        predicates.add(parm1);
        return actualBlackboardService.subscribe(parm1, parm2);
    }

    public Subscription subscribe(UnaryPredicate parm1, Collection parm2, boolean parm3) {
        predicates.add(parm1);
        return actualBlackboardService.subscribe(parm1, parm2, parm3);
    }

    public Subscription subscribe(Subscription parm1) {
        return actualBlackboardService.subscribe(parm1);
    }

    public Collection query(UnaryPredicate parm1) {
        return actualBlackboardService.query(parm1);
    }

    public void unsubscribe(Subscription parm1) {
        actualBlackboardService.unsubscribe(parm1);
    }

    public int getSubscriptionCount() {
        return actualBlackboardService.getSubscriptionCount();
    }

    public int getSubscriptionSize() {
        return actualBlackboardService.getSubscriptionSize();
    }

    public int getPublishAddedCount() {
        return actualBlackboardService.getPublishAddedCount();
    }

    public int getPublishChangedCount() {
        return actualBlackboardService.getPublishChangedCount();
    }

    public int getPublishRemovedCount() {
        return actualBlackboardService.getPublishRemovedCount();
    }

    public boolean haveCollectionsChanged() {
        return actualBlackboardService.haveCollectionsChanged();
    }

    public boolean publishAdd(Object parm1) {
        return actualBlackboardService.publishAdd(parm1);
    }

    public boolean publishRemove(Object parm1) {
        return actualBlackboardService.publishRemove(parm1);
    }

    public boolean publishChange(Object parm1) {
        return actualBlackboardService.publishChange(parm1);
    }

    public boolean publishChange(Object parm1, Collection parm2) {
        return actualBlackboardService.publishChange(parm1, parm2);
    }

    public void openTransaction() {
        actualBlackboardService.openTransaction();
    }

    public boolean tryOpenTransaction() {
        return actualBlackboardService.tryOpenTransaction();
    }

    public void closeTransaction() throws org.cougaar.core.blackboard.SubscriberException {
        actualBlackboardService.closeTransaction();
    }

    public void closeTransactionDontReset() {
        actualBlackboardService.closeTransactionDontReset();
    }

    public void closeTransaction(boolean parm1) throws org.cougaar.core.blackboard.SubscriberException {
        actualBlackboardService.closeTransaction(parm1);
    }

    public void signalClientActivity() {
        actualBlackboardService.signalClientActivity();
    }

    public SubscriptionWatcher registerInterest(SubscriptionWatcher parm1) {
        return actualBlackboardService.registerInterest(parm1);
    }

    public SubscriptionWatcher registerInterest() {
        return actualBlackboardService.registerInterest();
    }

    public void unregisterInterest(SubscriptionWatcher parm1) throws org.cougaar.core.blackboard.SubscriberException {
        actualBlackboardService.unregisterInterest(parm1);
    }

    public void setShouldBePersisted(boolean parm1) {
        actualBlackboardService.setShouldBePersisted(parm1);
    }

    public boolean shouldBePersisted() {
        return actualBlackboardService.shouldBePersisted();
    }

    public boolean didRehydrate() {
        return actualBlackboardService.didRehydrate();
    }

    public void persistNow() throws org.cougaar.core.persist.PersistenceNotEnabledException {
        actualBlackboardService.persistNow();
    }

    public Persistence getPersistence() {
        return actualBlackboardService.getPersistence();
    }
}