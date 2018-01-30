package DataStructures;

import java.util.Vector;

public class TRIE implements Tree{

	public TRIE_Node root;
	public String result;
	public int number = 0;

	public TRIE() {
		root = new TRIE_Node("");
	}
	
	@Override
	public void add(Tree_Node current, String word, String file) {
		// TODO Auto-generated method stub
		add((TRIE_Node)current,word,0,file);
	}

	private void add(TRIE_Node current, String word, int charAt, String fileName) {

		if (charAt + 1 > word.length()) {
			if (current.FileNames == null){
				current.FileNames = new LinkList();
			number++; 
			}
			current.FileNames.add(fileName);
			return;
		} else {
			if (current.childs == null) {
				current.childs = new Vector<TRIE_Node>();
				current.childs.add(new TRIE_Node(String.valueOf(word.charAt(charAt)).toLowerCase()));
				add(current.childs.elementAt(current.childs.size() - 1), word, charAt + 1, fileName);
			} else {
				int exist = exist(current, String.valueOf(word.charAt(charAt)).toLowerCase());
				if (exist >= 0) {
					add(current.childs.elementAt(exist),word, charAt + 1, fileName);
				} else {
					current.childs.add(new TRIE_Node(String.valueOf(word.charAt(charAt)).toLowerCase()));
					add(current.childs.elementAt(current.childs.size() - 1), word, charAt + 1, fileName);
				}
			}
		}
	}
	
	@Override
	public void printAllWordsFiles(Tree_Node current) {
		result = "";
		printAllWordsFiles((TRIE_Node)current, "");
	}

	private void printAllWordsFiles(TRIE_Node current,String incompleteWord){

		if(current.value == null)
			return ;
		else{
		if(current.FileNames != null){
			result += incompleteWord+current.value +"\t" + current.FileNames.print(current.FileNames.root.forward) + "\n";
		}
		if(current.childs!=null)
		for(int i =0 ;i<current.childs.size();i++)
			printAllWordsFiles(current.childs.get(i), incompleteWord+current.value);
		}
	}
	
	@Override
	public LinkList printFiles(Tree_Node current, String word) {
		return printFiles((TRIE_Node)current, word, 0);
	}
	
	private LinkList printFiles(TRIE_Node current, String word, int charAt){
		
		int exist = exist(current, String.valueOf(word.charAt(charAt)).toLowerCase());
		if(exist<0){
			return null;
		}	
		else{
			if(charAt+1 == word.length()){
				current = current.childs.elementAt(exist);
				if(current.FileNames != null){
					return current.FileNames;
				}
				else
					return null;
			}
			else
				return printFiles(current.childs.elementAt(exist), word, charAt+1);
		}
	}
	
	@Override
	public boolean IsStopWord(Tree_Node current, String word) {
		// TODO Auto-generated method stub
		return IsStopWord((TRIE_Node)current, word, 0);
	}
	
	private boolean IsStopWord(TRIE_Node current, String word, int charAt) {
	
		int exist = exist(current, String.valueOf(word.charAt(charAt)).toLowerCase());
		if(exist<0){
			return false;
		}	
		else{
			if(charAt+1 == word.length()){
				current = current.childs.elementAt(exist);
				if(current.FileNames != null){
				return true;
				}
				else
					return false;
			}
			else
				return IsStopWord(current.childs.elementAt(exist), word, charAt+1);
		}
	}
	
	@Override
	public void delete(Tree_Node before, Tree_Node current, String file) {
		// TODO Auto-generated method stub
		delete((TRIE_Node)before,(TRIE_Node)current,file);
	}
	
	private void delete(TRIE_Node before,TRIE_Node current, String fileName){
		
		if(current == null)
			return;
		else{
			if(current.FileNames != null){
				current.FileNames.delete(fileName);
			if(current.FileNames.root.forward == null){
				current.FileNames = null;
				number--;
				if(current.childs == null){
					before.childs.remove(current);
					current = null;
				}
				}
			}
			if(current.childs != null)
				for(int i = 0 ; i<current.childs.size();i++)
					delete(current,current.childs.elementAt(i), fileName);
		}
	}
	
	public int exist(TRIE_Node current,String character){
		
		if(current.childs == null)
			return -1;
		
		for(int i=0;i<current.childs.size();i++){
			if(character.equals(current.childs.get(i).value))
				return i;
			}
		return -1;	
	}

	@Override
	public Tree_Node getRoot() {
		// TODO Auto-generated method stub
		return (Tree_Node)root;
	}

	@Override
	public int getWordNumber() {
		// TODO Auto-generated method stub
		return number;
	}

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public int treeHeight(Tree_Node current) {
		return treeHeight((TRIE_Node)current);
	}
	
	private int treeHeight(TRIE_Node current){
		
		if(current == null)
			return 0;
		else{
			int max= 0;
			if(current.childs != null)
			for(int i=0;i<current.childs.size();i++)
				max = Math.max(max, treeHeight(current.childs.elementAt(i)));
			return max+1;
		}		
	}
}
