package org.cougaar.cougaarunit;

import org.cougaar.core.plugin.ComponentPlugin;
import org.cougaar.core.service.LoggingService;

import org.cougaar.cougaarunit.vo.results.TestResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import java.util.Collection;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * <p>Title: PluginTestCase</p>
 * <p>Description: Parent class for all implemented test cases.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
public abstract class PluginTestCase extends ComponentPlugin {
    public static final String RESULTS_DIRECTORY = "test_results";
    private String description;
    protected LoggingService logging;
    protected boolean started;

    /**
     * keep a static reference to the latest instance of this class
     */
    public PluginTestCase() {
        this.description = this.getClass().getName(); //initialize the description to the class name
        PluginTestResult.setTestName(this.getClass().getName());
    }

    public synchronized void setStarted(boolean b) {
        this.started = b;
    }

    public synchronized boolean isStarted() {
        return this.started;
    }

    /**
     * Set LoggingService
     * @param s
     */
    public void setLoggingService(LoggingService s) {
        this.logging = s;
    }

    public void load() {
        super.load();

        //this is here b/c for some reason sometimes setLoggingService() does not function?
        logging = (LoggingService) this.getServiceBroker().getService(this,
                LoggingService.class, null);
    }

    /**
     * Set a textual description for this test case
     * @param s description
     */
    public void setDescription(String s) {
        this.description = s;
    }

