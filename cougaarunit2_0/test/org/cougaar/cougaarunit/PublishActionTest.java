/*
 * Created on Nov 5, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit;

import junit.framework.TestCase;

import org.cougaar.planning.ldm.asset.ItemIdentificationPGImpl;
import org.cougaar.planning.ldm.plan.TaskImpl;
import org.cougaar.planning.ldm.plan.Verb;

/**
 * @author ttschampel
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PublishActionTest extends TestCase {

	/*
	 * Class to test for boolean equals(java.lang.Object)
	 */
	public void testEqualsObject() {
				PublishAction pa = new PublishAction();
				PublishAction pa2 = new PublishAction();
				//test asset
				TestAsset ta1 = new TestAsset();
				TestAsset ta2 = new TestAsset();
				ItemIdentificationPGImpl iip = new ItemIdentificationPGImpl();
				ItemIdentificationPGImpl iip2 = new ItemIdentificationPGImpl();
				iip.setItemIdentification("ta1");
				iip2.setItemIdentification("ta2");
				ta1.setItemIdentificationPG(iip);
				ta2.setItemIdentificationPG(iip2);
				pa.setObj(ta1);
				pa2.setObj(ta2);
				assertFalse(pa.equals(pa2));
		
				iip2.setItemIdentification("ta1");
				ta2.setItemIdentificationPG(iip2);
				pa2.setObj(ta2);
				assertTrue(pa.equals(pa2));
		
				//test task
				TaskImpl task1 = new TaskImpl(null);
				TaskImpl task2 = new TaskImpl(null);
				task1.setVerb(Verb.getVerb("task1"));
				task2.setVerb(Verb.getVerb("Task2"));
				pa.setObj(task1);
				pa2.setObj(task2);
				
				assertFalse(pa.equals(pa2));
				
				task2.setVerb(Verb.getVerb("task1"));
				pa2.setObj(task2);
				assertTrue(pa.equals(pa2));
				
				
				//test expansion
		
		
	}

}
