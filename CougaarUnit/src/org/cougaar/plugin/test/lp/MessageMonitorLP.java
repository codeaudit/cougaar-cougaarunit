package org.cougaar.plugin.test.lp;

import org.cougaar.core.domain.MessageLogicProvider;
import java.util.Collection;
import org.cougaar.plugin.test.capture.CapturedPublishAction;
import org.cougaar.plugin.test.ClassStringRendererRegistry;
import org.cougaar.plugin.test.capture.BlackboardServiceCapturingProxy;
import org.cougaar.core.blackboard.Directive;

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
    CapturedPublishAction cpe = new CapturedPublishAction(CapturedPublishAction.ACTION_INTERAGENT_TRANSFER, ClassStringRendererRegistry.render(parm1), parm1.getSource().toString());
    BlackboardServiceCapturingProxy.addToObjectStream(cpe);
  }

  public void init() {
  }

}