package org.cougaar.plugin.test;

import org.cougaar.core.service.DomainService;
import org.cougaar.planning.ldm.plan.ClusterObjectFactory;
import org.cougaar.core.domain.RootFactory;
import org.cougaar.core.domain.Factory;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DomainServiceProxy implements DomainService {
  private DomainService actualDomainService = null;

  public DomainServiceProxy(DomainService actualDomainService) {
    this.actualDomainService = actualDomainService;
  }

  public ClusterObjectFactory getClusterObjectFactory() {
    return actualDomainService.getClusterObjectFactory();
  }

  public RootFactory getFactory() {
    return actualDomainService.getFactory();
  }

  public RootFactory getLdmFactory() {
    return actualDomainService.getLdmFactory();
  }

  public Factory getFactory(String parm1) {
    return actualDomainService.getFactory(parm1);
  }

  public Factory getFactory(Class parm1) {
    return actualDomainService.getFactory(parm1);
  }

  public List getFactories() {
    return actualDomainService.getFactories();
  }

}