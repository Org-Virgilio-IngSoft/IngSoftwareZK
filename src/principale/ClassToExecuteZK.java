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
		
		String pathLogZK = HelpZK.getMyProperty("pathLogFileLinkage");
			
		double l = link.calculateLinkageZK(pathLogZK);
		
		Logger logger=Logger.getLogger("MyLogger");
		logger.log(Level.INFO ,"LINKAGE ZOOKEEPER: {0}", l);
		
		SnoringReleasesZK snor = new SnoringReleasesZK();
		String pathInfoFileProject = HelpZK.getMyProperty("pathInfoFileProject");
		String date = snor.getDateLastReleaseNotSnoring(pathInfoFileProject);
		
		logger.log(Level.INFO ,"BOOKKEEPER: LAST DATE TO CONSIDER {0}", date);
		
		
		logger.log(Level.INFO ,"FINE ClassToExecuteZK!!");

	}

}
