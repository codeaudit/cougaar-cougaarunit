package tutorial;

import org.cougaar.plugin.test.*;


/**
 * <p>Title: TutorialTestSuite</p>
 * <p>Description: CougaarUnit Test Suite for the tutorial plugins</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class TutorialTestSuite extends PluginTestSuite {

  public Class[] getTestClasses() {
    return new Class[] {DevelopmentExpanderTestCase.class, ManagerTestCase.class};
  }
}