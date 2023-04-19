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
import metrics.LOCADDEDmetricZK;
import metrics.NAUTHmetricZK;
import proportion.ProportionZK;
import weka.ConvertCsvToArffZK;
import weka.CreateArffFileZK;
import weka.WalkForwardZK;

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
		String pathLogNOsnoring = HelpZK.getMyProperty("pathLogFileNOsnoring");
		 
		String pathLogLinkageZK = HelpZK.getMyProperty("pathLogFileLinkage");			
		double linkage = LinkageZK.calculateLinkageZK(pathLogLinkageZK);
			
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: {0}", linkage);
		
	    ReleasesZK release = new ReleasesZK();
		String pathFileTicketsWithAffectedVersions = HelpZK.getMyProperty("pathFileTicketsWithAffectedVersions");
	    String pathTicketsIDwithAffectedVersionAndIDversionBK = HelpZK.getMyProperty("pathTicketsIDwithAffectedVersionAndIDversionZK");
		release.findAffectedVersionsIndex(pathFileTicketsWithAffectedVersions);
	    release.findInjectedVersions(pathTicketsIDwithAffectedVersionAndIDversionBK);
		
        String pathTicketsBugWithFVOVdatesBK = HelpZK.getMyProperty("pathTicketsBugWithFVOVdates");
        release.findFixVersionsOpenVersionsIndex(pathTicketsBugWithFVOVdatesBK);
		
		ProportionZK proportion = new ProportionZK();		
		proportion.calcolaProportionTicketsWithIV();
		double pMedio = proportion.calculatePmedio();
		logger.log(Level.INFO ,"pMedio : {0}", pMedio);	
		proportion.ristimaDiNuovoInjectedVersions(pMedio);
		
		AutoriZK autori = new AutoriZK();		
		autori.getNameAutorCommitDateCommitfromGitLog(pathLogNOsnoring);
		
		
		CommitTicketZK commit = new CommitTicketZK();
		commit.createTripleCommitTicketDate(pathLogNOsnoring);
		
		JavaClassesProjectZK javaClasses = new JavaClassesProjectZK();	
		String pathProjFile = HelpZK.getMyProperty("pathInfoFileProject");
		javaClasses.createPairsVersionJavaClass(pathLogNOsnoring, pathProjFile);
				
		
		NAUTHmetricZK auth= new NAUTHmetricZK();
		auth.caculateNAUTHforEveryVersion();
		
		LOCADDEDmetricZK loc = new LOCADDEDmetricZK();
		loc.calculateLocAdded();
		
		String pathDatasetCSV = HelpZK.getMyProperty("pathDatasetCSV");
		String pathDatasetARFF = HelpZK.getMyProperty("pathDatasetARFF");
		CreateArffFileZK.createArffFile("ZOOKEEPERVersionInfo");
		ConvertCsvToArffZK.convertMyDataset(pathDatasetCSV, pathDatasetARFF);
				
		WalkForwardZK walkForward = new WalkForwardZK();		
		walkForward.walkForwardTraining(pathDatasetARFF);
		walkForward.walkForwardTest(pathDatasetARFF);
		
		logger.log(Level.INFO ,"FINE ClassToExecuteBK!!");
			
	}//fine main

}
