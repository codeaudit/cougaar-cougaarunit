/*
 * Created on Nov 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.vo.results;

import java.io.Serializable;

import org.cougaar.cougaarunit.PluginTestResult;

/**
 * @author ttschampel
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestResultEntry implements Serializable{
	private String id;
	private BlackboardDeltaState acutalState;
	private BlackboardDeltaState expectedState;
	private String description;
	private String command;
	private String phase;
	private String result;
	
	public void populate(PluginTestResult.TestResultEntry entry){
		if(entry==null){
			return;
		}
		this.id = entry.getId()+"";
		org.cougaar.cougaarunit.BlackboardDeltaState _actual = entry.getActualState();
		if(_actual!=null){
			BlackboardDeltaState actual = new BlackboardDeltaState();
			actual.populate(_actual);
			this.acutalState= actual;
		}
		org.cougaar.cougaarunit.BlackboardDeltaState _expected =entry.getExpectedState();
		if(_expected!=null){
			BlackboardDeltaState expected = new BlackboardDeltaState();
			expected.populate(_expected);
			this.expectedState = expected;
		}
		int _command= entry.getTestCommand();
		int _phase = entry.getTestPhase();
		boolean _result = entry.isTestResult();
		switch(_command){
			case PluginTestResult.COMMAND_ASSERT_PUBLISH_ADD:
				command="Publish Add";
				break;
			case PluginTestResult.COMMAND_ASSERT_PUBLISH_CHANGE:
				command="Publish Change";
				break;
			case PluginTestResult.COMMAND_ASSERT_PUBLISH_REMOVE:
				command="Publish Remove";
				break;
			case PluginTestResult.COMMAND_SUBSCRIPTION_ASSERT:
				command="Subscription";
				break;
		}
		switch(_phase){
			case PluginTestResult.INITIAL_STATE:
				phase = "INITIAL_STATE";
				break;
			case PluginTestResult.PHASE_TEST_EXECUTION:
				phase="TEST_EXECUTION";
				break;
			case PluginTestResult.PHASE_TEST_SUBSCRIPTION:
				phase="TEST_SUBSCRIPTION";
				break;
				
		}
		if(_result){
			result = "pass";
		}else{
			result = "fail";
		}
		
		
	}
	/**
	 * @return Returns the acutalState.
	 */
	public BlackboardDeltaState getAcutalState() {
		return acutalState;
	}

	/**
	 * @param acutalState The acutalState to set.
	 */
	public void setAcutalState(BlackboardDeltaState acutalState) {
		this.acutalState = acutalState;
	}

	/**
	 * @return Returns the commmand.
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param commmand The commmand to set.
	 */
	public void setCommand(String commmand) {
		this.command = commmand;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the expectedState.
	 */
	public BlackboardDeltaState getExpectedState() {
		return expectedState;
	}

	/**
	 * @param expectedState The expectedState to set.
	 */
	public void setExpectedState(BlackboardDeltaState expectedState) {
		this.expectedState = expectedState;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the phase.
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * @param phase The phase to set.
	 */
	public void setPhase(String phase) {
		this.phase = phase;
	}

	/**
	 * @return Returns the result.
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result The result to set.
	 */
	public void setResult(String result) {
		this.result = result;
	}

}
