public class OurDoc {

    public OurLinkedList<String> words = new OurLinkedList<>();
    public int id;
    String content;

    // Constructor
    public OurDoc(int id, OurLinkedList<String> words , String content) {
        this.id = id;
        this.words = words;
        this.content=content;
    }
}
