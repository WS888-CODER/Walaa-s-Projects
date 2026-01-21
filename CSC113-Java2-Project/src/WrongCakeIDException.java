
package test;


public class WrongCakeIDException extends Exception { //user defiend exception #checked

    
    public WrongCakeIDException() {
        super("The Cake ID is not exist please enter the correct ID");
    }

    
    public WrongCakeIDException(String msg) {
        super(msg);
    }
}
