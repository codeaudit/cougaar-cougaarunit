package org.cougaar.cougaarunit;
import java.util.ArrayList;
import java.util.Collection;

import org.cougaar.core.service.DomainService;
import org.cougaar.cougaarunit.vo.ComponentImpl;
import org.cougaar.planning.ldm.PlanningFactory;
import org.cougaar.planning.ldm.plan.NewTask;
import org.cougaar.planning.ldm.plan.Task;
import org.cougaar.planning.ldm.plan.Verb;
import org.cougaar.planning.ldm.plan.Workflow;

/**
 * @author mabrams
 */
public class SamplePluginTest extends PluginTestCase {

	private DomainService domainService;

	/* (non-Javadoc)
	 * @see org.cougaar.cougaarunit.PluginTestCase#validateSubscriptions()
	 */
	public void validateSubscriptions() {
		Task checkInTask = makeTask("VERB",  null, null);
		this.subscriptionAssert(checkInTask, true);
		
	}

	/* (non-Javadoc)
	 * @see org.cougaar.cougaarunit.PluginTestCase#validateExecution()
	 */
	public void validateExecution() {
		if(logging.isInfoEnabled()){
			logging.info("Validating Execution");
		}
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cougaar.cougaarunit.PluginTestCase#getPluginClass()
	 */
	public String getPluginClass() {
		return "org.cougaar.cougaarunit.SamplePlugin";
	}
	public Collection getDomains(){
		ArrayList list = new ArrayList();
		ComponentImpl c = new ComponentImpl();
		c.setName("glm");
		c.setClassName("org.cougaar.glm.ldm.GLMDomain");
		list.add(c);	
		return list;
	}
	
	private NewTask makeTask(String verb, Task parent_task, Workflow wf) {
		   PlanningFactory factory = (PlanningFactory) getDomainService().getFactory(
				   "planning");
		   NewTask new_task = factory.newTask();

		   // Set the verb as given
		   new_task.setVerb(Verb.getVerb(verb));

		   // Set the reality plan for the task
		   new_task.setPlan(factory.getRealityPlan());

		   //set the parent task
		   if (parent_task != null) {
			   new_task.setParentTask(parent_task);
		   }

		   if (wf != null) {
			   new_task.setWorkflow(wf);
		   }

		   return new_task;
	   }

	/**
	 * @return Returns the domainService.
	 */
	public DomainService getDomainService() {
		return domainService;
	}
	/**
	 * @param domainService The domainService to set.
	 */
	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}
}
