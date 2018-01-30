package DataStructures;

public class Hash {

	Tree [] hashResult;
	public String result;
	
	public Hash() {
		hashResult = new Tree[26];
		for(int i =0;i<hashResult.length;i++)
			hashResult[i] = new BST();
	}
	
	public int hashFunction(String word){
		return (int)(word.toLowerCase().charAt(0)) - 97;
	}

	public void add(String word, String file) {
		int place = hashFunction(word);
		hashResult[place].add(hashResult[place].getRoot(), word.toLowerCase(), file);
	}
	
	public void delete(String file) {
		for(int i = 0; i<hashResult.length;i++)
			if(hashResult[i].getRoot().value != null)
				hashResult[i].delete(null, hashResult[i].getRoot(), file);
	}

	public void printAllWordsFiles() {
		result = "";
		for(int i = 0; i<hashResult.length;i++){
			hashResult[i].printAllWordsFiles(hashResult[i].getRoot());
			result += hashResult[i].getResult();
		}
	}

	public LinkList printFiles(String word) {
		int place = (int)(word.toLowerCase().charAt(0))-97;
		return hashResult[place].printFiles(hashResult[place].getRoot(), word.toLowerCase());
	}

	public boolean IsStopWord(String word) {
		int place = (int)(word.toLowerCase().charAt(0))-97;
		return hashResult[place].IsStopWord(hashResult[place].getRoot(), word.toLowerCase());	
	}

	public int getWordNumber() {
		int wordNo =0;
		for(int i=0;i<hashResult.length;i++){
			wordNo+= hashResult[i].getWordNumber();
		}
		return wordNo;
	}
	
	public String getResult() {
		return result;
	}
}
