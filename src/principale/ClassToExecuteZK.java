/**
 * 
 */
package principale;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataset.DatasetJavaClassesAndVersionsZK;

import helper.HelpPathZK;
import helper.HelpZK;
import metrics.BuggyZK;
import metrics.CHGSETSIZEmetricZK;
import metrics.CHURNmetricZK;
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
			
		logger.log(Level.INFO ,"LINKAGE ZOOKEEPER: {0}", linkage);
		
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
				
		
		DatasetJavaClassesAndVersionsZK dataset = new DatasetJavaClassesAndVersionsZK();
		dataset.fillFirstTwoColumnDataset();
		
		NAUTHmetricZK auth= new NAUTHmetricZK();
		auth.calculateNAUTHforEveryVersion();
		
		
		int i=0;
		String maxNumberOfversions = HelpZK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		Thread[] threadsLOCADDED = new Thread[max+1];	
		for ( i = 1; i <= max ; i++) {
			threadsLOCADDED[i] = new Thread(new LOCADDEDmetricZK(i));
			threadsLOCADDED[i].start();		
		}
					
		Thread[] threadsCHGSETSIZE = new Thread[max+1];	
		for ( i = 1; i <= max ; i++) {
			threadsCHGSETSIZE[i] = new Thread(new CHGSETSIZEmetricZK(i));
			threadsCHGSETSIZE[i].start();		
		}
					
		Thread[] threadsCHURN = new Thread[max+1];	
		for ( i = 1; i <= max ; i++) {
			threadsCHURN[i] = new Thread(new CHURNmetricZK(i));
			threadsCHURN[i].start();		
		}
		
		BuggyZK buggy = new BuggyZK();
		buggy.giveLabelBuggytoJavaClasses();
		
		String pathDatasetCSV = HelpZK.getMyProperty("pathDatasetCSV");
		String pathDatasetARFF = HelpZK.getMyProperty("pathDatasetARFF");
		CreateArffFileZK.createArffFile("DataSetZK");
		ConvertCsvToArffZK.convertMyDataset(pathDatasetCSV, pathDatasetARFF);
				
		WalkForwardZK walkForward = new WalkForwardZK();		
		walkForward.walkForwardTraining(pathDatasetARFF);
		walkForward.walkForwardTest(pathDatasetARFF);
		
		
		String[] pathTrainSets;
		pathTrainSets=HelpPathZK.createPathFileTrainingSet();
		HelpPathZK.printPaths(pathTrainSets);
		
		String[] pathTestSets;
		pathTestSets=HelpPathZK.createPathFileTestSet();
		HelpPathZK.printPaths(pathTestSets);
		
		
		logger.log(Level.INFO ,"FINE ClassToExecuteZK!!");
			
	}//fine main

}
