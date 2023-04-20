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

	public void calculateNAUTHforEveryVersion() throws IOException, SQLException {
		
		int i=0;
		String maxNumberOfversions = HelpZK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		for ( i = 1; i <= max ; i++) {
			calculateNAUTHforSpecificVersion(i);
		}
				
	}//fine metodo
	
    public void calculateNAUTHforSpecificVersion(int version) throws  IOException, SQLException {
		
		String javaClass;
		String autore;
		int nAuth=0;
		List<String> listAuthors=new ArrayList<>();
		
		
		ResultSet rsJavaClasses;
		ResultSet rsNAuth;
		Connection conn=DBaseZK.connectToDBtickectBugZookeeper();
		Connection conn2=DBaseZK.connectToDBtickectBugZookeeper();
		Connection connUpdate=DBaseZK.connectToDBtickectBugZookeeper();
		
		String queryJavaClasses=" SELECT DISTINCT \"NameClass\", \"Version\" "
				+ "FROM \"ListJavaClassesZK\"   "
				+ "WHERE \"NameClass\" LIKE '%.java' AND \"Version\"= ? ";
				
		
		try(PreparedStatement stat=conn.prepareStatement(queryJavaClasses) ){
			stat.setInt(1, version);
			rsJavaClasses=stat.executeQuery();
		
		  
          while( rsJavaClasses.next() ) {
        	
        	 javaClass=rsJavaClasses.getString("NameClass");
        	 
        	  
        	 String queryJavaClasses2=" SELECT  *  "   
     				+ "FROM \"ListJavaClassesZK\"  AS jc  "
        			+ "JOIN \"AutoriZK\"  AS auth  "
     				+ "ON  jc.\"Commit\"  =  auth.\"Commit\"    "
     				+ "WHERE jc.\"NameClass\" = ?  ";
     				
     		
        	 try(PreparedStatement stat2=conn2.prepareStatement(queryJavaClasses2) ){
  			     stat2.setString(1, javaClass);
        		 rsNAuth=stat2.executeQuery();
  			   
  			   			   
  			   while(rsNAuth.next()) {
  				    				     				   
  				   autore=rsNAuth.getString("NameAuthor");  				   				   
  				   listAuthors.add(autore);  				   
  				    				   
  			   }//while
  			   
  			   listAuthors=eliminaDuplicati(listAuthors);
			   
			   nAuth=listAuthors.size();
			   
			   String queryUpdate=" UPDATE \"DataSetZK\"  "
    		         + "SET \"NAuth\" = ?  "
  		             + "WHERE   \"Version\" = ? AND \"NameClass\" = ? ";
  		         
                  	           					
	         try(PreparedStatement statUpdate=connUpdate.prepareStatement(queryUpdate)){
	        	 statUpdate.setInt(1, nAuth);
	        	 statUpdate.setInt(2, version);
	        	 statUpdate.setString(3, javaClass);	        	
	        	 
	        	 statUpdate.executeUpdate();
	         }//try
  			    			   
  			   listAuthors.clear();
  			   
  			 }//try
        	       		      	         									
		}//while
		
	}//try
		
		
}//fine metodo
	
   public  List<String> eliminaDuplicati(List<String> autori){
	
      return autori.stream().distinct().collect(Collectors.toList());
	
   }//fine metodo
    

}
