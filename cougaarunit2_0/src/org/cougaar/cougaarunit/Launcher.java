package org.cougaar.cougaarunit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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
	private static final int TEST_SUITE_TYPE=1;
	private static final int TEST_CASE_TYPE=2;
	
    private static final int PLATFORM_WINDOWS = 0;
    private static final int PLATFORM_LINUX = 1;
    private static final int PLATFORM_OTHER = 2;
    private static int currentOS;

    
    
    public static final int OUTPUT_STYLE_TEXT = 0;
    public static final int OUTPUT_STYLE_XML = 1;
    private static final String[] OUTPUT_STYLES = {"text", "xml"};
    private int outputStyle = OUTPUT_STYLE_XML;


    private String testJarFile="";

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



    /**
     * Here is where we launch the cougaar environment once the node and
     * agent ini files have been configured.  This method presumes that
     */
    private int launchCougaar(OutputStream os) throws Exception {
        int retCode = 0;
        if (os == null) os = System.out;

        String execStr = createRunFile();

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

        logPropsFile = new File("logging.props");
        logPropsFile.createNewFile();
        FileWriter fw = new FileWriter(logPropsFile);
        fw.write("log4j.rootCategory=INFO,A1,A2\n"+
                 "log4j.appender.A1=org.apache.log4j.ConsoleAppender\n"+
                 "log4j.appender.A1.layout=org.apache.log4j.PatternLayout\n"+
                 "log4j.appender.A1.layout.ConversionPattern=%m%n\n"+
                 "log4j.appender.A2=org.cougaar.plugin.test.ErrorDetectionAppender");
        fw.close();
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
                            "set MYPROPERTIES= -Dorg.cougaar.class.path=\""+testJarFile+"\" "+
                            "-Dorg.cougaar.system.path=%COUGAAR_INSTALL_PATH%\\sys "+
                            "-Dorg.cougaar.install.path=%COUGAAR_INSTALL_PATH% "+
                            "-Dorg.cougaar.core.servlet.enable=true "+
                            "-Dorg.cougaar.lib.web.scanRange=100 "+
                            "-Dorg.cougaar.lib.web.http.port=8800 "+
                            "-Dorg.cougaar.lib.web.https.port=-1 "+
                            "-Dorg.cougaar.plugin.test.output.format="+OUTPUT_STYLES[outputStyle]+" "+
                            "-Dorg.cougaar.lib.web.https.clientAuth=true "+
                            "-Dorg.cougaar.core.logging.config.filename=logging.props "+
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
                            "MYPROPERTIES=\"-Dorg.cougaar.class.path=\""+testJarFile+"\" "+
                            "-Dorg.cougaar.system.path=$COUGAAR_INSTALL_PATH/sys "+
                            "-Dorg.cougaar.install.path=$COUGAAR_INSTALL_PATH "+
                            "-Dorg.cougaar.core.servlet.enable=true "+
                            "-Dorg.cougaar.lib.web.scanRange=100 "+
                            "-Dorg.cougaar.lib.web.http.port=8800 "+
                            "-Dorg.cougaar.lib.web.https.port=-1 "+
                            "-Dorg.cougaar.lib.web.https.clientAuth=true "+
                            "-Dorg.cougaar.plugin.test.output.format="+OUTPUT_STYLES[outputStyle]+" "+
                            "-Dorg.cougaar.core.logging.config.filename=log.props "+
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
        if (args.length>0) {
            Launcher launcher = new Launcher();
            try {
            	//get class name
            	Class _class = Class.forName(args[0]);
            	int testType = getTestType(_class.getSuperclass());
            	if(testType<0){
            		System.err.println("Unregonized test type");
            	}else if(testType==TEST_CASE_TYPE){
            		//process test case
            	}else if(testType == TEST_SUITE_TYPE){
            		//process test suite
            	}
               
            }
            catch (Exception ex) {
                System.out.println("Error running test: " + ex);
            }
        }else{
        	System.out.println("Missing required argument: Test classname");
        }
    }
    /**
     * Get the type of test (test suite or test case)
     * @param _class
     * @return
     */
    private static int getTestType(Class _class){
    	
    	if(_class.equals(PluginTestCase.class)){
    		return TEST_CASE_TYPE;
    	}else if(_class.equals(PluginTestSuite.class)){
    		return TEST_SUITE_TYPE;
    	}else{
    		if(_class.getSuperclass()==null){
    			return -1;
    		}else{
    			return getTestType(_class.getSuperclass());
    		}
    	}
    }
    
/*
    protected int runTestSuite(PluginTestSuite pts, OutputStream ps) throws Exception {
        Class[] testClasses = pts.getTestClasses();
        int cumulativeRet = 0;
        for (int i=0; i<testClasses.length; i++) {
            try {
                if (testClasses[i].getSuperclass().equals(PluginTestCase.class)) {
                    cumulativeRet += runTestCase((PluginTestCase)testClasses[i].newInstance(), ps);
                }
                else {
                    System.out.println("Invalid Test Case object found in Test Suite.");
                }
            }
            catch (Exception ex) {
                System.out.println("Invalid test class object.");
            }
        }
        return cumulativeRet;
    }


    protected int runTestCase(PluginTestCase tpc, OutputStream ps) throws Exception {
        String sourcePluginStr = tpc.getPluginClass();

        //now we need to generate the ini files for launching cougaar
        writeNodeIni(tpc.getAgentId());
        //now we write the agent ini file
        writeAgentIni(tpc.getClass().getName(), sourcePluginStr, tpc.getAgentId(), tpc.getPluginParameters());
        //now write the logging.props file
        createLogProps();
        //launch Cougaar
        return launchCougaar(ps);
    }

    public void setOutputStyle(int i) {
        if (i < OUTPUT_STYLES.length) {
            outputStyle = i;
        }
    }
*/
    public void setTestJarFile(String path) {
        this.testJarFile = path;
    }
}