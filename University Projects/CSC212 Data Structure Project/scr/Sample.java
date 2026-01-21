
import java.util.*;

public class Sample {
	public static void main ( String[] args ) {


		System.out.println("");
		System.out.println("---------------------------------- Welcome to our Search Engine ---------------------------------- ");


		String stopWordsPath = "/Users/juju/Desktop/pro212/CSC212/stop.txt";
		String datasetPath = "//Users/juju/Desktop/pro212/CSC212/dataset.csv";

		MainClass d = new MainClass();
		d.ReadFiles(stopWordsPath, datasetPath);
		Scanner input = new Scanner(System.in);
		int choice =0 ;
		

		do {
	
			System.out.println("\nPlease feel free to encounter with our Engine:");
			System.out.println("1- Retrieve a term \n( there are choices: using index with lists – inverted index with lists – inverted index with BST)\n");
			System.out.println("2-Boolean retrieval ( using AND – OR )\n");
			System.out.println("3-Ranked retrieval\n");
			System.out.println("4-Indexed document: print all documents\n");
			//System.out.println("5-Number of documents in the index ");
			//System.out.println("6-Number of unique in the index ");
			System.out.println("5-Show inverted index  with List \n");
			System.out.println("6-Show inverted index  with BST \n");
			System.out.println("7-Show Number of tokens and number of unique words including stop words\n ");
			System.out.println("8-Exit\n\n");
			
			choice =input.nextInt();
			switch(choice) {
			
			case 1 :
				System.out.println("Enter term to retrieve ");
				String term = input.next();
				term = term.toLowerCase().trim();
				
				System.out.println("-using index with list");
				OurLinkedList<Integer> result = MainClass.index.getDocByTerm(term);
				
				 if (result != null && !result.empty()) {
				System.out.print("Word : "+ term +"\n"+ "{");
				result.display();
				System.out.println("} ");
				 }else {
				        System.out.println("Not found in index with list");
				    }
				 
				 
				System.out.println("- inverted index with lists");
				boolean exist = d.inverted.searchInverted(term);
				if(exist) {
					d.inverted.invertedIndex.retrieve().display();
					System.out.println("");
				}
				else
					System.out.println("Not found in inverted index with lists");
				
				
				
				System.out.println("- inverted index with BST");
				boolean exist2 = d.invertedBST.searchInvBST (term);
				if(exist2) {
					d.invertedBST.invertedBst.retrieve().display();
					System.out.println("");
				}
				else
					System.out.println("Not found in inverted index with BST list");
			break;
			
			
			case 2 :
				input.nextLine();
				System.out.println("Enter Query to retrieve ");
				String Query = input.nextLine();
				Query = Query.toLowerCase();
				Query = Query.replaceAll("and", "AND");
				Query = Query.replaceAll(" or", "OR");
				
				System.out.println("\nWhich method you want to make Query : ");
				System.out.println("1- for using index\n");
				System.out.println("2- for using inverted index list of lists\n");
				System.out.println("3- for using BST\n");
				System.out.println("4- for Exit\n\n");

				int a = input.nextInt();
				
				do {
					if(a==1) {
						QIndex Q = new QIndex(d.index);
						System.out.println("========"+ Query +"========");
						OurLinkedList result1 = QIndex.AllQuery(Query);
						d.displayDocsByIDS(result1);
					}
					else if(a == 2) {
						QInverted q2 = new QInverted(d.inverted);
						System.out.println("========"+ Query +"========");
						OurLinkedList result2 = QInverted.AllQuery(Query);
						d.displayDocsByIDS(result2);
					}
					else if(a == 3) {
						
						QBST q3 = new QBST(d.invertedBST);
						System.out.println("========"+ Query +"========");
						OurLinkedList result3 = QBST.AllQuery(Query);
						d.displayDocsByIDS(result3);
					}
					else if (a == 4)
						break;
					else
						System.out.println("Wrong query");
					
					
					System.out.println("\nWhich method you want to make Query : ");
					System.out.println("1- for using index \n\n");
					System.out.println("2- for using inverted index list of lists\n\n");
					System.out.println("3- for using BST\n\n");
					System.out.println("4- for Exit\n\n");

					a = input.nextInt();
					
				}while(a !=4);
				break;

			case 3 :
				input.nextLine();
				System.out.println("Enter Query to Rank ");
				String Query2 = input.nextLine();
				Query2 = Query2.toLowerCase();
				
				Rank rank = new Rank(d.invertedBST , MainClass.index , Query2);
				rank.addBST();
				rank.displayDocsByScore();
				break;
				
			case 4 :
				d.index.displayDocs();
				break;
				
			/*
			 * case 5 : System.out.println("Number of document :" +
			 * Driver.ind.document.count); System.out.println("-----------------------");
			 * break;
			 * 
			 * case 6 : System.out.println("Number of unique words without stop words : " +
			 * d.inv.invertedList.count); System.out.println("-----------------------");
			 * break;
			 */
			case 5 :
				d.inverted.displayInverted();
				System.out.println("");
				break;
				
			case 6 :
					d.invertedBST.displayInvBST();
					System.out.println("");
				break;
					
			
			  case 7 : System.out.println("Number of tokens : " + d.num_tokens);
			  System.out.println("Number of unique words including stop words : " +
			  d.NoUnique); break;
			 
			case 8:
				System.out.println("See you soon , bye!");
					break;
					
			default:
				System.out.println("Wrong input!");
				break;
					
			}
			
		}while(choice != 8);
		

    }
	

}