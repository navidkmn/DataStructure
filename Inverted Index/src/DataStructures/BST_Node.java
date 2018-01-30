package DataStructures;

public class BST_Node extends Tree_Node {

	BST_Node RC;
	BST_Node LC;
	
	int rightChildsHeight;
	int leftChildsHeight;
	//String value;
	LinkList FileNames;

	public BST_Node(String word) {
		super(word);
		RC = null;
		LC = null;
		rightChildsHeight = 0;
		leftChildsHeight = 0;
		FileNames = null;
		//value = word;
	}
}
