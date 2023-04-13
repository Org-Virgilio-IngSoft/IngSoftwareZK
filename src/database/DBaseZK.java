/**
 * 
 */
package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import helper.HelpZK;

/**
 * @author Virgilio
 *
 */
public class DBaseZK {

	private static String urlDBzookeeper="jdbc:postgresql://localhost:5432/TicketBugDB_ZOOK";

	
	//metodo per la connessione verso il database con i dati di Zookeeper
		public static Connection  connectToDBtickectBugZookeeper() throws SQLException, IOException {
			String user;
			String password;
			
			user=HelpZK.getMyProperty("userid");
			password=HelpZK.getMyProperty("password");
					
			return DriverManager.getConnection(urlDBzookeeper,user,password);
		}
		
	private DBaseZK()	{
		
	}
	
}
