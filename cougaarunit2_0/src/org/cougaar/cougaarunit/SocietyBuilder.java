package org.cougaar.cougaarunit;


import java.io.FileReader;

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
    public static Society buildSociety(String societyName) {
        Society society = new Society(societyName);
        society.addNode(SocietyBuilder.buildNode(societyName));
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
            mapping.loadMapping("parameterMapping.xml");
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
    private static Node buildNode(String societyName) {
        Node node = new Node(societyName + "_testNode");
        setUpParameters(node);
        node.addAgent(SocietyBuilder.buildAgent(societyName));
        return node;
    }


    /**
     * DOCUMENT ME!
     *
     * @param testPluginName DOCUMENT ME!
     *
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
