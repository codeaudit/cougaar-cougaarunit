package org.cougaar.plugin.test.sample;

public class TestBBMessageObject2 {
    public String toString() {
        String name = this.getClass().getName();
        return name.substring(name.lastIndexOf("."));
    }
}