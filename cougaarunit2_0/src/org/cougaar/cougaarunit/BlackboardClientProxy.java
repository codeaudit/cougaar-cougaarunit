package org.cougaar.cougaarunit;

import java.util.Enumeration;
import java.util.Vector;

import org.cougaar.core.blackboard.BlackboardClient;

/**
 *
 * <p>Title: BlackboardClientProxy</p>
 * <p>Description: This class is used to detect when the plugins are loaded so that we can begin testing</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
public class BlackboardClientProxy implements BlackboardClient {
	private BlackboardClient actualPlugin;
	private static Vector pluginList = new Vector();
	private static boolean testStarted  =false;
	

	/**
	 * This method is added for testing
	 */
	public void registerClass() {
		pluginList.add(actualPlugin);

		//if this is an instance of the TestPlugin then we need to call it to find out the class name of the plugin
		//that is to be tested
		if (actualPlugin instanceof PluginTestCase) {
			String targetPluginClassName =
				((PluginTestCase) actualPlugin).getPluginClass();
			if (targetPluginClassName == null)
				throw new RuntimeException("You must implement the getPluginClass()) method");
			//now we need to see if that plugin has already been loaded
			for (Enumeration plugins = pluginList.elements();
				plugins.hasMoreElements();
				) {
				Object obj = plugins.nextElement();
				if (obj.getClass().getName().equals(targetPluginClassName)) {
					//if the target plugin has been loaded then we can start the tests
					if (testStarted == false) {
						testStarted = true;
						((PluginTestCase) actualPlugin).startTests();
						((PluginTestCase) actualPlugin).setStarted(true);
					}
					return;
				}
			}
		} else { //for any other class we need to check the pluginList to see if the TestPlugin has already been loaded
			for (Enumeration plugins = pluginList.elements();
				plugins.hasMoreElements();
				) {
				Object obj = plugins.nextElement();
				if (obj instanceof PluginTestCase) {
					//now we need to get the target plugin class name from the test plugin and see if this current class
					//is the one we're looking for
					String targetPluginClassName =
						((PluginTestCase) obj).getPluginClass();
					if (targetPluginClassName == null)
						throw new RuntimeException("You must implement the getPluginClass()) method");
					if (actualPlugin
						.getClass()
						.getName()
						.equals(targetPluginClassName)) {
						//if this current class is the target plugin, then start the tests
						if (testStarted == false) {
							testStarted = true;
							((PluginTestCase) obj).startTests();
							((PluginTestCase) obj).setStarted(true);
						}
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