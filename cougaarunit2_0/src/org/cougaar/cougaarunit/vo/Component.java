package org.cougaar.cougaarunit.vo;

import java.util.Vector;

/**
 * @author mabrams
 */
public interface Component {	
	public void setName(String name);
	public String getName();
	
	public void setClassName(String className);
	public String getClassName();
	
	public void setPriority(String priority);
	public String getPriority();
	
	public void setInsertionpoint(String insertionPoint);
	public String getInsertionpoint();
	
	public void setOrder(String order);
	public String getOrder();
	
	public void setArguments(Vector arguments);
	public Vector getArguments();
}
