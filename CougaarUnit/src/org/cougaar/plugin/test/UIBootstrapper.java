package org.cougaar.plugin.test;

import java.io.File;
import java.io.FilenameFilter;
import org.cougaar.plugin.test.util.Misc;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.borland.jbcl.control.MessageDialog;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class UIBootstrapper {
  static String csep = System.getProperty("path.separator");
  static String fsep = System.getProperty("file.separator");

  public static void main(String[] args) {
    //build the command string
    String cmdStr = "java -classpath "+getClassPath()+" org.cougaar.plugin.test.UI";
    System.out.println(cmdStr);
    try {
      byte[] buffer = new byte[1024];
      Process p = Runtime.getRuntime().exec(cmdStr);
      BufferedReader is= new BufferedReader(new InputStreamReader(p.getInputStream()));
      BufferedReader es = new BufferedReader(new InputStreamReader(p.getErrorStream()));
      String line = null;
      String line2 = null;
      while (((line = is.readLine()) != null) ||  ((line2 = es.readLine()) != null)) {
        if (line != null) {
          line += "\n";
          System.out.println(line);
        }
        if (line2 != null) {
          System.out.println(line2);
        }
        //os.write('\n');
      }
      //p.waitFor();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

   static String getClassPath() {
     String cip = Misc.getEnv("COUGAAR_INSTALL_PATH");
     if ((cip == null) || (cip.equals(""))) {
       MessageDialog md = new MessageDialog(null, "Error", "The COUGAAR_INSTALL_PATH is not set");
       md.show();
       System.exit(1);
     }
     String cp = buildClassPath(cip+fsep+"lib");
     cp += buildClassPath(cip+fsep+"sys");
     return cp;

   }

   static String buildClassPath(String dir) {
     String ret = "";
     try {
       File f = new File(dir);
       String[] files = f.list(new FilenameFilter() {
                               public boolean accept(File dir, String name) {
                               if (name.toLowerCase().endsWith(".jar")) {
                                 return true;
                               }
                               return false;
                               }
       });
       for (int i=0; i<files.length; i++ ) {
         ret += dir+fsep+files[i]+csep;
       }
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return ret;

  }
}