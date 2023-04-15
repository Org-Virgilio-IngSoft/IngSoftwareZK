package helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HelpInfoProjectZK {

	
	 public static int[] getVersionsIndex(String projectInfo) throws IOException {
	    	int lungFile=0; 
	    	int i = 0;
	    	String[] info; 
	    	
	    	Path path= Paths.get(projectInfo);
	    	List<String> linesProjectInfoFile =Files.readAllLines(path);
			lungFile=linesProjectInfoFile.size();
				
			int[] versions = new int[lungFile];
			int lungVersions = versions.length;
			
			for( i=1; i<lungVersions ;i++) {
				info=linesProjectInfoFile.get(i).split(",");
				versions[i]= Integer.parseInt(info[0]);	
				
			}
			
			return versions;
		}//fine metodo
	
	 public static String[] getNamesOfVersions(String projectInfo) throws IOException {
	    	int lungFile=0; 
	    	int i = 0;
	    	String[] info; 
	    	
	    	Path path= Paths.get(projectInfo);
	    	List<String> linesProjectInfoFile =Files.readAllLines(path);
			lungFile=linesProjectInfoFile.size();
				
			String[] namesVersions = new String[lungFile];
			int lungNamesVersions = namesVersions.length;
			
			for(i=1;i<lungNamesVersions;i++) {
				info=linesProjectInfoFile.get(i).split(",");
				namesVersions[i]=info[2];	
				
			}
			
			return namesVersions;
		}//fine metodo
	 
	 public static String[] getDatesOfVersions(String projectInfo) throws IOException {
	        int lungFile=0; 
	        int i = 0;
	        String[] info; 
	    	
	    	Path path= Paths.get(projectInfo);
	    	List<String> linesProjectInfoFile =Files.readAllLines(path);
			lungFile=linesProjectInfoFile.size();
			
			String[] datesVersions = new String[lungFile];
			int lungDatesVersions = datesVersions.length;
			
			for( i=1;i<lungDatesVersions;i++) {
				info=linesProjectInfoFile.get(i).split(",");
				datesVersions[i]=info[3].substring(0, 10);		
				
			}
			
			return datesVersions;
		}//fine metodo
	
    
    private HelpInfoProjectZK(){
    	
    }
    
}
