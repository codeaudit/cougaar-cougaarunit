package org.cougaar.plugin.test;

import java.util.HashMap;

public class ObjectComparators {
    private static HashMap comparatorTable = new HashMap();

    private static final Comparator CLASS_COMPARATOR = new Comparator() {
        public boolean compare(Object obj1, Object obj2) {
            if (obj1.getClass().equals(obj2.getClass()))
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