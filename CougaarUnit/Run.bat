@echo OFF
if "%COUGAAR_INSTALL_PATH%"=="" goto AIP_ERROR
if "%1"=="" goto ARG_ERROR
set LIBPATHS=%COUGAAR_INSTALL_PATH%\lib\bootstrap.jar
set MYPROPERTIES= -Dorg.cougaar.class.path=%COUGAAR_INSTALL_PATH%\lib\test\AAACougaarUnit.jar -Dorg.cougaar.system.path=%COUGAAR_INSTALL_PATH%\sys -Dorg.cougaar.install.path=%COUGAAR_INSTALL_PATH% -Dorg.cougaar.core.servlet.enable=true -Dorg.cougaar.lib.web.scanRange=100 -Dorg.cougaar.lib.web.http.port=8800 -Dorg.cougaar.lib.web.https.port=-1 -Dorg.cougaar.lib.web.https.clientAuth=true -Xbootclasspath/p:%COUGAAR_INSTALL_PATH%\lib\javaiopatch.jar
set MYMEMORY=
set MYCLASSES=org.cougaar.bootstrap.Bootstrapper org.cougaar.core.node.Node
set MYARGUMENTS= -c -n "%1"
@ECHO ON
java.exe %MYPROPERTIES% %MYMEMORY% -classpath %LIBPATHS% %MYCLASSES% %MYARGUMENTS% %2 %3
goto QUIT
:AIP_ERROR
echo Please set COUGAAR_INSTALL_PATH
goto QUIT
:ARG_ERROR
echo Run requires an argument  eg: Run ExerciseOneNode
goto QUIT
:QUIT
