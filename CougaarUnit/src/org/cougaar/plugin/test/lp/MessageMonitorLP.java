package org.cougaar.plugin.test.lp;

import org.cougaar.core.domain.MessageLogicProvider;
import org.cougaar.planning.ldm.plan.Directive;
import java.util.Collection;
import org.cougaar.plugin.test.capture.CapturedPublishAction;
import org.cougaar.plugin.test.ClassStringRendererRegistry;
import org.cougaar.plugin.test.capture.BlackboardServiceCapturingProxy;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class MessageMonitorLP implements MessageLogicProvider {

  public MessageMonitorLP() {
  }

  public void execute(Directive parm1, Collection parm2) {
    CapturedPublishAction cpe = new CapturedPublishAction(CapturedPublishAction.ACTION_INTERAGENT_TRANSFER, ClassStringRendererRegistry.render(parm1), parm1.getSource().cleanToString());
    BlackboardServiceCapturingProxy.addToObjectStream(cpe);
  }

  public void init() {
  }

}