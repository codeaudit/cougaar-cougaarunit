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
 * <p>Title: BlackboardServiceProxy</p>
 * <p>Description: This class acts as a wrapper or delegate to the actual BlackboardService.  The reason for this is that
 * we need to monitor the subscriptions requests and the publication requests.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class BlackboardServiceProxy implements BlackboardService {

    BlackboardService actualBlackboardService;
    static Vector predicates = new Vector();
    static BlackboardDeltaState currentBlackboardDeltaState = new BlackboardDeltaState();
    private static BlackboardServiceProxy instance;

    /**
     * Standard constructor
     * @param actualBlackboardService the BlackboardService to proxy
     */
    public BlackboardServiceProxy(BlackboardService actualBlackboardService){
        this.actualBlackboardService = actualBlackboardService;
    }

    /**
     * Reset by creating a new BlackboardDeltaState object.  This way the old object
     * can still hang around of something else grabs hold of it for reporting
     * purposes
     */
    public void resetBlackboardDeltaState() {
       currentBlackboardDeltaState = new BlackboardDeltaState();
    }

    /**
     * Get the current BlackboardDeltaState object
     * @return currentBlackboardDeltaState
     */
    public BlackboardDeltaState getCurrentBlackboardDeltaState() {
        return currentBlackboardDeltaState;
    }

    /**
     * Test to see if the specified object will pass or fail the registered UnaryPedicates
     * @param obj Object to test
     * @return true of at least one UnaryPredicate accepts the object, false otherwise
     */
    public boolean testSubscriptions(Object obj) {
        boolean ret = false;
        for (Enumeration enum = predicates.elements(); enum.hasMoreElements(); ) {
            UnaryPredicate up = (UnaryPredicate)enum.nextElement();
            ret = up.execute(obj);
            if (ret) break;
        }

        return ret;
    }

    /**
     * Proxied method
     * @return
     */
    public Subscriber getSubscriber() {
        return actualBlackboardService.getSubscriber();
    }

    /**
     * Proxied method
     * @return
     */
    public Subscription subscribe(UnaryPredicate parm1) {
        predicates.add(parm1);
        return actualBlackboardService.subscribe(parm1);
    }

    /**
     * Proxied method
     * @return
     */
    public Subscription subscribe(UnaryPredicate parm1, Collection parm2) {
        predicates.add(parm1);
        return actualBlackboardService.subscribe(parm1, parm2);
    }

    /**
     * Proxied method
     * @return
     */
    public Subscription subscribe(UnaryPredicate parm1, boolean parm2) {
        predicates.add(parm1);
        return actualBlackboardService.subscribe(parm1, parm2);
    }

    /**
     * Proxied method
     * @return
     */
    public Subscription subscribe(UnaryPredicate parm1, Collection parm2, boolean parm3) {
        predicates.add(parm1);
        return actualBlackboardService.subscribe(parm1, parm2, parm3);
    }

    /**
     * Proxied method
     * @return
     */
    public Subscription subscribe(Subscription parm1) {
        return actualBlackboardService.subscribe(parm1);
    }

    /**
     * Proxied method
     * @return
     */
    public Collection query(UnaryPredicate parm1) {
        return actualBlackboardService.query(parm1);
    }

    /**
     * Proxied method
     * @return
     */
    public void unsubscribe(Subscription parm1) {
        actualBlackboardService.unsubscribe(parm1);
    }

    /**
     * Proxied method
     * @return
     */
    public int getSubscriptionCount() {
        return actualBlackboardService.getSubscriptionCount();
    }

    /**
     * Proxied method
     * @return
     */
    public int getSubscriptionSize() {
        return actualBlackboardService.getSubscriptionSize();
    }

    /**
     * Proxied method
     * @return
     */
    public int getPublishAddedCount() {
        return actualBlackboardService.getPublishAddedCount();
    }

    /**
     * Proxied method
     * @return
     */
    public int getPublishChangedCount() {
        return actualBlackboardService.getPublishChangedCount();
    }

    /**
     * Proxied method
     * @return
     */
    public int getPublishRemovedCount() {
        return actualBlackboardService.getPublishRemovedCount();
    }

    /**
     * Proxied method
     * @return
     */
    public boolean haveCollectionsChanged() {
        return actualBlackboardService.haveCollectionsChanged();
    }

    /**
     * Proxied method
     * @return
     */
    public void publishAdd(Object parm1) {
        publishAdd(parm1, false);
    }

    /**
     * Helper method to allow the test framework to selectively bypass the BlackboardDeltaState.
     * This is necessary so that the test objects that are posted to the blackboard do not get
     * included into the BlackboardDeltaState.  This way the BlackboardDeltaState will only
     * contain the object add actions that are posted in reaction to the test object.
     * @return
     */
    public void publishAdd(Object parm1, boolean bypassDelta) {
        if (!bypassDelta)  {
            currentBlackboardDeltaState.add(new PublishAction(PublishAction.ADD, parm1));
        }
        actualBlackboardService.publishAdd(parm1);
    }

    /**
     * Proxied method
     * @return
     */
    public void publishRemove(Object parm1) {
      publishRemove(parm1, false);
    }

    /**
     * Helper method to allow the test framework to selectively bypass the BlackboardDeltaState.
     * This is necessary so that the test objects that are posted to the blackboard do not get
     * included into the BlackboardDeltaState.  This way the BlackboardDeltaState will only
     * contain the object remove actions that are posted in reaction to the test object.
     * @return
     */
    public void publishRemove(Object parm1, boolean bypassDelta) {
        if (!bypassDelta) {
            currentBlackboardDeltaState.add(new PublishAction(PublishAction.REMOVE, parm1));
        }
        actualBlackboardService.publishRemove(parm1);

    }

    /**
     * Proxied method
     * @param parm1
     * @return
     */
    public void publishChange(Object parm1) {
        publishChange(parm1, false);
    }

    /**
     * Helper method to allow the test framework to selectively bypass the BlackboardDeltaState.
     * This is necessary so that the test objects that are posted to the blackboard do not get
     * included into the BlackboardDeltaState.  This way the BlackboardDeltaState will only
     * contain the object change actions that are posted in reaction to the test object.
     * @return
     */
    public void publishChange(Object parm1, boolean bypassDelta) {
        if (!bypassDelta) {
            currentBlackboardDeltaState.add(new PublishAction(PublishAction.CHANGE, parm1));
        }
        actualBlackboardService.publishChange(parm1);

    }

    /**
     * Proxied method
     * @param parm1
     * @param parm2
     * @return
     */
    public void publishChange(Object parm1, Collection parm2) {
        publishChange(parm1, parm2, false);
    }

    /**
     * Helper method to allow the test framework to selectively bypass the BlackboardDeltaState.
     * This is necessary so that the test objects that are posted to the blackboard do not get
     * included into the BlackboardDeltaState.  This way the BlackboardDeltaState will only
     * contain the object change actions that are posted in reaction to the test object.
     * @return
    */
    public void publishChange(Object parm1, Collection parm2, boolean bypassDelta) {
      if (!bypassDelta) {
        currentBlackboardDeltaState.add(new PublishAction(PublishAction.CHANGE, parm1));
      }
      actualBlackboardService.publishChange(parm1, parm2);
    }

    /**
     * Proxied method
     */
    public void openTransaction() {
      actualBlackboardService.openTransaction();
    }

    /**
     * Proxued method
     * @return
     */
    public boolean isTransactionOpen() {
      return actualBlackboardService.isTransactionOpen();
    }

    /**
     * Proxied Method
     * @return
     */
    public boolean tryOpenTransaction() {
        return actualBlackboardService.tryOpenTransaction();
    }

    /**
     * Proxied method
     * @throws org.cougaar.core.blackboard.SubscriberException
     */
    public void closeTransaction() throws org.cougaar.core.blackboard.SubscriberException {
        actualBlackboardService.closeTransaction();
    }

    /**
     * Proxied method
     */
    public void closeTransactionDontReset() {
        actualBlackboardService.closeTransactionDontReset();
    }

    /**
     * Proxed method
     * @param parm1
     * @throws org.cougaar.core.blackboard.SubscriberException
     */
    public void closeTransaction(boolean parm1) throws org.cougaar.core.blackboard.SubscriberException {
        actualBlackboardService.closeTransaction(parm1);
    }

    /**
     * Proxied Method
     */
    public void signalClientActivity() {
        actualBlackboardService.signalClientActivity();
    }

    /**
     * Proxied method
     * @param parm1
     * @return
     */
    public SubscriptionWatcher registerInterest(SubscriptionWatcher parm1) {
        return actualBlackboardService.registerInterest(parm1);
    }

    /**
     * Proxied method
     * @return
     */
    public SubscriptionWatcher registerInterest() {
        return actualBlackboardService.registerInterest();
    }

    /**
     * proxied method
     * @param parm1
     * @throws org.cougaar.core.blackboard.SubscriberException
     */
    public void unregisterInterest(SubscriptionWatcher parm1) throws org.cougaar.core.blackboard.SubscriberException {
        actualBlackboardService.unregisterInterest(parm1);
    }

    /**
     * Proxied method
     * @param parm1
     */
    public void setShouldBePersisted(boolean parm1) {
        actualBlackboardService.setShouldBePersisted(parm1);
    }

    /**
     * Proxied method
     * @return
     */
    public boolean shouldBePersisted() {
        return actualBlackboardService.shouldBePersisted();
    }

    /**
     * Proxied method
     * @return
     */
    public boolean didRehydrate() {
        return actualBlackboardService.didRehydrate();
    }

    /**
     * Proxied method
     * @throws org.cougaar.core.persist.PersistenceNotEnabledException
     */
    public void persistNow() throws org.cougaar.core.persist.PersistenceNotEnabledException {
        actualBlackboardService.persistNow();
    }

    /**
     * Proxied method
     * @return
     */
    public Persistence getPersistence() {
        return actualBlackboardService.getPersistence();
    }
}