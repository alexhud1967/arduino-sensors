//Alexander Hudspeth
//03MAR2019


// PIN CONNECTIONS FOR LCD AND THERMISTOR TO ARDUINO. 
// USB POWERED IN THIS CONFIGURATION

// LCD PIN        ARDUINO NANO PIN
//  rs                12
//  e                 11
//  d4                5
//  d5                4
//  d6                3
//  d7                2


// THERMISTOR         A0
//
#include <LiquidCrystal.h> //includes Liquid Crystal Library

LiquidCrystal lcd(12, 11, 5, 4, 3, 2); //rs,e,d4,d5,d6,d7 
//Defines pin connections between Arduino and LCD 

//setup for Temp data
int ThermistorPin = A0; //sets thermistor to pin A0
int Vo;
float R11 = 100000; //known resistor value is 10000
float logR21, R21, T, C;//temp conversion math
float c1 = 1.009249522e-03, c2 = 2.378405444e-04, c3 = 2.019202697e-07;//temp conversion math


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
  lcd.print("     I CAME");
  lcd.setCursor(0,1);
  lcd.print("   TO KICK ASS");
 delay(2500); 
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("       AND   ");
  lcd.setCursor(0,1);
  lcd.print(" CHEW BUBBLEGUM");
 delay(2500); 
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("BUT I'M ALL OUT   ");
  lcd.setCursor(0,1);
  lcd.print(" OF BUBBLEGUM");
 delay(2500); 
  
 }
void loop() 
 {
   
  //Temperature section
  Vo = analogRead(ThermistorPin);
  R21 = R11 * (1023.0 / (float)Vo - 1.0);
  logR21 = log(R21);
  T = (1.0 / (c1 + c2*logR21 + c3*logR21*logR21*logR21));
  T = T - 273.15;
  C = T;
  T = (T * 9.0)/ 5.0 + 32.0; 

  lcd.clear();
  
// print temp to LCD
    lcd.setCursor(0,1);
    lcd.print("TEMP = ");
    lcd.print(T);   
    lcd.print(" F"); 
    lcd.setCursor(0,0);
    lcd.print("TEMP = ");
    lcd.print(C);
    lcd.print(" C");
    delay(500); 

  
}
