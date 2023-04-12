/**
 * 
 */
package helper;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Virgilio
 *
 */
public class HelpZK {

	private static HelpZK instance;
	
	public static String getMyProperty(String propertyName) throws IOException {
		String config="configZK";
		
		try(FileReader fr = new FileReader(config)){
			var property = new Properties();
			property.load(fr);
			
			return property.getProperty(propertyName);
		}//try
		  	
	}//fine metodo
	
	
	 //metodo per gestire la parte numerica variabile di un perticolare ticket bug
    public static String projectStringTicket(String str) {
		String id; 
		int lungStr;
		String projectName="ZOOKEEPER-"; 
		int lungProjectName=0;
		int i=0;
		int indice;
		int indice2;
		int diff=0;
							
		lungStr=str.length();
		lungProjectName=projectName.length();
		
		indice=str.indexOf(projectName);		
		indice2=indice+lungProjectName;
			
		if(indice2>lungStr-4) {
				
			diff=lungStr-indice2;
			id=str.substring(indice2, indice2+diff);
			return projectName+id;
		}
		else {
							
		    id=str.substring(indice2, indice2+4);			
		}
			
		for( i=0;i<4;i++) {
			if(!Character.isDigit(id.charAt(i) ) ) {
				break;
			}
		}
			
		return projectName+id.substring(0, i);
					
	}//fine metodo
	
	
    ////////////////////////////////////////////////////
	private HelpZK() {
		 //private constructor
	 }

	 
	 public static HelpZK getInstance() {
	     if (instance == null) {
	    	 instance = new HelpZK();
	     }

	     return instance;
	}//fine 
	
}
