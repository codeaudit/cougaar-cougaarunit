package org.cougaar.plugin.test;

import org.cougaar.core.service.DomainService;
import org.cougaar.planning.ldm.plan.ClusterObjectFactory;
import org.cougaar.core.domain.RootFactory;
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
  public ClusterObjectFactory getClusterObjectFactory() {
    return actualDomainService.getClusterObjectFactory();
  }

  /**
   * Proxied method
   * @return
   */
  public RootFactory getFactory() {
    return actualDomainService.getFactory();
  }

  /**
   * Proxied method
   * @return
   */
  public RootFactory getLdmFactory() {
    return actualDomainService.getLdmFactory();
  }

  /**
   * Proxied method
   * @param parm1
   * @return
   */
  public Factory getFactory(String parm1) {
    return actualDomainService.getFactory(parm1);
  }

  /**
   * Proxied method
   * @param parm1
   * @return
   */
  public Factory getFactory(Class parm1) {
    return actualDomainService.getFactory(parm1);
  }

  /**
   * Proxied method
   * @return
   */
  public List getFactories() {
    return actualDomainService.getFactories();
  }

}