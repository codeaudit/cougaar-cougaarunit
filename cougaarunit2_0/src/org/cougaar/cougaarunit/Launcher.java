package org.cougaar.cougaarunit;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	private Class testClass;
    
    public Launcher (Class testClassName) {
    	this.testClass = testClassName;
    }
   
	/**
	 * Here is where we launch the cougaar environment once the node and
	 * agent ini files have been configured.  This method presumes that
	 */
	private int launchCougaar(OutputStream os) throws Exception {
		int retCode = 0;
		if (os == null) os = System.out;

		String execStr = createTestSociety();

		System.out.println("execing: " + execStr);
		Process p = Runtime.getRuntime().exec(execStr);
		System.out.println("Process:  "+ p);

		//BufferedWriter ps = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		BufferedReader is= new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader es = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String line = null;
		String line2 = null;
		while (((line = is.readLine()) != null) ||  ((line2 = es.readLine()) != null)) {
			if (line != null) {
				line += "\n";
				byte[] lineData = line.getBytes();
				os.write(lineData);
			}
			if (line2 != null) {
				byte[] lineData = line2.getBytes();
				os.write(lineData);
			}
			//os.write('\n');
		}
		System.out.println("exiting Cougaar system...");
		System.out.flush();
		retCode = p.waitFor();   //wait for this process to terminate

		return retCode;
	}
   
   
    private void createLogProps() throws Exception {
        File logPropsFile;
        logPropsFile = new File("log.properties");
        logPropsFile.createNewFile();
        FileWriter fw = new FileWriter(logPropsFile);
        fw.write("log4j.rootCategory=INFO,A1\n"//,A2\n"
            + "log4j.appender.A1=org.apache.log4j.ConsoleAppender\n"
            + "log4j.appender.A1.layout=org.apache.log4j.PatternLayout\n"
            + "log4j.appender.A1.layout.ConversionPattern=%m%n\n");
            //+ "log4j.appender.A2=org.cougaar.plugin.test.ErrorDetectionAppender");
        fw.close();
    }


    public static void main(String[] args) {
        if (args.length > 0) {
            try {
            	
                //get class name
                Class _class = Class.forName(args[0]);
				int testType = getTestType(_class.getSuperclass());
                
                if (testType < 0) {
                    System.err.println("Unregonized test type");
                } else if (testType == TEST_CASE_TYPE) {
					Launcher launcher = new Launcher(_class);
					launcher.createLogProps();
                    launcher.launchCougaar(System.out);
                } else if (testType == TEST_SUITE_TYPE) {
                    //process test suite
					PluginTestSuite testSuite = (PluginTestSuite)_class.newInstance();
					Class[] _classes = testSuite.getTestClasses();
					if(_classes!=null && _classes.length>0){
						for(int i=0;i<_classes.length;i++){
							Launcher launcher  = new Launcher(_classes[i]);		
							launcher.createLogProps();
							launcher.launchCougaar(System.out);
							
						}
					}else{
						System.err.println("Test suite with no test cases");
					}
                }
            } catch (Exception ex) {
                System.out.println("Error running test: " + ex);
            }
        } else {
            System.out.println("Missing required argument: Test classname");
        }
    }


    private String createTestSociety() throws Exception{
    	
        Society testSociety = SocietyBuilder.buildSociety((PluginTestCase)this.testClass.newInstance());
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
                + "-classpath " + System.getProperty("org.cougaar.install.path")
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
	 * @return Returns the testClassName.
	 */
	public Class getTestClass() {
		return testClass;
	}
	/**
	 * @param testClassName The testClassName to set.
	 */
	public void setTestClass(Class testClassName) {
		this.testClass = testClassName;
	}
}
