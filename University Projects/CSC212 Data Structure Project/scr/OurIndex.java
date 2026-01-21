 public class OurIndex {

    OurLinkedList<OurDoc> Docs;

    public OurIndex() {
    	Docs = new OurLinkedList<OurDoc>();
    }

    public void insertDoc(OurDoc d) {
    	Docs.insert(d);
    }

    //////////////helping methods////////////
    public void displayDocs() {
        if (Docs == null) {
            System.out.println("null docs");
            return;
        } else if (Docs.empty()) {
            System.out.println("empty docs");
            return;
        }

        Docs.findFirst();
        while (!Docs.last()) {
            OurDoc doc = Docs.retrieve();
            System.out.println("\n");
            System.out.println("ID: " + doc.id);
            doc.words.display();
            System.out.println("");
            System.out.println("tokens:"+ doc.words.n);


            Docs.findNext();
        }

        // Print the last document
        OurDoc doc = Docs.retrieve();
        System.out.println("\n");
        System.out.println("ID: " + doc.id);
        // System.out.println("all words in this doc are:");
        doc.words.display();
        System.out.println("");
        System.out.println("tokens:"+ doc.words.n);

    }
    public OurDoc getDocById (int id) {
    if (Docs.empty()) {
    System.out.println ("no documents exist");
    return null;}
    Docs.findFirst() ;
    while (!Docs.last())
    { 
    	if(Docs.retrieve().id==id)
        return Docs.retrieve () ;
    	Docs.findNext() ;
        }
    
    if (Docs.retrieve().id==id)
    return Docs.retrieve();
    return null;
    }
    
    public OurLinkedList<Integer> getDocByTerm(String t) {
    	OurLinkedList<Integer> result = new OurLinkedList<>();
		
		if(Docs.empty()) {
			System.out.println("No document exist");
			return result;
		}
		
		Docs.findFirst();
		while(!Docs.last()) {
			if(Docs.retrieve().words.exist(t.toLowerCase().trim()))
				result.insert(Docs.retrieve().id);
			Docs.findNext();	
		}
		if(Docs.retrieve().words.exist(t.toLowerCase().trim()))
			result.insert(Docs.retrieve().id);
		
		return result;
	}
	


   
}
