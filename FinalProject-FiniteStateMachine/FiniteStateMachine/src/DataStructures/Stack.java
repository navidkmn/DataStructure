package DataStructures;

public class Stack {

	int top=0;
	int[] stack;
	int n;
	
	public Stack(int n){
		this.n = n;
		stack = new int[n];
	}
	
	
	public boolean IsEmpty() {

		if (top == 0)
			return true;
		else 
			return false;
	}
	
	public boolean IsFull(){
	
		if (top == n)
			return true;
	else 
		return false;
	}
	
	public void Push(int x){
		
		if (!IsFull())
			stack[top++] = x;
	}
	
	public int Pop(){
		
		if (!IsEmpty())
			return stack[--top];
		else
			return -1000;
	}
}
