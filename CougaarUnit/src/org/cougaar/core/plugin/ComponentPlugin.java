package org.cougaar.core.plugin;

import org.cougaar.core.component.BindingSite;
import org.cougaar.core.component.ServiceBroker;
import org.cougaar.core.component.ServiceRevokedEvent;
import org.cougaar.core.component.ServiceRevokedListener;

import org.cougaar.core.blackboard.BlackboardClient;
import org.cougaar.core.service.BlackboardService;
import org.cougaar.core.service.AlarmService;
import org.cougaar.core.service.SchedulerService;
import org.cougaar.core.blackboard.SubscriptionWatcher;

import org.cougaar.core.agent.ClusterServesPlugin;
import org.cougaar.core.agent.ClusterIdentifier;

import org.cougaar.util.ConfigFinder;
import org.cougaar.util.Trigger;
import org.cougaar.util.TriggerModel;
import org.cougaar.util.SyncTriggerModelImpl;

import java.util.*;
import org.cougaar.plugin.test.PluginTestCase;

/**
 * Test version of ComponentPlugin
 * This class should be identical to the normal ComponentPlugin except the following changes:
 * (1) setBlackboardService() method has been modified to use the BlackboardServiceProxy object
 * (2) registerClass() method has been added
 * (3) load() method modified to call registerClass() at the very end of the method
 * (3) pluginList vector has been added
 *
 * @see org.cougaar.core.blackboard.BlackboardClientComponent
 *
 */

