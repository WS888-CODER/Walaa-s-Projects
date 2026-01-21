
package test;
import java.io.*;
public class Order implements Serializable {
  //attribute
private String name ; // customer name 
private String orderID ; 
private int numOfCake ; 
private Cake [] cList ; //array of Cake  


//counstructor 
Order(String n , String oID, int size)
{
name = n ; 
orderID = oID ; 
cList = new Cake [size] ; //customer can order 10 cakes only in one order
numOfCake = 0 ;
}//end of constructor

//method to add Cake object to cList array 
public boolean addCake(Cake c ){
if(numOfCake < cList.length)
      {
      cList[numOfCake++] = c ; //aggregation
      return true ;
      }
else 
return false ; 
}//end of add method


//method to remove a cake from cList
public boolean removeCake(String cakeID)
{
for(int i=0; i<numOfCake; i++)
if (cList[i].getCakeid().equals(cakeID))
   {
   cList[i] = cList[numOfCake-1]; //delet by replasement
   cList[--numOfCake] = null;
   return true ; 
   }
   return false ;   
}//end of method 


//method to search for wedding cake with specified size and return num of them
public int getWeddingCake( char size){
int num = 0 ; //nom of wedding Cakes 

for(int i = 0 ; i < numOfCake ; i++)
{
if(cList[i] instanceof WeddingCake && cList[i].getSize() == size)
num++;
}
return num;
}//end of method 


//method to get total cost of the order 
public double getTotalCost()
{
double total = 0;

for(int i = 0 ; i < numOfCake ; i++)
   {
   total += cList[i].calculatePrice();
   }//end for
return total ; 
}//end of method 

public String toString()
{
String str = "Customer Name: "+name+"\nOrder ID: "+orderID+"\nNumber of cakes: "+numOfCake+
"\nInformation of all cakes in the order\n";

for(int i = 0 ; i<numOfCake ; i++)
   str+="Cake "+(i+1)+":\n"+cList[i].toString();
   
 return str+ "\nTOTAL COST: "+getTotalCost();
}

    public int getNumOfCake() {
        return numOfCake;
    }

    public Cake[] getcList() {
        return cList;
    }

    public String getName() {
        return name;
    }

    public String getOrderID() {
        return orderID;
    }
    

}//end of Order class   

