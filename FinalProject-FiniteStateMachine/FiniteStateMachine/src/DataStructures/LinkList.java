package DataStructures;

import java.util.Vector;

public class LinkList {
	/////////
	public class LinkListNode {

		public LinkListNode forward;
		public String cost;
		public int connectedNode;
		
		public LinkListNode(String cost,int connectedNode) {
			forward = null;
			this.cost = cost;
			this.connectedNode = connectedNode;
		}
	}
	////////
	
	public LinkListNode root;

	public LinkList(int mainNode) {
		root = new LinkListNode(null,mainNode);
	}

	public void add(String cost,int connectedNode) {

		LinkListNode current = root;

		while (current.forward != null){
			if(current.forward.cost.equals(cost) && current.forward.connectedNode == connectedNode)
				return;
			else
			current = current.forward;
			}

		LinkListNode newNode = new LinkListNode(cost,connectedNode);
		current.forward = newNode;
	}

	public void delete(int connectedNode) {

		LinkListNode current = root.forward;
		LinkListNode before = root;
		
		while (current!=null &&  current.connectedNode != connectedNode) {
			before = current;
			current = current.forward;
		}
		if(current!=null){
			LinkListNode forward = current.forward;
			before.forward = forward;
			current = null;
		}
	}
	
	public String convertToGraph(LinkListNode current){
		
		String allFileNames = "";
		
		if(current==null)
			return allFileNames;
		else{
			allFileNames+= root.connectedNode + "->" + current.connectedNode + "[label = " + current.cost +"]";
			return allFileNames+convertToGraph(current.forward); 
		}
	}
	
	public String convertToList(LinkListNode current){
		
		String allFileNames = "";
		
		if(current==null)
			return allFileNames;
		else{
			allFileNames+= "(" +current.connectedNode +" , "+current.cost +")";
			return allFileNames+convertToList(current.forward); 
		}
	}
    
    public void addToVector(LinkListNode current,Stack s){
 
        if(current == null)
            return;
        else{
        	s.Push(current.connectedNode);
        	addToVector(current.forward,s);
             //vector.add(current.connectedNode);  
        	//addToVector(current.forward, vector);
        	}
    }        
    
    public void addToStack(LinkListNode current,String cost,Stack stack){
    	
    	while (current != null){
    		if(current.cost.equals(cost))
    			stack.Push(current.connectedNode);
    		current = current.forward;
    	}
    }
	
//	public boolean fileAvailable(LinkListNode current,String fileName){
//
//		if(current == null)
//			return false;
//		else{
//			if(current.fileName.equals(fileName))
//				return true;
//			else
//				return fileAvailable(current.forward, fileName);
//		}
//	}
}
