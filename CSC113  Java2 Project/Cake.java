
package test;

import java.io.*;
public abstract class Cake implements Serializable {
    //abstract super class 

	//attribute
	protected char size ; //size of cake:  S,M or L
	protected String flavor ; //flavor of the cake: choco ,vanilla.....
   protected String cakeID;


	//constructer
	Cake(char size , String flavor, String cakeID)
	{ //initialize the attribute
	this.size = size ;
	this.flavor = flavor ; 
   this.cakeID=cakeID;
	}//end of constructor


	//abstract method
	public abstract double calculatePrice();


	public String toString()
	{
	return "Size of the cake: "+size+", Flavor of the cake: "+flavor + ", Cake ID: " + cakeID;
	} 

	//get 
	public char getSize(){
	return size;}
   
   public String getCakeid(){
   return cakeID; }


	}//end of Cake class  

