package org.cougaar.plugin.test;

import org.cougaar.util.*;
import org.cougaar.core.plugin.*;
import org.cougaar.core.service.BlackboardService;
import org.cougaar.core.agent.ClusterIdentifier;
import org.cougaar.core.component.*;
import org.cougaar.core.service.AlarmService;
import java.util.Collection;
import org.cougaar.core.blackboard.BlackboardClient;

import java.util.*;

/**
 *
 * <p>Title: BlackboardClientProxy</p>
 * <p>Description: This class is used to detect when the plugins are loaded so that we can begin testing</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
public class BlackboardClientProxy implements BlackboardClient
{
  private BlackboardClient actualPlugin;
  private static Vector pluginList = new Vector();

/*  private void setParameters(ComponentPlugin plugin, String[] params) {
    ArrayList list = new ArrayList(params.length);
    for (int i=0; i<params.length; i++) {
      list.add(params[i]);
    }
    plugin.setParameter(list);

  }*/

  /**
   * This method is added for testing
   */
  public void registerClass() {
    pluginList.add(actualPlugin);

    //if this is an instance of the TestPlugin then we need to call it to find out the class name of the plugin
    //that is to be tested
    if (actualPlugin instanceof PluginTestCase) {
      String targetPluginClassName = ((PluginTestCase)actualPlugin).getPluginClass();
      if (targetPluginClassName == null) throw new RuntimeException("You must implement the getPluginClass()) method");
      //now we need to see if that plugin has already been loaded
      for (Enumeration plugins = pluginList.elements(); plugins.hasMoreElements(); ) {
        Object obj = plugins.nextElement();
        if (obj.getClass().getName().equals(targetPluginClassName)) {
          //if the target plugin has been loaded then we can start the tests
          ((PluginTestCase)actualPlugin).startTests();
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
          if (actualPlugin.getClass().getName().equals(targetPluginClassName)) {
            //if this current class is the target plugin, then start the tests
            ((PluginTestCase)obj).startTests();
            return;
          }
        }
      }

    }
  }

  /**
   * Constrcutor
   * @param actualPlugin the BlackboardClient to proxy
   */
  public BlackboardClientProxy(BlackboardClient actualPlugin) {
    this.actualPlugin = actualPlugin;
  }

  /**
   * Proxied method
   * @param event
   * @return
   */
  public boolean triggerEvent(Object event) {
    return actualPlugin.triggerEvent(event);
  }

  /**
   * Proxied method
   * When a plugin is loaded the Blackboard service will call this function.  So,
   * this is what we trigger off of.
   * @return String blackboard client name
   */
  public synchronized String getBlackboardClientName() {
    registerClass();
    return actualPlugin.getBlackboardClientName();
  }

  /**
   * Proxied method.
   * @return
   */
  public long currentTimeMillis() {
    return actualPlugin.currentTimeMillis();
  }
}