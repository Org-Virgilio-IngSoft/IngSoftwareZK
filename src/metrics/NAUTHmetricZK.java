/**
 * 
 */
package metrics;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.PreparedStatement;

import database.DBaseZK;
import helper.HelpZK;

/**
 * @author Virgilio
 *
 */
public class NAUTHmetricZK {

	public void caculateNAUTHforEveryVersion() throws IOException, SQLException {
		
		int i=0;
		String maxNumberOfversions = HelpZK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		for ( i = 0; i < max ; i++) {
			caculateNAUTHforSpecificVersion(i);
		}
				
	}//fine metodo
	
    public void caculateNAUTHforSpecificVersion(int version) throws  IOException, SQLException {
		
		String javaClass;
		String autore;
		String commit;
		String dateCommit;
		int nAuth=0;
		List<String> listAuthors=new ArrayList<>();
		
		
		ResultSet rsJavaClasses;
		ResultSet rsJavaClasses2;
		Connection conn;
		Connection conn2;
		Connection connUpdate;
	    conn =DBaseZK.connectToDBtickectBugZookeeper();
	    conn2 =DBaseZK.connectToDBtickectBugZookeeper();
	    connUpdate =DBaseZK.connectToDBtickectBugZookeeper();
		
		String queryJavaClasses=" SELECT \"NameClass\",COUNT(\"NameClass\") "
				+ "FROM \"ListJavaClassesZK\"   "
				+ "WHERE \"NameClass\" LIKE '%.java' AND \"Version\"= ? "
				+ "GROUP BY \"NameClass\" ";
		
		try(PreparedStatement stat=conn.prepareStatement(queryJavaClasses) ){
			stat.setInt(1, version);
			rsJavaClasses=stat.executeQuery();
		
		  
          while( rsJavaClasses.next() ) {
        	
        	 javaClass=rsJavaClasses.getString("NameClass");
        	 
        	  
        	 String queryJavaClasses2=" SELECT  *  "   
     				+ "FROM \"ListJavaClassesZK\"  AS jc  "
        			+ "JOIN \"AutoriZK\"  AS auth  "
     				+ "ON  jc.\"Commit\"  =  auth.\"Commit\"    "
     				+ "WHERE jc.\"NameClass\" = ?  "
     				+ "ORDER BY jc.\"DateCommit\"  ASC   ";
     		
        	 try(PreparedStatement stat2=conn2.prepareStatement(queryJavaClasses2) ){
  			     stat2.setString(1, javaClass);
        		 rsJavaClasses2=stat2.executeQuery();
  			   
  			   			   
  			   while(rsJavaClasses2.next()) {
  				   
  				     				   
  				   autore=rsJavaClasses2.getString("NameAuthor");
  				   commit=rsJavaClasses2.getString("Commit");
  				   dateCommit=rsJavaClasses2.getString("DateCommit");
  				   
  				   listAuthors.add(autore);  				   
  				   listAuthors=eliminaDuplicati(listAuthors);
  				   
  				   nAuth=listAuthors.size();
  				   
  				 String queryUpdate=" UPDATE \"ListJavaClassesZK\"  "
	      		         + "SET \"NAuth\" = ? "
	    		         + "WHERE   \"NameClass\"  = ? AND   "
	    		         + "        \"Commit\"     = ? AND   "
	                     + "        \"DateCommit\" = ?       ";
			           					
		         try(PreparedStatement statUpdate=connUpdate.prepareStatement(queryUpdate)){
		        	 statUpdate.setInt(1, nAuth);
		        	 statUpdate.setString(2, javaClass);
		        	 statUpdate.setString(3, commit);	        	 
		        	 statUpdate.setString(4, dateCommit);
		        	 
		        	 statUpdate.executeUpdate();
		         }//try
  				   
  			   }//while
  			   
  			   listAuthors.clear();
  			   
  			 }//try
        	       		      	         									
		}//while
		
	}//try
		
		
}//fine metodo
	
   public  List<String> eliminaDuplicati(List<String> autori){
	
      return autori.stream().distinct().collect(Collectors.toList());
	
   }//fine metodo
    

}
