/*
 * <copyright>
 *  Copyright 2000-2003 Cougaar Software, Inc.
 *  All Rights Reserved
 * </copyright>
 */


package org.cougaar.cougaarunit.ant;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.ExecuteWatchdog;
import org.apache.tools.ant.taskdefs.LogStreamHandler;
import org.apache.tools.ant.taskdefs.PumpStreamHandler;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;


/**
 * DOCUMENT ME!
 *
 * @author mabrams
 */
public class CUnitTask extends Task {
    private CommandlineJava cmdl = new CommandlineJava();
    private List filesets;
    private boolean newEnvironment = false;
    private File dir = null;
    private File out;
    private boolean append = false;
    private Environment env = new Environment();
    private Long timeout = null;
    private PrintStream outStream = null;
    private boolean failOnError = true;

    /**
     * Do the execution.
     *
     * @throws BuildException DOCUMENT ME!
     */
    public void execute() throws BuildException {
        File savedDir = dir;

        int err = -1;
        try {
            if ((err = executeJava()) != 0) {
                if (failOnError) {
                    throw new BuildException("Java returned: " + err, location);
                } else {
                    log("Java Result: " + err, Project.MSG_ERR);
                }
            }
        } finally {
            dir = savedDir;
        }
    }


    public int executeJava() throws BuildException {
        String classname = cmdl.getClassname();
        if ((classname == null) && (cmdl.getJar() == null)) {
            throw new BuildException("Classname must not be null.");
        }

        log(cmdl.describeCommand(), Project.MSG_VERBOSE);

        try {
            return run(cmdl.getCommandline());
        } catch (BuildException e) {
            if (failOnError) {
                throw e;
            } else {
                log(e.getMessage(), Project.MSG_ERR);
                return 0;
            }
        } catch (Throwable t) {
            if (failOnError) {
                throw new BuildException(t);
            } else {
                log(t.getMessage(), Project.MSG_ERR);
                return 0;
            }
        }
    }


    /**
     * Sets the Java class to execute.
     *
     * @param s DOCUMENT ME!
     *
     * @throws BuildException DOCUMENT ME!
     */
    public void setTestClassname(String s) throws BuildException {
        if (cmdl.getJar() != null) {
            throw new BuildException("Cannot use 'jar' and 'classname' "
                + "attributes in same command");
        }

        cmdl.setClassname(s);
    }


    public void setTimeout(Long value) {
        timeout = value;
    }


    /**
     * Adds a command-line argument.
     *
     * @return DOCUMENT ME!
     */
    public Commandline.Argument createArg() {
        return cmdl.createArgument();
    }


    /**
     * The working directory of the process
     *
     * @param d DOCUMENT ME!
     */
    public void setDir(File d) {
        this.dir = d;
    }


    /**
     * Set the command line arguments for the JVM.
     *
     * @param s DOCUMENT ME!
     */
    public void setJvmargs(String s) {
        log("The jvmargs attribute is deprecated. "
            + "Please use nested jvmarg elements.", Project.MSG_WARN);
        cmdl.createVmArgument().setLine(s);
    }


    /**
     * Adds a JVM argument.
     *
     * @return DOCUMENT ME!
     */
    public Commandline.Argument createJvmarg() {
        return cmdl.createVmArgument();
    }


    /**
     * Set the command used to start the VM (only if not forking).
     *
     * @param s DOCUMENT ME!
     */
    public void setJvm(String s) {
        cmdl.setVm(s);
    }


    /**
     * Adds a system property.
     *
     * @param sysp DOCUMENT ME!
     */
    public void addSysproperty(Environment.Variable sysp) {
        cmdl.addSysproperty(sysp);
    }


    public void setFailonerror(boolean fail) {
        failOnError = fail;
    }


    /**
     * File the output of the process is redirected to.
     *
     * @param out DOCUMENT ME!
     */
    public void setOutput(File out) {
        this.out = out;
    }


    /**
     * Adds an environment variable.
     * 
     * <p>
     * Will be ignored if we are not forking a new VM.
     * </p>
     *
     * @param var DOCUMENT ME!
     *
     * @since Ant 1.5
     */
    public void addEnv(Environment.Variable var) {
        env.addVariable(var);
    }


    public void setAppend(boolean append) {
        this.append = append;
    }


    /**
     * Pass output sent to System.out to specified output file.
     *
     * @param line DOCUMENT ME!
     */
    protected void handleFlush(String line) {
        if (outStream != null) {
            outStream.print(line);
        } else {
            super.handleFlush(line);
        }
    }


    /**
     * Pass output sent to System.err to specified output file.
     *
     * @param line DOCUMENT ME!
     */
    protected void handleErrorOutput(String line) {
        if (outStream != null) {
            outStream.println(line);
        } else {
            super.handleErrorOutput(line);
        }
    }


    /**
     * Pass output sent to System.err to specified output file.
     *
     * @param line DOCUMENT ME!
     */
    protected void handleErrorFlush(String line) {
        if (outStream != null) {
            outStream.println(line);
        } else {
            super.handleErrorOutput(line);
        }
    }


    /**
     * Set the classpath to be used when running the Java class
     *
     * @param s an Ant Path object containing the classpath.
     */
    public void setClasspath(Path s) {
        createClasspath().append(s);
    }


    /**
     * Adds a path to the classpath.
     *
     * @return DOCUMENT ME!
     */
    public Path createClasspath() {
        return cmdl.createClasspath(project).createPath();
    }


    /**
     * Classpath to use, by reference.
     *
     * @param r DOCUMENT ME!
     */
    public void setClasspathRef(Reference r) {
        createClasspath().setRefid(r);
    }


    public void addFileset(FileSet set) {
        this.filesets.add(set);
    }


    private int run(String[] command) throws BuildException {
        FileOutputStream fos = null;
        try {
            Execute exe = null;
            if (out == null) {				 
                exe = new Execute(new LogStreamHandler(this, Project.MSG_INFO,
                            Project.MSG_WARN), createWatchdog());
            } else {            	
                fos = new FileOutputStream(out.getAbsolutePath(), append);
                exe = new Execute(new PumpStreamHandler(fos), createWatchdog());
            }

            exe.setAntRun(project);

            if (dir == null) {
                dir = project.getBaseDir();
            } else if (!dir.exists() || !dir.isDirectory()) {
                throw new BuildException(dir.getAbsolutePath()
                    + " is not a valid directory", location);
            }

            exe.setWorkingDirectory(dir);

            String[] environment = env.getVariables();
            if (environment != null) {
                for (int i = 0; i < environment.length; i++) {
                    log("Setting environment variable: " + environment[i],
                        Project.MSG_VERBOSE);
                }
            }

            exe.setNewenvironment(newEnvironment);
            exe.setEnvironment(environment);

            exe.setCommandline(command);
            try {
                int rc = exe.execute();
                if (exe.killedProcess()) {
                    log("Timeout: killed the sub-process", Project.MSG_WARN);
                }

                return rc;
            } catch (IOException e) {
                throw new BuildException(e, location);
            }
        } catch (IOException io) {
            throw new BuildException(io, location);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException io) {
                }
            }
        }
    }


    protected ExecuteWatchdog createWatchdog() throws BuildException {
        if (timeout == null) {
            return null;
        }

        return new ExecuteWatchdog(timeout.longValue());
    }
}
