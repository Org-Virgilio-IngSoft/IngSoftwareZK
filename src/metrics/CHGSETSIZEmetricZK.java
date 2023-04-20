/**
 * 
 */
package metrics;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commands.CommandGitShowZK;
import database.DBaseZK;
import helper.HelpZK;
import helper.HelpMathZK;

/**
 * @author Virgilio
 *
 */
public class CHGSETSIZEmetricZK {

public void calculateCHGSETSIZEforEveryVersion() throws IOException, SQLException, InterruptedException {
		
		int i=0;
		String maxNumberOfversions = HelpZK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		for ( i = 1; i <= max ; i++) {
			calculateCHGSETSIZEforSpecificVersion(i);
		}
				
	}//fine metodo
	
	public void calculateCHGSETSIZEforSpecificVersion(int version) throws SQLException, IOException, InterruptedException {
		
		List<String> listFiles=new ArrayList<>();
		
		int chgSetSize=0;
		int chgSetSizeMax=0;
		double chgSetSizeAvg=0.0;
		List<Integer> listChgSetSize=new ArrayList<>();
		
		CommandGitShowZK cmdgitShow = new  CommandGitShowZK();
		
		Connection conn=DBaseZK.connectToDBtickectBugZookeeper();
		Connection conn2=DBaseZK.connectToDBtickectBugZookeeper();
		Connection connUpdate=DBaseZK.connectToDBtickectBugZookeeper();
		ResultSet rsJavaNames;
		ResultSet rsCHGSETSIZE;
		
		String queryForClasses="SELECT DISTINCT \"NameClass\", \"Version\"  "
				+"FROM \"ListJavaClassesBK\" "
			    +"WHERE \"NameClass\" LIKE '%.java' AND \"Version\"= ? ";
		
		
		try(PreparedStatement stat=conn.prepareStatement(queryForClasses) ){
			stat.setInt(1, version);
			rsJavaNames=stat.executeQuery();
						
            while( rsJavaNames.next() ) {
        	
			String fileJavaName=rsJavaNames.getString("NameClass");
		         
			String query2 = "SELECT * FROM \"ListJavaClassesBK\"  "
					+ "WHERE  \"NameClass\" =? AND \"Version\"= ? ";
					
			
			try(PreparedStatement stat2=conn2.prepareStatement(query2) ){
				stat2.setString(1, fileJavaName);
				rsCHGSETSIZE=stat2.executeQuery();
				
				 while(rsCHGSETSIZE.next()) {
     				   
					 String commit = rsCHGSETSIZE.getString("Commit");
					    
					 listFiles=cmdgitShow.commandGitShow(commit);		   
					 chgSetSize=listFiles.size() - 1;
					
					 listChgSetSize.add(chgSetSize);  				   
	  				    				   
	  			 }//while interno
				
				
				chgSetSizeAvg=HelpMathZK.findAVG(listChgSetSize);
				chgSetSizeMax=HelpMathZK.findMax(listChgSetSize);
			    
				String queryUpd="UPDATE \"DataSetBK\"  "+
			              "SET  \"ChgSetSize\"= ?, \"MaxChgSetSize\"= ? , \"AvgChgSetSize\" = ? "+
					      "WHERE \"NameClass\" = ?  AND  \"Version\" = ? " ;
					           		 		
					try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpd)){
						
						statUpd.setInt(1, chgSetSize);
						statUpd.setInt(2, chgSetSizeMax);
						statUpd.setDouble(3 , chgSetSizeAvg);
						statUpd.setString(4, fileJavaName);
						statUpd.setInt(5, version);
						statUpd.executeUpdate();
					}
				
				   listChgSetSize.clear();
              }//try interno 
	        }//while
	    }//try
		
	}//fine metodo
	
}
