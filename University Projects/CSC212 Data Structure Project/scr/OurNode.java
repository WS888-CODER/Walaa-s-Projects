public class OurNode<T> { 

    public T data; 
    public OurNode<T> next; 

    public OurNode () { 
        data = null; 
        next = null; 
    } 

    public OurNode (T val) { 
        data = val; 
        next = null; 
    } 

}