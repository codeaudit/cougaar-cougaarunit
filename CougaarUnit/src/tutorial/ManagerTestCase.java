package tutorial;

import org.cougaar.util.*;
import org.cougaar.planning.ldm.PlanningFactory;
import org.cougaar.core.service.DomainService;
import org.cougaar.planning.ldm.asset.Asset;
import org.cougaar.planning.ldm.asset.NewItemIdentificationPG;
import org.cougaar.planning.ldm.plan.NewTask;
import org.cougaar.planning.ldm.plan.Task;
import org.cougaar.planning.ldm.plan.Verb;
import org.cougaar.planning.ldm.plan.Workflow;
import java.util.Vector;
import org.cougaar.planning.ldm.plan.NewPrepositionalPhrase;
import org.cougaar.planning.ldm.plan.ScoringFunction;
import org.cougaar.planning.ldm.plan.AspectType;
import org.cougaar.planning.ldm.plan.AspectValue;
import org.cougaar.planning.ldm.plan.Preference;
import org.cougaar.plugin.test.PluginTestCase;
import org.cougaar.plugin.test.BlackboardDeltaState;
import org.cougaar.plugin.test.PublishAction;
import org.cougaar.plugin.test.ObjectComparators;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class ManagerTestCase extends PluginTestCase {

  public ManagerTestCase() {
  }

  // The domainService acts as a provider of domain factory services
  private DomainService domainService = null;

  /**
   * Used by the binding utility through reflection to set my DomainService
   */
  public void setDomainService(DomainService aDomainService) {
    domainService = aDomainService;
  }

  /**
   * Used by the binding utility through reflection to get my DomainService
   */
  public DomainService getDomainService() {
    return domainService;
  }
  public void validateExecution() {
    PlanningFactory factory = (PlanningFactory)getDomainService().getFactory("planning");
    BlackboardDeltaState bds = new BlackboardDeltaState();

    Asset what_to_code = factory.createPrototype("AbstractAsset", "The Next Killer App");
    NewItemIdentificationPG iipg = (NewItemIdentificationPG)factory.createPropertyGroup("ItemIdentificationPG");
    iipg.setItemIdentification("e-somthing");
    what_to_code.setItemIdentificationPG(iipg);
    bds.add(new PublishAction(PublishAction.ADD, what_to_code, ObjectComparators.ASSET_COMPARATOR));
    bds.add(new PublishAction(PublishAction.ADD, makeTask(what_to_code),ObjectComparators.TASK_VERB_COMPARATOR));

    Asset what_else_to_code = factory.createInstance(what_to_code);
    iipg = (NewItemIdentificationPG)factory.createPropertyGroup("ItemIdentificationPG");
    iipg.setItemIdentification("something java");
    what_else_to_code.setItemIdentificationPG(iipg);
    bds.add(new PublishAction(PublishAction.ADD, what_else_to_code, ObjectComparators.ASSET_COMPARATOR));
    bds.add(new PublishAction(PublishAction.ADD, makeTask(what_else_to_code), ObjectComparators.TASK_VERB_COMPARATOR));

    // Create a task to code something java
    what_else_to_code = factory.createInstance(what_to_code);
    iipg = (NewItemIdentificationPG)factory.createPropertyGroup("ItemIdentificationPG");
    iipg.setItemIdentification("something big java");
    what_else_to_code.setItemIdentificationPG(iipg);
    bds.add(new PublishAction(PublishAction.ADD, what_else_to_code,ObjectComparators.ASSET_COMPARATOR));
    bds.add(new PublishAction(PublishAction.ADD, makeTask(what_else_to_code), ObjectComparators.TASK_VERB_COMPARATOR));

    what_else_to_code = factory.createInstance(what_to_code);
    iipg = (NewItemIdentificationPG)factory.createPropertyGroup("ItemIdentificationPG");
    iipg.setItemIdentification("distributed intelligent java agent");
    what_else_to_code.setItemIdentificationPG(iipg);
    bds.add(new PublishAction(PublishAction.ADD, what_else_to_code, ObjectComparators.ASSET_COMPARATOR));
    bds.add(new PublishAction(PublishAction.ADD, makeTask(what_else_to_code), ObjectComparators.TASK_VERB_COMPARATOR));

    this.assertInitialState(bds, 5000, true, true);
  }

  public void validateSubscriptions() {
  }

  public String getPluginClass() {
    return "tutorial.ManagerPlugin";
  }

  protected Task makeTask(Asset what) {
    PlanningFactory factory = (PlanningFactory) getDomainService().getFactory("planning");

    NewTask new_task = factory.newTask();

    // Set the verb as given
    new_task.setVerb(Verb.getVerb("CODE"));

    // Set the reality plan for the task
    new_task.setPlan(factory.getRealityPlan());

    new_task.setDirectObject(what);

    return new_task;
  }
}