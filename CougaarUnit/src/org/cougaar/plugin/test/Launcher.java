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
import java.io.OutputStream;

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
  private static final int PLATFORM_WINDOWS = 0;
  private static final int PLATFORM_LINUX = 1;
  private static final int PLATFORM_OTHER = 2;
  private static int currentOS;

  public static final int OUTPUT_STYLE_TEXT = 0;
  public static final int OUTPUT_STYLE_XML = 1;
  private static final String[] OUTPUT_STYLES = {"text", "xml"};
  private int outputStyle = OUTPUT_STYLE_TEXT;

  private static String RUN_BAT_TEXT = "@echo OFF\n"+
                                       "if \"%COUGAAR_INSTALL_PATH%\"==\"\" goto AIP_ERROR\n" +
                                       "if \"%1\"==\"\" goto ARG_ERROR\n"+
                                       "set LIBPATHS=%COUGAAR_INSTALL_PATH%\\lib\\bootstrap.jar\n"+
                                       "set MYPROPERTIES= -Dorg.cougaar.class.path=%COUGAAR_INSTALL_PATH%\\lib\\CougaarUnit.jar -Dorg.cougaar.system.path=%COUGAAR_INSTALL_PATH%\\sys -Dorg.cougaar.install.path=%COUGAAR_INSTALL_PATH% -Dorg.cougaar.core.servlet.enable=true -Dorg.cougaar.lib.web.scanRange=100 -Dorg.cougaar.lib.web.http.port=8800 -Dorg.cougaar.lib.web.https.port=-1 -Dorg.cougaar.lib.web.https.clientAuth=true -Xbootclasspath/p:%COUGAAR_INSTALL_PATH%\\lib\\javaiopatch.jar\n"+
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

  private static String RUN_SH_TEXT = "#!/bin/sh\n"+
                                      "if [ \"$COUGAAR_INSTALL_PATH\" = \"\" ]\n"+
                                      "then\n"+
                                      "echo Please set COUGAAR_INSTALL_PATH\n"+
                                      "exit 2\n"+
                                      "fi\n"+
                                      "LIBPATHS=$COUGAAR_INSTALL_PATH/lib/bootstrap.jar\n"+
                                      "MYPROPERTIES=\"-Dorg.cougaar.class.path=$COUGAAR_INSTALL_PATH/lib/CougaarUnit.jar -Dorg.cougaar.system.path=$COUGAAR_INSTALL_PATH/sys -Dorg.cougaar.install.path=$COUGAAR_INSTALL_PATH -Dorg.cougaar.core.servlet.enable=true -Dorg.cougaar.lib.web.scanRange=100 -Dorg.cougaar.lib.web.http.port=8800 -Dorg.cougaar.lib.web.https.port=-1 -Dorg.cougaar.lib.web.https.clientAuth=true -Xbootclasspath/p:$COUGAAR_INSTALL_PATH/lib/javaiopatch.jar\"\n"+
                                      "MYMEMORY=\n"+
                                      "MYCLASSES=\"org.cougaar.bootstrap.Bootstrapper org.cougaar.core.node.Node\"\n"+
                                      "MYARGUMENTS=\"-c -n $1\"\n"+
                                      "java $MYPROPERTIES $MYMEMORY -classpath $LIBPATHS $MYCLASSES $MYARGUMENTS $2 $3\n";


  static {
    //initilaize the currentOS setting
    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.indexOf("windows") != -1)
      currentOS = PLATFORM_WINDOWS;
    else if (osName.indexOf("linux")!= 01)
      currentOS = PLATFORM_LINUX;
    else
      currentOS = PLATFORM_OTHER;
  }



  private void writeNodeIni() throws Exception {
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

  private void writeAgentIni(String testPluginStr, String sourcePluginStr) throws Exception {
    File agentFile = new File("TestAgent.ini");
    FileWriter fw = new FileWriter(agentFile);
    fw.write("[ Cluster ]\n");
    fw.write("class = org.cougaar.core.agent.ClusterImpl\n");
    fw.write("uic = TestAgent\n");
    fw.write("cloned = false\n");
    fw.write("[ Plugins ]\n");
    fw.write("plugin = " + sourcePluginStr + "\n");
    fw.write("plugin = " + testPluginStr + "\n");
    fw.write("Node.AgentManager.Agent.PluginManager.Binder(BINDER) = org.cougaar.plugin.test.PluginServiceFilter\n");
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
  private void launchCougaar(OutputStream os) throws Exception {
    if (os == null) os = System.out;

    String execStr = createRunFile();

    System.out.println("execing: " + execStr);
    Process p = Runtime.getRuntime().exec(execStr);

    BufferedReader is= new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line;
    while ((line = is.readLine()) != null) {
      byte[] lineData = line.getBytes();
      os.write(lineData);
      //os.write('\n');
    }
    //p.waitFor();   //wait for this process to terminate
  }

  private String createRunFile() throws Exception {
    File shellFile;
    String shellFileText;
    String execStr;

    if (currentOS == PLATFORM_WINDOWS) {  //is this a Windows environment?
      shellFile = new File("Run.bat");
      shellFileText = "@echo OFF\n"+
                      "if \"%COUGAAR_INSTALL_PATH%\"==\"\" goto AIP_ERROR\n" +
                      "if \"%1\"==\"\" goto ARG_ERROR\n"+
                      "set LIBPATHS=%COUGAAR_INSTALL_PATH%\\lib\\bootstrap.jar\n"+
                      "set MYPROPERTIES= -Dorg.cougaar.class.path=%COUGAAR_INSTALL_PATH%\\lib\\CougaarUnit.jar "+
                      "-Dorg.cougaar.system.path=%COUGAAR_INSTALL_PATH%\\sys "+
                      "-Dorg.cougaar.install.path=%COUGAAR_INSTALL_PATH% "+
                      "-Dorg.cougaar.core.servlet.enable=true "+
                      "-Dorg.cougaar.lib.web.scanRange=100 "+
                      "-Dorg.cougaar.lib.web.http.port=8800 "+
                      "-Dorg.cougaar.lib.web.https.port=-1 "+
                      "-Dorg.cougaar.plugin.test.output.format="+OUTPUT_STYLES[outputStyle]+" "+
                      "-Dorg.cougaar.lib.web.https.clientAuth=true "+
                      "-Xbootclasspath/p:%COUGAAR_INSTALL_PATH%\\lib\\javaiopatch.jar\n"+
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

      execStr = shellFile.getName() + " TestNode";
    }
    else if (currentOS == PLATFORM_LINUX) {  //is this a Linux environment?
      shellFile = new File("Run.sh");
      shellFileText = "#!/bin/sh\n"+
                      "if [ \"$COUGAAR_INSTALL_PATH\" = \"\" ]\n"+
                      "then\n"+
                      "echo Please set COUGAAR_INSTALL_PATH\n"+
                      "exit 2\n"+
                      "fi\n"+
                      "LIBPATHS=$COUGAAR_INSTALL_PATH/lib/bootstrap.jar\n"+
                      "MYPROPERTIES=\"-Dorg.cougaar.class.path=$COUGAAR_INSTALL_PATH/lib/CougaarUnit.jar "+
                      "-Dorg.cougaar.system.path=$COUGAAR_INSTALL_PATH/sys "+
                      "-Dorg.cougaar.install.path=$COUGAAR_INSTALL_PATH "+
                      "-Dorg.cougaar.core.servlet.enable=true "+
                      "-Dorg.cougaar.lib.web.scanRange=100 "+
                      "-Dorg.cougaar.lib.web.http.port=8800 "+
                      "-Dorg.cougaar.lib.web.https.port=-1 "+
                      "-Dorg.cougaar.lib.web.https.clientAuth=true "+
                      "-Dorg.cougaar.plugin.test.output.format="+OUTPUT_STYLES[outputStyle]+" "+
                      "-Xbootclasspath/p:$COUGAAR_INSTALL_PATH/lib/javaiopatch.jar\"\n"+
                      "MYMEMORY=\n"+
                      "MYCLASSES=\"org.cougaar.bootstrap.Bootstrapper org.cougaar.core.node.Node\"\n"+
                      "MYARGUMENTS=\"-c -n $1\"\n"+
                      "java $MYPROPERTIES $MYMEMORY -classpath $LIBPATHS $MYCLASSES $MYARGUMENTS $2 $3\n";
      if (!shellFile.exists()) {
        shellFile.createNewFile();
        Runtime.getRuntime().exec("chmod +x " + shellFile.getName());
      }
      execStr = "./"+shellFile.getName() + " TestNode";
    }
    else throw new Exception("Unsupported platform");

    FileWriter fw = new FileWriter(shellFile);
    fw.write(shellFileText);
    fw.flush();
    fw.close();
    return execStr;
  }

  public static void main(String[] args) {
    if (args[0] != null) {
      Launcher launcher = new Launcher();
      try {
        //determine if args[0] is a PluginTestSuite or a PluginTestCase
        Class clazz = Class.forName(args[0]);
        if (clazz.getSuperclass().equals(PluginTestSuite.class)) {
          launcher.runTestSuite((PluginTestSuite)clazz.newInstance(), System.out);
        }
        else if (clazz.getSuperclass().equals(PluginTestCase.class)) {
          launcher.runTestCase((PluginTestCase)clazz.newInstance(), System.out);
        }
        else {
          System.out.println("Unknown test class type: " + clazz.getName());
        }
      }
      catch (Exception ex) {
        System.out.println("Error running test: " + ex);
      }

    }
  }

  protected void runTestSuite(PluginTestSuite pts, OutputStream ps) throws Exception {
    Class[] testClasses = pts.getTestClasses();
    for (int i=0; i<testClasses.length; i++) {
      try {
        if (testClasses[i].getSuperclass().equals(PluginTestCase.class)) {
          runTestCase((PluginTestCase)testClasses[i].newInstance(), ps);
        }
        else {
          System.out.println("Invalid Test Case object found in Test Suite.");
        }
      }
      catch (Exception ex) {
        System.out.println("Invalid test class object.");
      }
    }
  }


  protected void runTestCase(PluginTestCase tpc, OutputStream ps) throws Exception {
    String sourcePluginStr = tpc.getPluginClass();
    //now we need to generate the ini files for launching cougaar
    writeNodeIni();
    //now we write the agent ini file
    writeAgentIni(tpc.getClass().getName(), sourcePluginStr);
    //launch Cougaar
    launchCougaar(ps);
  }

  public void setOutputStyle(int i) {
    if (i < OUTPUT_STYLES.length) {
      outputStyle = i;
    }
  }
}