package org.cougaar.plugin.test.sample;

import org.cougaar.util.*;
import org.cougaar.plugin.test.*;
import org.cougaar.core.adaptivity.OperatingMode;
import org.cougaar.core.adaptivity.OperatingModeImpl;
import org.cougaar.core.adaptivity.OMCRange;
import org.cougaar.core.adaptivity.OMCRangeList;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author unascribed
 * @version 1.0
 */

public class InventoryPluginTestCase1 extends PluginTestCase {
  public final String SUPPLY_TYPE="Ammunition";
  public final Integer LEVEL_2_MIN = new Integer(40); // later, these should be parameters to plugin...
  public final Integer LEVEL_2_MAX = new Integer(225);
  public final String  LEVEL_2_TIME_HORIZON = "Level2TimeHorizon";
  public final Integer LEVEL_2_TIME_HORIZON_DEFAULT = LEVEL_2_MAX;
  public final Integer LEVEL_6_MIN = new Integer(20);
  public final Integer LEVEL_6_MAX = new Integer(225);
  public final String  LEVEL_6_TIME_HORIZON = "Level6TimeHorizon";
  public final Integer LEVEL_6_TIME_HORIZON_DEFAULT = LEVEL_6_MAX;

  OMCRange level2Range = new IntRange (LEVEL_2_MIN.intValue(), LEVEL_2_MAX.intValue());
  OMCRangeList rangeList = new OMCRangeList (level2Range);

  protected static class IntRange extends OMCRange {
    public IntRange (int a, int b) { super (a, b); }
  }


  public InventoryPluginTestCase1() {
  }

  public void validateExecution() {
    OperatingMode om = new OperatingModeImpl (LEVEL_2_TIME_HORIZON+"_"+SUPPLY_TYPE, rangeList,
                                                    LEVEL_2_TIME_HORIZON_DEFAULT);
    OperatingMode om2 = new OperatingModeImpl (LEVEL_6_TIME_HORIZON+"_"+SUPPLY_TYPE, rangeList,
                                                    LEVEL_6_TIME_HORIZON_DEFAULT);

    BlackboardDeltaState bds = new BlackboardDeltaState();
    bds.add(new PublishAction(PublishAction.ADD, om, ObjectComparators.EQUALS_COMPARATOR));
    bds.add(new PublishAction(PublishAction.ADD, om2, ObjectComparators.EQUALS_COMPARATOR));

    assertInitialState(bds, 5000, true, true);
    //assertPublishAdd(om, bds, 5000, true, true);
  }

  public String[] getPluginParameters() {
    return new String[] {"SUPPLY_TYPE="+SUPPLY_TYPE};
  }

  public String getPluginClass() {
    return "org.cougaar.logistics.plugin.inventory.InventoryPlugin";
  }

  public void validateSubscriptions() {
  }
}