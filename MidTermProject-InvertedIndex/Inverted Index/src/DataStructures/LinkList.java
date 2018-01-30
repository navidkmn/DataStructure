package DataStructures;

public class LinkList {
	/////////
	public class LinkListNode {

		public LinkListNode forward;
		public String fileName;
		
		public LinkListNode(String filename) {
			forward = null;
			fileName = filename;
		}
	}
	////////
	
	public LinkListNode root;

	public LinkList() {
		root = new LinkListNode(null);
	}

	public void add(String fileName) {

		LinkListNode current = root;

		while (current.forward != null){
			if(current.forward.fileName.equals(fileName))
				return;
			else
			current = current.forward;
			}

		LinkListNode newNode = new LinkListNode(fileName);
		current.forward = newNode;
	}

	public void delete(String fileName) {

		LinkListNode current = root.forward;
		LinkListNode before = root;
		
		while (current!=null && !current.fileName.equals(fileName)) {
			before = current;
			current = current.forward;
		}
		if(current!=null){
			LinkListNode forward = current.forward;
			before.forward = forward;
			current = null;
		}
	}
	
	public String print(LinkListNode current){
		
		String allFileNames = "";
		
		if(current==null)
			return allFileNames;
		else{
			allFileNames+=current.fileName+"\t";
			return allFileNames+print(current.forward); 
		}
	}
	
	public boolean fileAvailable(LinkListNode current,String fileName){

		if(current == null)
			return false;
		else{
			if(current.fileName.equals(fileName))
				return true;
			else
				return fileAvailable(current.forward, fileName);
		}
	}
}
