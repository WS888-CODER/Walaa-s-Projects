
public class InvertedBST {
	

	OurBST<OurWord>invertedBst;
	public InvertedBST() {
		invertedBst=new OurBST<OurWord>() ;
	}
	public void isertFromInv (Inverted inverted) {

	if (inverted.invertedIndex.empty())
	return;
	inverted.invertedIndex.findFirst();
	while (!inverted.invertedIndex.last()) {
		invertedBst.insert(inverted.invertedIndex.retrieve().text,inverted.invertedIndex.retrieve()) ;
	inverted.invertedIndex.findNext() ;
	}
	invertedBst.insert(inverted.invertedIndex.retrieve().text,inverted.invertedIndex.retrieve()) ;
}
	
	public void displayInvBST() {
	if (invertedBst==null) {
	System.out.println ("null inverted index") ;
	return;
	}
	else if (invertedBst.empty()) {
	System.out.println ("empty inverted_index");
	return;}
	invertedBst.inorder() ;
	}
	
	public void add (String text, int id) {
	if (!searchInvBST(text) ) {
		OurWord w=new OurWord(text) ;
	w.doc_IDS.insert(id) ;
	invertedBst.insert(text, w) ;
	}
	else {
	OurWord exist=invertedBst.retrieve() ;
	exist.add_Id(id) ;
	}
	}
	
	public boolean searchInvBST( String w) {
		return invertedBst.findKey(w) ;
	}
	
	
	
	
}
