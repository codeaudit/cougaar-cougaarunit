package org.cougaar.plugin.test;

import java.util.HashMap;
import java.util.HashSet;

public class ObjectComparators {
    private static HashMap comparatorTable = new HashMap();

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

    private static Comparator defaultComaprator = CLASS_COMPARATOR;

    public static void registerComparator(Class clazz, Comparator comp) {
        comparatorTable.put(clazz, comp);
    }

    public static void registerDefaultComparator(Comparator comp) {
        defaultComaprator = comp;
    }

    public static Comparator getComparator(Class clazz) {
        Comparator ret;

        ret = (Comparator)comparatorTable.get(clazz);
        if (ret == null)
            ret = defaultComaprator;  //use the class comparator as the default comparator

        return ret;
    }

}