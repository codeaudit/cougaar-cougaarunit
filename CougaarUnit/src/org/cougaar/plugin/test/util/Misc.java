package org.cougaar.plugin.test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Vector;
import java.util.Enumeration;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class Misc {

  public static String getEnv(String str) {
    String ret= "";
    //get the os version
    String os = System.getProperty("os.name");
    String fileName;
    String echoCommand;
    String execCommand;
    if (os.toLowerCase().indexOf("windows") != -1) {
      fileName = "env.bat";
      echoCommand = "@echo %"+str+"%\n";
      execCommand = fileName;
    }
    else if (os.toLowerCase().indexOf("linux") != -1) {
      fileName = "env.sh";
      echoCommand = "echo $"+str+"\n";
      execCommand="./"+fileName;
    }
    else
      return "";

    File f = new File(fileName);
    try {
      FileWriter fw = new FileWriter(f);
      fw.write(echoCommand);
      fw.flush();
      fw.close();
      if(os.toLowerCase().indexOf("linux") != -1) {
        Runtime.getRuntime().exec("chmod +x "+ fileName);
      }
      Process p = Runtime.getRuntime().exec(execCommand);
      BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line;
      while ((line = br.readLine()) != null) {
        ret += line;
      }
    }
    catch (Exception e){}
    return ret;
  }

  public static void main(String[] args) {
    //System.out.println("TEST: " + Misc.getEnv("COUGAAR_INSTALL_PATH"));
    Vector v = Misc.listFilesRecursively(new File("c:\\home"), new FileFilter() {
      public boolean accept(File pathName) {
        return true;
      }
    });
    for (Enumeration e = v.elements(); e.hasMoreElements(); ) {
      System.out.println(e.nextElement());
    }
  }

  private static FileFilter dirFilter = new FileFilter() {
    public boolean accept(File pathName) {
      if (pathName.isDirectory()) return true;
      return false;
    }
  };

  public static Vector listFilesRecursively(File dir, FileFilter filter) {
    Vector v = new Vector();

    //get the files from the current directory
    File[] files = dir.listFiles(filter);
    for (int i=0; i<files.length; i++) {
      v.addElement(files[i]);
    }

    //get the subdirectories and search them recursively
    File[] dirs = dir.listFiles(dirFilter);
    for (int i=0; i<dirs.length; i++) {
      Vector vf = listFilesRecursively(dirs[i], filter);
      v.addAll(vf);
    }

    return v;
  }
}