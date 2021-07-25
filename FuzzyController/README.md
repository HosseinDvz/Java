# Fuzzy Logic Level Control of a Cylindrical Water Tank
## Problem Definition

&nbsp;&nbsp; Consider a cylindrical water tank. Water enters the tank from the top at a rate proportional to the voltage, V, applied to the pump. The water leaves through an opening in the tank base at a rate that is proportional to the square root of the water height, H, in the tank. The presence of the square root in the water flow rate results in a nonlinear plant.<br/>

<p align="center"><img src = "images/WaterTank.jpg"><br/>
 
 The differential equation for the tank is given by: <br/>
 <p align="center"><img src = "images/DifferentialEqu.jpg"><br/>
   
 where R is the radius of the tank, H is actual water height in the tank, V is the voltage applied to the pump, a (in m^2 per minute) is a constant related to the flow rate out of the tank and b (in m^3 per minute per volt) is a constant related to the flow rate into the tank. The goal is to design a fuzzy logic controller for the pump so that the water level is always at the desired level.<br/>
I am going to simulate my design in these two condition:<br/>
 - **a**: R = 5m, V(max) = 48 volts, a = 0.1, b = 0.01 , Desired Height: *will be given by the user*
 - **b**: R = 5m, V(max) = 48 volts, b = 0.01, Desired Height: *will be given by the user*, a = 0.1 when t(time) <= 20 and a = 0.2 when t > 20<br/>
## Solution
   I used Java for the simulation and followed the pricipals of Object Oriented programming in defining the classes. <br/>
### Fuzzy Logic Controller 
   A fuzzy controller have the folowing architecture:<br/>
<p align="center"><img src = "images/FuzzyModel.jpg"><br/>  
  
I have strictly followed the above architecture. I will briefly explain different parts of the model and how I code it in Java but to better understand it you should already be familiar with fuzzy logic concepts like  different types of Membership Functions of different ways of deffuzification.<br/>
  #### Sensors
 &nbsp;&nbsp; Our sensor here is the above first order differential equation. Based on the current height of water tank (which will be determined by the user) and other related parameters, that equation tells us how the level of water will change.<br/>
  The class *Euler* handle this differential equation; It does two jobs: Firstly, It calculate the rate of change in the height which can directly be calculated by the equation and secondly, it will numerically solve the differential equation and calculate (approximates with high accuracy) the level of the water at a specific time. To numerically solve the the above first order  differential equation, I used one of the oldest and easiest method devised by **Euler** and is called, oddly enough, Euler’s Method. See the following diagram and equation:<br/>
<img src = "images/dif.jpg"><br/><img src = "images/Euler.jpg"><br/>
 
&nbsp;&nbsp;For our problem, I decided to return the height of water every 20 units of time but the step size to go from t to t+20 is 0.0025. As you know and you can also see in the above picture, smaller step size results to more accurate approximation.

 #### Fuzzification
  &nbsp;&nbsp; Process of finding the degree of membership of a value in a fuzzy set is called fuzzification which can be done by defining membership functions.<br/>
 In the codes, the class Fuzzification does this process. There are some private methods and one public method (*centerOfGeravity*) in this class. <br/>
 &nbsp;&nbsp;I defined three levels of water namely High, no change , low and a membership function for each of them and three levels for rate of change (errorDot).
 I will explain the process by an example. Assume the difference between the current and desired level of water is 2 meters. We pass this number to three different membership functions, *noChangeMemFunc*, *highMemFunc* and *lowMemFunc* to see how much that differece belongs to those three sets. For example the difference might be cosnsidered 75% high, 20% no change and 5% low. The rate of change in the level of water is also important. I defined three membership fucnctions namely, *noChangeErrorDotMemFunc*, *highErrorDotMemFunc* and lowErrorDotMemFunc to interpret the rate of change. For example, if the rate of change is 0.002, we interpret it as 30% high, 60% no change, and 10% low. <br/>
 &nbsp;&nbsp; This is very important part of the design. I interpreted the difference of **one** meter as high and the rate of change less than -0.003 as low rate of change but how did I find those numbers? There is no rule to find these number. You should find it by trial and error. If you look at the returned numbers by the differential equation and the levels of waters we are going to work with, you might guess some initial numbers for them and tune them after some trial and errors.
 #### Inference Engine
&nbsp;&nbsp; The process of drawing conclusions from existing data is called inference. For each rule, the inference engine looks up the membership values in the antecedents of the rule.The membership values are combined by fuzzy conjunction  (fuzzy AND i.e. A AND B = min(A,B)) to evaluate the degree of truth of the rule i. <br/>
We have a rule base in inference engin.<br/>
Look at the following table:
<p align="center"><img src = "images/FuzzyRullMatrix.jpg"><br/>
 
 &nbsp;&nbsp; We have three sets here. In the codes their names are *lowSet* (Four memebers), *highSet* (four memebers) and *noChangeSet* (one member). This table shows when we interpret the level of water as high, low and no need to change which are our *if - then* rules. For example we pass error = 0.5 and errorDot = 0.0002 to memebership functions. The membership functions return a number for each category (low,high, nochange). Then we perform Fuzzy and between each pair of returned numbers and fill in the table. For example we perform fuzzy AND between the returned numbers from *highErrorDotMemFunc* and *noChangeMemFunc* and write the number in the first row and second column of table which is of the memebers of our *highSet*. Then we need to return just one number for each set. To do this we calculate the **Root Sum Square** (RSS). It is a statistical method of dealing with a series of values where each value is squared, the sum of these squares is calculated and the square root of that sum is then taken. Now we have one number for each set. Now it is time to defuzzify.
 #### Defuzzification
 &nbsp;&nbsp; Combining inferred MF or aggregated output fuzzy set(RSS) and compute the “fuzzy centroid” of the area to obtain a crisp output is called *Defuzzification*. "Center of Gravity" (CoG) is one the defuzzification techniques. CoG is most widely used because the defuzzified values tend to move smoothly around the output fuzzy region, thus giving a more accurate representation of fuzzy set of any shape.<br/>
<p align="center"><img src = "images/CoG.jpg"><br/> 

 
 &nbsp;&nbsp; where X(i) is the center point of the inferred MF and *mu(x)* is the membership value of the inferred MF (i.e. (RSS). The CoG expression gives us the weighted average of the element in the support set. The choice of  center point is up to you and depends on the situation. I chose 100 for high , 0 for co change and -100 for low.<br/>
 
 This calculations are in Fuzzification class. Finally I use this number to calculate the voltage. Calculating the voltage is in *Defuzzification* class. It has just one method which calculate the voltage based on the CoG.<br/>
 
 ### Fuzzy Controller





 
 
 
   
 
  
