package org.cougaar.plugin.test;

/**
 *
 * <p>Title: Comparator</p>
 * <p>Description: interface used for comparing two objects</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
public interface Comparator {
  public boolean compare(Object obj1, Object obj2);
}