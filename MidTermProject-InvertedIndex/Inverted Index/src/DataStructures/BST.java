package DataStructures;

import java.util.Vector;

public class BST implements Tree {

	public BST_Node root;
	public String result;
	public int number=0;
	public Vector<BST_Node> generation;

	public BST() {
		root = new BST_Node(null);
	}

	@Override
	public void add(Tree_Node current, String word, String file) {
		generation = new Vector<BST_Node>();
		add((BST_Node)current,word,file);
	}

	private void add(BST_Node current, String word, String fileName) {
		
		if (current.value == null) {
			number++;
			current.value = word.toLowerCase();
			current.FileNames = new LinkList();
			current.FileNames.add(fileName);
		}
		else {			
			int compare = word.compareTo(current.value);
			if(compare != 0)
				generation.add(current);
			
			if (compare > 0) {
				if (current.RC != null){
					add(current.RC, word, fileName);
				}
				else {
					BST_Node newNode = new BST_Node(word.toLowerCase());
					number++;
					newNode.FileNames = new LinkList();
					newNode.FileNames.add(fileName);
					current.RC = newNode;
				
					for (int i = generation.size() - 1; i >= 0; i--) {
						BST_Node child = generation.get(i);
						BST_Node father;
						if (i > 0)
							father = generation.get(i - 1);
						else
							father = null;
						balanceTree(father, child);
					}
						generation.clear();
					return;
				}
			} else {
				if (compare < 0) {
					if (current.LC != null){
						add(current.LC, word, fileName);
						}
					else {
						BST_Node newNode = new BST_Node(word.toLowerCase());
						number++;
						newNode.FileNames = new LinkList();
						newNode.FileNames.add(fileName);
						current.LC = newNode;
						
						for (int i = generation.size() - 1; i >= 0; i--) {
							BST_Node child = generation.get(i);
							BST_Node father;
							if (i > 0)
								father = generation.get(i - 1);
							else
								father = null;
							balanceTree(father, child);
						}
						generation.clear();
						return;
					}
				} else {
					current.FileNames.add(fileName);
					return;
				}
			}
		}
	}
	
	@Override
	public void printAllWordsFiles(Tree_Node current) {
		result="";
		printAllWordsFiles((BST_Node)current);
	}
	
	private void printAllWordsFiles(BST_Node current) {
		if (current == null)
			return;
		else {
			printAllWordsFiles(current.LC);
			if(current.FileNames!= null)
				result +=current.value +"\t"+ current.FileNames.print(current.FileNames.root.forward) +"\n";
			printAllWordsFiles(current.RC);
		}
	}
	

	@Override
	public LinkList printFiles(Tree_Node current, String word) {
		return printFiles((BST_Node)current, word);
	}

	private LinkList printFiles(BST_Node current, String word) {

		if (current == null) {
			return null;
		} else {
			int compare = word.compareTo(current.value);
			if (compare == 0) {
				return current.FileNames;
			} else {
				if (compare > 0)
					return printFiles(current.RC, word);
				else
					return printFiles(current.LC, word);
			}
		}
	}
	
	@Override
	public boolean IsStopWord(Tree_Node current, String word) {
		return IsStopWord((BST_Node)current, word);
	}	

	private boolean IsStopWord(BST_Node current, String word) {
		
		if (current == null) {
			return false;
		} else {
			int compare = 0;
			if(current.value != null){
				compare = word.compareTo(current.value);
			}
			if (compare == 0) {
				return true;
			} else {
				if (compare > 0)
					return IsStopWord(current.RC, word);
				else
					return IsStopWord(current.LC, word);
			}
		}
	}
	
	@Override
	public void delete(Tree_Node before,Tree_Node current, String file) {
		generation = new Vector<BST_Node>();
		delete((BST_Node)before,(BST_Node)current, file);
	}

