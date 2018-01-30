package DataStructures;

import java.util.Vector;

public class TST implements Tree{

	public TST_Node root;
	public int number=0;
	public String result;
	public Vector<TST_Node> generation;

	public TST() {
		root = new TST_Node(null);
	}

	@Override
	public void add(Tree_Node current, String word, String file) {
		generation = new Vector<TST_Node>();
		add((TST_Node)current,word,0,file);
	}
	
	private void add(TST_Node current, String word, int charAt, String fileName) {

		if (current.value == null) {
			current.value = String.valueOf(word.charAt(charAt)).toLowerCase();
			add(current, word, charAt, fileName);
		}
		else {
			int compare = String.valueOf(word.charAt(charAt)).compareTo(current.value);
			generation.add(current);
			
			if (compare == 0) {
				if (charAt + 1 == word.length()) {
					if (current.FileNames == null){
						current.FileNames = new LinkList();
						number++;
					}
					current.FileNames.add(fileName);
					return;
				} else {
					if (current.MC == null) {
						TST_Node newNode = new TST_Node(String.valueOf(word.charAt(charAt + 1)).toLowerCase());
						current.MC = newNode;
						add(current.MC, word, charAt + 1, fileName);
					} else
						add(current.MC, word, charAt + 1, fileName);
				}
			} else {
				if (compare > 0) {
					if (current.RC == null) {
						TST_Node newNode = new TST_Node(String.valueOf(word.charAt(charAt)).toLowerCase());
						current.RC = newNode;
						
						for (int i = generation.size() - 1; i >= 0; i--) {
							TST_Node child = generation.get(i);
							TST_Node father;
							if (i > 0)
								father = generation.get(i - 1);
							else
								father = null;
							balanceTree(father, child);
						}
							generation.clear();
							add(newNode,word,charAt,fileName);
					}else 
						add(current.RC, word, charAt, fileName);
				} else {
					if (current.LC == null) {
						TST_Node newNode = new TST_Node(String.valueOf(word.charAt(charAt)).toLowerCase());
						current.LC = newNode;
						
						for (int i = generation.size() - 1; i >= 0; i--) {
							TST_Node child = generation.get(i); 
							TST_Node father;
							if (i > 0)
								father = generation.get(i - 1);
							else
								father = null;
							balanceTree(father, child);
						}
							generation.clear();
							add(newNode, word, charAt, fileName);
					}else 
						add(current.LC, word, charAt, fileName);
				}
			}
		}
	}
	
	@Override
	public void printAllWordsFiles(Tree_Node current) {
		result = "";
		printAllWordsFiles((TST_Node)current, "");
	}

	private void printAllWordsFiles(TST_Node current, String incompleteWord) {
		
		if(current == null)
			return;
		else{
		if (current.FileNames != null) {
			result += incompleteWord + current.value + "\t"+current.FileNames.print(current.FileNames.root.forward) + "\n";
		}
			printAllWordsFiles(current.MC, incompleteWord + current.value);
			printAllWordsFiles(current.RC, incompleteWord);
			printAllWordsFiles(current.LC, incompleteWord);
		}
	}
	
	@Override
	public LinkList printFiles(Tree_Node current, String word) {
		return printFiles((TST_Node)current, word, 0);
	}
	
	private LinkList printFiles(TST_Node current, String word, int charAt) {
		
		if (current == null) {
			return null;
			
		} else {
			int compare = String.valueOf(word.charAt(charAt)).compareTo(current.value);
			
			if (compare == 0) {
				if (charAt + 1 == word.length()) {
					if(current.FileNames != null)
					return  current.FileNames;
					else{
						return null;
						}
				} else
					return printFiles(current.MC, word, charAt + 1);
			} else {
				if (compare > 0)
					return printFiles(current.RC, word, charAt);
				else
					return printFiles(current.LC, word, charAt);
			}
		}
	}
	
	@Override
	public boolean IsStopWord(Tree_Node current, String word) {
		// TODO Auto-generated method stub
		return IsStopWord((TST_Node	)current, word, 0);
	}
	
