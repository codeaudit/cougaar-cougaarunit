package org.cougaar.plugin.test;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ArrayList;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import org.cougaar.bootstrap.XURLClassLoader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * <p>Title: Launcher</p>
 * <p>Description: Launches the Cougaar plugin test environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC </p>
 * @author David Craine
 * @version 1.0
 */
public class Launcher {
    protected final static List excludedJars = new ArrayList();
    static {
        excludedJars.add("javaiopatch.jar");
        excludedJars.add("bootstrap.jar");
    }

    private static String RUN_BAT_TEXT = "@echo OFF\n"+
                                       "if \"%COUGAAR_INSTALL_PATH%\"==\"\" goto AIP_ERROR\n" +
                                       "if \"%1\"==\"\" goto ARG_ERROR\n"+
                                       "set LIBPATHS=%COUGAAR_INSTALL_PATH%\\lib\\bootstrap.jar\n"+
                                       "set MYPROPERTIES= -Dorg.cougaar.system.path=%COUGAAR_INSTALL_PATH%\\sys -Dorg.cougaar.install.path=%COUGAAR_INSTALL_PATH% -Dorg.cougaar.core.servlet.enable=true -Dorg.cougaar.lib.web.scanRange=100 -Dorg.cougaar.lib.web.http.port=8800 -Dorg.cougaar.lib.web.https.port=-1 -Dorg.cougaar.lib.web.https.clientAuth=true -Xbootclasspath/p:%COUGAAR_INSTALL_PATH%\\lib\\javaiopatch.jar\n"+
                                       "set MYMEMORY=\n"+
                                       "set MYCLASSES=org.cougaar.bootstrap.Bootstrapper org.cougaar.core.node.Node\n"+
                                       "set MYARGUMENTS= -c -n \"%1\"\n"+
                                       "@ECHO ON\n"+
                                       "java.exe %MYPROPERTIES% %MYMEMORY% -classpath %LIBPATHS% %MYCLASSES% %MYARGUMENTS% %2 %3\n"+
                                       "goto QUIT\n"+
                                       ":AIP_ERROR\n"+
                                       "echo Please set COUGAAR_INSTALL_PATH\n"+
                                       "goto QUIT\n"+
                                       ":ARG_ERROR\n"+
                                       "echo Run requires an argument  eg: Run ExerciseOneNode\n"+
                                       "goto QUIT\n"+
                                       ":QUIT\n";


    private static void writeNodeIni() throws Exception {
        File nodeFile = new File("TestNode.ini");
        FileWriter fw = new FileWriter(nodeFile);
        fw.write("[ Clusters ]\n");
        fw.write("cluster = TestAgent\n");
        fw.write("[ AlpProcess ]\n");
        fw.write("[ Policies ]\n");
        fw.write("[ Permission ]\n");
        fw.write("[ AuthorizedOperation ]\n");
        fw.flush();
        fw.close();
    }

    private static void writeAgentIni(String testPluginStr, String sourcePluginStr) throws Exception {
        File agentFile = new File("TestAgent.ini");
        FileWriter fw = new FileWriter(agentFile);
        fw.write("[ Cluster ]\n");
        fw.write("class = org.cougaar.core.agent.ClusterImpl\n");
        fw.write("uic = TestAgent\n");
        fw.write("cloned = false\n");
        fw.write("[ Plugins ]\n");
        fw.write("plugin = " + sourcePluginStr + "\n");
        fw.write("plugin = " + testPluginStr + "\n");
        fw.write("[ Policies ]\n");
        fw.write("[ Permission ]\n");
        fw.write("[ AuthorizedOperation ]\n");
        fw.flush();
        fw.close();
    }

    /**
     * Here is where we launch the cougaar environment once the node and
     * agent ini files have been configured.  This method presumes that
     */
    private static void launchCougaar() throws Exception {
        File batFile = new File("Run.bat");
        FileWriter fw = new FileWriter(batFile);
        fw.write(RUN_BAT_TEXT);
        fw.flush();
        fw.close();
        Process p = Runtime.getRuntime().exec("Run.bat TestNode");
        BufferedReader is= new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = is.readLine()) != null)
            System.out.println(line);
        //p.waitFor();   //wait for this process to terminate
    }

    public static void main(String[] args) {
        if (args[0] != null) {
            try {
                //setClassLoader();
                //get an instance of the PluginTestCase
                PluginTestCase tpc = (PluginTestCase)Class.forName(args[0]).newInstance();
                //get the class of the source plugin
                String sourcePluginStr = tpc.getPluginClass();
                //now we need to generate the ini files for launching cougaar
                writeNodeIni();
                //now we write the agent ini file
                writeAgentIni(args[0], sourcePluginStr);
                //launch Cougaar
                launchCougaar();

            }
            catch (Exception ex) {
                System.out.println("Error running test: " + ex);
            }

        }
    }
}