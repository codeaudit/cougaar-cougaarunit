package org.cougaar.plugin.test.sample;

import org.cougaar.plugin.test.*;
import org.cougaar.util.*;
import org.cougaar.planning.ldm.plan.Task;
import org.cougaar.core.service.DomainService;
import org.cougaar.planning.ldm.plan.NewTask;
import org.cougaar.planning.ldm.plan.Verb;
import org.cougaar.planning.ldm.plan.Preference;
import org.cougaar.planning.ldm.plan.ScoringFunction;
import org.cougaar.planning.ldm.plan.AspectType;
import org.cougaar.planning.ldm.plan.AspectValue;
import org.cougaar.planning.ldm.plan.Allocation;
import org.cougaar.planning.ldm.asset.Asset;
import java.util.Vector;
import org.cougaar.planning.ldm.plan.NewPrepositionalPhrase;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TestSimpleSinkAllocatorPlugin extends PluginTestCase {
  // The domainService acts as a provider of domain factory services
  private DomainService domainService = null;

  public void setDomainService(DomainService aDomainService) {
    domainService = aDomainService;
  }

  public TestSimpleSinkAllocatorPlugin() {
  }

  public String getPluginClass() {
    return "org.cougaar.core.examples.SimpleSinkAllocatorPlugin";
  }

  public void validateSubscriptions() {
    //assert a task onto the blackboard...this should pass
    NewTask new_task = domainService.getFactory().newTask();
    this.subscriptionAssert(new_task, true);
    //assert something other than a task just to insure that it does fail
    this.subscriptionAssert(new Object(), false);
  }


  public void validateExecution() {
    //test an added task
    //create the test task object to publish to the blackboard
    NewTask newTask = domainService.getFactory().newTask();
    newTask.setVerb(new Verb("Random Verb"));
    newTask.setPlan(domainService.getFactory().getRealityPlan());

    ScoringFunction scorefcn = ScoringFunction.createStrictlyAtValue(new AspectValue(AspectType.START_TIME, 1));
    ScoringFunction scorefcn2 = ScoringFunction.createStrictlyAtValue(new AspectValue(AspectType.END_TIME, 2));
    Preference pref = domainService.getFactory().newPreference(AspectType.START_TIME, scorefcn);
    Preference pref2 = domainService.getFactory().newPreference(AspectType.END_TIME, scorefcn2);
    Vector preferences = new Vector();
    preferences.add(pref);
    preferences.add(pref2);
    blackboard.openTransaction();
    newTask.setPreferences(preferences.elements());
    blackboard.closeTransaction();
    //create the PublishAction object to represent the expected result
    //in this case, we just want to see if we get an Allocation object back
    PublishAction pa = new PublishAction(PublishAction.ADD, Allocation.class);
    //create the BlackboardDeltaState object
    BlackboardDeltaState bds  = new BlackboardDeltaState();
    bds.add(pa);
    this.assertPublishAdd(newTask, bds, 5000, true, false);

    //test a changed task
  }

}