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
public class LOCADDEDmetricZK {

public void calculateLOCADDEDforEveryVersion() throws IOException, SQLException, InterruptedException {
		
		int i=0;
		String maxNumberOfversions = HelpZK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		for ( i = 1; i <= max ; i++) {
			calculateLOCADDEDforSpecificVersion(i);
		}
				
	}//fine metodo
	
	public void calculateLOCADDEDforSpecificVersion(int version) throws SQLException, IOException, InterruptedException {
		
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
		Connection connUpdate=DBaseZK.connectToDBtickectBugZookeeper();
		ResultSet rsJavaNames;
		ResultSet rsLocAdded;
		
		String queryForClasses="SELECT DISTINCT \"NameClass\", \"Version\"  "
				+ "FROM \"ListJavaClassesBK\"  "
			    + "WHERE \"NameClass\" LIKE '%.java' AND \"Version\"= ?   ";
		
		try(PreparedStatement stat=conn.prepareStatement(queryForClasses) ){
			stat.setInt(1, version);
			rsJavaNames=stat.executeQuery();
		
				
          while( rsJavaNames.next() ) {
        	
			String fileJavaName=rsJavaNames.getString("NameClass");
		
			String query2 = "SELECT * FROM \"ListJavaClassesBK\"  "
					+ "WHERE  \"NameClass\" =? AND \"Version\"= ? ";
					
			
			try(PreparedStatement stat2=conn2.prepareStatement(query2) ){
				stat2.setString(1, fileJavaName);
				stat2.setInt(2, version);
				rsLocAdded=stat2.executeQuery();
				
				while(rsLocAdded.next()) {
    				   	  				    				   				   	  				     				   	  				    				   	  		  				
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
						
						 foundFile=false;
						 buffSplitHasRightLenght=false;
						 break;
					  }//if
					
				  }//for
				
			  }//while interno
				
			  locAdded=HelpMathZK.findSum(listLocAdded);
			  locAddedAvg=HelpMathZK.findAVG(listLocAdded);
			  locAddedMax=HelpMathZK.findMax(listLocAdded);
				
								
			  String queryUpd="UPDATE \"DataSetBK\"  "+
		              "SET  \"LOCadded\"= ?, \"MaxLOCadded\"= ? , \"AvgLOCadded\" = ? "+
				      "WHERE \"NameClass\" = ?  AND  \"Version\" = ? " ;
				           		 		
			  try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpd)){
					
					statUpd.setInt(1, locAdded);
					statUpd.setInt(2, locAddedMax);
					statUpd.setDouble(3 , locAddedAvg);
					statUpd.setString(4, fileJavaName);
					statUpd.setInt(5, version);
					statUpd.executeUpdate();
				}//try interno
			  
			  listLocAdded.clear();	
				
			}//try medio
			
			
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
