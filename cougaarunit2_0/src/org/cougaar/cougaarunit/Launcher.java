/*
 * <copyright>
 *  Copyright 2000-2003 CougaarSoftware
 *  All Rights Reserved
 * </copyright>
 */


package org.cougaar.cougaarunit;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.cougaar.cougaarunit.vo.Node;
import org.cougaar.cougaarunit.vo.Society;
import org.cougaar.cougaarunit.vo.TestResult;
import org.cougaar.cougaarunit.vo.TestResultId;
import org.cougaar.cougaarunit.vo.TestResultSummary;
import org.cougaar.cougaarunit.vo.TestSuiteResult;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;


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
    private static final String COUGAAR_UNIT_PROPS = "cougaar_unit.properties";
    private static final String SHOW_OUTPUT = "org.cougaar.cougaarunit.showoutput";

    //Result Codes
    /** DOCUMENT ME! */
    public static final int COUGAAR_ERROR_CODE = -2;
    /** DOCUMENT ME! */
    public static final int TEST_PASS_CODE = 1;
    /** DOCUMENT ME! */
    public static final int TEST_FAIL_CODE = -1;
    /** DOCUMENT ME! */
    public static final int OUTPUT_STYLE_TEXT = 0;
    /** DOCUMENT ME! */
    public static final int OUTPUT_STYLE_XML = 1;
    private static boolean writeToOutput = false;
    private Class testClass;

    /**
     * Creates a new Launcher object.
     *
     * @param testClassName DOCUMENT ME!
     */
    public Launcher(Class testClassName) {
        this.testClass = testClassName;
    }

    /**
     * Here is where we launch the cougaar environment once the node and agent
     * ini files have been configured. This method presumes that
     *
     * @param os DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    private int launchCougaar(OutputStream os) throws Exception {
        int retCode = TEST_FAIL_CODE;

        if (os == null) {
            os = System.out;
        }

        String execStr = createTestSociety();

        System.out.println("execing: " + execStr);

        Process p = Runtime.getRuntime().exec(execStr);
        System.out.println("Process:  " + p);


        //BufferedWriter ps = new BufferedWriter(new
        // OutputStreamWriter(p.getOutputStream()));
        BufferedReader is = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
        BufferedReader es = new BufferedReader(new InputStreamReader(
                    p.getErrorStream()));
        String line = null;
        String line2 = null;

        while (((line = is.readLine()) != null)
            || ((line2 = es.readLine()) != null)) {
            if (line != null) {
                line += "\n";

                byte[] lineData = line.getBytes();

                if ((new String(lineData).toUpperCase().indexOf("Exception") >= 0)
                    || (new String(lineData).toUpperCase().indexOf("ERROR") >= 0)) {
                    retCode = COUGAAR_ERROR_CODE;
                }

                if (writeToOutput) {
                    os.write(lineData);
                }
            }

            if (line2 != null) {
                byte[] lineData = line2.getBytes();

                if ((new String(lineData).toUpperCase().indexOf("Exception") >= 0)
                    || (new String(lineData).toUpperCase().indexOf("ERROR") >= 0)) {
                    retCode = COUGAAR_ERROR_CODE;
                }

                if (writeToOutput) {
                    os.write(lineData);
                }
            }

            if (retCode == COUGAAR_ERROR_CODE) {
                p.destroy();

                return COUGAAR_ERROR_CODE;
            }

            //os.write('\n');
        }

        System.out.println("exiting Cougaar system...");
        System.out.flush();

        int processCode = p.waitFor(); //wait for this process to terminate

        if (processCode != 0) {
            return COUGAAR_ERROR_CODE;
        }

        //test result of test case
        TestResult testResult = null;
        String fileName = PluginTestCase.RESULTS_DIRECTORY + File.separator
            + ((PluginTestCase) this.testClass.newInstance()).getDescription()
            + ".xml";

        try {
            Mapping mapping = new Mapping();
            mapping.loadMapping("resultMapping.xml");

            Unmarshaller unmar = new Unmarshaller(mapping);

            testResult = (TestResult) unmar.unmarshal(new InputSource(
                        new FileReader(fileName)));

            Vector ids = testResult.getIdList();
            Enumeration enumeration = ids.elements();
            boolean pass = true;

            while (enumeration.hasMoreElements()) {
                TestResultId _id = (TestResultId) enumeration.nextElement();

                if (!(_id.getResult().equals("pass"))) {
                    pass = false;
                }
            }

            if (pass) {
                retCode = TEST_PASS_CODE;
            } else {
                retCode = TEST_FAIL_CODE;
            }
        } catch (Exception e) {
            System.err.println("Exception reading results XML file " + fileName);
            e.printStackTrace();
        }

        return retCode;
    }


    private void createLogProps() throws Exception {
        File logPropsFile;
        logPropsFile = new File("log.properties");
        logPropsFile.createNewFile();

        FileWriter fw = new FileWriter(logPropsFile);
        fw.write(
            "# Set the root category priority to WARN and add a stdout and rolling appender.\n"
            + "log4j.rootCategory=ERROR, stdout, rolling\n"
            + "log4j.category.org.cougaar.cougaarunit=INFO\n"
            + "# -- Configure all the Appenders\n"
            + "#  ------ Configure the STDOUT Appender\n"
            + "log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n"
            + "log4j.appender.stdout.Target=System.out\n"
            + "# Define the STDOUT pattern to:  date level [thread] - message\n"
            + "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n"
            + "log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %-5p [%c{1}] - %m%n\n"
            + "#  ------ End STDOUT Appender\n"
            + "# ----- Configure the Rolling Log File\n" + "#\n"
            + "# Configure a Rolling Log File Appender\n"
            + "log4j.appender.rolling=org.apache.log4j.RollingFileAppender\n"
            + "log4j.appender.rolling.File=" + this.testClass.getName()
            + ".log\n" + "# Define the logfile size\n"
            + "log4j.appender.rolling.MaxFileSize=1024KB\n"
            + "# Keep a backup file\n"
            + "log4j.appender.rolling.MaxBackupIndex=1\n"
            + "# Define the Rolling pattern to:  date level [thread] - message\n"
            + "log4j.appender.rolling.layout=org.apache.log4j.PatternLayout\n"
            + "log4j.appender.rolling.layout.ConversionPattern=%d{ABSOLUTE} %-5p [%C{1}] - %m%n\n"
            + "#\n" + "# ---- End Rolling Log File");

        fw.close();
    }


    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                if ((System.getProperty(SHOW_OUTPUT) != null)
                    && System.getProperty(SHOW_OUTPUT).toUpperCase().equals("TRUE")) {
                    writeToOutput = true;
                }


                //get class name
                Class _class = Class.forName(args[0]);
                int testType = getTestType(_class.getSuperclass());

                if (testType < 0) {
                    System.err.println("Unregonized test type");
                } else if (testType == TEST_CASE_TYPE) {
                    Launcher launcher = new Launcher(_class);
                    launcher.createLogProps();

                    int returnCode = launcher.launchCougaar(System.out);
                    System.out.println("Return code:" + returnCode);

                    if (returnCode == TEST_PASS_CODE) {
                        System.out.println("Test Passed");
                        System.exit(0);
                    } else {
                        System.out.println("Test Failed");
                        System.exit(1);
                    }
                } else if (testType == TEST_SUITE_TYPE) {
                    //process test suite
                    PluginTestSuite testSuite = (PluginTestSuite) _class
                        .newInstance();
                    Class[] _classes = testSuite.getTestClasses();

                    if ((_classes != null) && (_classes.length > 0)) {
                        boolean pass = true;
                        Vector tests = new Vector();
                        for (int i = 0; i < _classes.length; i++) {
                            Launcher launcher = new Launcher(_classes[i]);
                            launcher.createLogProps();

                            int returnCode = launcher.launchCougaar(System.out);

                            if (returnCode != TEST_PASS_CODE) {
                                pass = false;
                            }

                            //get test result
                            if (returnCode != COUGAAR_ERROR_CODE) {
                                try {
                                    //														test result of test case
                                    TestResult testResult = null;
                                    String fileName = new File(PluginTestCase.RESULTS_DIRECTORY
                                            + File.separator
                                            + ((PluginTestCase) _classes[i]
                                            .newInstance()).getDescription()
                                            + ".xml").getAbsolutePath();


                                    Mapping mapping = new Mapping();
                                    mapping.loadMapping("resultMapping.xml");

                                    Unmarshaller unmar = new Unmarshaller(mapping);

                                    testResult = (TestResult) unmar.unmarshal(new InputSource(
                                                new FileReader(fileName)));
                                    TestResultSummary summary = new TestResultSummary();
                                    summary.setName(_classes[i].getName());
                                    summary.setIdList(testResult.getIdList());
                                    tests.add(summary);
                                } catch (Exception e) {
                                    System.err.println(
                                        "Error getting test results");
                                    e.printStackTrace();
                                }
                            }
                        }

                        //create test suite xml file
                        TestSuiteResult testSuiteResult = new TestSuiteResult();
                        testSuiteResult.setName(args[0]);
                        testSuiteResult.setTestList(tests);
                        testSuiteResult.saveResult(PluginTestCase.RESULTS_DIRECTORY);
                        try{
							TransformerFactory tFactory = TransformerFactory.newInstance();
							Transformer transformer = tFactory.newTransformer(new StreamSource("testsuite.xsl"));
							File f = new File(PluginTestCase.RESULTS_DIRECTORY+File.separator + testSuiteResult.getName()+".html");
							FileOutputStream fos = new FileOutputStream(f);
							transformer.transform(new StreamSource(PluginTestCase.RESULTS_DIRECTORY+File.separator + testSuiteResult.getName()+".xml"), new StreamResult(new OutputStreamWriter(fos)));
                        }catch(Exception e){
                        	
                        }
                        
                        
                        if (pass) {
                            System.out.println("All tests in suite passed");
                            System.exit(0);
                        } else {
                            System.out.println(
                                "All tests in suite DID NOT pass");
                            System.exit(1);
                        }
                    } else {
                        System.err.println("Test suite with no test cases");
                        System.exit(1);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error running test: " + ex);
                System.exit(1);
            }
        } else {
            System.out.println("Missing required argument: Test classname");
            System.exit(1);
        }
    }


    /**
     * Create the Society XML file and return the Command line to launch the
     * Cougaar Society
     *
     * @return Command line to launch cougaar
     *
     * @throws Exception
     */
    private String createTestSociety() throws Exception {
        PluginTestCase pluginTestCase = (PluginTestCase) this.testClass
            .newInstance();
        Society testSociety = SocietyBuilder.buildSociety(pluginTestCase);
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
            j = testNode.getEnvironmentParams().iterator();

            while (j.hasNext()) {
                envParams += ((String) j.next() + " ");
            }

            String myClass = "org.cougaar.bootstrap.Bootstrapper";
            String cmdLine = "java " + vmParams + " " + envParams + " "
                + "-classpath "
                + System.getProperty("org.cougaar.install.path")
                + "/lib/bootstrap.jar" + " " + myClass + " " + programArgs;
            String cip = System.getProperty("org.cougaar.install.path", "CIP");
            String ws = System.getProperty("org.cougaar.workspace", "./");
            cmdLine = cmdLine.replaceAll("COUGAAR_INSTALL_PATH", cip);
            cmdLine = cmdLine.replaceAll("COUGAAR_WORKSPACE", ws);

            return cmdLine;
        }

        return null;
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


    /**
     * DOCUMENT ME!
     *
     * @return Returns the testClassName.
     */
    public Class getTestClass() {
        return testClass;
    }


    /**
     * DOCUMENT ME!
     *
     * @param testClassName The testClassName to set.
     */
    public void setTestClass(Class testClassName) {
        this.testClass = testClassName;
    }
}
