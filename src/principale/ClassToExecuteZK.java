/**
 * 
 */
package principale;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.HelpMathZK;
import helper.HelpZK;
import metrics.NAUTHmetricZK;
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
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, ParseException, SQLException, InterruptedException {
		Logger logger=Logger.getLogger("MyLogger");	
		 
		/*String pathLogBK = HelpZK.getMyProperty("pathLogFileLinkage");			
		double linkage = LinkageZK.calculateLinkageZK(pathLogBK);
			
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: {0}", linkage);*/
		
	    //ReleasesZK release = new ReleasesZK();
		//String pathFileTicketsWithAffectedVersions = HelpZK.getMyProperty("pathFileTicketsWithAffectedVersions");
	    //String pathTicketsIDwithAffectedVersionAndIDversionBK = HelpZK.getMyProperty("pathTicketsIDwithAffectedVersionAndIDversionZK");
		//release.findAffectedVersionsIndex(pathFileTicketsWithAffectedVersions);
	    //release.findInjectedVersions(pathTicketsIDwithAffectedVersionAndIDversionBK);
		
        //String pathTicketsBugWithFVOVdatesBK = HelpZK.getMyProperty("pathTicketsBugWithFVOVdates");
       // release.findFixVersionsOpenVersionsIndex(pathTicketsBugWithFVOVdatesBK);
		
		//ProportionZK proportion = new ProportionZK();		
		//proportion.calcolaProportionTicketsWithIV();
		///double pMedio = proportion.calculatePmedio();
		///System.out.println("pMedio "+pMedio);
		//proportion.ristimaDiNuovoInjectedVersions(pMedio);
		
		/*AutoriZK autori = new AutoriZK();
		String pathLog = HelpZK.getMyProperty("pathLogFileNOsnoring");
		autori.getNameAutorCommitDateCommitfromGitLog(pathLog);*/
		
		/*String pathLog = HelpZK.getMyProperty("pathLogFileNOsnoring");
		CommitTicketZK com = new CommitTicketZK();
		com.createTripleCommitTicketDate(pathLog);*/
		
		/*JavaClassesProjectZK javaClasses = new JavaClassesProjectZK();
		String pathLog = HelpZK.getMyProperty("pathLogFileNOsnoring");
		String pathProjFile = HelpZK.getMyProperty("pathInfoFileProject");
		javaClasses.createPairsVersionJavaClass(pathLog, pathProjFile);*/
		
		/*List<Integer> arg = Arrays.asList(6);
		int res = HelpMathZK.numberBiggerThanMinusOne(arg);
		System.out.println("res "+res);*/
		
		NAUTHmetricZK auth= new NAUTHmetricZK();
		auth.caculateNAUTHforEveryVersion();
		
		logger.log(Level.INFO ,"FINE ClassToExecuteBK!!");
			
	}//fine main

}
