/**
 * 
 */
package weka;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.HelpZK;

/**
 * @author Virgilio
 *
 */
public class CreateArffFileZK {

	public static void createArffFile(String nameArffFile) throws IOException {
		
		Logger logger=Logger.getLogger("MyLogger");	
		
		String pathFolder=HelpZK.getMyProperty("pathArffFolder");
		String ext=".arff";
		
	    File myFile = new File(pathFolder+nameArffFile+ext);
          if (myFile.createNewFile()) {
  	         logger.log(Level.INFO ,"File creato ok: {0}", myFile.getName());        
          } else {
  	         logger.log(Level.INFO ,"File già esiste: {0}", myFile.getName());	       
            
          }
    
  }//fine metodo
	

	
	private CreateArffFileZK() {
		
	}
	
}
