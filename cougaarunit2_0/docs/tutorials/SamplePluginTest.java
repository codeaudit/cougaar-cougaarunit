import org.cougaar.core.service.DomainService;
import org.cougaar.cougaarunit.BlackboardDeltaState;
import org.cougaar.cougaarunit.PluginTestCase;
import org.cougaar.cougaarunit.PublishAction;
import org.cougaar.planning.ldm.PlanningFactory;
import org.cougaar.planning.ldm.plan.NewTask;
import org.cougaar.planning.ldm.plan.Task;
import org.cougaar.planning.ldm.plan.Verb;
import org.cougaar.planning.ldm.plan.Workflow;


/**
 * DOCUMENT ME!
 *
 * @author mabrams
 */
public class SamplePluginTest extends PluginTestCase {
	
	private DomainService domainService;
	
    public void validateSubscriptions() {
        Task checkInTask = makeTask("VERB", null, null);       
        this.subscriptionAssert(checkInTask, true);
    }


    public void validateExecution() {
    	Task t1 = makeTask("VERB", null, null);
		
		BlackboardDeltaState bds = new BlackboardDeltaState();
		bds.add(new PublishAction(PublishAction.REMOVE, t1));
		assertPublishAdd(t1, bds, 1000, true, false);
    }


    /* (non-Javadoc)
     * @see org.cougaar.cougaarunit.PluginTestCase#getPluginClass()
     */
    public String getPluginClass() {
        // return the name of the class we are testing
        return "SamplePlugin";
    }


    private NewTask makeTask(String verb, Task parent_task, Workflow wf) {
        PlanningFactory factory = (PlanningFactory) getDomainService()
                                                        .getFactory("planning");
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
