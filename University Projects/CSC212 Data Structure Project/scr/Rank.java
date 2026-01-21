
public class Rank {

    static String Query;
    static InvertedBST inverted;
    static OurIndex index;
    static OurLinkedList<Integer> docQuery;
    static BSTMod<Integer> docRank;

    public Rank(InvertedBST inverted, OurIndex index, String Query) {
        this.inverted = inverted;
        this.index = index;
        this.Query = Query;
        docQuery = new OurLinkedList<>();
        docRank = new BSTMod<>();
    }

    public static void addBST() {
    	RankQ(Query); // To fill all_doc_in_query

        if (docQuery.empty()) {
            System.out.println("empty query");
            return;
        }

        docQuery.findFirst();
        while (!docQuery.last()) {
        	OurDoc d = getById(docQuery.retrieve());
            int Rank = getDocByScore(d, Query);
            docRank.insert(Rank, docQuery.retrieve());
            docQuery.findNext();
        }

        OurDoc d = getById(docQuery.retrieve());
        int Rank = getDocByScore(d, Query);
        docRank.insert(Rank, docQuery.retrieve());
    }

    public static void displayDocsByScore() {
    	docRank.display_decreasing();
    }

    public static OurDoc getById(int id) {
        return index.getDocById(id);
    }

    public static int termFreq(OurDoc d, String term) {
        int freq = 0;
        OurLinkedList<String> words = d.words;
        if (words.empty()) return 0;

        words.findFirst();
        while (!words.last()) {
            if (words.retrieve().equalsIgnoreCase(term))
                freq++;
            words.findNext();
        }

        if (words.retrieve().equalsIgnoreCase(term))
            freq++;

        return freq;
    }

    public static int getDocByScore(OurDoc d, String Query) {
        if (Query.length() == 0) return 0;

        String terms[] = Query.split(" ");
        int sum_freq = 0;

        for (String term : terms) {
            sum_freq += termFreq(d, term.trim().toLowerCase());
        }

        return sum_freq;
    }

    public static void RankQ(String Query) {
        OurLinkedList<Integer> A = new OurLinkedList<>();
        if (Query.length() == 0) return;

        String terms[] = Query.split(" ");
        boolean found = false;

        for (String term : terms) {
            found = inverted.searchInvBST(term.trim().toLowerCase());
            if (found) {
                A = inverted.invertedBst.retrieve().doc_IDS;
                Add1List(A);
            }
        }
    }

    public static void Add1List(OurLinkedList<Integer> A) {
        if (A.empty()) return;

        A.findFirst();
        while (!A.empty()) {
            boolean found = FoundIn(docQuery, A.retrieve());
            if (!found) { // Not found in result
            	insertById(A.retrieve());
            }

            if (!A.last())
                A.findNext();
            else
                break;
        }
    }

    public static void insertById(Integer id) {
        if (docQuery.empty()) {
        	docQuery.insert(id);
            return;
        }

        docQuery.findFirst();
        while (!docQuery.last()) {
            if (id > docQuery.retrieve()) {
                Integer id1 = docQuery.retrieve();
                docQuery.update(id);
                docQuery.insert(id1);
                return;
            } else {
            	docQuery.findNext();
            }
        }

        if (id > docQuery.retrieve()) {
            Integer id1 = docQuery.retrieve();
            docQuery.update(id);
            docQuery.insert(id1);
        }
    }

    public static boolean FoundIn(OurLinkedList<Integer> result, Integer id) {
        if (result.empty()) return false;

        result.findFirst();
        while (!result.last()) {
            if (result.retrieve().equals(id)) {
                return true;
            }
            result.findNext();
        }

        return result.retrieve().equals(id);
    }
}
