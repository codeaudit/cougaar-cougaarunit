package org.cougaar.cougaarunit;

import org.cougaar.core.plugin.PluginBase;
import org.cougaar.core.plugin.PluginManager;
import org.cougaar.cougaarunit.vo.Agent;
import org.cougaar.cougaarunit.vo.Component;
import org.cougaar.cougaarunit.vo.ComponentImpl;
import org.cougaar.cougaarunit.vo.Node;
import org.cougaar.cougaarunit.vo.Society;

/**
 * @author mabrams
 */
public class SocietyBuilder {
	private final static String COMPONENT_PRIORITY = "COMPONENT";
	public static Society buildSociety(String societyName) {
		Society society = new Society(societyName);
		society.addNode(SocietyBuilder.buildNode(societyName));	
		society.saveSociety("./");
		return society;
	}

	/**
	 * @return
	 */
	private static Node buildNode(String societyName) {
		Node node = new Node(societyName + "_testNode");
		node.addAgent(SocietyBuilder.buildAgent(societyName));		
		return node;
	}

	/**
	 * @return
	 */
	private static Agent buildAgent(String testPluginName) {
		Agent agent = new Agent(testPluginName + "_testAgent");
		Component comp = new ComponentImpl(testPluginName);
		comp.setInsertionpoint(PluginBase.INSERTION_POINT);
		comp.setClassName(testPluginName);
		comp.setPriority(SocietyBuilder.COMPONENT_PRIORITY);
		agent.addComponent(comp);
		String pluginServiceFilter = "org.cougaar.cougaarunit.PluginServiceFilter";
		comp = new ComponentImpl(pluginServiceFilter);
		comp.setClassName(pluginServiceFilter);		
		comp.setInsertionpoint(PluginManager.INSERTION_POINT + ".BINDER");
		comp.setPriority("BINDER");
		agent.addComponent(comp);
		return agent;
	}
}
