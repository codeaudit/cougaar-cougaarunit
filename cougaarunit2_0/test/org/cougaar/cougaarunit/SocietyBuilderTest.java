package org.cougaar.cougaarunit;

import java.util.Iterator;

import junit.framework.TestCase;

import org.cougaar.cougaarunit.vo.Agent;
import org.cougaar.cougaarunit.vo.Node;
import org.cougaar.cougaarunit.vo.Society;

/**
 * @author mabrams
 */
public class SocietyBuilderTest extends TestCase {

	public void testBuildSociety() {
		
		Society s = SocietyBuilder.buildSociety(new SimpleTest());
		assertNotNull(s.getNodeList());
		Iterator i = s.getNodeList().iterator();
		while (i.hasNext()) {
			Node node = (Node) i.next();
			assertNotNull(node.getAgents());
			Iterator j = node.getAgents().iterator();
			while (j.hasNext()) {
				Agent agent = (Agent) j.next();
				assertNotNull(agent.getComponents());				
			}
		}
		
	}
	private class SimpleTest extends PluginTestCase{

		/* (non-Javadoc)
		 * @see org.cougaar.cougaarunit.PluginTestCase#validateSubscriptions()
		 */
		public void validateSubscriptions() {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see org.cougaar.cougaarunit.PluginTestCase#validateExecution()
		 */
		public void validateExecution() {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see org.cougaar.cougaarunit.PluginTestCase#getPluginClass()
		 */
		public String getPluginClass() {
			// TODO Auto-generated method stub
			return "org.cougaar.cougaarunit.SocietyBuilderPlugin";
		}
	}
}
