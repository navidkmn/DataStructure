package DataStructures;

import java.util.Vector;

public class Stack {

	int top=0;
	Vector<Object> stack;
	
	public Stack(){
		stack = new Vector<Object>();
	}
	
	public boolean IsEmpty() {

		if (top == 0)
			return true;
		else 
			return false;
	}
	
	public Object Top(){
		if(top!=0){
		int get = top-1;
		return stack.get(get);
		}
		return null;
	}
	
	public void Push(Object x){
			//stack.add(top++, x);
			stack.insertElementAt(x, top++);
	}

	public Object Pop(){
		
		if (!IsEmpty())
			return stack.get(--top);
		else
			return null;
	}
}
