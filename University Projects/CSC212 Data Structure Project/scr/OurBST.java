public class OurBST<T> {
	
private OurBSTNode<T> root, current;

public OurBST () {
current = root = null;}

public boolean empty () {
return root ==  null;}

public boolean full () {
return false;}

public T retrieve () {
return current.data;}


public boolean findKey (String k)
{
	OurBSTNode<T> p = root;
while (p != null) {
current = p;
if (k.compareToIgnoreCase (p.key)==0) {
return true;}
else if (k.compareToIgnoreCase ( p.key) <0) {
p = p.left; }
else {
p = p. right; }
}// end while
return false; }

public boolean insert (String k, T val) {
if (root == null) {
current = root = new OurBSTNode <T>(k, val);
return true;} 

OurBSTNode<T> p = current; 
if (findKey (k)) {
current = p;
return false;}

OurBSTNode<T> tmp = new OurBSTNode<T> (k, val) ;
if (k.compareToIgnoreCase (current. key) <0) {
current.left = tmp;}
else {
current.right = tmp;
}
current = tmp;
return true;
}


public void inorder () {
if (root==null)
System.out.println ("empty tree") ;
else
	inorder(root) ;}

private void inorder (OurBSTNode p) {
if (p==null) return;
inorder (p.left) ;

System.out.println ();
System.out.println ("key= "+ p.key) ;
//System.out.println (p.data) ;
((OurWord)p.data).display();

inorder (p.right) ;
}

public void preOrder () {
if (root==null)
System.out.println ("empty tree");
else
preOrder (root) ;}

private void preOrder (OurBSTNode p) {
	if (p==null) return;
	System.out.println ("key= "+ p.key) ;
	System.out.println (p.data.toString());
	preOrder (p.left) ;
	preOrder (p.right) ;
	
}

public OurList <String> getKeys (){
	OurList<String> result=new OurLinkedList<String>();
get_inorder (root, result) ;
return result; 
}

private void get_inorder (OurBSTNode<T>p, OurList<String> result) {
if (p==null) return;
else {
get_inorder (p.left, result);
result.insert (p.key) ;
get_inorder (p.right, result);
}
}


}


