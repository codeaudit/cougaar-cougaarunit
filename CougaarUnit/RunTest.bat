@echo off
if "%COUGAAR_INSTALL_PATH%"=="" goto AIP_ERROR
if "%1"=="" goto ARG_ERROR
if "%2"=="" goto ARG2_ERROR

echo on
java -classpath CougaarUnit.jar;%COUGAAR_INSTALL_PATH%\lib\planning.jar;%COUGAAR_INSTALL_PATH%\lib\core.jar;%COUGAAR_INSTALL_PATH%\lib\util.jar;%2 org.cougaar.plugin.test.Launcher %1 %2
@goto QUIT

:AIP_ERROR
echo Please set the COUGAAR_INSTALL_PATH
goto QUIT

:ARG_ERROR
echo RunTest requires the fully qualified name of a test class to run
echo e.g., tutorial.DevelopmentExpanderTestCase
goto QUIT

:ARG2_ERROR
echo Specify the classpath that where the test is located
goto QUIT

:QUIT