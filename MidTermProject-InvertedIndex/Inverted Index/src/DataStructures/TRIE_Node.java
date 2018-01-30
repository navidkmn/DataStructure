package DataStructures;

import java.util.Vector;

public class TRIE_Node extends Tree_Node {

	Vector<TRIE_Node> childs;	
//	String value;
	LinkList FileNames;

	public TRIE_Node(String word) {
		super(word);
		childs = null;
		FileNames = null;
//		value = word;
	}
}
