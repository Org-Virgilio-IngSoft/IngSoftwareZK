/**
 * 
 */
package weka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import helper.HelpZK;

/**
 * @author Virgilio
 *
 */
public class WalkForwardZK {

	
	public void walkForwardTraining(String pathArffDataset) throws IOException {
        
		String line="";		
		String fileName="TrainingSetZK"; 
		String nameRelation="my_TrainingSetZK";
		String ext=".arff";
		String pathFolder = HelpZK.getMyProperty("pathArffFolder");
    	String nFilesString=HelpZK.getMyProperty("numberTrainingSetsToCreate");  	
		int nFilesInt = Integer.parseInt(nFilesString);
		
				
		BufferedWriter[] arffTrainingWriter =new BufferedWriter[nFilesInt];
		String[] pathArffTrainingFiles =new String[nFilesInt];
		
    	int i=0;
    	for (i = 0; i <nFilesInt ; i++) {
			CreateArffFileZK.createArffFile(fileName+Integer.toString(i+1) );
			pathArffTrainingFiles[i]=pathFolder+fileName+Integer.toString(i+1)+ext;			
		}//for  	
    	
    	try(BufferedReader arffReader = new BufferedReader(new FileReader(pathArffDataset));
    	     	 	   			 
    			                                                                              ){
    		 
    		 for ( i = 0; i < nFilesInt; i++) {// write relation name
		      		arffTrainingWriter[i] = new BufferedWriter(new FileWriter(pathArffTrainingFiles[i])); 
		      		arffTrainingWriter[i].write("@RELATION "+nameRelation+Integer.toString(i+1)+"\n");		      		 
					arffTrainingWriter[i].flush();
			}
    		 
    		 line = arffReader.readLine();//get first line original dataset
    		 line = arffReader.readLine();//discard first line original dataset
    		 while (  !(line.startsWith("@DATA"))  )  {//write fino a "@DATA" escluso
    	        	
    	        for ( i = 0; i < nFilesInt; i++) {
    	          	arffTrainingWriter[i].write(line+"\n"); 
    	          	arffTrainingWriter[i].flush();
    	    	}
    	        line = arffReader.readLine();
    		}//WHILE

    	    for (i = 0; i < nFilesInt; i++) {//write "@DATA" 
    	      	arffTrainingWriter[i].write(line+"\n"); 
    	      	arffTrainingWriter[i].flush();
    	    }//for
    		 
    		 while ((line = arffReader.readLine()) != null) {
    	         	String[] values=line.split(",");
    	         	int index=Integer.parseInt(values[0]);
    	         	
    	         	int j=index-1;
    	 			while(j<nFilesInt) {
    	 				arffTrainingWriter[j].write(line+"\n"); 
    	 	      		arffTrainingWriter[j].flush();
    	 	      		j=j+1;
    	 			}
    	 			
    	 	}//while
    		 
    	 }//try
    	
    	 
    	
    	
	}//fine metodo
	
    public void walkForwardTest(String pathArffDataset) throws IOException {
		
    	String line="";		
		String fileName="TestSetZK"; 
		String nameRelation="my_TestSetZK";
		String ext=".arff";
		String pathFolder = HelpZK.getMyProperty("pathArffFolder");
		String nFilesString=HelpZK.getMyProperty("numberTestSetsToCreate");
		int nFilesInt = Integer.parseInt(nFilesString);
    	
				
		BufferedWriter[] arffTestWriter = new BufferedWriter[nFilesInt];
		String[] pathArffTestFiles =new String[nFilesInt];
		
    	int i=0;
    	for (i = 0; i < nFilesInt ; i++) {
    		CreateArffFileZK.createArffFile(fileName+Integer.toString(i+2) );
    		pathArffTestFiles[i]=pathFolder+fileName+Integer.toString(i+2)+ext;			
 
		}//for
    	
    	try(BufferedReader arffReader = new BufferedReader(new FileReader(pathArffDataset));){
    		
    		 
    		for ( i = 0; i < nFilesInt; i++) {
	      		arffTestWriter[i] = new BufferedWriter(new FileWriter(pathArffTestFiles[i])); 
	      		arffTestWriter[i].write("@RELATION "+nameRelation+Integer.toString(i+2)+"\n");	      		 
	      		arffTestWriter[i].flush();
		   }//for
		 
    	   line = arffReader.readLine();//get first line original dataset
   		   line = arffReader.readLine();//discard first line original dataset
		   while (  !(line.startsWith("@DATA"))  )  {//write fino a "@DATA" escluso
	        	
	         for ( i = 0; i < nFilesInt; i++) {
	          	arffTestWriter[i].write(line+"\n"); 
	          	arffTestWriter[i].flush();
	    	 }
	         line = arffReader.readLine();
		   }//WHILE

	       for (i = 0; i < nFilesInt; i++) {//write "@DATA" 
	      	 arffTestWriter[i].write(line+"\n"); 
	      	 arffTestWriter[i].flush();
		   }
    		
    		while ((line = arffReader.readLine()) != null) {
            	String[] values=line.split(",");
            	int version=Integer.parseInt(values[0]);
            	
            	if(version<2) {
            		continue;
            	}
            	int index=version - 2;
            	if(index<nFilesInt) {
            	  arffTestWriter[index].write(line+"\n"); 
        	      arffTestWriter[index].flush();
            	}		    
    	      					
    		}//while
    		
    	}//try
    	
    	
	}//fine metodo
	
}
