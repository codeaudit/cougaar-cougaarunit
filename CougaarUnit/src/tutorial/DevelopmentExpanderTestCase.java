package tutorial;

import org.cougaar.plugin.test.*;
import org.cougaar.util.*;
import org.cougaar.planning.ldm.plan.NewTask;
import org.cougaar.planning.ldm.plan.Workflow;
import org.cougaar.planning.ldm.plan.Verb;
import org.cougaar.core.service.DomainService;
import org.cougaar.planning.ldm.plan.Task;
import org.cougaar.planning.ldm.plan.PlanElement;
import org.cougaar.planning.ldm.plan.AllocationResult;
import org.cougaar.planning.ldm.plan.Expansion;
import org.cougaar.planning.ldm.PlanningFactory;


/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class DevelopmentExpanderTestCase extends PluginTestCase {

  private DomainService domainService;

  public DevelopmentExpanderTestCase() {
  }

  /**
   * Used by the binding utility through reflection to set my DomainService
   */
  public void setDomainService(DomainService aDomainService) {
    domainService = aDomainService;
  }

  public String getPluginClass() {
    return "tutorial.DevelopmentExpanderPlugin";
  }

  public void validateSubscriptions() {
    //test subscription for code tasks
    Workflow wf = ((PlanningFactory)domainService.getFactory("planning")).newWorkflow();
    Task codeTask = makeTask("CODE", null, wf);
    subscriptionAssert(codeTask, true);

    //test the DESIGN plan element subscription
    AllocationResult estAR = null;
    Task designTask = makeTask("DESIGN", codeTask, wf);
    Expansion deisgnExp = ((PlanningFactory)domainService.getFactory("planning")).createExpansion(designTask.getPlan(), designTask, wf, estAR);
    subscriptionAssert(deisgnExp, true);

    //test the DEVELOP expansion
    Task developTask = makeTask("DEVELOP", designTask, wf);
    Expansion developExp = ((PlanningFactory)domainService.getFactory("planning")).createExpansion(developTask.getPlan(), designTask, wf, estAR);
    subscriptionAssert(developExp, true);

    //test the DEVELOP expansion
    Task testTask = makeTask("TEST", developTask, wf);
    Expansion testExp = ((PlanningFactory)domainService.getFactory("planning")).createExpansion(testTask.getPlan(), designTask, wf, estAR);
    subscriptionAssert(testExp, true);
  }

  public void validateExecution() {
    Workflow wf = ((PlanningFactory)domainService.getFactory("planning")).newWorkflow();
    Task codeTask = makeTask("CODE", null, wf);  //this is the task that we're going to assert onto the blackboard

    Task designTask = makeTask("DESIGN", codeTask, wf);  //this is one of the expected resulting tasks
    AllocationResult estAR = null;
    Expansion designExp = ((PlanningFactory)domainService.getFactory("planning")).createExpansion(designTask.getPlan(), codeTask, wf, estAR);  //this is the other expected result

    BlackboardDeltaState bds = new BlackboardDeltaState();
    bds.add(new PublishAction(PublishAction.ADD, designTask, ObjectComparators.TASK_VERB_COMPARATOR));
    bds.add(new PublishAction(PublishAction.ADD, designExp, ObjectComparators.EXPANSION_COMPARATOR));

    assertPublishAdd(codeTask, bds, 5000, true, true);
  }

  private NewTask makeTask(String verb, Task parent_task, Workflow wf) {
    PlanningFactory factory = (PlanningFactory)domainService.getFactory("planning");
    NewTask new_task = factory.newTask();

    // Set the verb as given
    new_task.setVerb(new Verb(verb));

    // Set the reality plan for the task
    new_task.setPlan(factory.getRealityPlan());

    //set the parent task
    if (parent_task != null)
      new_task.setParentTask(parent_task);

    if (wf != null) {
      new_task.setWorkflow(wf);
    }

    return new_task;
  }
}