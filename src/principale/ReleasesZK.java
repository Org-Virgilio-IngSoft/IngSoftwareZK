/**
 * 
 */
package principale;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.DBaseZK;
import helper.HelpZK;
import helper.HelpInfoProjectZK;


/**
 * @author Virgilio
 *
 */
public class ReleasesZK {

		
	public static void findAffectedVersionsIndex(String pathFileWithKnownAffectedVersions) throws FileNotFoundException, IOException {
		
		String lineFileRead;
		String[] splitNameVersion;    // file With Known Injected Version
		String[] cleanSplitNameVersion; // file With Known Injected Version without empty strings
		int i=1;
		int index;
		
        String[] namesOfAllVersions;
		
		String pathFileInfoProject= HelpZK.getMyProperty("pathInfoFileProject");
		String pathAffectedVersionAndIDversion = HelpZK.getMyProperty("pathTicketsIDwithAffectedVersionAndIDversionBK");
		
		namesOfAllVersions=HelpInfoProjectZK.getNamesOfVersions(pathFileInfoProject);		

		
		
		try (
			FileReader fr=new FileReader(pathFileWithKnownAffectedVersions);
			BufferedReader br=new BufferedReader(fr);
			FileWriter fw=new FileWriter(pathAffectedVersionAndIDversion);
			BufferedWriter bw=new BufferedWriter(fw);
				                                          ){
			 lineFileRead=br.readLine(); //get rid of title  			
			 while( (lineFileRead=br.readLine() ) !=null ) {
				 splitNameVersion=lineFileRead.split(",");
				 cleanSplitNameVersion = getRidOfEmptyString(splitNameVersion);
				//System.out.println("in while");
				 
				 bw.write(cleanSplitNameVersion[0]+",");
				 for (i = 1; i < cleanSplitNameVersion.length ; i++) {
					 index=getIndexVersionFromName(cleanSplitNameVersion[i],namesOfAllVersions);
					 bw.write(cleanSplitNameVersion[i]+","+index+",");			 
					 
					//System.out.println("in for");
				}
				bw.write("fine_riga\n"); 
				bw.flush();
				 
			 }//while
		}//try		
		
		
	}//fine metodo
	
	
	public static void findInjectedVersions(String pathFileWithKnownInjectedVersionsIndex) throws FileNotFoundException, IOException, SQLException {
	     
		String lineFile;
		String[] split;
		int i;
        
        List<Integer> temporary= new ArrayList<Integer>();
        List<Integer> injectedVersions= new ArrayList<Integer>();
        List<String> ticketsBugID= new ArrayList<String>();  //tickets with affected version 
      
        List<String> listSQLbugID= new ArrayList<String>();
        List<Integer> listSQLiv= new ArrayList<Integer>();
        List<String> listSQLdatesIV= new ArrayList<String>();
        
        Connection con;		       
        con =DBaseZK.connectToDBtickectBugZookeeper();
        
        String pathInfoFileProject = HelpZK.getMyProperty("pathInfoFileProject");
        String[] datesAllVersions = HelpInfoProjectZK.getDatesOfVersions(pathInfoFileProject);
        
        
		try (
			FileReader fr=new FileReader(pathFileWithKnownInjectedVersionsIndex);
			BufferedReader br=new BufferedReader(fr);
			                                          ){
					 		
			 while( (lineFile=br.readLine() ) !=null ) {
	              split = lineFile.split(",");
       			  int lungSplit = split.length;
	              
       			  ticketsBugID.add(split[0]); //this is the name of ticket
       			  //System.out.print(split[0]+" ");
       			  
       			  i=lungSplit-2;
	              while ( !(split[i].startsWith("ZOOKEEPER-")) ) {
	            	temporary.add( Integer.parseInt(split[i]) );  
					i=i-2;
				  }
	              
	              Collections.sort(temporary);
	              if(temporary.get(0)==-1) {  //-1 means this version is not present in the file project .csv
	            	 injectedVersions.add(temporary.get(1) );
	            	 //System.out.println("IV "+temporary.get(1));
	              }
	              else {
	            	 injectedVersions.add(temporary.get(0) ); 
	            	 //System.out.println("IV "+temporary.get(0));
	              }
	              
	              String bugID = ticketsBugID.get(0);
	              int IV = injectedVersions.get(0);
	              String dateIV=datesAllVersions[IV];
	              
	              listSQLbugID.add(bugID);
	              listSQLiv.add(IV);
	              listSQLdatesIV.add(dateIV);
	              
	              temporary.clear();
	              injectedVersions.clear();
	              ticketsBugID.clear();
	              
	      }//while
       }//try
		
	   //execute query sql
	   String query="INSERT INTO \"TicketWithInjectedVersionZK\" (  \"TicketBugID\" , \"DateInjectedVersion\" , \"InjectedVersion\")  "+
					"VALUES ( ? , ? , ?)";
		
	   
	   try(PreparedStatement statUpdate=con.prepareStatement(query) ){
		    	
		   for (int j = 0; j <  listSQLbugID.size(); j++) {
		     statUpdate.setString(1,listSQLbugID.get(j));
		     statUpdate.setString(2,listSQLdatesIV.get(j) );
			 statUpdate.setInt(3, listSQLiv.get(j));
			 
			 statUpdate.executeUpdate();	
	       }//for  
		    	 	    	   			   
  	  }//try interno
		     		  		
	}//fine metodo
	
	
	public static void findFixVersionsOpenVersionsIndex(String pathFileWithFixVersionsOpenVersions) throws FileNotFoundException, IOException, ParseException, SQLException {
		
		String lineFile;
		String[] split;
		String[] datesAllVersions;
		
		int fixV;
		int openV;
		
	     List<Integer> fixVersions= new ArrayList<Integer>();
	     List<Integer> openVersions= new ArrayList<Integer>();
	     List<String> listSQLdatesFixV= new ArrayList<String>();
	     List<String> listSQLdatesopenV= new ArrayList<String>();
	     List<String> ticketsBugID= new ArrayList<String>();  
			     
		String pathFileInfoProject= HelpZK.getMyProperty("pathInfoFileProject");				
	    datesAllVersions = HelpInfoProjectZK.getDatesOfVersions(pathFileInfoProject);		
		
	    Connection con;		       
        con =DBaseZK.connectToDBtickectBugZookeeper();
	    
		try (
			FileReader fr=new FileReader(pathFileWithFixVersionsOpenVersions);
			BufferedReader br=new BufferedReader(fr);
				                                          ){
				 			
			while( (lineFile=br.readLine() ) !=null ) {
		         split = lineFile.split(",");
		         ticketsBugID.add(split[0]);
		         //System.out.print(split[0]+" ");
		         
		         fixV = HelpZK.dateBeforeDate(split[1], datesAllVersions);
		         openV = HelpZK.dateBeforeDate(split[2], datesAllVersions);
		         String datefixV=datesAllVersions[fixV];
		         String dateopenV=datesAllVersions[openV];
		         
		         fixVersions.add(fixV);
		         openVersions.add(openV);
		         listSQLdatesFixV.add(datefixV);
		         listSQLdatesopenV.add(dateopenV);
		         
			 }//while
		}//try
				 
		//execute sql query
		String query="INSERT INTO \"Ticket_FV_OV_P_IV_ZK\" (  \"TicketBugID\" , \"FV\" , \"DateFixVersion\" , \"OV\" , \"DateOpenVersion\" )  "+
				"VALUES ( ? , ?, ?, ?, ? )";
		
		  try(PreparedStatement statUpdate=con.prepareStatement(query) ){
    	
		   for (int j = 0; j <  ticketsBugID.size(); j++) {
		     statUpdate.setString(1, ticketsBugID.get(j));
			 statUpdate.setInt(2, fixVersions.get(j));
			 statUpdate.setString(3, listSQLdatesFixV.get(j));
			 statUpdate.setInt(4, openVersions.get(j));	 
			 statUpdate.setString(5, listSQLdatesopenV.get(j));
			 statUpdate.executeUpdate();	
	       }//for  
		    	 	    	   			   
	  }//try interno
		   
	}//fine metodo
	
	public static int getIndexVersionFromName(String nameVersion, String[] allVersions) throws IOException {

		int i = 0;
		
		int lungAllVersions = allVersions.length;
		
		for (i = 1; i < lungAllVersions; i++) {
			
			if(allVersions[i].equals(nameVersion)){
				
				return i;			
			}
		}//for
		
		return -1;
	}//fine metodo
	
	public static int getIndexVersionFromDate(String dateVersion, String[] allDateVersions) throws IOException {

		int i = 0;
		
		int lungAllDateVersions = allDateVersions.length;
		
		for (i = 1; i < lungAllDateVersions; i++) {
			
			if(allDateVersions[i].equals(dateVersion)){
				
				return i;			
			}
		}//for
		
		return -1;
	}//fine metodo
	
	public static String[] getRidOfEmptyString(String[] array) {
		
		int i = 0;
		int lungArray = array.length;
		String[] newArray;
		
		for (i = 0; i < lungArray; i++) {
			if( array[i].isEmpty() ) {
				break;
			}			
		} 
		
		newArray = new String[i];
		int lungNewArray = newArray.length;
		for (i = 0; i < lungNewArray; i++) {
			newArray[i] = array[i];										
		}
		
		return newArray;
	}//fine metodo
	
}
