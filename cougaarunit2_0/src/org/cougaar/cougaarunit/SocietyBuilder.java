package org.cougaar.cougaarunit;


import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;

import org.cougaar.core.plugin.PluginBase;
import org.cougaar.core.plugin.PluginManager;
import org.cougaar.cougaarunit.vo.Agent;
import org.cougaar.cougaarunit.vo.Component;
import org.cougaar.cougaarunit.vo.ComponentImpl;
import org.cougaar.cougaarunit.vo.Node;
import org.cougaar.cougaarunit.vo.Parameter;
import org.cougaar.cougaarunit.vo.Parameters;
import org.cougaar.cougaarunit.vo.Society;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;


/**
 * DOCUMENT ME!
 *
 * @author mabrams
 */
public class SocietyBuilder {
    private static final String COMPONENT_PRIORITY = "COMPONENT";

    /**
     * DOCUMENT ME!
     *
     * @param societyName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Society buildSociety(PluginTestCase pluginTestCase) {
        Society society = new Society(pluginTestCase.getDescription());
        society.addNode(SocietyBuilder.buildNode(pluginTestCase));
        society.saveSociety("./");
        return society;
    }


    /**
     * DOCUMENT ME!
     *
     * @param node
     */
    private static void setUpParameters(Node node) {
        Mapping mapping = new Mapping();
        String filename = System.getProperty("org.cougaar.cougaarunit.parameters",
                "parameters.xml");
        Parameters parameters = null;
        try {
        	File f = new File("parameterMapping.xml");
        	System.out.println(f.getAbsolutePath().toString());
            mapping.loadMapping(f.getAbsolutePath());
            Unmarshaller unmar = new Unmarshaller(mapping);

            parameters = (Parameters) unmar.unmarshal(new InputSource(
                        new FileReader(filename)));
        } catch (Exception e) {
            System.err.println("Exception reading society XML file " + filename);
            e.printStackTrace();
        }

        if (parameters != null) {
            Iterator i = parameters.getParameters().iterator();
            while (i.hasNext()) {
                Parameter p = (Parameter) i.next();
                String pName = p.getName();
                String value = p.getValue();
//                value = value.replaceAll("COUGAAR_INSTALL_PATH", System.getProperty("org.cougaar.install.path"));
                if (pName.indexOf("-X") < 0) {
                    node.addVMParam(pName + "=" + value);
                } else {
                    node.addEnvParam(value);
                }
            }
            String progParam = "org.cougaar.core.node.Node";
            node.addProgramParameter(progParam);
        }
    }


    /**
     * DOCUMENT ME!
     *
     * @param societyName DOCUMENT ME!
     *
     * @return
     */
    private static Node buildNode(PluginTestCase pluginTestCase) {
		Node node = new Node("TestNode");
        setUpParameters(node);
		Collection domains = pluginTestCase.getDomains();
        if(domains!=null){
        	Iterator iterator = domains.iterator();
        	while(iterator.hasNext()){
        		Component component = (Component)iterator.next();
        		node.addVMParam("-Dorg.cougaar.domain."+component.getName()+"="+component.getClassName());
        	}
        }
        Collection serviceComponents = pluginTestCase.getServiceComponents();
        if (serviceComponents != null) {
            Iterator iterator = serviceComponents.iterator();
            while (iterator.hasNext()) {
                Component serviceComponent = (Component) iterator.next();
                node.addComponent(serviceComponent);
            }
        }
        node.addAgent(SocietyBuilder.buildAgent(pluginTestCase));
        return node;
    }


    /**
     * DOCUMENT ME!
     *
     * @param testPluginName DOCUMENT ME!
     *
     * @return
     */
    private static Agent buildAgent(PluginTestCase pluginTestCase) {
    	String testPluginName = pluginTestCase.getClass().getName();
    	String pluginBeingTested = pluginTestCase.getPluginClass();
        Agent agent = new Agent("TestAgent");
        String agentClass = System.getProperty("org.cougaar.cougaarunit.defaultagent", "org.cougaar.core.agent.SimpleAgent");
        agent.setClassName(agentClass);
        Component comp = new ComponentImpl(testPluginName);
        comp.setInsertionpoint(PluginBase.INSERTION_POINT);
        comp.setClassName(testPluginName);
        comp.setPriority(SocietyBuilder.COMPONENT_PRIORITY);
        agent.addComponent(comp);
        //add plugin to be tested
		Component comp2 = new ComponentImpl(testPluginName);
		comp2.setInsertionpoint(PluginBase.INSERTION_POINT);
		comp2.setClassName(pluginBeingTested);
		comp2.setPriority(SocietyBuilder.COMPONENT_PRIORITY);
		agent.addComponent(comp2);
        String pluginServiceFilter = "org.cougaar.cougaarunit.PluginServiceFilter";
        comp = new ComponentImpl(pluginServiceFilter);
        comp.setClassName(pluginServiceFilter);
        comp.setInsertionpoint(PluginManager.INSERTION_POINT + ".Binder");
        comp.setPriority("BINDER");
        agent.addComponent(comp);
        return agent;
    }
}
