package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AnimatedPanel extends JPanel implements Runnable {
  public final static int ANIMATION_SPIRAL = 1;
  public final static int ANIMATION_POINTS_1 = 2;
  public final static int ANIMATION_POINTS_2 = 3;
  public final static int ANIMATION_CIRCLES = 4;

  private BorderLayout borderLayout1 = new BorderLayout();
  private Thread myThread;
  public  Dimension size = null;
  private Image offScreenImage = null;
  private int currentAnimation = ANIMATION_CIRCLES;
  boolean running = false;

  public AnimatedPanel() {
    try {
      jbInit();
      myThread = new Thread(this);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
  }

  public void start() {
    if (!running) {
      myThread.start();
      running = true;
    }
  }

  public void setAnimation(int i) {
    this.currentAnimation = i;
  }

  public void stop() {
    myThread.interrupt();
    running = false;
  }

  private Animation getCurrentAnimation() {
    switch(currentAnimation) {
      case ANIMATION_SPIRAL: return new SpiralAnimation();
      case ANIMATION_POINTS_1: return new PointsAnimation();
      case ANIMATION_POINTS_2: return new PointsAnimation2();
      case ANIMATION_CIRCLES: return new CirclesAnimation();
      default: return new CirclesAnimation();
    }
  }

  public void run() {
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    size = this.getSize();
    offScreenImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    Graphics2D grp = (Graphics2D)offScreenImage.getGraphics();
    getCurrentAnimation().renderTo(grp, this, offScreenImage);
  }

  class SpiralAnimation implements Animation {
    Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.PINK, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.YELLOW, Color.WHITE};

    public void renderTo(Graphics2D grp, JPanel offScreenPanel, Image offScreenImage) {
      Dimension d = AnimatedPanel.this.getSize();
      grp.setPaint(Color.BLACK);
      grp.fillRect(0,0, d.width, d.height);
      Point2D.Double p = new Point2D.Double(d.width/2, d.height/2);
      Point2D.Double pEnd = new Point2D.Double(d.width/2, d.height/2);
      //Color c = Color.RED;
      //grp.setPaint(c);
      double theta = 0;
      double length = 1;
      int colorIndex = 0;
      while (true) {
        grp.setPaint(colors[colorIndex]);
        colorIndex = (colorIndex+1)%colors.length;
        theta += Math.PI/10;
        length+=.3;
        pEnd = new Point2D.Double(Math.sin(theta)*length,
                                  Math.cos(theta)*length);
        grp.drawRect((int)(p.getX()+pEnd.getX()), (int)(p.getY()+pEnd.getY()), 1, 1);
        try {offScreenPanel.getGraphics().drawImage(offScreenImage, 0, 0, offScreenPanel);} catch (Exception e){}
        try {Thread.currentThread().sleep(10);} catch (Exception e){}
        if ((length > AnimatedPanel.this.getSize().width/2) || (length > AnimatedPanel.this.getSize().height/2)) {
          pEnd = new Point2D.Double(d.width/2, d.height/2);
          grp.setPaint(Color.BLACK);
          grp.fillRect(0,0, d.width, d.height);
          try {offScreenPanel.getGraphics().drawImage(offScreenImage, 0, 0, offScreenPanel);} catch (Exception e){}
          theta = 0;
          length = 1;
        }

      }
    }
  }

  class CirclesAnimation implements Animation {
    public void renderTo(Graphics2D grp, JPanel offScreenPanel, Image offScreenImage) {
      Dimension d = AnimatedPanel.this.getSize();

      Point2D.Double p = new Point2D.Double(d.height/2, d.width/2);
      Color c = Color.RED;
      grp.setPaint(c);
      while (true) {
        grp.drawOval((int)p.getX(),  (int)p.getY(),  25, 25);
        grp.drawOval((int)p.getX()+5,  (int)p.getY()+5,  25, 25);
        offScreenPanel.getGraphics().drawImage(offScreenImage, 0, 0, offScreenPanel);
      }

    }
  }

  class PointsAnimation implements Animation {
    public void renderTo(Graphics2D grp, JPanel offScreenPanel, Image offScreenImage) {
      Dimension d = AnimatedPanel.this.getSize();
      int radius = 10;
      int points = 50;

      while (true) {
        radius++;
        points--;
        grp.setPaint(Color.BLACK);
        grp.fillRect(0,0, d.height, d.width);
        points(grp, radius, points);
        offScreenPanel.getGraphics().drawImage(offScreenImage, 0, 0,
            offScreenPanel);
        try {Thread.currentThread().sleep(100);} catch (Exception e) {}
        if (points == 0) {
          break;
        }
      }
    }

    private void points(Graphics2D grp, int radius, int points) {
      Dimension d = AnimatedPanel.this.getSize();
      Color[] c = new Color[] {Color.RED, Color.GREEN, Color.BLUE};
      Point center = new Point(d.height/2, d.width/2);
      for (int i=0;i<points;i++) {
        int x = (int)(Math.random() * radius);
        int y = (int)(Math.random() * radius);
        if (Math.random() > .5) {
          x = -x;
        }
        if (Math.random() > .5) {
          y = -y;
        }
        grp.setPaint(c[(int)(Math.random()*3)]);
        grp.drawRect(x+center.x,y+center.y, 1, 1);
      }
    }
  }

  class PointsAnimation2  implements Animation {
    public void renderTo(Graphics2D grp, JPanel offScreenPanel, Image offScreenImage) {
      Dimension d = AnimatedPanel.this.getSize();
      int radius = 10;
      int pointCount = 50;
      Set points = new HashSet();
      Color[] c = new Color[] {Color.BLACK, Color.GRAY, Color.RED};
      Point center = new Point(d.height/2, d.width/2);
      for (int i=0;i<pointCount;i++) {
        int len = (int)(Math.random() * radius);
        PointWrapper pd = new PointWrapper(c[(int)(Math.random()*3)], len, (int)(Math.random()*360));
        points.add(pd);
      }

      while (true) {
        grp.setPaint(Color.BLACK);
        grp.fillRect(0,0, d.height, d.width);
        for (Iterator i = points.iterator(); i.hasNext();) {
          PointWrapper pd = (PointWrapper)i.next();
          if (Math.random() > .3) {
            pd.expand();
          }
          if (pd.getRadius() > 25) {
            break;
          }
          grp.setPaint(pd.getC());
          int x = (int)(pd.getRadius() * Math.sin(Math.toRadians(pd.getTheta())));
          int y = (int)(pd.getRadius() * Math.cos(Math.toRadians(pd.getTheta())));
          grp.drawRect(center.x + x, center.y+y ,1,1);
        }

        offScreenPanel.getGraphics().drawImage(offScreenImage, 0, 0,
            offScreenPanel);
        try {Thread.currentThread().sleep(100);} catch (Exception e) {}
      }
    }
  }

  class PointWrapper {
    private int rad;
    private Color c;
    private int theta;
    public PointWrapper(Color c, int rad, int theta) {
      this.c = c;
      this.rad = rad;
      this.theta = theta;
    }

    public int getRadius() {
      return rad;
    }

    public Color getC() {
      return c;
    }

    public int getTheta() {
      return theta;
    }
    public void expand() {
      rad++;
    }
  }

  public static void main(String[] args) {
    JFrame f = new JFrame("Test");
    f.pack();
    f.setSize(300,200);
    f.setResizable(false);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setLayout(new BorderLayout());
    AnimatedPanel ap = new AnimatedPanel();
    ap.setAnimation(AnimatedPanel.ANIMATION_SPIRAL);
    f.getContentPane().add(ap, BorderLayout.CENTER);
    f.show();
    ap.start();

  }
}