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
 In the codes, the class Fuzzification does this process. There are some private functions and one public function (*centerOfGeravity*) in this class. <br/>
 I defined three levels of water namely High, no change , low for and a membership function for each of them and three levels for rate of change (errorDot).  


 
 
 
   
 
  
