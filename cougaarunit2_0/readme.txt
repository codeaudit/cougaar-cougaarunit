This release of Cougaar Unit is an update of version 1.1 to comply with the latest version of Cougaar (10.4.6 as of this writing).  

This release does not contain any of the GUI components present within the 1.1 version.  

Changes from version 1.1
	- Use of Castor for writing and reading XML file results
	- Use of XSL style sheets to create HTML output of tests
	- Replaced the Object Comparator framework with a more extensible object comparator design.
	- The Launcher returns 0 for successfull test and 1 for unsuccessfull test.
	- The Launcher will run and compile the test results of all test cases within a test suite.
	- Cougaar Unit Ant task to execute a Plugin Test Case or Plugin Test Suite
	
 
 Release Structure:
 src:  Contains all of the source code for cougaar unit.
 test/cunit: Contains source code for sample cougaar unit tests.
 test/junit: Contains source code for junit tests.
 lib: Contains the required 3rd party libraries.
 build.xml: The ant build file.
 configs: Contains the configuration files used by cougaar unit.
 docs: Contains documentation and tutorials.
 build/jars: Contains the cougaar unit jar file.
 
 More comprehensive documentation for version 1.1 is avialable here (http://cougaar.org/project/showfiles.php?group_id=12)