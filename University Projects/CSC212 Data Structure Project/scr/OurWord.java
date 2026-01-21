
public class OurWord {
    String text;
    OurLinkedList<Integer> doc_IDS;

    public OurWord(String w) {
        text = w;
        doc_IDS = new OurLinkedList<Integer>();
    }

    public void add_Id(int id) {
        if (!existsInDocIDS(id)) {
            doc_IDS.insert(id);
        }
    }

    
    
    
    public void display () {
    System.out.println("\n----------------------");
    System.out.print("word:" + text ) ;
    System.out.print(" {");
    doc_IDS.display ();
    System.out.print ("}") ;}
    
    
    
    
    
    
    public boolean existsInDocIDS(int id) {
        if (doc_IDS.empty()) {
            return false;
        }
        doc_IDS.findFirst();
        while (!doc_IDS.last()) {
            if (doc_IDS.retrieve().equals(id)) {
                return true;
            }
            doc_IDS.findNext();
        }
        // Check the last element
        if(doc_IDS.retrieve().equals(id)) {
        	return true;
    }
        return false; }
}
