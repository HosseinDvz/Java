package waterTankFuzzyLogic;

/*
 * This class caculate the volatage that should be applied to the pump 
 */
public class Defuzzification {
	private static final double MAX_VOLTAGE = 48.0; //maximum pomp voltage
	
	public static double setVoltage(double CoG,double currentVoltage) {
		double voltage = 0;
		if (CoG < 0) 
			if (currentVoltage == 0 )
				voltage = (-CoG / 100) * MAX_VOLTAGE;
			else if (currentVoltage > 0 && currentVoltage <= 10 )
				voltage = 10 + currentVoltage;
			else
				voltage = currentVoltage + (-CoG / 100) * currentVoltage;
		else if (CoG == 0)
			voltage = currentVoltage;
		else
			voltage = currentVoltage - (CoG / 100) * currentVoltage;
		
		if (voltage < 0)
			voltage = 0;
		else if (voltage > MAX_VOLTAGE)
			voltage = MAX_VOLTAGE;
		else
			return voltage;
			
		return voltage;	
		
	}
	

}
