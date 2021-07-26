package waterTankFuzzyLogic;

import java.util.Scanner;
import java.util.ArrayList;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class PompFuzzyController {
	public static void main(String args[]) {
		
		//double currentVoltage = 0;
		double time = 0;
		double errorDot = -0;
		double error;
	
		//Three arrayList that stores the results and will be used to display voltage and water level charts
		ArrayList<Double> heightChart = new ArrayList<Double>();
		ArrayList<Double> voltageChart = new ArrayList<Double>();
		ArrayList<Double> timeChart = new ArrayList<Double>();
		
		
		try (//creating a scanner
		Scanner Input = new Scanner(System.in)) {
			//prompt user to enter the desired water level
			System.out.print("Please enter a positive number for the DESIRED level of water in meter: ");
			double desiredHeight = Input.nextDouble();

			//prompt user to enter the current water level
			System.out.print("Please enter a positive number for the CURRENT level of water in meter: ");
			double currentHeight = Input.nextDouble();
			
			//prompt user to enter the current voltage of the pomp
			System.out.print("Please enter a positive number between 0 and 48 for the CURRENT voltage of pomp: ");
			double currentVoltage = Input.nextDouble();
			
			System.out.print("Which part of problem a or b? ");
			String part = Input.next();

			Watertanke tanker = new Watertanke (desiredHeight, currentHeight ,currentVoltage);
			
			//Creating Euler object for calculating the changes and assigning the step size
			Euler dydx = new Euler();
			double stepSize = 0.0025;
	
			//*********************************************************MAIN CONTOLLER*******************************************************//
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
			while (time < 250) {
				
				System.out.println("Time: " + time);
				//storing the current status of tanker in the arrays to plot at the end
				heightChart.add(tanker.getCurrenHeight());
				voltageChart.add(tanker.getCurrentVoltage());
				timeChart.add(time);
				
				tanker.print();
				
				if (part.equalsIgnoreCase("a")) {
					errorDot = dydx.funcA(time, currentHeight, currentVoltage);
					currentHeight = dydx.eulerA(time, currentHeight, stepSize, time + 20,currentVoltage);
				}
				else {
					errorDot = dydx.funcB(time, currentHeight, currentVoltage);
					currentHeight = dydx.eulerB(time, currentHeight, stepSize, time + 20,currentVoltage);
				}
				error = tanker.error();
				
				tanker.setCurrentHeight(currentHeight);
						
				//System.out.println("EroorDot = " + errorDot);
				//System.out.println("Eroor = " + error);
				//System.out.println("*******************CoG: " + Fuzzification.centerOfGeravity(error, errorDot, currentHeight, desiredHeight));
				
				currentVoltage  = Defuzzification.setVoltage(Fuzzification.centerOfGeravity(error, errorDot), currentVoltage);
				tanker.setCurrentVoltage(currentVoltage);
				
				time++ ;		
				
			}
			//Creating charts
			XYChart chart = QuickChart.getChart("Water Level", "time", "height", "f(x)", timeChart, heightChart);
			XYChart chart2 = QuickChart.getChart("Voltage", "time", "voltage", "f(x)", timeChart, voltageChart);
			
			//Displaying charts
			new SwingWrapper<XYChart>(chart).displayChart();
		    new SwingWrapper<XYChart>(chart2).displayChart();
			
			
			
		}
			
	}
	
	
}
