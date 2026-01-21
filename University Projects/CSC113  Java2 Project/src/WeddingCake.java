
package test;


public class WeddingCake extends Cake {
    //attribute 
private int numOfTiers; // num of tiers that user want to add more than the basic 3 tiers


//constructor
WeddingCake(char size , String flavor , String cakeID, int numOfTiers )
{
super(size , flavor, cakeID );
this.numOfTiers = numOfTiers ;
}//end of constructor


//calculate price of the wedding cake  pased on size and num of tiers 
public double calculatePrice()
{
double price = 0;

switch(size)
{
      case 'S' : case 's':
      price = 500+(numOfTiers*150);
      break ;
      
      case 'M' : case 'm':
      price = 800+(numOfTiers*150);
      break ; 
      
      case 'L' : case 'l':
      price = 1200+(numOfTiers*150);
      break ;
      
      default :
      System.out.println("Unvalid size");

      
}//end of switch 

return price ; 

}//end of method


public String toString()
{
return super.toString()+", Price of Wedding Cake: "+calculatePrice()+" SR\n";
}


}//end of WeddingCake class 


