/**
 * 
 */
package helper;

import java.io.IOException;

import logger.MyLoggerZK;

/**
 * @author Virgilio
 *
 */
public class HelpPathZK {

	private static String TRAINSETNAME="TrainingSetZK";
	private static String TESTSETNAME="TestSetZK";
	private static final String EXT=".arff";
	
	public static String[] createPathFileTrainingSet() throws IOException {
		
		String pathFolder=HelpZK.getMyProperty("pathArffFolder");
		String nTimes=HelpZK.getMyProperty("numberTrainingSetsToCreate");
		int n=Integer.parseInt(nTimes);
		
		String[] pathTrainSets = new String[n+1]; 
		for (int i = 1; i <= n; i++) {
			
			pathTrainSets[i]=pathFolder+TRAINSETNAME+Integer.toString(i)+EXT;
		}
		
		return pathTrainSets;
	}//fine metodo
	
     public static String[] createPathFileTestSet() throws IOException {
		
    	String pathFolder=HelpZK.getMyProperty("pathArffFolder");	 
    	String nTimes=HelpZK.getMyProperty("numberTestSetsToCreate");
		int n=Integer.parseInt(nTimes); 

		String[] pathTestSets = new String[n+1]; 
		for (int i = 1; i <= n; i++) {
			
			pathTestSets[i]=pathFolder+TESTSETNAME+Integer.toString(i+1)+EXT;
		}
		return pathTestSets;
	}//fine metodo
	
     public static void printPaths(String[] paths) {
    	 
    	int lung=paths.length;
    	
    	for (int i = 1; i < lung; i++) {
			MyLoggerZK.logInfo(paths[i]);
		}
    	 
     }//fine metodo
     
    private HelpPathZK() {
    	
    }
     
}
