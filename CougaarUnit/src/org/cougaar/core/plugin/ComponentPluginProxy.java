package org.cougaar.core.plugin;

import org.cougaar.util.*;
import org.cougaar.core.plugin.*;
import org.cougaar.core.service.BlackboardService;
import org.cougaar.core.agent.ClusterIdentifier;
import org.cougaar.core.component.*;
import org.cougaar.core.service.AlarmService;
import java.util.Collection;

public class ComponentPluginProxy extends ComponentPlugin {
    private ComponentPlugin actualPlugin;

    public ComponentPluginProxy(ComponentPlugin actualPlugin) {
        this.actualPlugin = actualPlugin;
    }

    public void unload() {
        actualPlugin.unload();
    }

    public String toString() {
        return actualPlugin.toString();
    }

    public void load() {
        actualPlugin.load();
    }

    public void setParameter(Object param) {
        actualPlugin.setParameter(param);
    }

    public Object getParameter() {
        return actualPlugin.getParameter();
    }

    public boolean triggerEvent(Object event) {
        return actualPlugin.triggerEvent(event);
    }

    public synchronized String getBlackboardClientName() {
        return actualPlugin.getBlackboardClientName();
    }

    public void start() {
        actualPlugin.start();
    }

    public void halt() {
        actualPlugin.halt();
    }

    public void resume() {
        actualPlugin.resume();
    }

    public void setBindingSite(BindingSite bs) {
        actualPlugin.setBindingSite(bs);
    }

    public long currentTimeMillis() {
        return actualPlugin.currentTimeMillis();
    }

    public void suspend() {
        actualPlugin.suspend();
    }

    public void registerClass() {
        actualPlugin.registerClass();
    }

    public void initialize() {
        actualPlugin.initialize();
    }

    public void stop() {
        actualPlugin.stop();
    }

    public Collection getParameters() {
        return actualPlugin.getParameters();
    }

    protected void execute() {
        actualPlugin.execute();
    }

    protected void cycle() {
        actualPlugin.cycle();
    }

    protected BlackboardService getBlackboardService() {
        return actualPlugin.getBlackboardService();
    }

    protected AlarmService getAlarmService() {
        return actualPlugin.getAlarmService();
    }

    protected void precycle() {
        actualPlugin.precycle();
    }

    protected ClusterIdentifier getAgentIdentifier() {
        return actualPlugin.getAgentIdentifier();
    }

    protected void setupSubscriptions() {
        actualPlugin.setupSubscriptions();
    }

    protected ConfigFinder getConfigFinder() {
        return actualPlugin.getConfigFinder();
    }

    protected boolean shouldExecute() {
        return actualPlugin.shouldExecute();
    }

    protected ServiceBroker getServiceBroker() {
        return actualPlugin.getServiceBroker();
    }

    protected ClusterIdentifier getClusterIdentifier() {
        return actualPlugin.getClusterIdentifier();
    }

    protected PluginBindingSite getBindingSite() {
        return actualPlugin.getBindingSite();
    }
}
