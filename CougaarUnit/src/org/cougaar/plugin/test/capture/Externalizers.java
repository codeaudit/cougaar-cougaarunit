package org.cougaar.plugin.test.capture;

import java.util.HashMap;
import java.io.Serializable;
import org.cougaar.plugin.test.ClassStringRenderer;
import org.cougaar.plugin.test.ClassStringRendererRegistry;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class Externalizers {
  private static HashMap map = new HashMap();  //used to map a class name to an externalizer
  private static HashMap indexMap = new HashMap();  //used to map an index id to an externalizer


  static Externalizer DEFAULT_EXTERNALIZER = new Externalizer() {
    public String getUniqueId() {
      return "default externalizer";
    }

    public Serializable externalize(Object obj) {
      return ClassStringRendererRegistry.render(obj);
    }

    public Object internalize(Object obj) {
      return obj;
    }
  };

  static Externalizer SERIAL_EXTERNALIZER = new Externalizer() {
    public String getUniqueId() {
      return "serial externalizer";
    }
    public Serializable externalize(Object obj) {
      if (obj instanceof Serializable)
        return (Serializable)obj;
      return ClassStringRendererRegistry.render(obj);
    }
    public Object internalize(Object obj) {
      return obj;
    }
  };

  static {
    addExternalizer(Object.class, DEFAULT_EXTERNALIZER);
  }

  public static void addExternalizer(Class clazz, Externalizer ex) {
    map.put(clazz, ex);

    //we need this indexMap beacuse once we externalize an object with a particular externalizer,
    //we need to make sure we can get back that exact same externalizer when we need to internalize
    //the object
    indexMap.put(ex.getUniqueId(), ex);
  }

  private static Externalizer getInheritedExternalizer(Class srcClass) {
    Class clazz = srcClass.getSuperclass();
    if (clazz != null) {
      Externalizer ext = (Externalizer)map.get(clazz);
      if (ext != null) {
        return ext;
      }
      else {
        return getInheritedExternalizer(clazz);
      }
    }
    return null;
  }

  private static Externalizer getExternalizer(Object obj) {
    //first let's see if there is an externalizer for this object's class
    Externalizer ext = (Externalizer)map.get(obj.getClass());
    if (ext != null) {
      return ext;
    }

    //now let's see if there is a externalizer for the object's interfaces
    Class[] classes = obj.getClass().getInterfaces();
    for (int i=0; i<classes.length; i++) {
      ext = (Externalizer)map.get(classes[i]);
      if (ext != null) {
        return ext;
      }
    }

    //finally, let's see if there is a externalizer for a superclass
    ext = getInheritedExternalizer(obj.getClass());
    if (ext != null) {
      return ext;
    }

    return DEFAULT_EXTERNALIZER;
  }

  private static Externalizer getExternalizer(String id) {
    return (Externalizer)indexMap.get(id);
  }

  /**
   * Externalize the given object using a registered Externalizer and then wrap the results
   * in an ExternalizdContainer
   * @param obj
   * @return
   */
  public static ExternalizedContainer externalize(Object obj) {
    Externalizer ext = (Externalizer)getExternalizer(obj);
    Object extObj = ext.externalize(obj);
    ExternalizedContainer cont = new ExternalizedContainer(extObj, ext.getUniqueId());
    return cont;
  }

  /**
   * Internalize the sourceobject contained in the ExternalizedContainer based upon the Externaizer
   * id specified in the container
   * @param obj
   * @return
   */
  public static Object internalize(ExternalizedContainer obj) {
    Externalizer ext = (Externalizer)getExternalizer(obj.externalizerId);
    return ext.internalize(obj.sourceObject);
  }
}