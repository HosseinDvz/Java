package waterTankFuzzyLogic;

public class Fuzzification {
	
	/**Defining Fuzzy And*/
	private static double fuzzyAnd(double x,double y) {
		return Math.min(x,y);
	}
	
	
	/**Defining membership function for no change situation i.e
	 when -1 < error = curr - desi < 1 */
	private static double noChangeMemFunc(double x) {
		double noChangeMemValue;
		if (x < 1 && x > 0 )
			noChangeMemValue = 1 - x ;
		else if (x < 0 && x > -1)
			noChangeMemValue = 1 + x ;
		else if (x == 0)
			noChangeMemValue = 1;
		else 
			noChangeMemValue = 0;
		return noChangeMemValue;
		
	}
	
	/**Defining membership function for high situation i.e. when
	 the error = curr - desi > 0 */
	private static double highMemFunc(double x) {
		double highMemValue;
		if (x < 1 && x > 0)
			highMemValue = x;
		else if (x <= 0)
			highMemValue = 0;
		else
			highMemValue = 1;
		return highMemValue;
	}
	
	/**Defining membership function for low situation i.e. when
	 the error < 0 */
	private static double lowMemFunc(double x) {
		double lowMemValue;
		if (x < 0 && x > -1)
			lowMemValue = -x;
		else if (x <= -1)
			lowMemValue = 1;
		else
			lowMemValue = 0;
		return lowMemValue;
		
	}
	
	/**Defining membership function when for no change situation i.e
	 when -0.003 < errorDot < 0.003 */
	private static double noChangeErrorDotMemFunc(double x) {
		double noChangeMemValue;
		if (x < 0.003 && x > 0 )
			noChangeMemValue = 1 - x / 0.003;
		else if (x < 0 && x > -0.003)
			noChangeMemValue = 1 + x / 0.003;
		else if (x == 0)
			noChangeMemValue = 1;
		else 
			noChangeMemValue = 0;
		return noChangeMemValue;
		
	}
	
	/**Defining membership function for high situation i.e. when
	 the errorDOT > 0 */
	private static double highErrorDotMemFunc(double x) {
		double highMemValue;
		if (x < 0.003 && x > 0)
			highMemValue = x / 0.003;
		else if (x <= 0)
			highMemValue = 0;
		else
			highMemValue = 1;
		return highMemValue;
	}
	
	/**Defining membership function for low situation i.e. when
	 the errorDot < 0 */
	private static double lowErrorDotMemFunc(double x) {
		double lowMemValue;
		if (x < 0 && x > -0.003)
			lowMemValue = -x / 0.003;
		else if (x <= -0.003)
			lowMemValue = 1;
		else
			lowMemValue = 0;
		return lowMemValue;
		
	}
//**********************************  Inference Engine  *******************************************//
	/*Calculating Root Sum Square*/
	private static double lowRSS (double error, double errorDot) {
		
		double lowRSS = 0;
		double[] lowSet = new double[4];
		
		lowSet[0] = fuzzyAnd(lowMemFunc(error),lowErrorDotMemFunc(errorDot));
		lowSet[1] = fuzzyAnd(lowMemFunc(error),noChangeErrorDotMemFunc(errorDot));
		lowSet[2] = fuzzyAnd(lowMemFunc(error),highErrorDotMemFunc(errorDot));
		lowSet[3] = fuzzyAnd(noChangeMemFunc(error),lowErrorDotMemFunc(errorDot));
		
		for (int i = 0; i < lowSet.length; i++)
			lowRSS = lowRSS + Math.pow(lowSet[i] ,2);
		lowRSS = Math.sqrt(lowRSS);
		/*
		for (int i = 0; i < lowSet.length ; i++ )
			System.out.println("low set: " + lowSet[i]);
		*/
		return lowRSS;
	}
	
	private static double highRSS(double error, double errorDot) {
		
		double highRSS = 0;
		double[] highSet = new double[4];
		
		highSet[0] = fuzzyAnd(highMemFunc(error), lowErrorDotMemFunc(errorDot));
		highSet[1] = fuzzyAnd(highMemFunc(error), noChangeErrorDotMemFunc(errorDot));
		highSet[2] = fuzzyAnd(highMemFunc(error), highErrorDotMemFunc(errorDot));
		highSet[3] = fuzzyAnd(noChangeMemFunc(error), highErrorDotMemFunc(errorDot));
		
		for (int i = 0; i < highSet.length; i++)
			highRSS = highRSS + Math.pow(highSet[i] ,2);
		highRSS = Math.sqrt(highRSS);
		
		/*
		 for (int i = 0; i < highSet.length ; i++ )
			System.out.println("high set: " + highSet[i]);
		*/
		return highRSS;
	
	}
	
	private static double noChangeRSS(double error, double errorDot) {
		
		double noChangeRSS = 0;
		double[] noChangeSet = new double[1];
		noChangeSet[0] = Math.min(noChangeMemFunc(error), noChangeErrorDotMemFunc(errorDot));
		
		for (int i = 0; i < noChangeSet.length; i++)
			noChangeRSS = noChangeRSS + Math.pow(noChangeSet[i] ,2);
		noChangeRSS = Math.sqrt(noChangeRSS);
		
		/*
		for (int i = 0; i < noChangeSet.length ; i++ )
			System.out.println("no change set: " + noChangeSet[i]);
		*/
		return noChangeRSS;
		
	}
	
	//Calculating the Center of Geravity(CoG) 
	
	public static double centerOfGeravity(double error, double errorDot/*,double currentHeight, double desiredHeight*/) {
			
		return (highRSS(error,errorDot) * 100 + noChangeRSS(error,errorDot) * 0 + lowRSS(error,errorDot) * -100)
				/
				(highRSS(error,errorDot) + noChangeRSS(error,errorDot) + lowRSS(error,errorDot));
		
	}
	
		
}
