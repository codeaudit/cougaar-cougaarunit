package org.cougaar.plugin.test.capture;

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
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import org.cougaar.plugin.test.ClassStringRendererRegistry;

/**
 * <p>Title: BlackboardServiceCapturingProxy</p>
 * <p>Description: This class acts as a wrapper or delegate to the actual BlackboardService.  The reason for this is that
 * we need to monitor the subscriptions requests and the publication requests.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class BlackboardServiceCapturingProxy implements BlackboardService {

    BlackboardService actualBlackboardService;
    String requestingClient;
    static Vector objectStream = new Vector(250);

    /**
     * Standard constructor
     * @param actualBlackboardService the BlackboardService to proxy
     */
    public BlackboardServiceCapturingProxy(BlackboardService actualBlackboardService, Object requestingClient){
        this.actualBlackboardService = actualBlackboardService;
        this.requestingClient = requestingClient.toString();
    }


    public Vector getSerializedData() {
      return objectStream;
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
        return actualBlackboardService.subscribe(parm1);
    }

    /**
     * Proxied method
     * @return
     */
    public Subscription subscribe(UnaryPredicate parm1, Collection parm2) {
        return actualBlackboardService.subscribe(parm1, parm2);
    }

    /**
     * Proxied method
     * @return
     */
    public Subscription subscribe(UnaryPredicate parm1, boolean parm2) {
        return actualBlackboardService.subscribe(parm1, parm2);
    }

    /**
     * Proxied method
     * @return
     */
    public Subscription subscribe(UnaryPredicate parm1, Collection parm2, boolean parm3) {
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
    public boolean publishAdd(Object parm1) {
        objectStream.add(new CapturedPublishAction(CapturedPublishAction.ACTION_ADD, ClassStringRendererRegistry.render(parm1), requestingClient));
        return actualBlackboardService.publishAdd(parm1);
    }


    /**
     * Proxied method
     * @return
     */
    public boolean publishRemove(Object parm1) {
      objectStream.add(new CapturedPublishAction(CapturedPublishAction.ACTION_REMOVE, ClassStringRendererRegistry.render(parm1), requestingClient));
      return actualBlackboardService.publishRemove(parm1);
    }

    /**
     * Proxied method
     * @param parm1
     * @return
     */
    public boolean publishChange(Object parm1) {
        objectStream.add(new CapturedPublishAction(CapturedPublishAction.ACTION_CHANGE, ClassStringRendererRegistry.render(parm1), requestingClient));
        return actualBlackboardService.publishChange(parm1);
    }

    /**
     * Proxied method
     * @param parm1
     * @param parm2
     * @return
     */
    public boolean publishChange(Object parm1, Collection parm2) {
        objectStream.add(new CapturedPublishAction(CapturedPublishAction.ACTION_CHANGE, ClassStringRendererRegistry.render(parm1), requestingClient));
        return actualBlackboardService.publishChange(parm1, parm2);
    }

    /**
     * Proxied method
     */
    public void openTransaction() {
        actualBlackboardService.openTransaction();
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