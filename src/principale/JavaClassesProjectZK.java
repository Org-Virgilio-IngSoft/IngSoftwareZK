package principale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import commands.CommandGitShowZK;
import database.DBaseZK;
import helper.HelpInfoProjectZK;
import helper.HelpZK;

public class JavaClassesProjectZK {

	//metodo per associare una versione ad una classe java
		public void createVersionJavaClassPairs(String fileLogGit,String projectInfo) throws IOException, ParseException, SQLException, InterruptedException{
 
			String line;		 	
			String version;
			String commit="";
			String dataCommit="/";
			List<String> nameFiles; 
			int indexDataJavaClassVersion;			
			
		    Connection con;

			String queryInsert;
							
			String[] datesVersions;
			String[] versions;
			
			con=DBaseZK.connectToDBtickectBugZookeeper();
			
			versions=HelpInfoProjectZK.getVersions(projectInfo);
			datesVersions = HelpInfoProjectZK.getDatesOfVersions(projectInfo);
		
					
			try (
				FileReader fr=new FileReader(fileLogGit);
				BufferedReader br=new BufferedReader(fr);			
					                                      ){
				
				while( (line=br.readLine() ) !=null ) {
						
					 if(line.startsWith("commit") ) {
							commit=line.substring(7);					
					 }
					 
					 
					 if(line.startsWith("Date") ) {
						dataCommit=line.substring(8,18);					
					 }
					
				   				
					if( line.contains("ZOOKEEPER-") ) {	
										  
					    nameFiles=searchAndGetFileNames(commit);
					    indexDataJavaClassVersion=HelpZK.dateBeforeDate(dataCommit, datesVersions);					
						version=versions[indexDataJavaClassVersion];
						
						
					    for(var i=0;i<nameFiles.size();i++) {
									
						 queryInsert="INSERT INTO \"ListJavaClassesZK\" ( \"NameClass\" , \"Commit\" , \"DateCommit\" , \"Version\")  " + 
								"VALUES ( ? , ?, ? ,?) ";
								
						
						try(PreparedStatement statUpdate=con.prepareStatement(queryInsert)){
							statUpdate.setString(1, nameFiles.get(i) );
	    					statUpdate.setString(2, commit);
	    			        statUpdate.setString(3, dataCommit);
	    			        statUpdate.setString(4, version);
						    statUpdate.executeUpdate();
						}//try interno
						
						
					  }//for
					}//if
				  				
						
		          }//while
			}//try esterno
			
				 		
		}//fine metodo
		
		
		public List<String> searchAndGetFileNames(String commit) throws IOException, InterruptedException {
			List<String> commandResult;
			var sizeResult=0;
			
			List<String> nameFiles=new ArrayList<>();
			
			
			String[] buffSplit;
			String line;
			int i;
			
			CommandGitShowZK cmdShow=new CommandGitShowZK();
			
			commandResult=cmdShow.commandGitShow(commit);
			sizeResult=commandResult.size();
			
			
			i=sizeResult-1;
			while(!(line=commandResult.get(i)).equals("") && i>=0) {
			   
			   buffSplit=line.split("\t");	
		       if( (buffSplit.length) ==3 ) {
		    	   
		    	   var file=buffSplit[2];
			       nameFiles.add(file);	       	    	       	      
			   } 
		       
		       i=i-1;
			}   
		      	      	
			return nameFiles;
		}//fine metodo
	
}
