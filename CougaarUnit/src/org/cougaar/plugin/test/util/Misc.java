package org.cougaar.plugin.test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
    String s = System.getProperty("os.name");
    String fileName;
    if (s.toLowerCase().indexOf("windows") != -1) {
      fileName = "env.bat";
    }
    else if (s.toLowerCase().indexOf("linux") != -1) {
      fileName = "env.sh";
    }
    else
      return "";

      File f = new File("env.bat");
    try {
      FileWriter fw = new FileWriter(f);
      fw.write("@echo %"+str+"%\n");
      fw.flush();
      fw.close();
      Process p = Runtime.getRuntime().exec("env.bat");
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
    System.out.println("TEST: " + Misc.getEnv("COUGAAR_INSTALL_PATH"));
  }
}