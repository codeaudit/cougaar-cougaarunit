package org.cougaar.cougaarunit.vo;


import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import org.cougaar.core.util.UID;


/**
 * DOCUMENT ME!
 *
 * @author mabrams
 */
public class Node implements Serializable {   
    private Vector programParams = null;
    private Vector environmentParams = null;
    private Vector vmParams = null;
    private Vector agents = null;
    private Vector components = null;
    private String name = null;    
	private UID uid;

    /**
     * default constructor
     */
    public Node() {
        programParams = new Vector();
        environmentParams = new Vector();
        vmParams = new Vector();
        agents = new Vector();
        components = new Vector();        
    }


    /**
     * Creates a new Node object.
     *
     * @param _name DOCUMENT ME!
     */
    public Node(String _name) {
        this();
        name = _name;
    }

    /**
     * get the agents on this node
     *
     * @return Returns the agents.
     */
    public Vector getAgents() {
        return agents;
    }


    /**
     * set the agents on the node
     *
     * @param agents The agents to set.
     */
    public void setAgents(Vector agents) {
        this.agents = agents;
    }


    /**
     * get the components on the node
     *
     * @return Returns the components.
     */
    public Vector getComponents() {
        return components;
    }


    /**
     * set the components on the node
     *
     * @param components The components to set.
     */
    public void setComponents(Vector components) {
        this.components = components;
    }


    /**
     * get the enviromental paramarameters
     *
     * @return Returns the environmentParams.
     */
    public Vector getEnvironmentParams() {
        return environmentParams;
    }


    /**
     * set the enviromental paramarameters
     *
     * @param environmentParams The environmentParams to set.
     */
    public void setEnvironmentParams(Vector envrionmentParams) {
        this.environmentParams = envrionmentParams;
    }

    /**
     * get the node name
     *
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }


    /**
     * set the node name
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * get the program parameters
     *
     * @return Returns the programParams.
     */
    public Vector getProgramParams() {
        return programParams;
    }


    /**
     * set the program parameters
     *
     * @param programParams The programParams to set.
     */
    public void setProgramParams(Vector programParams) {
        this.programParams = programParams;
    }


    /**
     * get the vm arguments
     * 
     * @return Returns the vmParams.
     */
    public Vector getVmParams() {
        return vmParams;
    }


    /**
     * set the vm arguments
     *
     * @param vmParams The vmParams to set.
     */
    public void setVmParams(Vector vmParams) {
        this.vmParams = vmParams;
    }


    /**
     * add an agent to the node
     *
     * @param o
     *
     * @return
     */
    public boolean addAgent(Object o) {
        return agents.add(o);
    }


    /**
     * remove an agent from the node
     *
     * @param o
     *
     * @return
     */
    public boolean removeAgent(Object o) {
        return agents.remove(o);
    }


    /**
     * add a program paremeter
     *
     * @param param prameter to add
     *
     * @return true if added
     */
    public boolean addProgramParameter(String param) {
        return programParams.add(param);
    }


    /**
     * remove a program parameter
     *
     * @param param parameter to remove
     *
     * @return true if removed
     */
    public boolean removeProgramParameter(String param) {
        return programParams.remove(param);
    }


    /**
     * add a vm parameter
     *
     * @param param vm param to add
     *
     * @return true if added
     */
    public boolean addVMParam(String param) {
        return vmParams.add(param);
    }


    /**
     * 
     *
     * @param param remove vm parameters
     *
     * @return true if removed
     */
    public boolean removeVMParam(String param) {
        return vmParams.remove(param);
    }


    /**
     * add a component
     *
     * @param component component to add
     *
     * @return true if added
     */
    public boolean addComponent(Object component) {
        return components.add(component);
    }


    /**
     * remove a component
     *
     * @param component component to remove
     *
     * @return true if removed
     */
    public boolean removeComponent(Object component) {
        return components.remove(component);
    }


    /**
     * add env param
     *
     * @param param param to add
     *
     * @return true if added
     */
    public boolean addEnvParam(String param) {
        return environmentParams.add(param);
    }
    

    /**
     * remvoed environement param
     *
     * @param param param to remove
     *
     * @return true if added
     */
    public boolean removeEnvParam(String param) {
        return environmentParams.remove(param);
    }
	
	/**
	 * @return
	 */
	public int getComponentListSize() {
		return components.size();
	}
	
	public Vector getChildComponents() {
		Vector ret = new Vector();
		ret.addAll(agents);
		ret.addAll(components);
		return ret;
	}


	/* (non-Javadoc)
	 * @see org.cougaar.core.util.UniqueObject#getUID()
	 */
	public UID getUID() {
		return this.uid;
	}


	/* (non-Javadoc)
	 * @see org.cougaar.core.util.UniqueObject#setUID(org.cougaar.core.util.UID)
	 */
	public void setUID(UID uid) {
		this.uid = uid;
		
	}
	
	public boolean containsAgent(Agent agent) {
		return agents.contains(agent);
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Node)) {
			return false;
		}
		Node n = (Node)o;
		if (!n.getName().equals(this.getName())) {
			return false;
		}
		
		Iterator i = agents.iterator();
		while (i.hasNext()) {
			Agent agent = (Agent) i.next();
			if (!n.containsAgent(agent)) {
				return false;
			}
		}
		return true;
	}
}
