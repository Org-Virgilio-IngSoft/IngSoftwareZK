/**
 * 
 */
package metrics;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBaseZK;
import logger.MyLoggerZK;

/**
 * @author Virgilio
 *
 */
public class BuggyZK {

public void giveLabelBuggytoJavaClasses() throws SQLException, IOException {
		
		String javaClassName;		
		int versionFixJavaClass;
		int versionInjJavaClass;
		
		String msg="Buggy ZK ";
		
		ResultSet rsBuggy;
		Connection conn=DBaseZK.connectToDBtickectBugZookeeper();
		Connection connUpdate=DBaseZK.connectToDBtickectBugZookeeper();
		
		String queryBuggy=" SELECT *  "
		      +"FROM \"CommitTicketsZK\"  AS ct "
		      +"JOIN \"Ticket_FV_OV_P_IV_ZK\"  AS fvov  "
		      +"ON ct.\"TicketID\" = fvov.\"TicketBugID\"  "
		      +"JOIN \"ListJavaClassesZK\"  AS ljc  "
		      +"ON  ljc.\"Commit\" = ct.\"Commit\"  "
		      +"WHERE ljc.\"NameClass\"  LIKE '%.java' AND "
              +"  fvov.\"P\" >= 1 AND ( fvov.\"IV\" >= 1 OR fvov.\"FV\" =1 ) "; 
		       	                                
		
		try(PreparedStatement stat=conn.prepareStatement(queryBuggy) ){
			  rsBuggy=stat.executeQuery();
					 
	          while( rsBuggy.next() ) {
	        	 
	        	  javaClassName=rsBuggy.getString("NameClass");	        	 
	        	  versionFixJavaClass=rsBuggy.getInt("FV");
	        	  versionInjJavaClass = rsBuggy.getInt("IV");
	        	  
	        	  if(versionFixJavaClass==1  && versionInjJavaClass==0 ) {
		        	  versionInjJavaClass=1;
	        	  }
	        	 
	        	  
	        	  String queryUpdate="UPDATE \"DataSetZK\"  "
	    	        	  + "SET \"Buggy\" = true  "
	    	        	  + "WHERE \"NameClass\" = ?  AND   \"Version\" = ? ";
	    	     
	    		  
	    		  try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpdate) ){
	    			  statUpd.setString(1,javaClassName);
	    			  statUpd.setInt(2, versionInjJavaClass);
	    			  	    			 
	    			  MyLoggerZK.logInfo(msg.concat(javaClassName) );
	    			  statUpd.executeUpdate();
	    		  }//try interno	
	        	  
	          }//while
		}//try
				
		
	}//fine metodo
	
}
