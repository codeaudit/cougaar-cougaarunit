package org.cougaar.plugin.test;

import org.cougaar.core.service.DomainService;
import org.cougaar.planning.ldm.plan.ClusterObjectFactory;
import org.cougaar.core.domain.Factory;
import java.util.List;

/**
 * <p>Title: DomainServiceProxy</p>
 * <p>Description: Proxies the DomainService</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class DomainServiceProxy implements DomainService {
  private DomainService actualDomainService = null;

  /**
   * Constructor
   * @param actualDomainService the DomainService to proxy
   */
  public DomainServiceProxy(DomainService actualDomainService) {
    this.actualDomainService = actualDomainService;
  }

  /**
   * Proxied method
   * @return
   */
  public Factory getFactory(String s) {
    return actualDomainService.getFactory(s);
  }

  /**
   * Proxied method
   * @return
   */
  public Factory getFactory(Class clazz) {
    return actualDomainService.getFactory(clazz);
  }


  /**
   * Proxied method
   * @return
   */
  public List getFactories() {
    return actualDomainService.getFactories();
  }

}