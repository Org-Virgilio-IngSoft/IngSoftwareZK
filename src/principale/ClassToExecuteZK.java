/**
 * 
 */
package principale;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.HelpZK;

/**
 * @author Virgilio
 *
 */
public class ClassToExecuteZK {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String pathLogZK = HelpZK.getMyProperty("pathLogFileLinkage");		
		double linkage = LinkageZK.calculateLinkageZK(pathLogZK);
		
		Logger logger=Logger.getLogger("MyLogger");
		logger.log(Level.INFO ,"LINKAGE ZOOKEEPER: {0}", linkage);
				
		
		
		
		
		logger.log(Level.INFO ,"FINE ClassToExecuteZK!!");

	}

}
