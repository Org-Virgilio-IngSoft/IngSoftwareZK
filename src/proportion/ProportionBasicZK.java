/**
 * 
 */
package proportion;

/**
 * @author Virgilio
 *
 */
public class ProportionBasicZK {

	public static double calculatePspecificBug(int fixV, int openV,int injectedV) {
		double p;
			
		if(fixV==openV) {			
			return -(Math.PI);
		}
		
		p=( ((double)fixV) - injectedV)/(fixV - openV);
		
		return p;
	}//fine metodo
	
	public static int calculateIVspecificBug(double p,int fixV, int openV) {
		
		int injectedV;
			
		int intP=(int) Math.round(p);
		
		injectedV= fixV - (fixV-openV) * intP;
		
		return injectedV;
		
	}//fine metodo
	
	private ProportionBasicZK() {
		
	}
	
}
