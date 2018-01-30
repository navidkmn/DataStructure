package DataStructures;

public class TST_Node extends Tree_Node {

	TST_Node RC;
	TST_Node LC;
	TST_Node MC;
	
	int rightChildsHeight;
	int leftChildsHeight;
	
	LinkList FileNames;
	//String value;

	public TST_Node(String word) {
		super(word);
		RC = null;
		LC = null;
		MC = null;
		rightChildsHeight = 0;
		leftChildsHeight = 0;
		FileNames = null;
	//	value = word;
	}
}
