/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logging;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*
 * Этот класс демонстрирует работу с логгером log4j
 */

public class log4jExample{
  static Logger log = Logger.getLogger(log4jExample.class);
 
  public  static void main(String[] args){
      
       /*  String workingDir = System.getProperty("user.dir");
                      System.out.println("Current working directory : " + workingDir); 
        */
      
      
      
    /*  File file = new File("/log.out");
 
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
      
      */
        
   
        try {
           
              
           String log4JPropertyFile = "/resources/log4j.properties";
           Properties p = new Properties();         
                String workingDir = System.getProperty("user.dir");
                System.out.println("Current working directory : " + workingDir);
           p.load(log4jExample.class.getResourceAsStream(log4JPropertyFile));              
           PropertyConfigurator.configure(p);
           log.info("Wow! I'm configured!");
 } catch (IOException ex) {
           System.out.println("XYU "+ex.getMessage());
 }
         

                // log.("Trace Message!");
                 log.debug("Debug Message!");
                 log.info("Info Message!");
                 log.warn("Warn Message!");
                 log.error("Error Message!");
                 log.fatal("Fatal Message!");
       
  }
}