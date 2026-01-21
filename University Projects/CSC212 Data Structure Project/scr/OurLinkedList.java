
public class OurLinkedList<T> implements OurList<T> {
    int n=0;
 private OurNode<T> head; 
 private OurNode<T> current; 

 public OurLinkedList () { 
   head = current = null; 
  } 

 public boolean empty () {
   return head == null;
} 

 public boolean last () { 
   return current.next == null;   
}

 public void findFirst( ) {
    current = head;
 }
    
 public void findNext( ) {
     current = current.next;
 } 
 
 public T retrieve( ) {
     return current.data;
 } 
 
 public void update(T val) {
     current.data = val;
 } 
 
 public void insert(T val) {
	 n++;
     OurNode<T> tmp; 
     if (empty()) { 
         current = head = new OurNode<T> (val);
     } 
     else {
         tmp = current.next; 
         current.next = new OurNode<T> (val); 
         current = current.next; 
         current.next = tmp;
     }
 }
 
 public void remove( ) {
   if (current == head) { 
          head = head.next; 
   } 
   else { 
          OurNode<T> tmp = head; 
          while (tmp.next != current) tmp = tmp.next; 
          tmp.next = current.next; } 
   
   if (current.next == null) 
          current = head; 
   else 
          current = current.next; 
 }
 
 public boolean full( ) {
   return false;  
 }
 
 public boolean exist (T x) {
 OurNode<T>p=head; 
 while (p !=null) {
 if(p.data.equals (x)) 
 return true;
 p=p.next;}
 return false;}
 
 public int size() {
 int n=0;
 OurNode<T> p= head;
 while(p!=null) {
	 n++;
	 p=p.next;}
 return n;
 }
 
 public void display () {
	if (head == null) 
			System.out.println("Empty list"); 
	OurNode<T> p=head;
	while(p!=null) {
		System.out.print(p.data+ " ");
		p=p.next;
	}
 }
 
 
}


