package org.cougaar.plugin.test;

import org.cougaar.core.plugin.ComponentPlugin;
import java.net.URLClassLoader;
import java.net.URL;
import org.cougaar.bootstrap.XURLClassLoader;

public class SamplePlugin extends ComponentPlugin {
    protected void setupSubscriptions() {
        System.out.println("**************SamplePlugin - setupScriptions()");
    }

    protected void execute() {
        System.out.println("*********SamplePlugin - execute()");
        URL[] urls = ((XURLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs();
        //URL[] urls = ((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs();
        for (int i=0; i<urls.length; i++)
            System.out.println("URL: " + urls[i].getPath());
    }
}