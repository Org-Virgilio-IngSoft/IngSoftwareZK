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
		LinkageZK link = new LinkageZK();
		HelpZK help = new HelpZK();
		
		
		String pathLogZK = help.getMyProperty("pathLogFileLinkage");
			
		double l = link.calculateLinkageZK(pathLogZK);
		
		Logger logger=Logger.getLogger("MyLogger");
		logger.log(Level.INFO ,"LINKAGE ZOOKEEPER: {0}", l);

	}

}
