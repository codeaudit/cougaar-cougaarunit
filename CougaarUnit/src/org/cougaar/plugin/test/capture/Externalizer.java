package org.cougaar.plugin.test.capture;

import java.io.Serializable;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public interface Externalizer {
  public String getUniqueId();
  public Serializable externalize(Object obj);
  public Object internalize(Object obj);
}