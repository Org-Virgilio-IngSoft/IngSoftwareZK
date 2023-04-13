/**
 * 
 */
package helper;



/**
 * @author Virgilio
 *
 */
public class HelpMathZK {

	public int findMax(int[] numbers) {
		int max=numbers[0];
		int temp;
		int i=0;
		for( i=1;i<numbers.length;i++) {
			temp=numbers[i];
			if(temp>max) {
				max=temp;
			}		
		}
				
		return max;
	}//fine metodo
	
	public static int findMin(int[] numbers) {
		int min=numbers[0];
		int temp;
		int i=0;
		
		for( i=1;i<numbers.length;i++) {
			temp=numbers[i];
			if(temp<min) {
				min=temp;
			}		
		}
		
		return min;
    }//fine metodo	
    
    public static double mean(double[] numbers) {
    	double total = 0;
    	
    	 
    	for(int i=0; i<numbers.length; i++){
         	total = total + numbers[i];        
    	}

        return total / numbers.length;
    }//fine metodo

    
	
}