	private boolean IsStopWord(TST_Node current, String word , int charAt) {

		if (current == null) {
			return false;
		} else {
			int compare = String.valueOf(word.charAt(charAt)).compareTo(current.value);
			if (compare == 0) {
				if (charAt + 1 == word.length()) {
					if(current.FileNames != null)
						return true;
					else
					return false;
				} else
					return IsStopWord(current.MC, word, charAt + 1);
			} else {
				if (compare > 0)
					return IsStopWord(current.RC, word, charAt);
				else
					return IsStopWord(current.LC, word, charAt);
			}
		}
	}
	
	@Override
	public void delete(Tree_Node before, Tree_Node current, String file) {
		generation = new Vector<TST_Node>();
		delete((TST_Node)before, (TST_Node)current, file);
	}

	private void delete(TST_Node before,TST_Node current, String fileName){
		
		if(current == null)
			return;
		else{
			generation.add(current);
			
			delete(current, current.MC, fileName);
			delete(current, current.LC, fileName);
			delete(current, current.RC, fileName);
		
			if(current.FileNames!=null){
				current.FileNames.delete(fileName);		
				if(generation.size()>0)
					generation.remove(generation.size()-1);


			if(current.FileNames.root.forward == null){
				number--;
				if(current.MC == null){
					deleteNode(before,current);
					generation.clear();
					delete(null,root,fileName);	
				}
				}
		}
		}
	}

	private void deleteNode(TST_Node before, TST_Node current) {

		if (current.LC == null && current.RC == null) {
			if(before!=null)
			before.MC = null;
			else
				root.value = null;
			current = null;
		} else {
			if (current.LC == null && current.RC != null) {
				before.MC = current.RC;
				current = null;
			} else {
				if (current.RC == null && current.LC != null) {
					before.MC = current.LC;
					current = null;
				} else {
					TST_Node maxOfMinimums = current.LC;	
					TST_Node fatherOfMaxOfMinimums = current;
					while (maxOfMinimums.RC != null) {
						fatherOfMaxOfMinimums = maxOfMinimums;
						maxOfMinimums = maxOfMinimums.RC;
					}
					before.MC = maxOfMinimums;
					if(maxOfMinimums.FileNames!=null)
						before.MC.FileNames = maxOfMinimums.FileNames;
					
					if(maxOfMinimums.LC!=null)
						fatherOfMaxOfMinimums.RC = maxOfMinimums.LC;
					maxOfMinimums.LC = current.LC;
					maxOfMinimums.RC = current.RC;
					current = null;
				}
			}
		}
		for (int i = generation.size() - 1; i >= 0; i--) {
			TST_Node child =  generation.get(i);
			TST_Node father;
			if (i > 0)
				father = generation.get(i - 1);
			else
				father = null;
			balanceTree(father, child);
		}
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
		return treeHeight((TST_Node)current);
	}
	
	private int treeHeight(TST_Node current){
		
		if(current == null)
			return 0;
		else{
			int max = 0;
			max = Math.max(treeHeight(current.LC),treeHeight( current.RC));
			max = Math.max(max, treeHeight(current.MC))+1;	
			return max;
		}		
	}
	
	private void balanceTree(TST_Node before,TST_Node current){
		
		if(current.RC != null)	
			current.rightChildsHeight = Math.max(current.RC.rightChildsHeight, current.RC.leftChildsHeight)+1;
		else
			current.rightChildsHeight = 0;
		
			if(current.LC != null)
			current.leftChildsHeight = Math.max(current.LC.rightChildsHeight, current.LC.leftChildsHeight)+1;
			else
				current.leftChildsHeight = 0;
		
		if(current.leftChildsHeight - current.rightChildsHeight == 2){
			if(current.LC.leftChildsHeight > current.LC.rightChildsHeight){
				LLproblem(before,current);
				}
			else{
				LRproblem(before,current);
			}
			}
		if(current.leftChildsHeight - current.rightChildsHeight == - 2){
			if(current.RC.leftChildsHeight > current.RC.rightChildsHeight){
				RLproblem(before,current);
			}
			else{
				RRproblem(before,current);
			}
			}
	}
	
