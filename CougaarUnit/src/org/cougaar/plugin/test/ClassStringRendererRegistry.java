package org.cougaar.plugin.test;

import java.util.Vector;
import java.util.HashMap;
import org.cougaar.planning.ldm.plan.Task;
import org.cougaar.planning.ldm.asset.Asset;
import org.cougaar.planning.ldm.plan.Expansion;

/**
 * <p>Title: ClassStringRendererRegistry</p>
 * <p>Description: Renderes an object to a string based upon registered renderer classes</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class ClassStringRendererRegistry {
  private static HashMap registry = new HashMap();

  //setup and register some default renderers for the more common classes
  //renderer for tasks
  private final static ClassStringRenderer TASK_STRING_RENDERER = new ClassStringRenderer() {
    public String render(Object obj) {
      if (obj instanceof Task) {
        return "Task: " + ((Task)obj).getVerb().toString();
      }
      return obj.toString();
    }
  };

  //renderer for assets
  private final static ClassStringRenderer ASSET_STRING_RENDERER = new ClassStringRenderer() {
    public String render(Object obj) {
      if (obj instanceof Asset) {
        try {
          return "Asset: " + ((Asset)obj).getItemIdentificationPG().getItemIdentification();
        }
        catch (Exception e){}
      }
      return obj.toString();
    }
  };

  //renderer for expansions
  private final static ClassStringRenderer EXPANSION_STRING_RENDERER = new ClassStringRenderer() {
    public String render(Object obj) {
      if (obj instanceof Expansion) {
        try {
          return "Expansion for: " + ((Expansion)obj).getTask().getWorkflow().getParentTask().getVerb().toString();
        }
        catch (Exception e){}
      }
      return obj.toString();
    }
  };

  //static initializer
  static {
    //register the default renderers
    registry.put(Task.class, TASK_STRING_RENDERER);
    registry.put(Asset.class, ASSET_STRING_RENDERER);
    registry.put(Expansion.class, EXPANSION_STRING_RENDERER);
  }

  private ClassStringRendererRegistry() {
  }

  public static void addRenderer(Class objClass, ClassStringRenderer renderer) {
    registry.put(objClass, renderer);
  }

  public static String render(Object obj) {
    ClassStringRenderer renderer = null;

    //first let's see if there is a renderer for this object's class
    renderer = (ClassStringRenderer)registry.get(obj.getClass());
    if (renderer != null) {
      return renderer.render(obj);
    }

    //now let's see if there is a renderer for the object's interfaces
    Class[] classes = obj.getClass().getInterfaces();
    for (int i=0; i<classes.length; i++) {
      renderer = (ClassStringRenderer)registry.get(classes[i]);
      if (renderer != null) {
        return renderer.render(obj);
      }
    }

    //finally, let's see if there is a renderer for a superclass
    renderer = getInheritedRenderer(obj.getClass());
    if (renderer != null) {
      return renderer.render(obj);
    }

    //if renderer is still null, let's kust return the toString() on the object
    return obj.toString();
  }

  private static ClassStringRenderer getInheritedRenderer(Class srcClass) {
    Class clazz = srcClass.getSuperclass();
    if (clazz != null) {
      ClassStringRenderer renderer = (ClassStringRenderer)registry.get(clazz);
      if (renderer != null) {
        return renderer;
      }
      else {
        return getInheritedRenderer(clazz);
      }
    }
    return null;
  }

}