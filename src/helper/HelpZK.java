/**
 * 
 */
package helper;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * @author Virgilio
 *
 */
public class  HelpZK {
 
	
	public static String getMyProperty(String propertyName) throws IOException {
		String config="configZK";
		
		try(FileReader fr = new FileReader(config)){
			Properties property = new Properties();
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
		
    
  //metodo per ottere la data subito precedente rispetto ad una data di riferimento	
    public static int dateBeforeDate(String myDate, String[] dates) throws ParseException {
		
		int lung; 
		int i=1;
		
		lung=dates.length;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		Date inputDate=sdf.parse(myDate);
		Date date;
		
		
		Date maxDate= sdf.parse( dates[lung-1] );
		
		if(inputDate.after(maxDate)) {
		return lung-1;	
		}
		
		for( i=1;i<lung;i++) {
			date=sdf.parse( dates[i] );
			
			if(inputDate.after(date)) {
				//if per trovare la data precedente di inputDate
			}
		    
			else {
				break;
			}
		}//for
					
		if(i==1){
		  i=1;
		  return i;
		}
		
		return i;
	}//fine metodo

public static String[] getRidOfEmptyString(String[] array) {
		
		int i = 0;
		int lungArray = array.length;		
		
		for (i = 0; i < lungArray; i++) {
			if( array[i].isEmpty() ) {
				break;
			}			
		} 
				
		return Arrays.copyOf(array,i);
		
	}//fine metodo
        
	private HelpZK() {
		
	}
	
}
