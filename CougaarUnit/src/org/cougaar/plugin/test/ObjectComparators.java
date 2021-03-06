package org.cougaar.plugin.test;

import java.util.HashMap;
import java.util.HashSet;
import org.cougaar.planning.ldm.plan.Task;
import org.cougaar.planning.ldm.plan.Expansion;
import org.cougaar.planning.ldm.asset.Asset;
import org.cougaar.planning.ldm.plan.Workflow;

/**
 *
 * <p>Title: ObjectComparators</p>
 * <p>Description: A registry of comparator objects that are used to compare vraious objects.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
public class ObjectComparators {
    private static HashMap comparatorTable = new HashMap();

    /**
     * Predefined Comparator object that compares to see if two objects are instances
     * of the same class.
     */
    public static final Comparator CLASS_COMPARATOR = new Comparator() {
      public boolean compare(Object obj1, Object obj2) {
        if ((obj1 instanceof Class) && (obj2 instanceof Class)) {
          return obj1.equals(obj2);
        }
        else if (obj1 instanceof Class) {
          return obj1.equals(obj2.getClass());
        }
        else if (obj2 instanceof Class) {
          return obj2.equals(obj1.getClass());
        }
        else if (obj1.getClass().equals(obj2.getClass())) {
          return true;
        }
        return false;
      }
    };

    /**
    * Compares to objects to see if they implement the same interaface
    */
    public static final Comparator INTERFACE_COMAPARATOR = new Comparator() {
      public boolean compare(Object obj1, Object obj2) {
        if ((obj1 == null) || (obj2 == null)) return false;

        Class[] obj1Interfaces = obj1.getClass().getInterfaces();
        Class[] obj2Interfaces = obj2.getClass().getInterfaces();

        HashSet hs1 = new HashSet(obj1Interfaces.length);
        for (int i=0; i<obj1Interfaces.length; i++) {
          hs1.add(obj1Interfaces[i]);
        }
        HashSet hs2 = new HashSet(obj2Interfaces.length);
        for (int i=0; i<obj2Interfaces.length; i++) {
          hs2.add(obj2Interfaces[i]);
        }

        hs1.retainAll(hs2);

        if (hs1.size() > 0)
          return true;

        return false;
      }
    };

    public static final Comparator EQUALS_COMPARATOR = new Comparator() {
      public boolean compare(Object obj1, Object obj2) {
        if ((obj1 == null) || (obj2 == null)) return false;
        return obj1.equals(obj2);
      }
    };

    public static final Comparator TASK_VERB_COMPARATOR = new Comparator() {
      public boolean compare(Object obj1, Object obj2) {
        if ((obj1 == null) || (obj2 == null)) return false;
        if ((!(obj1 instanceof Task) || (!(obj2 instanceof Task)))) return false;
        Task t1 = (Task)obj1;
        Task t2 = (Task)obj2;

        try {return t1.getVerb().equals(t2.getVerb());} catch (Exception e){}

        return false;
      }
    };


    public static final Comparator EXPANSION_COMPARATOR = new Comparator() {
      public boolean compare(Object obj1, Object obj2) {
        if ((obj1 == null) || (obj2 == null)) return false;
        if ((!(obj1 instanceof Expansion) || (!(obj2 instanceof Expansion)))) return false;

        Expansion e1 = (Expansion)obj1;
        Expansion e2 = (Expansion)obj2;
        try {return e1.getTask().getVerb().equals(e2.getTask().getVerb());} catch (Exception e){}

        return false;
      }
    };

    public static final Comparator ASSET_COMPARATOR = new Comparator() {
      public boolean compare(Object obj1, Object obj2) {
        if ((obj1 == null) || (obj2 == null)) return false;
        if ((!(obj1 instanceof Asset) || (!(obj2 instanceof Asset)))) return false;

        Asset a1 = (Asset)obj1;
        Asset a2 = (Asset)obj2;

        try {return a1.getItemIdentificationPG().getItemIdentification().equals(a2.getItemIdentificationPG().getItemIdentification());} catch(Exception e){}
        return false;
      }
    };

    public static final Comparator WORKFLOW_COMPARATOR = new Comparator() {
        public boolean compare(Object obj1, Object obj2) {
            if ((obj1 == null) || (obj2 == null)) return false;
            if ((!(obj1 instanceof Workflow) || (!(obj2 instanceof Workflow)))) return false;

            Workflow w1 = (Workflow)obj1;
            Workflow w2 = (Workflow)obj2;
            try {
                return w1.getParentTask().getVerb().equals(w2.getParentTask().getVerb());
            } catch (Exception e) {
            }

            return false;
        }
    };

    private static Comparator defaultComaprator = EQUALS_COMPARATOR;

    /**
     * Register a new comparator object to be used when comparing instances of class clazz.
     * @param clazz  Class for which the comparator is to be used
     * @param comp  The Comparator object
     */
    public static void registerComparator(Class clazz, Comparator comp) {
        comparatorTable.put(clazz, comp);
    }

    /**
     * Register a default Comparator object to be use when no other comparator
     * can be located.
     * @param comp  Comparator object to register
     */
    public static void registerDefaultComparator(Comparator comp) {
        defaultComaprator = comp;
    }

    /**
     * Return the comparator registered for a specific class. If no comparator is
     * found for that class, the default comparator will be returned.
     * @param clazz Class for which the the Comparator is requested
     * @return Comparator
     */
    public static Comparator getComparator(Class clazz) {
        Comparator ret;

        ret = (Comparator)comparatorTable.get(clazz);
        if (ret == null)
            ret = defaultComaprator;  //use the class comparator as the default comparator

        return ret;
    }

}