/**
 * 
 */
package database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Virgilio
 *
 */
public class DBaseZK {

	private String config="config";
	private String urlDBzookeeper="jdbc:postgresql://localhost:5432/TickectBugDB";
			
	private FileReader fr;
	
	//metodo per la connessione verso il database con i dati di Bookkeeper
		public Connection  connectToDBtickectBugZookeeper() throws SQLException, IOException {
			String user;
			String password;
			
			user=getUserID();
			password=getPws();
					
			return DriverManager.getConnection(urlDBzookeeper,user,password);
		}
		
		//metodo per reperire user id da file di configurazione
	    public String getUserID() throws IOException{
	    	    	
	    	fr=new FileReader(config);
	    	var property=new Properties();
			property.load(fr);
		 
			return property.getProperty("userid");
		}
		
	  //metodo per reperire password da file di configurazione
		public String getPws() throws IOException{
				
			fr=new FileReader(config);
			var property=new Properties();
			property.load(fr);
			 
			return property.getProperty("password");
		    	
		}
	
}
