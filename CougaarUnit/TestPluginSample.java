class TestPlugin {
/**
* This class provides the test framework methods
*/
	/**
	* Used for testing subscriptions
	* @paran obj - The object to publish to the blackboard
	* @param expectedResult - Indicates whether this test is expected to pass or fail
		true - pass
		false - fail
	*/
	public void assetPubishAdd(Object obj, boolean expectedResult) {
	}
	
	/**
	* Publishes obj to the blackboard and blocks waitTime milliseconds until the bbs is achieved.
	* A timeout indicates an error.  
	* @param obj - The object to publish to the blackbaord
	* @param bbs -The blackboard state to be achieved.  If this value is null then no state will be tested.  Passing
	* null for this parameter can be useful if the test requires making several publications to the blackboard
	* before checking the blackboard state.
	* @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.  
	* 	-1 - wait indefinitely (not recommended)
		0 - do no waut
		>0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
	* @param expectedResult - Indicates whether this test is expected to pass or fail
		true - pass
		false - fail
	*/
	public void assetPubishAdd(Object obj, BlackBoardState bbs, long waitTime, boolean expectedResult) {
	}
	
	/**
	* Used for testing subscriptions
	* @paran obj - The object change to publish to the blackboard
	* @param expectedResult - Indicates whether this test is expected to pass or fail
		true - pass
		false - fail
	*/
	public void assetPubishChange(Object obj, boolean b) {
	}
	
	/**
	* Publishes an object change  to the blackboard and blocks waitTime milliseconds until the bbs is achieved.
	* A timeout indicates an error.  
	* @param obj - The changed object to publish to the blackbaord
	* @param bbs -The blackboard state to be achieved,  If this value is null then no state will be tested.  Passing
	* null for this parameter can be useful if the test requires making several publications to the blackboard
	* before checking the blackboard state.
	* @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.  
	* 	-1 - wait indefinitely (not recommended)
		0 - do no waut
		>0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
	* @param expectedResult - Indicates whether this test is expected to pass or fail
		true - pass
		false - fail
	*/
	public void assetPubishChange(Object obj, BlackBoardState bbs, long waitTime, boolean expectedResult) {
	}

	/**
	* Used for testing subscriptions
	* @paran obj - The object to remove from the blackboard
	* @param expectedResult - Indicates whether this test is expected to pass or fail
		true - pass
		false - fail
	*/
	public void assetPubishRemove(Object obj, boolean b) {
	}
	
	/**
	* Removes obj from the blackboard and blocks waitTime milliseconds until the bbs is achieved.
	* A timeout indicates an error.  
	* @param obj - The object to remove from the blackbaord
	* @param bbs -The blackboard state to be achieved.  If this value is null then no state will be tested.  Passing
	* null for this parameter can be useful if the test requires making several publications to the blackboard
	* before checking the blackboard state.
	* @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.  
	* 	-1 - wait indefinitely (not recommended)
		0 - do no waut
		>0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
	* @param expectedResult - Indicates whether this test is expected to pass or fail
		true - pass
		false - fail
	*/
	public void assetPubishRemove(Object obj, BlackBoardState bbs, long waitTime, boolean expectedResult) {
	}


	//<other methods>
	
	abstract public void validateSubscriptions();
		
	abstract public void validateExecution();
	
	public void run() {
		validateSubscriptions();
		validateExecution();
	}
}

/**
* This class must replace the normal cougaar ComponentPlugin in the classpath by ocurring before
* that plugin in the classpath.  Two approaches can be used in developing this class: 1) it can either
* be a stubbed environment that simulates the entire cougaar environment that the plugin requires 
* access to; or 2) it can act as a proxy into the real ComponentPlugin class. 
*/
class ComponentPlugin 
  extends org.cougaar.util.GenericStateModelAdapter
  implements PluginBase, BlackboardClient {
  
  public ComponentPlugin() { 
  }
  public void setParameter(Object param) {
  }
  public Object getParameter() {
  }
  public Collection getParameters() {        
  }
  public void setBindingSite(BindingSite bs) {
  }
  protected PluginBindingSite getBindingSite() {
  }
  protected ServiceBroker getServiceBroker() {
  }
  public final void setSchedulerService(SchedulerService ss) {
  }
  public final void setBlackboardService(BlackboardService bs) {
  }
  public final void setAlarmService(AlarmService s) {
  }
  protected BlackboardService getBlackboardService() {
  }
  protected AlarmService getAlarmService() {
  }
  protected final void requestCycle() {
  }
  public void initialize() {
  }
  public void load() {
  }
  public void start() {
  }
  public void suspend() {
  }
  public void resume() {
  }
  public void stop() {
  }
  public void halt() {
  }
  public void unload() {
  }
  protected void precycle() {
  }      
  protected void cycle() {
  }
  protected boolean shouldExecute() {
  }
  protected abstract void setupSubscriptions();
  protected abstract void execute();
  protected ConfigFinder getConfigFinder() {
  }
  protected ClusterIdentifier getClusterIdentifier() { 
  }
  protected ClusterIdentifier getAgentIdentifier() { 
  }
  protected final boolean wasAwakened() { return awakened; }
  public synchronized String getBlackboardClientName() {
  }
  public long currentTimeMillis() {
  }
  public boolean triggerEvent(Object event) {
  }
  public String toString() {
  }
}



/**
* This class allows the tester to contsruct Publish Actions which can then be tested
* for.  A publish action consists of an action code and a reference to the object upom whcih  that action 
* taken.  
*/
class PublishAction {
	public static final int ADD = 1;
	public static final int REMOVE = 2;		
	public static final int CHANGE = 3;
		
	public PublishAction(int actionId) {
	}
	
	public PublishAction(int actionId, Object objRef) {
	}
	
	public void add(Object objRef) {
	}
	
	
}

/**
* This class is used to represent a set of publsh actions that are expected to occur on the 
* system blackboard.  The test infratstrucre is able to monitor a plugins blackboard activity
* to validate it's actions against the BlackboardState object.
*/
class BlackboardState {
	public void add(PublishAction pa) {
	}
}

class TestFooPlugin  extends TestPlugin {
	public void validateSubscriptions() {
		/*publishes this object to the blackboard and validates that the 
		 *plugin's subscription model passes the object through
		*/
		assertPublishAdd(new FooSubscriptionObject1(), true);	

		/*publishes this object to the blackboard and validates that the 
		 *plugin's subscription model filters out the object
		*/
		assertPublishAdd(new FooSubscriptionObject2(), false);

		/**
		*Also consider publishRemove and publishChange
		*/
	}	

	public void validateExecution() {
		/*
		These three lines construct a blackboard state object that is used to validate the state if the 
		blackboard after the asserPublishX statement. In this case, the assertPublishAdd() method will
		publish a FooObject1 object to the blackboard and validate that it results in a FooObject2 object
		being added to to the blackboard.
		*/
		BlackboardState bbs = new BlackBoardState();
		bbs.add(new PublishAction(PublishAction.ADD, FooObject2.getClass());
		assertPublishAdd(new FooObject1(), bbs);
		 /**
		 * PublishAdd, PublishRemove, or PublishChange objects in the blackboard and validate that some 
		 * resulting blackboard state (i.e., object added/removed/changed)
		 */
	}

	public void runTest() {
		loadPlugin("org.cougaar.plugin.FooPlugin.class");
		run();
		//validateSubscriptions();
		//validateExecution();
	}
}
