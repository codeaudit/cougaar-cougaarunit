package org.cougaar.plugin.test.capture;

import java.io.Serializable;

/**
 * <p>Title: ExternalizedContainer</p>
 * <p>Description: Wrapper for externalized objects</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class ExternalizedContainer implements Serializable {
  Object sourceObject;
  String externalizerId;

  public ExternalizedContainer(Object obj, String id) {
    this.sourceObject = obj;
    this.externalizerId = id;
  }

}