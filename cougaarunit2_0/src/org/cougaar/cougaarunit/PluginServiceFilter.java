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
package org.cougaar.cougaarunit;

import org.cougaar.core.blackboard.BlackboardClient;
import org.cougaar.core.component.BinderFactory;
import org.cougaar.core.component.ContainerAPI;
import org.cougaar.core.component.ServiceBroker;
import org.cougaar.core.component.ServiceFilter;
import org.cougaar.core.component.ServiceFilterBinder;
import org.cougaar.core.service.BlackboardService;

/** A plugin's view of its parent component (Container).
 * Add a line like the following to a cluster.ini file:
 * <pre>
 * Node.AgentManager.Agent.PluginManager.Binder = org.cougaar.core.examples.PluginServiceFilter
 * </pre>

 **/
public class PluginServiceFilter
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

    // this method specifies a binder proxy to use, so as to avoid exposing the binder
    // itself to the lower level objects.
    protected ContainerAPI createContainerProxy() { return new ServiceFilterContainerProxy(); }

    // this method installs the "filtering" service broker
    protected ServiceBroker createFilteringServiceBroker(ServiceBroker sb) {
      return new PluginFilteringServiceBroker(sb);
    }

    // this class catches requests for blackboard services, and
    // installs its own service proxy.
    protected class PluginFilteringServiceBroker  extends FilteringServiceBroker
    {
      public PluginFilteringServiceBroker(ServiceBroker sb) {
        super(sb);
      }

      // here's where we catch the service request for Blackboard and proxy the
      // returned service.  See FilteringServiceBroker for more options.
      protected Object getServiceProxy(Object service, Class serviceClass, Object client) {
        if (service instanceof BlackboardService) {
          //return new BlackboardServiceProxy((BlackboardService) service, client);
          return new BlackboardServiceProxy((BlackboardService)service);
        }
        return null;
      }

      protected Object getClientProxy(Object client, Class serviceClass) {
        if ((client instanceof BlackboardClient) && (serviceClass.equals(BlackboardService.class))) {
          return (BlackboardClient)client;
        }
        return null;
      }
    }
  }
}