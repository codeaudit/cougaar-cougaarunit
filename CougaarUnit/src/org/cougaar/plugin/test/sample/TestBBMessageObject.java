package org.cougaar.plugin.test.sample;

import java.io.Serializable;

public class TestBBMessageObject implements Serializable {
    public int x;
    public int y;

    static int i=0;

    public TestBBMessageObject() {
        x = i++;
        y=i++;
    }

    public String toString() {
        return "TestBBMessageObject: x=" + String.valueOf(x)+ ", y="+String.valueOf(y);
    }
}