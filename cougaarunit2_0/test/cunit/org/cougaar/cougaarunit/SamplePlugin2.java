package org.cougaar.cougaarunit;
import org.cougaar.core.blackboard.IncrementalSubscription;
import org.cougaar.core.plugin.ComponentPlugin;
import org.cougaar.planning.ldm.plan.Task;
import org.cougaar.planning.ldm.predicate.TaskPredicate;

/**
 * @author mabrams
 */
public class SamplePlugin2 extends ComponentPlugin {
	
	private IncrementalSubscription taskSubscription;
	private static TaskPredicate taskPredicate = new TaskPredicate() {
		public boolean execute(Task t) {
			return true;
		}
	};
	
	public void load() {
		super.load();
		System.out.println("loading sample plugin2");
	}

	/* (non-Javadoc)
	 * @see org.cougaar.core.plugin.ComponentPlugin#setupSubscriptions()
	 */
	protected void setupSubscriptions() {
		taskSubscription= (IncrementalSubscription) getBlackboardService()
				.subscribe(taskPredicate);
		
	}

	/* (non-Javadoc)
	 * @see org.cougaar.core.plugin.ComponentPlugin#execute()
	 */
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

}
