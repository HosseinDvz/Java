package waterTankFuzzyLogic;

//Java program to find approximation of an ordinary 
//differential equation using euler method 
//import java.io.*; 

class Euler { 
 /*
  Consider a differential equation 
  pi*r^2 = 78.5398
  dy/dx=(a/78.5398 + .01y^0.5/78.5398)
 */
	
	double funcA(double t, double y,double v) { 
		return (0.01 * v / 78.5398 - 0.1 * Math.sqrt(y) / 78.5398); 
		 
	 } 
	
 double funcB(double t, double y,double v) { 
	 if (t <= 20)
		 return (0.01 * v / 78.5398 - 0.1 * Math.sqrt(y) / 78.5398); 
	 else
		 return (0.01 * v / 78.5398 - 0.2 * Math.sqrt(y) / 78.5398);
	 
 } 

 // Function for Euler formula 
 
 public double eulerA(double t0, double y, double h, double t, double v) { 
     // Iterating till the point at which we 
     // need approximation 
     while (t0 < t) { 
    	// temp = y;
         y = y + h * funcA(t0, y , v); 
         t0 = t0 + h; 
     } 
     return y;
 }
 
 public double eulerB(double t0, double y, double h, double t, double v) { 
     // Iterating till the point at which we 
     // need approximation 
     while (t0 < t) { 
    	 //temp = y;
         y = y + h * funcB(t0, y , v); 
         t0 = t0 + h; 
     } 
     return y;

     // Printing approximation 
     //System.out.println("Approximate solution at x = "
     //                  + x + " is " + y); 
 } 
/*
 // Driver program 
 public static void main(String args[]) throws IOException { 
     Euler obj = new Euler(); 
     // Initial Values 
     double x0 = 0; 
     double y0 = 0; 
     double h = 0.00025f; 
     double v = 48;

     // Value of x at which we need approximation 
     double x = 40; 

     System.out.println("Approximate solution at x = "
         + x + " is " + obj.eulerB(x0, y0, h, x,v)); 
 } 
 */
} 
