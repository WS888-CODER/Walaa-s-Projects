
package test;


public class CupCake extends Cake {
    //attribute 
private int numOfCupCake ; //num of cupcake 


//constructor
CupCake(char size , String flavor , String cakeID, int numOfCupCake )
{
super(size , flavor, cakeID );
this.numOfCupCake = numOfCupCake ; 
}//end of constructor


//calculate price of the cupcake pased on size and num of cupcake 
public double calculatePrice()
{
double price = 0;

switch(size)
{
      case 'S' : case 's':
      price = numOfCupCake*5;
      break ;
      
      case 'M' : case 'm':
      price = numOfCupCake*10;
      break ; 
      
      case 'L' : case 'l':
      price = numOfCupCake*20;
      break ;
      
      default :
      System.out.println("Unvalid size");

      
}//end of switch 

return price ; 

}//end of method


public String toString()
{
return super.toString()+", Price of CupCake: "+calculatePrice()+" SR\n";
}


}//end of CupCake class

