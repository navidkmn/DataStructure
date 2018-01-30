package DataStructures;

public interface Tree {
	
	Tree_Node getRoot();
	int getWordNumber();
	String getResult();
	
	void add(Tree_Node current,String word,String file);
	void delete(Tree_Node before,Tree_Node current,String file);
	void printAllWordsFiles(Tree_Node current);
	LinkList printFiles(Tree_Node current,String word);
	boolean IsStopWord(Tree_Node current,String word);
	int treeHeight(Tree_Node current);
}
