package Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import DataStructures.BST;
import DataStructures.Hash;
import DataStructures.LinkList;
import DataStructures.LinkList.LinkListNode;
import DataStructures.Stack;
import DataStructures.TRIE;
import DataStructures.TST;
import DataStructures.Tree;

public class Commands {

	Vector<String> fileNamesInStructure;

	Tree treeWords;
	Tree stopWords;
	
	Hash hashWords;
	Hash hashStopWords;

	String HashOTree;
	
	File directory;

	MenuFrame mf;

	public Commands(MenuFrame mf, File directory, String name) {

		HashOTree = "tree";
		
		mf.redo = new Stack();
		mf.undo = new Stack();

		this.mf = mf;
		this.directory = directory;

		fileNamesInStructure = new Vector<String>();

		if (name.equals("BST")) {
			treeWords = new BST();
			stopWords = new BST();
		}
		if (name.equals("TST")) {
			treeWords = new TST();
			stopWords = new TST();
		}
		if (name.equals("TRIE")) {
			treeWords = new TRIE();
			stopWords = new TRIE();
		}
		if(name.equals("Hash")){
			hashWords = new Hash();
			hashStopWords = new Hash();
			HashOTree = "hash";
		}
		initializeStructure();
	}

	public void initializeStructure() {

		long firstTime = System.currentTimeMillis();

		File[] filesInDirectory = directory.listFiles();
		String[] words;

		Scanner s = null;
		try {
			s = new Scanner(new File(directory.getPath() + "\\StopWords.txt"));	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (s.hasNextLine()) {
			words = s.nextLine().split("[^a-zA-Z]");
			for (int i = 0; i < words.length; i++){
				if(HashOTree.equals("tree"))
					stopWords.add(stopWords.getRoot(), words[i], "StopWords.txt");
				else
					hashStopWords.add(words[i], "StopWords.txt");
			}
		}

		for (int i = 0; i < filesInDirectory.length; i++) {
			Scanner scan = null;
			if (filesInDirectory[i].isFile() && !filesInDirectory[i].getName().equals("StopWords.txt")) {
				try {
					scan = new Scanner(new File(filesInDirectory[i].getPath()));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileNamesInStructure.add(filesInDirectory[i].getName());
				while (scan.hasNext()) {
					words = scan.nextLine().split("[^a-zA-Z]");
					for (int j = 0; j < words.length; j++) {
						if (!words[j].equals("")) {
							if(HashOTree.equals("tree")){
							if (!stopWords.IsStopWord(stopWords.getRoot(), words[j].toLowerCase())) 
								treeWords.add(treeWords.getRoot(), words[j].toLowerCase(),filesInDirectory[i].getName());
							}
							else{
								if(!hashStopWords.IsStopWord(words[j].toLowerCase()))
									hashWords.add(words[j], filesInDirectory[i].getName());
							}
						}
					}
				}
			}
		}
		if(HashOTree.equals("tree"))
		mf.result.setText(Integer.toString(treeWords.treeHeight(treeWords.getRoot())) + " : tree Height" + "\n"
				+ "Create Tree Time : " + new Long(System.currentTimeMillis() - firstTime).toString()
				+ "  mili second");
		else
			mf.result.setText("Create Hash Time : " + new Long (System.currentTimeMillis() - firstTime).toString());
	}
	
	public void tooknizeFileWordsAndAdd(String file) {

		String[] words;

		try {
			Scanner scanner = new Scanner(new File(directory.getPath() + "\\" + file + ".txt"));
			while (scanner.hasNext()) {
				words = scanner.nextLine().split("[^a-zA-Z]");
				for (int j = 0; j < words.length; j++) {
					if (!words[j].equals("")) {
						if(HashOTree.equals("tree")){
						if (!stopWords.IsStopWord(stopWords.getRoot(), words[j].toLowerCase())) 
							treeWords.add(treeWords.getRoot(), words[j].toLowerCase(), file + ".txt");
					}
						else{
							if(!hashStopWords.IsStopWord(words[j].toLowerCase()))
								hashWords.add(words[j], file+".txt");
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkAdd(String file) {

		File[] files = directory.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(file + ".txt")) {
				if (fileNamesInStructure.contains(file + ".txt")) {
					mf.result.setText("Your file is already exists");
					return false;
				} else
					return true;
			}
		}
		mf.reset.setText("Your file doesn't exist in this directory");
		return false;
	}

	public boolean checkDel(String file) {

		File[] files = directory.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(file + ".txt")) {
				if (fileNamesInStructure.contains(file + ".txt")) {
					return true;
				} else {
					mf.result.setText("this file doesn't exist in tree");
					return false;
				}
			}

		}
		mf.result.setText("Your file doesn't exist in this directory");
		return false;
	}

	public boolean checkUpdate(String file) {

		File[] files = directory.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(file + ".txt")) {
				if (fileNamesInStructure.contains(file + ".txt")) {
					return true;
				} else {
					mf.result.setText("this file doesn't exist in tree");
					return false;
				}
			}
		}
		mf.reset.setText("Your file doesn't exist in this directory");
		return false;
	}

	public Vector<String> SubscriptionBetweenFiles(Vector<LinkList> files) {

		Vector<String> subscriptionFiles = new Vector<String>();
		LinkList file = files.firstElement();
		LinkListNode current = file.root.forward;

		while (current != null) {
			if (files.size() > 1) {
				for (int i = 0; i < files.size(); i++) {
					if (files.elementAt(i).fileAvailable(current, current.fileName));
					if (!subscriptionFiles.contains(current.fileName))
						subscriptionFiles.addElement(current.fileName);
				}
			} else
				subscriptionFiles.addElement(current.fileName);
			current = current.forward;
		}
		return subscriptionFiles;
	}

	public void runCommand(String order) {

		mf.redo.Push(order);
		long firstTime = System.currentTimeMillis();

		if (order.contains(">> add")) {

			String[] sentence = order.split("[^a-zA-Z0-9]");
			String file = sentence[4];

			if (checkAdd(file)) {
				tooknizeFileWordsAndAdd(file);
				fileNamesInStructure.add(file + ".txt");
				mf.result.setText(file + " added in " + new Long(System.currentTimeMillis() - firstTime).toString() + " mili seconds");
			}
		} else {
			if (order.contains(">> del")) {
				String[] sentence = order.split("[^a-zA-Z0-9]");
				String file = sentence[4];

				if (checkDel(file)) {
					if(HashOTree.equals("tree"))
						treeWords.delete(null, treeWords.getRoot(), file + ".txt");
					else
						hashWords.delete(file+".txt");
					
					fileNamesInStructure.remove(file + ".txt");
					mf.result.setText(file + " deleted in " + new Long(System.currentTimeMillis() - firstTime).toString() + " mili seconds");
				}
			} else {
				if (order.contains(">> update")) {

					String[] sentence = order.split("[^a-zA-Z0-9]");
					String file = sentence[4];

					if (checkUpdate(file)) {
						if(HashOTree.equals("tree"))
							treeWords.delete(null, treeWords.getRoot(), file + ".txt");
							else
								hashWords.delete(file+".txt");
						tooknizeFileWordsAndAdd(file);
				
						mf.result.setText(file + " updated in " + new Long(System.currentTimeMillis() - firstTime).toString() + " mili seconds");
					}
				} else {
					if (order.contains(">> list -w")) {
						if(HashOTree.equals("tree")){
						treeWords.printAllWordsFiles(treeWords.getRoot());
						mf.result.setText(
								treeWords.getResult() + "\n" + "Number of all words : " + treeWords.getWordNumber()+ "\n"+new Long(System.currentTimeMillis() - firstTime).toString() + " mili seconds");
						}
						else{
							hashWords.printAllWordsFiles();
							mf.result.setText(hashWords.getResult() + "\n" + "Number of all words" + hashWords.getWordNumber()+ "\n"+new Long(System.currentTimeMillis() - firstTime).toString() + " mili seconds");
						}
						} else {
						if (order.contains(">> list -f")) {
							File[] file = directory.listFiles();
							String result = "";
							for (int i = 0; i < file.length; i++) {
								result += file[i].getName() + "\n";
							}
							result += "Number of files\t" + Integer.toString(file.length);
							mf.result.setText(result+ "\n"+" done in "+new Long(System.currentTimeMillis() - firstTime).toString()+ " mili seconds");
						} else {
							if (order.contains(">> list -l")) {
								String result = "";
								for (int i = 0; i < fileNamesInStructure.size(); i++)
									result += fileNamesInStructure.elementAt(i) + "\n";
								result += "Number of files in tree\t" + Integer.toString(fileNamesInStructure.size());
								mf.result.setText(result+ "\n"+" done in "+new Long(System.currentTimeMillis() - firstTime).toString() + " mili seconds");
							} else {
								if (order.contains(">> search -s")) {

									String[] sentence = order.split("[^a-zA-Z0-9]");
									Vector<LinkList> fileNames = new Vector<LinkList>();

									for (int i = 6; i < sentence.length; i++) {
										if(HashOTree.equals("tree")){
											if (!sentence[i].equals("") && !stopWords.IsStopWord(stopWords.getRoot(),sentence[i].toLowerCase())) {
												LinkList fileName = treeWords.printFiles(treeWords.getRoot(),sentence[i].toLowerCase());
												if (fileName != null)
													fileNames.add(fileName);
										}
										}
										else{
											if (!sentence[i].equals("") && !hashStopWords.IsStopWord(sentence[i].toLowerCase())) {
												LinkList fileName = hashWords.printFiles(sentence[i].toLowerCase());
												if (fileName != null)
													fileNames.add(fileName);
										}	
										}
									}
									if (fileNames.size() > 0) {
										Vector<String> subscriptionFile = SubscriptionBetweenFiles(fileNames);
										String result = "";
										for (int i = 0; i < subscriptionFile.size(); i++) {
											result += subscriptionFile.elementAt(i) + "\n";
										}
										mf.result.setText(result + "\n"+" done in "+new Long(System.currentTimeMillis() - firstTime).toString()+ " mili seconds");
									}
								} else {
									if (order.contains(">> search -w")) {

										String[] sentence = order.split("[^a-zA-Z0-9]");
										String word = sentence[7];
										LinkList files;
										if(HashOTree.equals("tree"))
											files = treeWords.printFiles(treeWords.getRoot(), word.toLowerCase());
										else
											files = hashWords.printFiles(word.toLowerCase());
										
										if (files != null) {
											mf.result.setText(files.print(files.root.forward) + "\n"+" done in "+new Long(System.currentTimeMillis() - firstTime).toString() + " mili seconds");
										} else
											mf.result.setText("Your word doesn't exist in tree");
									} else{
										if(order.contains(">> height") && HashOTree.equals("tree"))
											mf.result.setText("Height : " +new Integer(treeWords.treeHeight(treeWords.getRoot())).toString()  + "\n"+" done in "+new Long(System.currentTimeMillis() - firstTime).toString() + " mili seconds");
									else{
										mf.redo.Pop();
										mf.result.setText("Your command is incorrent");
									}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
