package org.cougaar.cougaarunit;

import java.util.Comparator;

/**
 * <p>
 * Title: PublishAction
 * </p>
 * <p>
 * Description: This class allows the tester to contsruct Publish Actions which
 * can then be tested for. A publish action consists of an action code and a
 * reference to the object upom whcih that action taken.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: InfoEther, LLC
 * </p>
 * 
 * @author David Craine
 * @version 1.0
 */

public class PublishAction {

	private int actionId;
	private Object obj = null;
	public static final int ADD = 1;
	public static final int REMOVE = 2;
	public static final int CHANGE = 3;

	/**
	 * Standard constructor
	 */
	public PublishAction() {
	}

	/**
	 * Constructor
	 * 
	 * @param actionId
	 * @param obj
	 */
	public PublishAction(int actionId, Object obj) {
		this.actionId = actionId;
		this.obj = obj;

	}

	/**
	 * Constructor
	 * 
	 * @param actionId
	 */
	public PublishAction(int actionId) {
		this(actionId, null);
	}

	/**
	 * Get the string representation of an action
	 * 
	 * @param actionID
	 * @return
	 */
	private String getActionString(int actionID) {
		switch (actionID) {
			case ADD :
				return "ADD";
			case REMOVE :
				return "REMOVE";
			case CHANGE :
				return "CHANGE";
		}
		return "";
	}

	/**
	 * Returns the class name of the object
	 * 
	 * @return
	 */
	private String getResultString() {
		if (obj instanceof Class) {
			return ((Class) obj).getName();
		} else
			// return ClassStringRendererRegistry.render(obj);
			return null;
	}

	/**
	 * Compares another PublishAction object
	 * 
	 * @param pa
	 * @return
	 */
	public boolean equals(PublishAction pa) {
		if (pa == null)
			return false;
		try {
			//compare the action ids
			if (actionId != pa.actionId)
				return false;
			//is there a specific comparator registered for this PublishAction
			if (obj == null && pa.getObj() == null) {
				return true;
			} else if (obj == null || pa.getObj() == null) {
				return false;
			}

			Comparator comparator = this.getComparator(obj.getClass());
			if (comparator == null) {
				return obj.equals(pa.obj);
			} else {
				if (comparator.compare(obj, pa.obj) == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private Comparator getComparator(Class _class) {
		String className = _class.getName();
		String comparatorName =
			"org.cougaar.cougaarunit.comparators."
				+ className.substring(className.lastIndexOf(".")+1, className.length())
				+ "Comparator";
		try {
			Class comparatorClass = Class.forName(comparatorName);
			Comparator comparator = (Comparator) comparatorClass.newInstance();
			return comparator;
		} catch (ClassNotFoundException cnfe) {
			if (_class.getSuperclass() != null) {
				return getComparator(_class.getSuperclass());
			} else {
				return null;
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * @return Returns the actionId.
	 */
	public int getActionId() {
		return actionId;
	}

	/**
	 * @param actionId
	 *            The actionId to set.
	 */
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	/**
	 * @return Returns the obj.
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj
	 *            The obj to set.
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

}