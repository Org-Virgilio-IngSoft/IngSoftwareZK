/**
 * 
 */
package helper;

import java.util.List;

/**
 * @author Virgilio
 *
 */
public class HelpMathZK {

	
	public static int findMax(List<Integer> numbers) {
		int max=numbers.get(0);
		int temp;
		int i=0;
		for( i=1;i<numbers.size();i++) {
			temp=numbers.get(i);
			if(temp>max) {
				max=temp;
			}		
		}
				
		return max;
	}//fine metodo
	
    
   
    public static double findMean(List<Double> numbers) {
    	double total = 0;
    	
    	 
    	for(int i=0; i<numbers.size(); i++){
         	total = total + numbers.get(i);        
    	}

        return total / numbers.size();
    }//fine metodo
    
    public static double findAVG(List<Integer> numbers) {
    	double total = 0;
    	
    	 
    	for(int i=0; i<numbers.size(); i++){
         	total = total + numbers.get(i);        
    	}

        return total / numbers.size();
    }//fine metodo
    
    public static int findSum(List<Integer> numbers) {
    	int total = 0;
    	
    	 
    	for(int i=0; i<numbers.size(); i++){
         	total = total + numbers.get(i);        
    	}

        return total;
    }//fine metodo
    
  //restituise il primo numero > -1 nella list
    public static int numberBiggerThanMinusOne(List<Integer> orderedlist) {
		int i=0;
		int lung = orderedlist.size();
		int max=0;
		
		if(orderedlist.get(lung-1)==-1 ) {
			return -1;
		}
		
		if(lung==1) {
			return orderedlist.get(0);
		}
		
		max = Math.max(orderedlist.get(0), orderedlist.get(1) ) ;
		if(max > 0 ) {
			return orderedlist.get(0);
		}
		
		for ( i = 0; i < lung; i++) {
			max = Math.max(orderedlist.get(i), orderedlist.get(i+1) );
			if( max > 0) {
				break;
			}
			
		}//for
		return max;					
		
    }//fine metodo
    
    
     private HelpMathZK() {
    	
    }
    
}
