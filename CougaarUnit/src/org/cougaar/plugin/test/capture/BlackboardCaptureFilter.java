/*
* <copyright>
*  Copyright 2000-2001 BBNT Solutions, LLC
*  under sponsorship of the Defense Advanced Research Projects Agency (DARPA).
*
*  This program is free software; you can redistribute it and/or modify
*  it under the terms of the Cougaar Open Source License as published by
*  DARPA on the Cougaar Open Source Website (www.cougaar.org).
*
*  THE COUGAAR SOFTWARE AND ANY DERIVATIVE SUPPLIED BY LICENSOR IS
*  PROVIDED 'AS IS' WITHOUT WARRANTIES OF ANY KIND, WHETHER EXPRESS OR
*  IMPLIED, INCLUDING (BUT NOT LIMITED TO) ALL IMPLIED WARRANTIES OF
*  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND WITHOUT
*  ANY WARRANTIES AS TO NON-INFRINGEMENT.  IN NO EVENT SHALL COPYRIGHT
*  HOLDER BE LIABLE FOR ANY DIRECT, SPECIAL, INDIRECT OR CONSEQUENTIAL
*  DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE OF DATA OR PROFITS,
*  TORTIOUS CONDUCT, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
*  PERFORMANCE OF THE COUGAAR SOFTWARE.
* </copyright>
 */
package org.cougaar.plugin.test.capture;

import java.util.*;
import org.cougaar.util.*;
import org.cougaar.core.component.*;
import org.cougaar.core.agent.*;
import org.cougaar.core.domain.*;
import org.cougaar.core.blackboard.*;
import org.cougaar.core.mts.Message;
import org.cougaar.core.mts.MessageAddress;
import org.cougaar.core.persist.*;
import org.cougaar.core.blackboard.*;
import org.cougaar.core.plugin.PluginManagerForBinder;
import org.cougaar.core.service.BlackboardService;
import org.cougaar.core.plugin.ComponentPlugin;
import org.cougaar.core.service.DomainService;

/** A plugin's view of its parent component (Container).
 * Add a line like the following to a cluster.ini file:
 * <pre>
 * Node.AgentManager.Agent.PluginManager.Binder = org.cougaar.core.examples.PluginServiceFilter
 * </pre>

 **/
public class BlackboardCaptureFilter
    extends ServiceFilter
{
  //  This method specifies the Binder to use (defined later)
  protected Class getBinderClass(Object child) {
    return PluginServiceFilterBinder.class;
  }


  // This is a "Wrapper" binder which installs a service filter for plugins
  public static class PluginServiceFilterBinder
      extends ServiceFilterBinder
  {
    public PluginServiceFilterBinder(BinderFactory bf, Object child) {
      super(bf,child);
    }

    protected final PluginManagerForBinder getPluginManager() { return (PluginManagerForBinder)getContainer(); }

    // this method specifies a binder proxy to use, so as to avoid exposing the binder
    // itself to the lower level objects.
    protected ContainerAPI createContainerProxy() { return new PluginFilteringBinderProxy(); }

    // this method installs the "filtering" service broker
    protected ServiceBroker createFilteringServiceBroker(ServiceBroker sb) {
      return new PluginFilteringServiceBroker(sb);
    }

    // this class implements a simple proxy for a plugin wrapper binder
    protected class PluginFilteringBinderProxy
        extends ServiceFilterContainerProxy
        implements PluginManagerForBinder
    {
      public MessageAddress getAgentIdentifier() { return getPluginManager().getAgentIdentifier(); }
      public ConfigFinder getConfigFinder() { return getPluginManager().getConfigFinder(); }
    }


    // this class catches requests for blackboard services, and
    // installs its own service proxy.
    protected class PluginFilteringServiceBroker
        extends FilteringServiceBroker
    {
      public PluginFilteringServiceBroker(ServiceBroker sb) {
        super(sb);
      }

      // here's where we catch the service request for Blackboard and proxy the
      // returned service.  See FilteringServiceBroker for more options.
      protected Object getServiceProxy(Object service, Class serviceClass, Object client) {
        if (service instanceof BlackboardService) {
          return new BlackboardServiceCapturingProxy((BlackboardService)service, client);
        }
        //else if (service instanceof DomainService) {
        //  return new DomainServiceProxy((DomainService)service);
        //}
        return null;
      }
    }
  }
}