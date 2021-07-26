package waterTankFuzzyLogic;

/*
 * This class creates a water tank object
 */
public class Watertanke {
	private double desiredHeight;
	private double currentHeight;
	private double currentVoltage;
	
	public Watertanke() {}
	public Watertanke(double desiredHeight, double currentHeight,double currentVoltage) {
		this.currentHeight = currentHeight;
		this.desiredHeight = desiredHeight; 
		this.currentVoltage = currentVoltage;
	}
	
	public double error () {
		return currentHeight - desiredHeight;
	}
	
	public double getCurrenHeight() {return currentHeight;}
	public double getDesiredHeight() {return desiredHeight;}
	public double getCurrentVoltage() {return currentVoltage;}
	
	public void setCurrentHeight(double x) {
		currentHeight = x;
	}
	public void setDesiredHeight(double x) {
		desiredHeight = x;
	}
	public void setCurrentVoltage(double currentVoltage) {
		this.currentVoltage = currentVoltage;
		
	}
	
	public void	print() {
		
		System.out.println("===========================================================================================");
		System.out.println("Desired level of water: " + desiredHeight + "   " + "Current level of water: " + currentHeight + "   " 
		+ "current voltage of pomp: " + currentVoltage);
		System.out.println("===========================================================================================");
		
	}
	


}
