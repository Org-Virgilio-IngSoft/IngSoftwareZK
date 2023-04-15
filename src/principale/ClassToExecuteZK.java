/**
 * 
 */
package principale;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.HelpZK;
import proportion.ProportionZK;

/**
 * @author Virgilio
 *
 */
public class ClassToExecuteZK {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		Logger logger=Logger.getLogger("MyLogger");	
		 
		String pathLogBK = HelpZK.getMyProperty("pathLogFileLinkage");			
		double linkage = LinkageZK.calculateLinkageZK(pathLogBK);
			
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: {0}", linkage);
		
		ReleasesZK release = new ReleasesZK();
		String pathFileTicketsWithAffectedVersions = HelpZK.getMyProperty("pathFileTicketsWithAffectedVersions");
		String pathTicketsIDwithAffectedVersionAndIDversionBK = HelpZK.getMyProperty("pathTicketsIDwithAffectedVersionAndIDversionBK");
		release.findAffectedVersionsIndex(pathFileTicketsWithAffectedVersions);
		release.findInjectedVersions(pathTicketsIDwithAffectedVersionAndIDversionBK);
		
        String pathTicketsBugWithFVOVdatesBK = HelpZK.getMyProperty("pathTicketsBugWithFVOVdates");
        release.findFixVersionsOpenVersionsIndex(pathTicketsBugWithFVOVdatesBK);
		
		ProportionZK proportion = new ProportionZK();		
		proportion.calcolaProportionTicketsWithIV();
		double pMedio = proportion.calculatePmedio();
		proportion.ristimaDiNuovoInjectedVersions(pMedio);
		
		logger.log(Level.INFO ,"FINE ClassToExecuteBK!!");
			
	}//fine main

}
