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
public class CHURNmetricZK {
	
public void calculateCHURNforEveryVersion() throws IOException, SQLException, InterruptedException {
		
		int i=0;
		String maxNumberOfversions = HelpZK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		for ( i = 1; i <= max ; i++) {
			calculateCHURNforSpecificVersion(i);
		}
				
	}//fine metodo
	
public void calculateCHURNforSpecificVersion(int version) throws IOException, InterruptedException, SQLException {
		
		List<String> listaFile=new ArrayList<>();
		String[] bufSplit;		
		String locAdded="/";
		String locDeleted="/";
		
		int churn=0;
		int churnMax=0;
		double churnAvg=0;
		List<Integer> listChurnValues=new ArrayList<>();
	
		boolean fileTrovato=false;
		boolean buffSplitLenghtOK=false; 
		
		CommandGitShowZK comGitShow= new  CommandGitShowZK();
				
		Connection conn=DBaseZK.connectToDBtickectBugZookeeper();
		Connection conn2=DBaseZK.connectToDBtickectBugZookeeper();
		Connection connUpdate=DBaseZK.connectToDBtickectBugZookeeper();
		ResultSet rsJavaClasses;
		ResultSet rsChurn;
		
		String queryClasses="SELECT DISTINCT \"NameClass\", \"Version\"  "
				+"FROM \"ListJavaClassesBK\" "
			    +"WHERE \"NameClass\" LIKE '%.java'  AND \"Version\"= ? ";
					
		try(PreparedStatement stat=conn.prepareStatement(queryClasses) ){
			stat.setInt(1, version);
			rsJavaClasses=stat.executeQuery();
		
				
          while( rsJavaClasses.next() ) {
        	
			String fileNameJava=rsJavaClasses.getString("NameClass");
			
			String query2 = "SELECT * FROM \"ListJavaClassesBK\"  "
					+"WHERE  \"NameClass\" =? AND \"Version\"= ? ";
			
			try(PreparedStatement stat2=conn2.prepareStatement(query2) ){
				stat2.setString(1, fileNameJava);
				stat2.setInt(2, version);
				rsChurn=stat2.executeQuery();
				
				while(rsChurn.next()) {
																
		          String commit = rsJavaClasses.getString("Commit");
		    
		          listaFile=comGitShow.commandGitShow(commit);		    
			      int sizeList=listaFile.size();
			
			
			      for(int j=(sizeList-1);j>=0;j--) {
				
				
				  if(listaFile.get(j).contains(fileNameJava)) {
					fileTrovato = true;
				  }
				
				  bufSplit = listaFile.get(j).split("\t");
				
				  if( (bufSplit.length) == 3 ) {
				    buffSplitLenghtOK = true;
				  }
				  if(  fileTrovato && buffSplitLenghtOK ) {
				     bufSplit = listaFile.get(j).split("\t");
										
					 locAdded=bufSplit[0];
					 locDeleted=bufSplit[1];
										
					 locAdded=specialCaseChurnValuselocAdded(locAdded);
					 locDeleted=specialCaseChurnValuselocDeleted(locDeleted);
					
					 churn=Integer.parseInt(locAdded)-Integer.parseInt(locDeleted);
					
					 listChurnValues.add(churn);
										
					 fileTrovato=false;
					 buffSplitLenghtOK=false;
					 break;
				 }//if
				
			 }//for
          }//while interno
				
			churn=HelpMathZK.findSum(listChurnValues);
	        churnAvg=HelpMathZK.findAVG(listChurnValues);
		    churnMax=HelpMathZK.findMax(listChurnValues);	
				
			String queryUpdChurn="UPDATE \"DataSetBK\"  "
	                   +"SET  \"Churn\"= ? , \"MaxChurn\"= ? , \"AvgChurn\"= ?	"                
			           +"WHERE \"NameClass\"= ?  AND \"Version\"= ? ";
			           		
			
			try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpdChurn)){
				statUpd.setInt(1, churn);
				statUpd.setInt(2, churnMax);
				statUpd.setDouble(3, churnAvg);
				statUpd.setString(4, fileNameJava);
				statUpd.setInt(5, version);
				
				statUpd.executeUpdate();
			}//try interno
			
			listChurnValues.clear();
			
          }//try medio	
		}//while
		
	}//try
		
					 
}//fine metodo 
	

	
	//metodo che elimina il caso LocAdded = "-"  	
public String specialCaseChurnValuselocAdded(String locAdded) {
		
	if(locAdded.equals("-")) {
		return "0";
	}
	return locAdded;
					
}//fine metodo


//metodo che elimina il caso LocDeleted = "-"  
public String specialCaseChurnValuselocDeleted(String locDeleted) {
	
	if(locDeleted.equals("-")) {
		return "0";
	}
	return locDeleted;
	
}//fine metodo

	
}

	


	