
public class QBST {
static InvertedBST inv;
	public QBST(InvertedBST inv) {
		this.inv = inv;
		
	}
	
	public static OurLinkedList<Integer> And(String Q) {
		OurLinkedList<Integer> L1 = new OurLinkedList<Integer> ();
		OurLinkedList<Integer> L2 = new OurLinkedList<Integer> ();
		String[] Split = Q.split("AND");
		if(Split.length == 0 )
			return L1;
		boolean exist = inv.searchInvBST(Split[0].trim().toLowerCase());
		if(exist) {
			L1 = inv.invertedBst.retrieve().doc_IDS;
		}
		for(int i = 1; i < Split.length; i++) {
			 exist = inv.searchInvBST(Split[i].trim().toLowerCase());
			if(exist) {
				L2 = inv.invertedBst.retrieve().doc_IDS;
				
			}
			L1 = And(L1,L2);
		}
		return L1;
		

	}
	
	public static  OurLinkedList<Integer> And( OurLinkedList<Integer> L1 ,  OurLinkedList<Integer> L2) {
		OurLinkedList<Integer> OutCome = new OurLinkedList<Integer> ();
		 if(L1.empty() || L2.empty()) 
			 return OutCome;
		 L1.findFirst(); 
		 while(true ) {
	      boolean exist = inOutCome(OutCome,L1.retrieve());
	      if(!exist) {
	    	  L2.findFirst();
	    	  while(true) {
	    		  if(L2.retrieve().equals(L1.retrieve())) {
	    			  OutCome.insert(L1.retrieve());
	    			  break;
	    		  }
	    		  if(!L2.last()) 
	    			  L2.findNext();
	    		  else
	    			  break;
	    	  }
	      }
	      if(!L1.last()) 
	    	  L1.findNext();
	      else
	    	  break;
		 }
		 return OutCome;
		
	}
	public static boolean inOutCome(OurLinkedList<Integer>  OutCome , Integer ID) {
		if(OutCome.empty()) 
			return false;
		OutCome.findFirst();
		while(!OutCome.last()) {
			if(OutCome.retrieve().equals(ID))
				return true;
			OutCome.findNext();
		}
		if(OutCome.retrieve().equals(ID))
			return true;
		return false;
	}
	
	public static OurLinkedList<Integer> Or(String Q) {
		OurLinkedList<Integer> L1 = new OurLinkedList<Integer> ();
		OurLinkedList<Integer> L2 = new OurLinkedList<Integer> ();
		String[] Split = Q.split("OR");
		if(Split.length == 0 )
			return L1;
		boolean exist = inv.searchInvBST(Split[0].trim().toLowerCase());
		if(exist) {
			L1 = inv.invertedBst.retrieve().doc_IDS;
		}
		for(int i = 1; i < Split.length; i++) {
			 exist = inv.searchInvBST(Split[i].trim().toLowerCase());
			if(exist) {
				L2 = inv.invertedBst.retrieve().doc_IDS;
				
			}
			L1 = Or(L1,L2);
		}
		return L1;
		

	}

	public static  OurLinkedList<Integer> Or( OurLinkedList<Integer> L1 ,  OurLinkedList<Integer> L2) {
		OurLinkedList<Integer> OutCome = new OurLinkedList<Integer> ();
		 if(L1.empty() && L2.empty()) 
			 return OutCome;
		 L1.findFirst(); 
		 while(!L1.empty()) {
	      boolean exist = inOutCome(OutCome,L1.retrieve());
	      if(!exist) {
	    	  OutCome.insert(L1.retrieve());
	      }
	    		  if(!L1.last()) 
	    			  L1.findNext();
	    		  else
	    			  break;
	    	  }
	      L2.findFirst();
	      while(!L2.empty()) {
	    	  boolean exist = inOutCome(OutCome,L2.retrieve());
	      if(!exist) {
	    	  OutCome.insert(L2.retrieve());
	      }
	      
	      if(!L2.last()) 
	    	  L2.findNext();
	      else
	    	  break;
		 }
		 return OutCome;
		
	}

	public static  OurLinkedList<Integer> Boolean( String Q) {
		if(!Q.contains("AND")&& !Q.contains("OR"))
			return And(Q);
		else
			if(Q.contains("AND") && !Q.contains("OR"))
				return And(Q);
			else
				if(!Q.contains("AND") && Q.contains("OR"))
					return Or(Q);
				else
					return  AllQuery(Q);

	}
	public static  OurLinkedList<Integer> AllQuery( String Q) {
		OurLinkedList<Integer> L1 = new OurLinkedList<Integer> ();
		OurLinkedList<Integer> L2 = new OurLinkedList<Integer> ();
		if(Q.length() == 0)
			return L1;
		String[] Split = Q.split("OR");
		L1 = And(Split[0]);
		for(int i = 1 ; i < Split.length ; i++) {
			L2 = And(Split[i]);
			L1 = Or(L1,L2);
		}
		return L1;
		
		
		
	}
	
	
}