abstract public class ComponentPlugin   extends org.cougaar.util.GenericStateModelAdapter
  implements PluginBase, BlackboardClient {

  private Object parameter = null;

  private SchedulerService scheduler;
  protected BlackboardService blackboard;
  protected AlarmService alarmService;

  protected String blackboardClientName;

  private PluginBindingSite pluginBindingSite;
  private ServiceBroker serviceBroker;

  private TriggerModel tm;
  private SubscriptionWatcher watcher;
  private static Vector pluginList = new Vector();

  public ComponentPlugin() {
  }


  /**
   * This method is added for testing
   */
  public void registerClass() {
      pluginList.add(this);

      //if this is an instance of the TestPlugin then we need to call it to find out the class name of the plugin
      //that is to be tested
      if (this instanceof PluginTestCase) {
          String targetPluginClassName = ((PluginTestCase)this).getPluginClass();
          if (targetPluginClassName == null) throw new RuntimeException("You must implement the getPluginClass()) method");
          //now we need to see if that plugin has already been loaded
          for (Enumeration plugins = pluginList.elements(); plugins.hasMoreElements(); ) {
              Object obj = plugins.nextElement();
              if (obj.getClass().getName().equals(targetPluginClassName)) {
                  //if the target plugin has been loaded then we can start the tests
                  ((PluginTestCase)this).startTests();
                  return;
              }
          }
      }
      else {  //for any other class we need to check the pluginList to see if the TestPlugin has already been loaded
          for (Enumeration plugins = pluginList.elements(); plugins.hasMoreElements(); ) {
              Object obj = plugins.nextElement();
              if (obj instanceof PluginTestCase) {
                  //now we need to get the target plugin class name from the test plugin and see if this current class
                  //is the one we're looking for
                  String targetPluginClassName = ((PluginTestCase)obj).getPluginClass();
                  if (targetPluginClassName == null) throw new RuntimeException("You must implement the getPluginClass()) method");
                  if (this.getClass().getName().equals(targetPluginClassName)) {
                      //if this current class is the target plugin, then start the tests
                      ((PluginTestCase)obj).startTests();
                      return;
                  }
              }
          }

      }
  }

  /**
   * Called just after construction (via introspection) by the
   * loader if a non-null parameter Object was specified by
   * the ComponentDescription.
   **/
  public void setParameter(Object param) {
    parameter = param;
  }

  /**
   * @return the parameter set by {@link #setParameter}
   **/
  public Object getParameter() {
    return parameter;
  }

  /**
   * Get any Plugin parameters passed by the plugin instantiator.
   * @return The parameter specified
   * if it was a collection, a collection with one element (the parameter) if
   * it wasn't a collection, or an empty collection if the parameter wasn't
   * specified.
   */
  public Collection getParameters() {
    if (parameter == null) {
      return new ArrayList(0);
    } else {
      if (parameter instanceof Collection) {
        return (Collection) parameter;
      } else {
        List l = new ArrayList(1);
        l.add(parameter);
        return l;
      }
    }
  }

  /**
   * Binding site is set by reflection at creation-time.
   */
  public void setBindingSite(BindingSite bs) {
    if (bs instanceof PluginBindingSite) {
      pluginBindingSite = (PluginBindingSite)bs;
    } else {
      throw new RuntimeException("Tried to load "+this+" into "+bs);
    }
    serviceBroker = pluginBindingSite.getServiceBroker();
  }

  /**
   * Get the binding site, for subclass use.
   *
   */
  protected PluginBindingSite getBindingSite() {
    return pluginBindingSite;
  }

  /**
   * Get the service broker, for subclass use.
   */
  protected ServiceBroker getServiceBroker() {
    return serviceBroker;
  }

  // rely upon load-time introspection to set these services -
  //   don't worry about revokation.
  public final void setSchedulerService(SchedulerService ss) {
    scheduler = ss;
  }

  //Method modified for testing purposes
  /*
  public final void setBlackboardService(BlackboardService bs) {
     blackboard = bs;
  }*/

  public final void setBlackboardService(BlackboardService bs) {
      //blackboard = org.cougaar.plugin.test.BlackboardServiceProxy.getInstance(bs);      //construct the test framework wrapper for the BlackboardService
      blackboard = bs;
  }

  public final void setAlarmService(AlarmService s) {
    alarmService = s;
  }

  /**
   * Get the blackboard service, for subclass use.
   */
  protected BlackboardService getBlackboardService() {
    return blackboard;
  }

  /**
   * Get the alarm service, for subclass use.
   */
  protected AlarmService getAlarmService() {
    return alarmService;
  }

  protected final void requestCycle() {
    tm.trigger();
  }

  //
  // implement GenericStateModel:
  //

  public void initialize() {
    super.initialize();
  }

  public void load() {
    super.load();

    // create a blackboard watcher
    this.watcher =
      new SubscriptionWatcher() {
        public void signalNotify(int event) {
          // gets called frequently as the blackboard objects change
          super.signalNotify(event);
          requestCycle();
        }
        public String toString() {
          return "ThinWatcher("+ComponentPlugin.this.toString()+")";
        }
      };

    // create a callback for running this component
    Trigger myTrigger =
      new Trigger() {
        String pluginName = null;
        private boolean didPrecycle = false;
        // no need to "sync" when using "SyncTriggerModel"
        public void trigger() {
          Thread currentThread = Thread.currentThread();
          String savedName = currentThread.getName();
          if (pluginName == null) pluginName = getBlackboardClientName();
          currentThread.setName(pluginName);
          awakened = watcher.clearSignal();
          try {
            if (didPrecycle) {
              cycle();
            } else {
              didPrecycle = true;
              precycle();
            }
          } finally {
            awakened = false;
            currentThread.setName(savedName);
          }
        }
        public String toString() {
          return "Trigger("+ComponentPlugin.this.toString()+")";
        }
      };

    // create the trigger model
    this.tm = new SyncTriggerModelImpl(scheduler, myTrigger);

    // activate the blackboard watcher
    blackboard.registerInterest(watcher);

    // activate the trigger model
    tm.initialize();
    tm.load();

    registerClass();
  }

  public void start() {
    super.start();
    tm.start();
    // Tell the scheduler to run me at least this once
    requestCycle();
  }

  public void suspend() {
    super.suspend();
    tm.suspend();
  }

  public void resume() {
    super.resume();
    tm.resume();
  }

  public void stop() {
    super.stop();
    tm.stop();
  }

  public void halt() {
    super.halt();
    tm.halt();
  }

  public void unload() {
    super.unload();
    if (tm != null) {
      tm.unload();
      tm = null;
    }
    blackboard.unregisterInterest(watcher);
    if (alarmService != null) {
      serviceBroker.releaseService(this, AlarmService.class, alarmService);
      alarmService = null;
    }
    if (blackboard != null) {
      serviceBroker.releaseService(this, BlackboardService.class, blackboard);
      blackboard = null;
    }
    if (scheduler != null) {
      serviceBroker.releaseService(this, SchedulerService.class, scheduler);
      scheduler = null;
    }
  }

  //
  // implement basic "callback" actions
  //

  protected void precycle() {
    try {
      blackboard.openTransaction();
      setupSubscriptions();

      // run execute here so subscriptions don't miss out on the first
      // batch in their subscription addedLists
      execute();                // MIK: I don't like this!!!
    } catch (Throwable t) {
      System.err.println("Error: Uncaught exception in "+this+": "+t);
      t.printStackTrace();
    } finally {
      blackboard.closeTransaction();
    }
  }

  protected void cycle() {
    // do stuff
    try {
      blackboard.openTransaction();
      if (shouldExecute()) {
        execute();
      }
    } catch (Throwable t) {
      System.err.println("Error: Uncaught exception in "+this+": "+t);
      t.printStackTrace();
    } finally {
      blackboard.closeTransaction();
    }
  }

  protected boolean shouldExecute() {
    return (wasAwakened() || blackboard.haveCollectionsChanged());
  }

  /**
   * Called once after initialization, as a "pre-execute()".
   */
  protected abstract void setupSubscriptions();

  /**
   * Called every time this component is scheduled to run.
   */
  protected abstract void execute();

  //
  // misc utility methods:
  //

  protected ConfigFinder getConfigFinder() {
    // Fix to use service-based API instead of custom BindingSite
    return ((PluginBindingSite) getBindingSite()).getConfigFinder();
  }

  /**
   * @deprecated Use the self Organization or plugin parameters
   * instead.  This method will be removed.
   */
  protected ClusterIdentifier getClusterIdentifier() {
    return getAgentIdentifier();
  }

  /**
   * Get the local agent's address.
   */
  protected ClusterIdentifier getAgentIdentifier() {
    // Fix to use service-based API instead of custom BindingSite
    return ((PluginBindingSite) getBindingSite()).getAgentIdentifier();
  }

  /** storage for wasAwakened - only valid during cycle().
   **/
  private boolean awakened = false;

  /** true IFF were we awakened explicitly (i.e. we were asked to run
   * even if no subscription activity has happened).
   * The value is valid only within the scope of the cycle() method.
   */
  protected final boolean wasAwakened() { return awakened; }

  // for BlackboardClient use
  public synchronized String getBlackboardClientName() {
    if (blackboardClientName == null) {
      StringBuffer buf = new StringBuffer();
      buf.append(getClass().getName());
      if (parameter instanceof Collection) {
        buf.append("[");
        String sep = "";
        for (Iterator params = ((Collection)parameter).iterator(); params.hasNext(); ) {
          buf.append(sep);
          buf.append(params.next().toString());
          sep = ",";
        }
        buf.append("]");
      }
      blackboardClientName = buf.substring(0);
    }
    return blackboardClientName;
  }

  public long currentTimeMillis() {
    if (alarmService != null)
      return alarmService.currentTimeMillis();
    else
      return System.currentTimeMillis();
  }

  // odd BlackboardClient method -- will likely be removed.
  public boolean triggerEvent(Object event) {
    return false;
  }

  public String toString() {
    return getBlackboardClientName();
  }
}