    /**
     * Get the description for this test case
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Used for testing subscriptions
     * Will not affect the state of the blackboard
     * @paran obj - The object to publish to the blackboard
     * @param expectedResult - Indicates whether this test is expected to pass or fail
              true - pass
              false - fail
              */
    public void subscriptionAssert(Object obj, boolean expectedResult) {
        boolean result = ((BlackboardServiceProxy) blackboard).testSubscriptions(obj);
        PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_SUBSCRIPTION,
            PluginTestResult.COMMAND_SUBSCRIPTION_ASSERT,
            !(result ^ expectedResult), obj.getClass().getName());
    }

    /**
     * This method can be used to test that a source plugin's initial set of publications
     * is what is expected.
     * @param bbs The BlackBoardDeltaState object that defines the initial set of publications
     * by the source plugin
     * @param waitTime length of time to wait before testing the inital state
     * @param expectedResult Indicates whether this test is expected to pass or fail
     */
    public void assertInitialState(BlackboardDeltaState bds, long waitTime,
        boolean expectedResult, boolean isOrdered) {
        if (bds != null) {
            try {
                Thread.currentThread().sleep((waitTime > 0) ? waitTime : 0); //wait the designated amount of time

                BlackboardDeltaState currentBDS = ((BlackboardServiceProxy) blackboard).getCurrentBlackboardDeltaState();
                boolean result = bds.compare(currentBDS, isOrdered);
                PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_EXECUTION,
                    PluginTestResult.INITIAL_STATE, !(result ^ expectedResult),
                    "Initial State", bds, currentBDS);
                ((BlackboardServiceProxy) blackboard).resetBlackboardDeltaState(); //reset the blackboard state
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Publishes obj to the blackboard and blocks waitTime milliseconds until the bbs is achieved.
     * A timeout indicates an error.
     * @param obj - The object to publish to the blackbaord
     * @param bbs -The blackboard state to be achieved.  If this value is null then no state will be tested.  Passing
     * null for this parameter can be useful if the test requires making several publications to the blackboard
     * before checking the blackboard state.
     * @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.
     *         -1 - wait indefinitely (not recommended)
              0 - do no waut
              >0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
              * @param expectedResult - Indicates whether this test is expected to pass or fail
              true - pass
              false - fail
              * @param expectedResult  what the expected outcome should be
              * @param ordered whether the comparison of the BlackboardDeltaState object should be order dependednt
              */
    public void assertPublishAdd(Object obj, BlackboardDeltaState bds,
        long waitTime, boolean expectedResult, boolean ordered) {
        blackboard.openTransaction();
        ((BlackboardServiceProxy) blackboard).publishAdd(obj, true);
        blackboard.closeTransaction();

        if (bds != null) {
            try {
                Thread.currentThread().sleep((waitTime > 0) ? waitTime : 0); //wait the designated amount of time

                BlackboardDeltaState currentBDS = ((BlackboardServiceProxy) blackboard).getCurrentBlackboardDeltaState();
                boolean result = bds.compare(currentBDS, ordered);
                PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_EXECUTION,
                    PluginTestResult.COMMAND_ASSERT_PUBLISH_ADD,
                    !(result ^ expectedResult), obj.getClass().getName(), bds,
                    currentBDS);
                ((BlackboardServiceProxy) blackboard).resetBlackboardDeltaState(); //reset the blackboard state
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Helper method for asserting objects that don't require any state evaluation
     * @param obj
     */
    public void assertPublishAdd(Object obj) {
        assertPublishAdd(obj, null, 0, false, false);
    }

    /**
     * Publishes an object change  to the blackboard and blocks waitTime milliseconds until the bbs is achieved.
     * A timeout indicates an error.
     * @param obj - The changed object to publish to the blackbaord
     * @param bbs -The blackboard state to be achieved,  If this value is null then no state will be tested.  Passing
     * null for this parameter can be useful if the test requires making several publications to the blackboard
     * before checking the blackboard state.
     * @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.
     *         -1 - wait indefinitely (not recommended)
              0 - do no waut
              >0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
              * @param expectedResult - Indicates whether this test is expected to pass or fail
              true - pass
              false - fail
              * @param expectedResult  what the expected outcome should be
              * @param ordered whether the comparison of the BlackboardDeltaState object should be order dependednt

              */
    public void assertPublishChange(Object obj, BlackboardDeltaState bds,
        long waitTime, boolean expectedResult, boolean ordered) {
        blackboard.openTransaction();
        ((BlackboardServiceProxy) blackboard).publishChange(obj, true);
        blackboard.closeTransaction();

        if (bds != null) {
            try {
                Thread.currentThread().sleep((waitTime > 0) ? waitTime : 0); //wait the designated amount of time

                BlackboardDeltaState currentBDS = ((BlackboardServiceProxy) blackboard).getCurrentBlackboardDeltaState();
                boolean result = bds.compare(currentBDS, ordered);
                PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_EXECUTION,
                    PluginTestResult.COMMAND_ASSERT_PUBLISH_CHANGE, result,
                    obj.getClass().getName(), bds, currentBDS);
                ((BlackboardServiceProxy) blackboard).resetBlackboardDeltaState(); //reset the blackboard state
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Helper method for asserting objects that don't require any state evaluation
     * @param obj
     */
    public void assertPublishChange(Object obj) {
        assertPublishChange(obj, null, 0, false, false);
    }

    /**
     * Removes obj from the blackboard and blocks waitTime milliseconds until the bbs is achieved.
     * A timeout indicates an error.
     * @param obj - The object to remove from the blackbaord
     * @param bbs -The blackboard state to be achieved.  If this value is null then no state will be tested.  Passing
     * null for this parameter can be useful if the test requires making several publications to the blackboard
     * before checking the blackboard state.
     * @param waitTime - The number of milliseconds to wait until the blackbaord state is achieved.
     *         -1 - wait indefinitely (not recommended)
              0 - do no waut
              >0 - waits this number if milliseconds - a timeout indicates a failure to achieve the state
              * @param expectedResult - Indicates whether this test is expected to pass or fail
              true - pass
              false - fail
              * @param expectedResult  what the expected outcome should be
              * @param ordered whether the comparison of the BlackboardDeltaState object should be order dependednt
              */
    public void assertPublishRemove(Object obj, BlackboardDeltaState bds,
        long waitTime, boolean expectedResult, boolean ordered) {
        blackboard.openTransaction();
        ((BlackboardServiceProxy) blackboard).publishRemove(obj, true);
        blackboard.closeTransaction();

        if (bds != null) {
            try {
                Thread.currentThread().sleep((waitTime > 0) ? waitTime : 0); //wait the designated amount of time

                BlackboardDeltaState currentBDS = ((BlackboardServiceProxy) blackboard).getCurrentBlackboardDeltaState();
                boolean result = bds.compare(currentBDS, ordered);
                PluginTestResult.addEntry(PluginTestResult.PHASE_TEST_EXECUTION,
                    PluginTestResult.COMMAND_ASSERT_PUBLISH_REMOVE,
                    !(result ^ expectedResult), obj.getClass().getName(), bds,
                    currentBDS);
                ((BlackboardServiceProxy) blackboard).resetBlackboardDeltaState(); //reset the blackboard state
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Helper method for asserting objects that don't require any state evaluation
     * @param obj
     */
    public void assertPublishRemove(Object obj) {
        assertPublishRemove(obj, null, 0, false, false);
    }

    /**
     * reset the state of the BlackboardServiceProxy.  This clears whatever PublishAction objects it currently
     * is tracking.
     */
    public void resetBlackboardDeltaState() {
        ((BlackboardServiceProxy) this.blackboard).resetBlackboardDeltaState();
    }

    /**
     * Subclasses use this method to implement assertions to test the subscriptions of the target plugin
     */
    abstract public void validateSubscriptions();

    /**
     * Subclasses use this method to implement assertions to test the blackboard state during execution
     */
    abstract public void validateExecution();

    /**
     * Subclasses may override this if the source plugin requires parameters
     * @return
     */
    public String[] getPluginParameters() {
        return new String[] {  };
    }

    /**
     * Subclasses may override this to provide a different identifier
     * for the agent
     * @return
     */
    public String getAgentId() {
        return "TestAgent"; //default identifier
    }

    /**
     *Get the plugin class to be tested
     * @return
     */
    abstract public String getPluginClass();

    /**
     * Get any domains to be loaded
     * @return Collection of Component Value Objects
     */
    protected Collection getDomains() {
        return null;
    }
    
    /**
     * get any service components to add to the node
     * @return a collection of Component Value objects
     */
    protected Collection getServiceComponents() {
        return null;
    }

    /**
     * Initiates the tests by calling the validateSubscritpions() and validateExecution()
     * methods.  These methods are called via a separate thread.
     */
    public void startTests() {
        if (logging.isInfoEnabled()) {
            logging.info("STARTING TESTS...");
        }
       
        
        //determine the report format
        Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.currentThread().sleep(5000);
                        } catch (Exception e) {
                        }
                         //for now we add a delay to let the source plugin start

                        try {
                            if (logging.isInfoEnabled()) {
                                logging.info("VALIDATING SUBSCRIPTIONS...");
                            }

                            validateSubscriptions();

                            if (logging.isInfoEnabled()) {
                                logging.info("VALIDATING EXECUTION...");
                            }

                            validateExecution();

                            if (logging.isInfoEnabled()) {
                                logging.info("PROCESSING RESULTS...");
                            }

                            String reportFormat = System.getProperty(
                                    "org.cougaar.plugin.test.output.format");
                            String reportResult = "";

                            //make results directory if not present
                            try {
                                File dir = new File(RESULTS_DIRECTORY);

                                if (dir.exists() == false) {
                                    dir.mkdir();
                                }
                            } catch (Exception e) {
                                if (logging.isErrorEnabled()) {
                                    logging.error("Error creating results directory",
                                        e);
                                }
                            }

                            if (reportFormat == null) {
                                reportFormat = "xml";
                            }

                            if (reportFormat.equals("text")) {
                                reportResult = (PluginTestResult.getReportAsString()); //print the test results to stdout

                                try {
                                    File txtFile = new File(RESULTS_DIRECTORY +
                                            File.separator + description +
                                            ".txt");

                                    if (logging.isInfoEnabled()) {
                                        logging.info("Writing results to :" +
                                            txtFile.getAbsolutePath());
                                    }

                                    BufferedWriter writer = new BufferedWriter(new FileWriter(
                                                txtFile));
                                    writer.write(reportResult);
                                    writer.close();
                                } catch (Exception e) {
                                    if (logging.isErrorEnabled()) {
                                        logging.error("Error writing text file",
                                            e);
                                    }
                                }
                            } else if (reportFormat.equals("xml")) {
                                try {
                                    File xmlFile = new File(RESULTS_DIRECTORY +
                                            File.separator + description +
                                            ".xml");
                                    String location = xmlFile.getAbsolutePath();
                                    String htmlLocation = new File(RESULTS_DIRECTORY +
                                            File.separator + description + ".html").getAbsolutePath();
                                    if (logging.isInfoEnabled()) {
                                        logging.info("Writing results to :" +
                                            location);
                                    }

                                    TestResult result = new TestResult();
                                    result.populate();
                                    result.save(location);

                                    //transfrom
                                    try {
                                        TransformerFactory tFactory = TransformerFactory.newInstance();
                                        Transformer transformer = tFactory.newTransformer(new StreamSource(
                                                    "cougaar_unit_results.xsl"));
                                        File f = new File(htmlLocation);
                                        FileOutputStream fos = new FileOutputStream(f);
                                        transformer.transform(new StreamSource(location),
                                            new StreamResult(new OutputStreamWriter(
                                                    fos)));
                                    } catch (Exception e) {
                                    	if(logging.isErrorEnabled()){
                                    		logging.error("Error writing html file",e);
                                    	}
                                    }
                                } catch (Exception e) {
                                    if (logging.isErrorEnabled()) {
                                        logging.error("Error writing xml file",
                                            e);
                                    }
                                }
                            } else {
                                reportResult = (PluginTestResult.getReportAsString()); //print the test results to stdout
                            }

                            if (logging.isInfoEnabled()) {
                                //logging.info(reportResult);
                            }
                        } catch (Error err) {
                            err.printStackTrace();
                        } catch (RuntimeException rte) {
                            rte.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.exit((PluginTestResult.getOverallResult()) ? 0 : 1); //exit code = 0 if all tests passed, otherwise 1
                    }
                });
        if(isStarted()==false){
        	t.start();
        }
    }

    /**
     * ComponentPlugin method.
     * Since the subclasses of PluginTestCase don't need to use this method,
     * it's implemented here adn does nothing rather than making it abstract.
     */
    protected void setupSubscriptions() {
    }

    /**
     * ComponentPlugin method.
     * Since the subclasses of PluginTestCase don't need to use this method,
     * it's implemented here adn does nothing rather than making it abstract.
     */
    protected void execute() {
    }
}
