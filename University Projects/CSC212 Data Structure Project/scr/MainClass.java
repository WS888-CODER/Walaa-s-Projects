import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainClass{
 OurLinkedList<String> stopWords;
 static OurIndex index;
 Inverted inverted;
 InvertedBST invertedBST ;
 int num_tokens=0;
OurLinkedList<String> uniques=new OurLinkedList<>();
int NoUnique=0;

  public MainClass () {
stopWords=new OurLinkedList<>() ;
index=new OurIndex () ;
inverted=new Inverted();
invertedBST=new InvertedBST () ;
}

public void ReadStops (String fileName) {
try {
File f=new File (fileName) ;
Scanner s=new Scanner (f) ;
while (s.hasNextLine() ) {
String line=s.nextLine(); 
stopWords.insert(line);
}
}
catch(IOException e) {
e.printStackTrace() ;}

}

public void ReadDocs(String fileName) {
String line=null;
try {
File f=new File (fileName) ;
Scanner s=new Scanner(f) ;
//
s.nextLine() ;
while (s.hasNextLine()){
line=s.nextLine() ;
if (line.trim().length() <3)
{
break;
}
String x=line.substring(0,line.indexOf(','));
int id = Integer.parseInt(x.trim());
//System.out.println("Line="+line);
String content = line.substring(line.indexOf(',')+1).trim();
//System.out.println("content="+content);

OurLinkedList<String>wordsInDoc=CreatLSofWordsInDocs(content, id) ;
index.insertDoc(new OurDoc(id, wordsInDoc,content));
CountTokenANDUniqueWords(content);
}

}catch (Exception e) {
	System.out.println("end of file");
}
}

public OurLinkedList<String > CreatLSofWordsInDocs (String content, int id){
	OurLinkedList<String>wordsInDoc=new OurLinkedList<String> ();
	CreateIndexInv(content, wordsInDoc, id);

return wordsInDoc;
}

public void CreateIndexInv(String content, OurLinkedList<String>wordsInDoc, int id) {
	 while (content.contains("-")){
         if (content.charAt(content.indexOf("-")-2)==' ')
        	 content=content.replaceFirst("-","");
         else
        	 content=content.replaceFirst("-"," ");
     }  
	 content = content.toLowerCase().replaceAll ("[^a-zA-Z0-9 ]", "") ;
	 String[] tokens = content.split("\\s+") ;

for (String w : tokens) {
if(! InSWords(w)) {
	wordsInDoc.insert(w); 
inverted.add(w, id);
invertedBST.add(w, id);;
}
}}

public void CountTokenANDUniqueWords(String Content) {
    Content=Content.replaceAll("\'"," ");
    Content=Content.replaceAll("-"," ");
    Content = Content.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
    String[] tokens = Content.split("\\s+");
    num_tokens +=tokens.length;
    for (String w : tokens) {
        if (!uniques.exist(w)){
        	uniques.insert(w);
        	NoUnique++;
        }

    }
}
//check if the word exist in the stop words
		public boolean InSWords (String word) {
			if(stopWords == null || stopWords.empty())
				return false;
			
			stopWords.findFirst();
			while(!stopWords.last()) {
				if(stopWords.retrieve().equals(word)) {
					return true;
				}
				stopWords.findNext();
			}
			if(stopWords.retrieve().equals(word)) {
				return true;
			}
			return false;
		}


public void ReadFiles (String stop_file, String documents_file) {
	ReadStops(stop_file);
	ReadDocs(documents_file);
}

public void displayDocsByIDS (OurLinkedList<Integer>IDs) {
if (IDs.empty()) {
	System.out.println("no documents exist");
    return;}

IDs.findFirst();
while (!IDs.last())
{
OurDoc d=index.getDocById(IDs.retrieve()) ;
if (d!=null) {
System.out.println ("Document: "+d.id+":"+d.content) ;}
IDs.findNext() ;}
OurDoc d=index.getDocById(IDs.retrieve());
if (d!=null) {
System.out.println ("Document "+d.id+": "+d.content) ;}
System.out.println("") ;
}


public void displaystops () {
stopWords.display(); }
}