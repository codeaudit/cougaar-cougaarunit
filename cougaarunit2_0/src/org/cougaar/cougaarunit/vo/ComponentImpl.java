/*
 * <copyright> Copyright 2000-2003 Cougaar Software, Inc. All Rights Reserved
 * </copyright>
 */

package org.cougaar.cougaarunit.vo;

import java.io.Serializable;

import java.util.Vector;

import org.cougaar.core.util.UID;

/**
 * DOCUMENT ME!
 * 
 * @author mabrams
 */
public class ComponentImpl implements Serializable, Component {
	private Vector arguments = null;
	private String name = null;
	private String className = null;
	private String priority;
	private String insertionPoint = null;
	private UID uid;

	/**
	 * Creates a new ComponentImpl object.
	 */
	public ComponentImpl() {
		arguments = new Vector();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param _name
	 */
	public ComponentImpl(String _name) {
		this();
		setName(_name);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return Returns the arguments.
	 */
	public Vector getArguments() {
		return arguments;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param arguments
	 *            The arguments to set.
	 */
	public void setArguments(Vector arguments) {
		this.arguments = arguments;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return Returns the className.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param className
	 *            The className to set.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return Returns the insertionPoint.
	 */
	public String getInsertionpoint() {
		return insertionPoint;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param insertionPoint
	 *            The insertionPoint to set.
	 */
	public void setInsertionpoint(String insertionPoint) {
		this.insertionPoint = insertionPoint;
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
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return Returns the priority.
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param arg
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean addArgument(String arg) {
		return arguments.add(arg);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param arg
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean removeArgument(String arg) {
		return arguments.remove(arg);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param o
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object o) {
		ComponentImpl c2 = (ComponentImpl) o;
		if (!c2.getName().equals(this.getName())) {
			return false;
		}

		if (((c2.getClassName() != null) && (this.getClassName() != null))
				&& !c2.getClassName().equals(this.getClassName())) {
			return false;
		} else if ((c2.getClassName() == null) && (this.getClassName() != null)) {
			return false;
		} else if ((this.getClassName() == null) && (c2.getClassName() != null)) {
			return false;
		}

		if ((c2.getInsertionpoint() != null && this.getInsertionpoint() != null)
				&& !c2.insertionPoint.equals(this.insertionPoint)) {
			return false;
		} else if ((c2.getInsertionpoint() == null)
				&& (this.getInsertionpoint() != null)) {
			return false;
		} else if ((this.getInsertionpoint() == null)
				&& (c2.getInsertionpoint() != null)) {
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cougaarsoftware.config.vo.SocietyObject#getChildComponents()
	 */
	public Vector getChildComponents() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cougaarsoftware.config.vo.Component#setOrder(java.lang.String)
	 */
	public void setOrder(String order) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cougaarsoftware.config.vo.Component#getOrder()
	 */
	public String getOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cougaarsoftware.config.vo.Component#setPriority(int)
	 */
	public void setPriority(String priority) {
		this.priority = priority;
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
}