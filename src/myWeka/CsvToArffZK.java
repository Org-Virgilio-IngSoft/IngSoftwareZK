/**
 * 
 */
package myWeka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Virgilio
 *
 */
public class CsvToArffZK {

	public static void convert(String pathCsvToConvert,String pathToArff) throws IOException {
		String row="";
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
		int lung_labels=0;
		Logger logger=Logger.getLogger("MyLogger");	
		
		String dataset_name="@RELATION my_dataset";
		
		File myFile = new File(pathToArff);
	      if (myFile.createNewFile()) {
	    	logger.log(Level.INFO ,"File creato ok: {0}", myFile.getName());        
	      } else {
	    	logger.log(Level.INFO ,"File già esiste: {0}", myFile.getName());	       
	        return;
	      }
	     
	      
		try(BufferedReader csvReader = new BufferedReader(new FileReader(pathCsvToConvert));
		    BufferedWriter arffWriter = new BufferedWriter(new FileWriter(pathToArff))
				                                                                         ){
			
		
			arffWriter.write(dataset_name+"\n");
			arffWriter.write("\n");
			arffWriter.flush();
			
			row = csvReader.readLine();
			labels = row.split(",");
			lung_labels=labels.length;
			
			for (int i = 0; i < lung_labels; i++) {
				arffWriter.write("@ATTRIBUTE "+labels[i]+" "+typeLabels[i]+"\n");
				arffWriter.flush();
			}//for						
			arffWriter.write("\n");
			
			arffWriter.write("@DATA\n");
			arffWriter.flush();
			while ((row = csvReader.readLine()) != null) {
				
				arffWriter.write(row+"\n");
				arffWriter.flush();
			}//while
			
		}//try
		
		
		
		
		
		
		
		
		
		
		
	}//method
	
}
