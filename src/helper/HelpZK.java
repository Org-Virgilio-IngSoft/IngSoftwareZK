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
		String config="config";
		
		try(FileReader fr = new FileReader(config)){
			var property = new Properties();
			property.load(fr);
			
			return property.getProperty(propertyName);
		}//try
		  	
	}//fine metodo
	
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
