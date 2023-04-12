package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import helper.HelpZK;

public class CommandGitShowZK {

	public List<String> commandGitShow(String commit) throws IOException, InterruptedException {
		
		List<String> resultOK = new ArrayList<>();
		String lineOK;
	
		String pathRepoZookeeper = HelpZK.getMyProperty("pathRepoZookeeper");
				
		ProcessBuilder pb = new ProcessBuilder();
	    
	    File fromFolder = new File(pathRepoZookeeper);
	    pb.directory( fromFolder);
	    pb.command("git", "--no-pager", "show",commit,"--numstat");
	    pb.redirectErrorStream(true);
		
		var process = pb.start();
					
		try(InputStream isOK = process.getInputStream();		
			InputStreamReader isr = new InputStreamReader( isOK );		
			BufferedReader brOK = new BufferedReader(isr);
					                                    ){
										
			while( (lineOK=brOK.readLine()) != null) {				
					resultOK.add(lineOK);								
			}//while
			
			process.waitFor();							
			return resultOK;			
		}//try 
		
	}//fine metodo
	
}
