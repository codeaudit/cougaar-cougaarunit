package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * <p>Title: AnimatedPanel</p>
 * <p>Description: Provides a panel in which a selected animation can be displayed</p>
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
  public final static int ANIMATION_FIREWORKS = 5;

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
      case ANIMATION_FIREWORKS: return new FireworksAnimation();
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
          points = 50;
          radius = 10;
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
    Dimension d;
    int radius;
    int pointCount;
    Set points;
    Color[] c;
    Point center;

    private void reset() {
      d = AnimatedPanel.this.getSize();
      radius = 10;
      pointCount = 50;
      points = new HashSet();
      c = new Color[] {Color.BLACK, Color.GRAY, Color.RED};
      center = new Point(d.height/2, d.width/2);
      for (int i=0;i<pointCount;i++) {
        int len = (int)(Math.random() * radius);
        PointWrapper pd = new PointWrapper(c[(int)(Math.random()*3)], len, (int)(Math.random()*360));
        points.add(pd);
      }

    }

    public void renderTo(Graphics2D grp, JPanel offScreenPanel, Image offScreenImage) {
      reset();

      while (true) {
        grp.setPaint(Color.BLACK);
        grp.fillRect(0,0, d.height, d.width);
        for (Iterator i = points.iterator(); i.hasNext();) {
          PointWrapper pd = (PointWrapper)i.next();
          if (Math.random() > .3) {
            pd.expand();
          }
          if (pd.getRadius() > 25) {
            reset();
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
    ap.setAnimation(AnimatedPanel.ANIMATION_FIREWORKS);
    f.getContentPane().add(ap, BorderLayout.CENTER);
    f.show();
    ap.start();

  }
}

class FireworksAnimation implements Animation {
  public void renderTo(Graphics2D grp, JPanel offScreenPanel, Image offScreenImage) {
    AnimationSpeed = 100;
    RocketStyleVariability = 20;
    MaxRocketNumber = 5;
    MaxRocketExplosionEnergy = 500;
    MaxRocketPatchNumber = 50;
    MaxRocketPatchLength = 100;
    Gravity = 20;
    mx = offScreenPanel.getSize().width - 1;
    my = offScreenPanel.getSize().height - 1;
    rocket = new Rocket[MaxRocketNumber];

    grp.setColor(Color.black);
    grp.fillRect(0, 0, mx + 1, my + 1);

    for(int i = 0; i < MaxRocketNumber; i++)
      rocket[i] = new Rocket(mx, my, Gravity);


    int k = (int)((Math.random() * (double)MaxRocketExplosionEnergy * 3D) / 4D) + MaxRocketExplosionEnergy / 4 + 1;
    int l = (int)((Math.random() * (double)MaxRocketPatchNumber * 3D) / 4D) + MaxRocketPatchNumber / 4 + 1;
    int i1 = (int)((Math.random() * (double)MaxRocketPatchLength * 3D) / 4D) + MaxRocketPatchLength / 4 + 1;
    long l1 = (long)(Math.random() * 10000D);
    do
    {
      try
      {
        Thread.sleep(100 / AnimationSpeed);
      }
      catch(InterruptedException _ex) { }
      boolean flag = true;
      for(int i = 0; i < MaxRocketNumber; i++)
        flag = flag && rocket[i].sleep;

      if(flag && Math.random() * 100D < (double)RocketStyleVariability)
      {
        k = (int)((Math.random() * (double)MaxRocketExplosionEnergy * 3D) / 4D) + MaxRocketExplosionEnergy / 4 + 1;
        l = (int)((Math.random() * (double)MaxRocketPatchNumber * 3D) / 4D) + MaxRocketPatchNumber / 4 + 1;
        i1 = (int)((Math.random() * (double)MaxRocketPatchLength * 3D) / 4D) + MaxRocketPatchLength / 4 + 1;
        l1 = (long)(Math.random() * 10000D);
      }
      for(int j = 0; j < MaxRocketNumber; j++)
      {
        if(rocket[j].sleep && Math.random() * (double)MaxRocketNumber * (double)i1 < 1.0D)
        {
        rocket[j].init(k, l, i1, l1);
        rocket[j].start();
      }
      rocket[j].show(grp);
      offScreenPanel.getGraphics().drawImage(offScreenImage, 0, 0,
          offScreenPanel);

      }

    } while(true);
  }


  public FireworksAnimation()
  {
  }

  public int AnimationSpeed;
  public int RocketStyleVariability;
  public int MaxRocketNumber;
  public int MaxRocketExplosionEnergy;
  public int MaxRocketPatchNumber;
  public int MaxRocketPatchLength;
  public int Gravity;
  public String RocketSoundtrack;
  private int mx;
  private int my;
  private Thread thread;
  private Rocket rocket[];
}
class Rocket
{

  public Rocket(int i, int j, int k)
  {
    sleep = true;
    mx = i;
    my = j;
    gravity = k;
  }

  public void init(int i, int j, int k, long l)
  {
    energy = i;
    patch = j;
    length = k;
    random = new Random(l);
    vx = new int[patch];
    vy = new int[patch];
    red = (int)(random.nextDouble() * 128D) + 128;
    blue = (int)(random.nextDouble() * 128D) + 128;
    green = (int)(random.nextDouble() * 128D) + 128;
    ox = (int)((Math.random() * (double)mx) / 2D) + mx / 4;
    oy = (int)((Math.random() * (double)my) / 2D) + my / 4;
    for(int i1 = 0; i1 < patch; i1++)
    {
      vx[i1] = (int)(Math.random() * (double)energy) - energy / 2;
      vy[i1] = (int)((Math.random() * (double)energy * 7D) / 8D) - energy / 8;
    }

  }

  public void start()
  {
    t = 0;
    sleep = false;
  }

  public void show(Graphics g)
  {
    if(!sleep)
    {
      if(t < length)
      {
        int j = ((int)(random.nextDouble() * 64D) - 32) + red;
        if(j >= 0 && j < 256)
          red = j;
        j = ((int)(random.nextDouble() * 64D) - 32) + blue;
        if(j >= 0 && j < 256)
          blue = j;
        j = ((int)(random.nextDouble() * 64D) - 32) + green;
        if(j >= 0 && j < 256)
          green = j;
        Color color = new Color(red, blue, green);
        for(int i = 0; i < patch; i++)
        {
          double d = (double)t / 100D;
          x = (int)((double)vx[i] * d);
          y = (int)((double)vy[i] * d - (double)gravity * d * d);
          g.setColor(color);
          g.drawLine(ox + x, oy - y, ox + x, oy - y);
          if(t >= length / 2)
          {
            for(int k = 0; k < 2; k++)
            {
              double d1 = (double)((t - length / 2) * 2 + k) / 100D;
              x = (int)((double)vx[i] * d1);
              y = (int)((double)vy[i] * d1 - (double)gravity * d1 * d1);
              g.setColor(Color.black);
              g.drawLine(ox + x, oy - y, ox + x, oy - y);
            }

          }
        }

        t++;
        return;
      }
      sleep = true;
    }
  }

  public boolean sleep;
  private int energy;
  private int patch;
  private int length;
  private int mx;
  private int my;
  private int gravity;
  private int ox;
  private int oy;
  private int vx[];
  private int vy[];
  private int x;
  private int y;
  private int red;
  private int blue;
  private int green;
  private int t;
  private Random random;
}