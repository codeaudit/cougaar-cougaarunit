package org.cougaar.plugin.test;

import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Image;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface Animation {
      public void renderTo(Graphics2D grp, JPanel offScreenPanel, Image offScreenImage);
}