	private void delete(BST_Node before, BST_Node current, String fileName) {

		if (current == null) {
			return;
		} else {
			generation.add(current);

			delete(current, current.LC, fileName);
			delete(current, current.RC, fileName);
			
			if(current.FileNames!=null){
			current.FileNames.delete(fileName);
			if(generation.size()>0)
				generation.remove(generation.size()-1);
			
			if (current.FileNames.root.forward == null){
				number--;
				current.FileNames = null;
				deleteNode(before, current);
				generation.clear();
				delete(null,root,fileName);
				}
			}
		}
	}
	
	private void deleteNode(BST_Node before, BST_Node current) {
		// current is a leaf
		if (current.LC == null && current.RC == null) {
			if (before != null && before.LC == current) {
				before.LC = null;
				current = null;
			} 
			else{
			if (before != null &&before.RC == current) {
				before.RC = null;
				current = null;
			}
			else
			if(before == null){
				root.value = null;
				current = null;
			}
			}
		} // end leaf
		else {
			// current is a father of RC child
			if (current.LC == null && current.RC != null) {
				if (before != null && before.LC == current) {
					before.LC = current.RC;
					current = null;
				}
				else{
				if (before != null && before.RC == current) {
					before.RC = current.RC;
					current = null;
				}
				else
				if(before == null){
					root = current.RC;
					current = null;
				}
				}
			} // end RC
			else {
				// current is a father of LC child
				if (current.LC != null && current.RC == null) {
					if (before != null && before.LC == current) {
						before.LC = current.LC;
						current = null;
					} 
					else{
					if (before != null && before.RC == current) {
						before.RC = current.LC;
						current = null;
					}
					else
					if(before == null){
						root = current.LC;
						current = null;
					}
					}
				} // end LC
				else {
					// current have two child
					generation.add(current);
					BST_Node maxOfMinimums = current.LC;	
					generation.add(maxOfMinimums);
					BST_Node fatherOfMaxOfMinimums = current;
					
					while (maxOfMinimums.RC != null) {
						fatherOfMaxOfMinimums = maxOfMinimums;
						maxOfMinimums = maxOfMinimums.RC;
						generation.add(maxOfMinimums);
					}
					generation.remove(generation.lastElement());
					current.value = maxOfMinimums.value;
					current.FileNames = maxOfMinimums.FileNames;
					deleteNode(fatherOfMaxOfMinimums, maxOfMinimums);
					} // end RC & LC 
				}
			}
		for (int i = generation.size() - 1; i >= 0; i--) {
			BST_Node child = generation.get(i);
			BST_Node father;
			if (i > 0)
				father = generation.get(i - 1);
			else
				father = null;
			balanceTree(father, child);
		}
	}

	@Override
	public Tree_Node getRoot() {
		return (Tree_Node)root;
	}

	@Override
	public int getWordNumber() {
		return number;
	}

	@Override
	public String getResult() {
		return result;
	}
	
	@Override
	public int treeHeight(Tree_Node current) {
		return treeHeight((BST_Node)current);
	}
	
	private int treeHeight(BST_Node current){	
		if(current == null)
			return 0;
		else{
			return Math.max(treeHeight(current.LC), treeHeight(current.RC))+1;
		}		
	}
	
	private void balanceTree(BST_Node before,BST_Node current){
		
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
	
	private void RRproblem(BST_Node before,BST_Node current){

		boolean isLeft = false;	
		BST_Node child = current.RC;
		
		if(before != null && before.LC == current){
			before.LC = child;
			isLeft = true;
		}
		if(before != null && before.RC == current)
			before.RC = child;
		
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

	private void RLproblem(BST_Node before,BST_Node current){
		
		BST_Node child = current.RC;
		BST_Node childOfChild = child.LC;
		
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
	
	private void LLproblem(BST_Node before,BST_Node current){
		
		boolean IsLeft = false;
		
		BST_Node child = current.LC;
		if(before != null && before.LC == current){
			before.LC = child;
			IsLeft = true;
		}
		if(before != null && before.RC == current)
			before.RC = child;
		
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
	
	private void LRproblem(BST_Node before,BST_Node current){
		
		BST_Node child = current.LC;
		BST_Node childOfChild = child.RC;
		
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