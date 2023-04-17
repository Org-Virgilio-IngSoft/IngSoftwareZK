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
import helper.HelpMathZK;

/**
 * @author Virgilio
 *
 */
public class LOCADDEDmetricZK {

	public void calculateLocAdded() throws SQLException, IOException, InterruptedException {
		
		List<String> listFiles=new ArrayList<>();
		String[] bufferSplit;
		String locAddedString="/";
		int locAdded=0;
		
		int locAddedMax=0;
		double locAddedAvg=0.0;
		List<Integer> listLocAdded=new ArrayList<>();
		
				
		boolean foundFile=false;
		boolean buffSplitHasRightLenght=false; 
		
		CommandGitShowZK cmdgitShow=new  CommandGitShowZK();
				
		Connection conn=DBaseZK.connectToDBtickectBugZookeeper();
		Connection conn2=DBaseZK.connectToDBtickectBugZookeeper();
		ResultSet rsJavaNames;
		ResultSet rsLocAdded;
		
		String queryForClasses="SELECT DISTINCT FROM \"ListJavaClassesZK\" "+
				 " WHERE \"NameClass\" LIKE '%.java' ";
		
		try(PreparedStatement stat=conn.prepareStatement(queryForClasses) ){
			rsJavaNames=stat.executeQuery();
		
				
          while( rsJavaNames.next() ) {
        	
			String fileJavaName=rsJavaNames.getString("NameClass");
		
			String query2 = "SELECT * FROM \"ListJavaClassesZK\"  "
					+ "WHERE  \"NameClass\" =? "
					+ "ORDER BY \"Version\" ASC ";
			
			try(PreparedStatement stat2=conn2.prepareStatement(query2) ){
				stat2.setString(1, fileJavaName);
				rsLocAdded=stat2.executeQuery();
				
				
				String commit = rsLocAdded.getString("Commit");
			    
			    listFiles=cmdgitShow.commandGitShow(commit);		   
				int size=listFiles.size();
				
				for(int i=(size-1);i>=0;i--) {
					
					if(listFiles.get(i).contains(fileJavaName) ) {
						foundFile=true;
					}
					
					bufferSplit=listFiles.get(i).split("\t");
					
					if((bufferSplit.length) ==3 ) {
						buffSplitHasRightLenght=true;
					}
					
					if(foundFile && buffSplitHasRightLenght) {
						
						bufferSplit=listFiles.get(i).split("\t");
						
						
						locAddedString=bufferSplit[0];				
						locAddedString=specialCaseLOCaddedValue(locAddedString);
						
						locAdded=Integer.parseInt(locAddedString);
						
						listLocAdded.add(locAdded);
						locAddedAvg=HelpMathZK.findAVG(listLocAdded);
						locAddedMax=HelpMathZK.findMax(listLocAdded);
						
						
						String queryUpd="UPDATE \"ListJavaClassesZK\"  "+
				              "SET  \"LOCadded\"= ?, \"MaxLOCadded\"= ? , \"AvgLOCadded\" = ? "+
						      "WHERE \"NameClass\" = ?  AND  \"Commit\" = ? " ;
						           		 		
						try(PreparedStatement statUpd=conn.prepareStatement(queryUpd)){
							
							statUpd.setInt(1, locAdded);
							statUpd.setInt(2, locAddedMax);
							statUpd.setDouble(3 , locAddedAvg);
							statUpd.setString(4, fileJavaName);
							statUpd.setString(5, commit);
							statUpd.executeUpdate();
						}
						
						foundFile=false;
						buffSplitHasRightLenght=false;
						break;
					}//if
					
				}//for
				
				
			}//try interno
			
			
          }//while
	   }//try
		
	}//fine metodo
	
	
	//metodo che elimina il caso LocAdded = "-"  
	public String specialCaseLOCaddedValue(String locAddedString) {
			
		if(locAddedString.equals("-")) {
			locAddedString="0";
		}
		return locAddedString;
		
	}//fine metodo
	
}
