package org.cougaar.cougaarunit;


import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Vector;

import org.cougaar.cougaarunit.vo.Node;
import org.cougaar.cougaarunit.vo.Society;


/**
 * <p>
 * Title: Launcher
 * </p>
 * 
 * <p>
 * Description: Launches the Cougaar plugin test environment
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * 
 * <p>
 * Company: InfoEther, LLC
 * </p>
 *
 * @author David Craine
 * @version 1.0
 */
public class Launcher {
    private static final int TEST_SUITE_TYPE = 1;
    private static final int TEST_CASE_TYPE = 2;
    
    /** DOCUMENT ME! */
    public static final int OUTPUT_STYLE_TEXT = 0;
    /** DOCUMENT ME! */
    public static final int OUTPUT_STYLE_XML = 1;
   
   
   
    private void createLogProps() throws Exception {
        File logPropsFile;
        logPropsFile = new File("logging.props");
        logPropsFile.createNewFile();
        FileWriter fw = new FileWriter(logPropsFile);
        fw.write("log4j.rootCategory=INFO,A1,A2\n"
            + "log4j.appender.A1=org.apache.log4j.ConsoleAppender\n"
            + "log4j.appender.A1.layout=org.apache.log4j.PatternLayout\n"
            + "log4j.appender.A1.layout.ConversionPattern=%m%n\n"
            + "log4j.appender.A2=org.cougaar.plugin.test.ErrorDetectionAppender");
        fw.close();
    }


    public static void main(String[] args) {
        if (args.length > 0) {
            Launcher launcher = new Launcher();
            try {
                //get class name
                Class _class = Class.forName(args[0]);
                launcher.createTestSociety(_class.getName());
                int testType = getTestType(_class.getSuperclass());
                if (testType < 0) {
                    System.err.println("Unregonized test type");
                } else if (testType == TEST_CASE_TYPE) {
                    //process test case
                } else if (testType == TEST_SUITE_TYPE) {
                    //process test suite
                }
            } catch (Exception ex) {
                System.out.println("Error running test: " + ex);
            }
        } else {
            System.out.println("Missing required argument: Test classname");
        }
    }


    private void createTestSociety(String testName) {
        Society testSociety = SocietyBuilder.buildSociety(testName);
        Vector v = testSociety.getNodeList();
        Iterator i = v.iterator();

        //there should only be one node in a cougaarunit test
        if (i.hasNext()) {
            Node testNode = (Node) i.next();
            Iterator j = testNode.getProgramParams().iterator();
            String programArgs = "";
            while (j.hasNext()) {
                programArgs += ((String) j.next() + " ");
            }

            j = null;
            j = testNode.getVmParams().iterator();
            String vmParams = "";
            while (j.hasNext()) {
                vmParams += ((String) j.next() + " ");
            }

            j = null;
            String envParams = "";
            j = testNode.getEnvrionmentParams().iterator();
            while (j.hasNext()) {
                envParams += ((String) j.next() + " ");
            }

            String myClass = "org.cougaar.bootstrap.Bootstrapper";
            String cmdLine = "java " + vmParams + " " + envParams + " "
                + "-classpath " + System.getProperty("org.cougaar.install.path")
                + "/lib/bootstrap.jar" + " " + myClass + " " + programArgs;
            String cip = System.getProperty("org.cougaar.install.path", "CIP");
            String ws = System.getProperty("org.cougaar.workspace", "./");
            cmdLine = cmdLine.replaceAll("COUGAAR_INSTALL_PATH", cip);
			cmdLine = cmdLine.replaceAll("COUGAAR_WORKSPACE", ws);
            System.out.println(cmdLine);
        }
    }


    /**
     * Get the type of test (test suite or test case)
     *
     * @param _class
     *
     * @return
     */
    private static int getTestType(Class _class) {
        if (_class.equals(PluginTestCase.class)) {
            return TEST_CASE_TYPE;
        } else if (_class.equals(PluginTestSuite.class)) {
            return TEST_SUITE_TYPE;
        } else {
            if (_class.getSuperclass() == null) {
                return -1;
            } else {
                return getTestType(_class.getSuperclass());
            }
        }
    }

}
