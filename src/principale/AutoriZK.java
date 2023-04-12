package principale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBaseZK;

public class AutoriZK {

	
	public void getNameAutorCommitDateCommitfromGitLog(String pathLogGitFile) throws SQLException, IOException {
		String commit="/";
		String date="/";
		String autore="/";	
		int count=0;
		String lineFile;
		
		Connection con;		
        DBaseZK db=new DBaseZK();
        
        con =db.connectToDBtickectBugZookeeper();
        
        try (
        	FileReader fr=new FileReader(pathLogGitFile);
        	BufferedReader br=new BufferedReader(fr);
      			                                          ){
      			 			
      	    while( (lineFile=br.readLine() ) !=null ) {
      					
      			if(lineFile.startsWith("commit") ) {
      				commit=lineFile.substring(7);
      				count=count+1;
      					
      			}
      				
      			if(lineFile.startsWith("Date") ) {
      				date=lineFile.substring(8,18);
      				count=count+1;
      			}
      				
      			if(lineFile.startsWith("Author") ) {
      				autore=lineFile.substring(8);
      				count=count+1;
      			}
      				
      			if(count==3) {
    					
    					String queryInsert="INSERT INTO \"AutoriZK\" (  \"NameAuthor\" , \"Commit\" , \"DateCommit\")  "+
    							"VALUES ( ? , ?, ? )";
    					try(PreparedStatement statUpdate=con.prepareStatement(queryInsert) ){
    					    statUpdate.setString(1, autore);
    					    statUpdate.setString(2, commit);
    					    statUpdate.setString(3, date);
    					    statUpdate.executeUpdate();
    					    count=0;
    				    }//try interno
    					
    			}//if
      				
      	   }//while
        }//try esterno
        
	}//fine metodo
}