	private void RRproblem(TST_Node before,TST_Node current){

		boolean isLeft = false;	
		TST_Node child = current.RC;
		
		if(before != null && before.LC == current){
			before.LC = child;
			isLeft = true;
		}
		if(before != null && before.RC == current)
			before.RC = child;
		
		if(before!=null && before.MC == current)
			before.MC = child;
		
		if(child.LC != null)
			current.RC = child.LC;
		else
			current.RC = null;
		
		child.LC = current;
		
		if(current.RC != null)
			current.rightChildsHeight = Math.max(current.RC.leftChildsHeight, current.RC.rightChildsHeight)+1;
		else
			current.rightChildsHeight = 0;
		
		if(child.LC != null)
			child.leftChildsHeight = Math.max(child.LC.leftChildsHeight, child.LC.rightChildsHeight)+1;
	
		if(isLeft && before != null && before.LC!=null)
			before.leftChildsHeight = Math.max(before.LC.leftChildsHeight, before.LC.rightChildsHeight)+1;
		if(!isLeft && before != null && before.RC!=null)
			before.rightChildsHeight = Math.max(before.RC.leftChildsHeight, before.RC.rightChildsHeight)+1;
	
		if(before == null)
			root = child;
	}

	private void RLproblem(TST_Node before,TST_Node current){
		
		TST_Node child = current.RC;
		TST_Node childOfChild = child.LC;
		
		current.RC = childOfChild;	
		
		if(childOfChild.RC != null)
			child.LC = childOfChild.RC;
		else
			child.LC = null;
		
		childOfChild.RC = child;
		
		if(child.LC != null)
			child.leftChildsHeight = Math.max(child.LC.leftChildsHeight, child.LC.rightChildsHeight)+1;
		else
			child.leftChildsHeight = 0;
		
		if(childOfChild.RC != null)
			childOfChild.rightChildsHeight = Math.max(childOfChild.RC.leftChildsHeight, childOfChild.RC.rightChildsHeight)+1;

		if(current.RC != null)
			current.rightChildsHeight = Math.max(current.RC.leftChildsHeight, current.RC.rightChildsHeight)+1;

		RRproblem(before, current);
	}
	
	private void LLproblem(TST_Node before,TST_Node current){
		
		boolean IsLeft = false;
		
		TST_Node child = current.LC;
		if(before != null && before.LC == current){
			before.LC = child;
			IsLeft = true;
		}
		if(before != null && before.RC == current)
			before.RC = child;
		
		if(before!=null && before.MC == current)
			before.MC = child;
		
		if(child.RC != null)
			current.LC = child.RC;
		else
			current.LC = null;
		
		child.RC = current;
		
		if(current.LC != null)
			current.leftChildsHeight = Math.max(current.LC.leftChildsHeight, current.LC.rightChildsHeight)+1;
		else
			current.leftChildsHeight = 0;
			
		if(child.LC != null)
			child.leftChildsHeight = Math.max(child.LC.leftChildsHeight, child.LC.rightChildsHeight)+1;
		
		if(child.RC != null)
			child.rightChildsHeight = Math.max(child.RC.leftChildsHeight, child.RC.rightChildsHeight)+1;
		
		if(IsLeft && before != null && before.LC!= null)
			before.leftChildsHeight = Math.max(before.LC.leftChildsHeight, before.LC.rightChildsHeight)+1;
		if(!IsLeft && before != null && before.RC != null)
			before.rightChildsHeight = Math.max(before.RC.leftChildsHeight, before.RC.rightChildsHeight)+1;
	
		if(before == null)
			root = child;
	}
	
	private void LRproblem(TST_Node before,TST_Node current){
		
		TST_Node child = current.LC;
		TST_Node childOfChild = child.RC;
		
		current.LC = childOfChild;	
		
		if(childOfChild.LC != null)
			child.RC = childOfChild.LC;
		else
			child.RC = null;
		
		childOfChild.LC = child;
		
		if(child.RC != null)
			child.rightChildsHeight = Math.max(child.RC.leftChildsHeight, child.RC.rightChildsHeight)+1;
		else
			child.rightChildsHeight = 0;

		if(childOfChild.LC != null)
			childOfChild.leftChildsHeight = Math.max(childOfChild.LC.leftChildsHeight, childOfChild.LC.rightChildsHeight)+1;
		
		if(current.LC != null)
			current.leftChildsHeight = Math.max(current.LC.leftChildsHeight, current.LC.rightChildsHeight)+1;

		LLproblem(before, current);
	}
}
