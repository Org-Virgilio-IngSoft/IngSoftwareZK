/**
 * 
 */
package dataset;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBaseZK;

/**
 * @author Virgilio
 *
 */
public class DatasetJavaClassesAndVersionsZK {

	public void fillFirstTwoColumnDataset() throws SQLException, IOException {
		
		Connection conn=DBaseZK.connectToDBtickectBugZookeeper();
		Connection connInsert=DBaseZK.connectToDBtickectBugZookeeper();
		ResultSet rsJavaNames;
		
		String queryJavaClassesNames="SELECT DISTINCT \"NameClass\", \"Version\"  "
				+ "FROM \"ListJavaClassesZK\"  "
				+ "WHERE \"NameClass\" LIKE '%.java'  "
				+ "ORDER BY \"Version\"  " ;
		
		try(PreparedStatement stat=conn.prepareStatement(queryJavaClassesNames) ){
			rsJavaNames=stat.executeQuery();
		
				
          while( rsJavaNames.next() ) {
        	
			String fileJavaName=rsJavaNames.getString("NameClass");
			int version = rsJavaNames.getInt("Version");
			
			String queryInsert="INSERT INTO \"DataSetZK\"  "
			         +" ( \"Version\"  , \"NameClass\"  )  "
					 +"VALUES ( ? , ? ) " ;
					           		
														 		
			  try(PreparedStatement statIns=connInsert.prepareStatement(queryInsert)){													
				  statIns.setInt(1, version);							
				  statIns.setString(2, fileJavaName);
							
				  statIns.executeUpdate();
			  }//try
																												
			
          }//while
          
		}//try  
	}//fine metodo
	
	
}
