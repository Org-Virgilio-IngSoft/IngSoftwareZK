package helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HelpInfoProjectZK {

	
	public static String[] getVersions(String projectInfo) throws IOException {
    	int lung=0; 
    	String[] info; 
    	
    	Path path= Paths.get(projectInfo);
    	List<String> linesProjectInfoFile =Files.readAllLines(path);
		lung=linesProjectInfoFile.size();
			
		String[] versions = new String[lung-1];
		
		for(int i=1;i<lung;i++) {
			info=linesProjectInfoFile.get(i).split(",");
			versions[i-1]=info[0];			
		}
		
		return versions;
	}//fine metodo
	
    public static String[] getDatesOfVersions(String projectInfo) throws IOException {
        int lung=0; 
        String[] info; 
    	
    	Path path= Paths.get(projectInfo);
    	List<String> linesProjectInfoFile =Files.readAllLines(path);
		lung=linesProjectInfoFile.size();
		
		String[] datesVersions = new String[lung-1];
		
		for(int i=1;i<lung;i++) {
			info=linesProjectInfoFile.get(i).split(",");
			datesVersions[i-1]=info[3].substring(0, 10);		
		}
		
		return datesVersions;
	}//fine metodo
	
    
    private HelpInfoProjectZK(){
    	
    }
    
}
