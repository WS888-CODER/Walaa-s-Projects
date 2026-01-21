
package test;


public class BirthdayCake extends Cake {
 //attribute
private int numOfCandles ; //num of candles that the user want to dicorate the cake with 
private int numOfTiers ; //num of total birthday cake tiers


//constructor 
BirthdayCake(char size , String flavor , String cakeID, int numOfCandles , int numOfTiers)
{
super(size , flavor, cakeID );
this.numOfCandles = numOfCandles ; 
this.numOfTiers =numOfTiers ;
}//end of constructor 


//calculate price of the birthday cake  pased on size ,num of tiers and num of candles 
public double calculatePrice()
{
double price = 0;

switch(size)
{
      case 'S' : case 's':
      price = 85+(5*numOfCandles);
      break ;
      
      case 'M' : case 'm':
      price = 150+(5*numOfCandles);
      break ; 
      
      case 'L' : case 'l':
      price = 300+(5*numOfCandles);
      break ;
      
      default :
      System.out.println("Unvalid size");
      
}//end of switch 
if (numOfTiers > 1)
   price += 50*(numOfTiers-1);

return price ; 

}//end of method


public String toString()
{
return super.toString()+", Price of Birthday Cake: "+calculatePrice()+" SR\n";
}


}//end of BirthdayCake class    

