//Alexander Hudspeth
//03MAR2019

#include <LiquidCrystal.h> //includes Liquid Crystal Library

LiquidCrystal lcd(12, 11, 5, 4, 3, 2); //rs,e,d4,d5,d6,d7 
//Defines pin connections between Arduino and LCD 

//setup for Temp data
int ThermistorPin = A1; //sets thermistor to pin A1
int Vo;
float R11 = 10000; //known resistor value is 10000
float logR21, R21, T;//temp conversion math
float c1 = 1.009249522e-03, c2 = 2.378405444e-04, c3 = 2.019202697e-07;//temp conversion math

//setup for Resistance Data (from WorkingOhmmeter
int Vin=5;        //voltage at 5V pin of arduino
float Vout=A0;     //voltage at A0 pin of arduino
float R1=1000;    //value of known resistance
float R2=0;       //value of unknown resistance
int   TestRES=0;    
float buffer=0;   

void setup() 
{
  lcd.begin(16,2);
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("   (C) 2019");
  lcd.setCursor(0,1);
  lcd.print(" Alex Hudspeth");
 delay(2500); 
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("     Visit    ");
  lcd.setCursor(0,1);
  lcd.print(" www.arduino.cc");
 delay(2500); 
  lcd.clear();
 
 
}


void loop() 

  {

  
//    
//Temperature section
  Vo = analogRead(ThermistorPin);
  R21 = R11 * (1023.0 / (float)Vo - 1.0);
  logR21 = log(R21);
  T = (1.0 / (c1 + c2*logR21 + c3*logR21*logR21*logR21));
  T = T - 273.15;
  T = (T * 9.0)/ 5.0 + 32.0; 
  
//Ohmmeter section
   TestRES=analogRead(A0); //Connect test resistor circuit between ground and PIN A1
  if(TestRES)
  
    buffer=TestRES*Vin;
    Vout=(buffer)/1024.0;
    buffer=Vout/(Vin-Vout); 
    R2=R1*buffer;
//output section


    
// print Resistor value to LCD line 1
    lcd.setCursor(0,0);
    lcd.print("OHMS = ");
    lcd.print(R2);
    lcd.print(" ");
    
  
  
// print temp to LCD line 2
    lcd.setCursor(0,1);
    lcd.print("TEMP = ");
    lcd.print(T);   
    lcd.print(" F"); 
  
  
  
    delay(5000);
    


  

  
}

