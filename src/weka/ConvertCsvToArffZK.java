/**
 * 
 */
package weka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Virgilio
 *
 */
public class ConvertCsvToArffZK {

	public static void convertMyDataset(String pathToCsv,String pathToArff) throws IOException {
		
		String line="";	   
		String[] labels;
		String numeric = "NUMERIC";
		String[] typeLabels={numeric, //version
				              "STRING",//java class name
				              numeric,// Nauth
				              numeric,//LOCadded
				              numeric,//MaxLOCadded
				              numeric,//AvgLOCadded
				              numeric,//Churn
				              numeric,//MaxChurn
				              numeric,//AvgChurn
				              numeric,//ChgSetSize
				              numeric,//MaxChgSetSize
				              numeric,//AvgChgSetSize
				              "{true,false}"}; // buggy
		int lungLabels=0;
		
		String datasetName="@RELATION my_dataset";
		
	      
		try(BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
		    BufferedWriter arffWriter = new BufferedWriter(new FileWriter(pathToArff))
				                                                                         ){
			
		
			arffWriter.write(datasetName+"\n");
			arffWriter.write("\n");
			arffWriter.flush();
			
			line = csvReader.readLine();
			labels = line.split(",");
			lungLabels=labels.length;
			
			for (int i = 0; i < lungLabels; i++) {
				arffWriter.write("@ATTRIBUTE "+labels[i]+" "+typeLabels[i]+"\n");
				arffWriter.flush();
			}//for						
			arffWriter.write("\n");
			
			arffWriter.write("@DATA\n");
			arffWriter.flush();
			
			 while ((line = csvReader.readLine()) != null) {
					
				  arffWriter.write(line+"\n");
				  arffWriter.flush();
			  }//while
			
		}//try
	    				      							  		 
									
	}//fine method
	

	
	
     private ConvertCsvToArffZK() {
		
	}
	
}
