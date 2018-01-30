package Menu;

import javax.swing.JTable;

import DataStructures.LinkList;
import DataStructures.Stack;
import GraphViz.GraphDrawer;
import java.util.Vector;

public class Commands {

	MenuFrame mf;
    Stack c;
    
    boolean haveCycle =  false;
    boolean IsFinal = false;
    
	public Commands(MenuFrame mf) {
		this.mf = mf;
	}
	
	public void fillArray(JTable table){
        	
		int size = Integer.parseInt(mf.nodes.getText().toString());
		c = new Stack(size);
		
		mf.isVisited = new boolean[size];
		
		for(int i =0 ;i<size;i++)
			mf.isVisited[i] = false;
		
		for(int row = 0; row<table.getRowCount();row++){
			mf.adjacent[row] = new LinkList(row);
			for(int column = 0;column<table.getColumnCount();column++){
				if(mf.table.getModel().getValueAt(row, column) != null){
				String[] costs = mf.table.getModel().getValueAt(row, column).toString().split("[^0-9/a-zA-Z]");
				for(int i = 0 ; i<costs.length;i++)
					if(costs[i]!=""){
					mf.adjacent[row].add(costs[i], column);
					}
				}
            }
        }
}
    
    public String getList(){
        String list = "";
		for(int i =0 ; i<mf.adjacent.length;i++)
           list +=i +"\t"+mf.adjacent[i].convertToList(mf.adjacent[i].root.forward)+ "\n";
		
		return list;
    }
    
    public void createImg(){
       
        String circleNodes="";
        for(int i=0;i<mf.FinalNodes.size();i++){
            circleNodes += mf.FinalNodes.elementAt(i).toString();
                circleNodes += ";";
        }

		String fin="node [shape = doublecircle];" + circleNodes + "node [shape = plaintext]; start;" +"node [shape = circle];"+ "start ->" ;
    
		for(int i =0 ; i<mf.adjacent.length;i++)
			fin += mf.adjacent[i].convertToGraph(mf.adjacent[i].root.forward);
		GraphDrawer gd = new GraphDrawer();
		gd.draw("test", fin);
    }
    
    public boolean haveCircle(int node){
      
    	if(!mf.isVisited[node])   
    		mf.adjacent[node].addToVector(mf.adjacent[node].root.forward,c);
    	
    	mf.isVisited[node]  = true;
   
    	while(!c.IsEmpty()){
    		int next = c.Pop();
    		if(mf.isVisited[next]){
    			int[] connections = new int[2];
    			connections[0] = node;
    			connections[1] = next;
    			mf.cycles.add(connections);
    			haveCycle = true;
    		}
         else   
            haveCircle(next);
        }
    	return haveCycle;
    }
        public void removeCycle(){
        	if(mf.cycles.size()!=0)
        		for(int i =0 ; i<mf.cycles.size() ; i++)
        			mf.adjacent[mf.cycles.get(i)[0]].delete(mf.cycles.get(i)[1]);
        }
        
        public boolean isFinal(int node, String input, int charAt){
        	if(charAt  == input.length()){
        		if(mf.FinalNodes.contains(node)){
        			System.out.println("I:m here");
        			IsFinal = true;
        			return IsFinal;
        			}
        		else{
        			IsFinal = false;
        			return IsFinal;
        			}
        	}
        	else{
        		System.out.println("else");
			Stack stack = new Stack(10);
			System.out.println(input + " " + input.charAt(charAt));
			mf.adjacent[node].addToStack(mf.adjacent[node].root.forward, Character.toString(input.charAt(charAt)),stack);
			System.out.println("before while");
			while (!stack.IsEmpty()) {
				System.out.println("Helloo");
				int next = stack.Pop();
				return IsFinal | isFinal(next, input, charAt + 1);
			}
			IsFinal = false;
			return IsFinal;
        }
      }
}
