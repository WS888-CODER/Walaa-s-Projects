
public class Inverted {
	OurLinkedList<OurWord> invertedIndex;
	public Inverted() {
		invertedIndex=new OurLinkedList<OurWord> () ;}
	
		public void add (String text, int id) {
			if( ! searchInverted(text) ) {
					OurWord w=new OurWord(text) ;
					w.doc_IDS.insert(id) ;
					invertedIndex.insert(w);
		}
		else {
		OurWord existing_word=invertedIndex.retrieve();
		existing_word.add_Id(id) ;
		}
	
}
		
		public boolean searchInverted( String w)
		{
		if (invertedIndex==null|| invertedIndex.empty () )
		return false;
		invertedIndex.findFirst();	
		while (!invertedIndex.last()) {
	if(invertedIndex.retrieve().text.equals(w)) {
			return true;
			}
	invertedIndex.findNext() ;
		}
			if (invertedIndex.retrieve().text.equals(w)) {
			return true;
			}
			return false;
}
		
		
		public void displayInverted() {
		if(invertedIndex==null){
		System.out.println ("null inverted index") ;
		return;}
		else if(invertedIndex.empty () ) {
		System.out.println ("empty inverted index") ;
		return;}
		
		invertedIndex.findFirst() ;
		while (!invertedIndex.last()) {
		OurWord w = invertedIndex.retrieve ();
		w.display();
		invertedIndex.findNext () ;
		}
		OurWord w = invertedIndex.retrieve ();
		w.display();
		invertedIndex.findNext () ;
		

		}
		
}
