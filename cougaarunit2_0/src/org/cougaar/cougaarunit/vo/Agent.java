package org.cougaar.cougaarunit.vo;


import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import org.cougaar.core.util.UID;


/**
 * Represents an agent in a cougaar society
 *
 * @author mabrams
 */
public class Agent implements Serializable, Component {
    private Vector components;
    private Vector facets;
    private String className;
    private String priority;
    private String insertionPoint;
    private String order;
    private String name;
    /** backpointer to node */
    private String nodeName;
    private Vector arguments;
    private UID uid;

    /**
     * Creates a new Agent object.
     */
    public Agent() {
        components = new Vector();
        facets = new Vector();
        arguments = new Vector();
    }


    /**
     * constructor
     *
     * @param _name
     */
    public Agent(String _name) {
        this();
        setName(_name);
    }   


	/**
     * returns all of the components on this agent
     *
     * @return Returns the components.
     */
    public Vector getComponents() {
        return components;
    }


    /**
     * sets the components on this agent
     *
     * @param components The components to set.
     */
    public void setComponents(Vector components) {
        this.components = components;
    }


    /**
     * DOCUMENT ME!
     *
     * @return Returns the facets.
     */
    public Vector getFacets() {
        return facets;
    }


    /**
     * DOCUMENT ME!
     *
     * @param facets The facets to set.
     */
    public void setFacets(Vector facets) {
        this.facets = facets;
    }


    /**
     * DOCUMENT ME!
     *
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }


    /**
     * DOCUMENT ME!
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * DOCUMENT ME!
     *
     * @param facet DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean addFacet(String facet) {
        return facets.add(facet);
    }


    /**
     * DOCUMENT ME!
     *
     * @param facet DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean removeFacet(String facet) {
        return facets.remove(facet);
    }


    /**
     * DOCUMENT ME!
     *
     * @param component DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean addComponent(Object component) {
        return components.add(component);
    }


    /**
     * DOCUMENT ME!
     *
     * @param component DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean removeComponent(Object component) {
        return components.remove(component);
    }


    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public boolean equals(Object o) {
        Agent agent = (Agent) o;
        if (!agent.getName().equals(this.getName())) {
            return false;
        }

        if (agent.getClassName() !=null && this.className != null && !agent.getClassName().equals(this.getClassName())) {
            return false;
        }

        Vector compComponents = agent.getComponents();
        Iterator i = compComponents.iterator();

        while (i.hasNext()) {
            boolean matched = false;
            ComponentImpl comp1 = (ComponentImpl) i.next();
            Iterator j = this.components.iterator();
            while (j.hasNext()) {
                ComponentImpl comp2 = (ComponentImpl) j.next();
                if (comp1.equals(comp2)) {
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        return true;
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Vector getChildComponents() {
        return components;
    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#setClassName(java.lang.String)
     */
    public void setClassName(String className) {
        this.className = className;

    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#getClassName()
     */
    public String getClassName() {
        return this.className;
    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#setPriority(java.lang.String)
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#getPriority()
     */
    public String getPriority() {
        return priority;
    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#setInsertionPoint(java.lang.String)
     */
    public void setInsertionpoint(String insertionPoint) {
        this.insertionPoint = insertionPoint;

    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#getInsertionPoint()
     */
    public String getInsertionpoint() {
        return insertionPoint;
    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#setOrder(java.lang.String)
     */
    public void setOrder(String order) {
        this.order = order;
    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#getOrder()
     */
    public String getOrder() {
        return this.order;
    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#setArguments(java.util.Vector)
     */
    public void setArguments(Vector arguments) {
        this.arguments = arguments;

    }


    /* (non-Javadoc)
     * @see com.cougaarsoftware.config.vo.Component#getArguments()
     */
    public Vector getArguments() {
        return arguments;
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public UID getUID() {
        return uid;
    }


    /**
     * DOCUMENT ME!
     *
     * @param uid DOCUMENT ME!
     */
    public void setUID(UID uid) {
        this.uid = uid;
    }
	/**
	 * @return Returns the node.
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * @param node The node to set.
	 */
	public void setNodeName(String node) {
		this.nodeName = node;
	}
}
