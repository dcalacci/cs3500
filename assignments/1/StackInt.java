//Recipe implementation of the StackInt ADT

public abstract class StackInt {
	
	abstract boolean 	isEmpty();
	abstract int 		top();
	abstract StackInt 	pop();
	abstract int 		size();
	
	public static StackInt empty() {
		return new Empty();
	}
	
	public static StackInt push (StackInt s, int n) {
		return new Push(s, n);
	}
	
	//Delegating to corresponding abstract method
	
	public static boolean isEmpty(StackInt s) {
		return s.isEmpty();
	}
	
	public static int top(StackInt s) {
		return s.top();
	}
	
	public static StackInt pop(StackInt s) {
		return s.pop();
	}
	
	public static int size(StackInt s) {
		return s.size();
	}
	
}

class Empty extends StackInt {}

	Empty () { }
	
	boolean isEmpty() {
		return true;
	}
	int top() {
		throw new RuntimeException("Attempted to compute the top of an empty StackInt");
	}
	int size() {
		return 0;
	}
	StackInt pop() {
		throw new RuntimeException("Attempted to pop an empty stack");	
	}
	
}

class Push extends StackInt {
	StackInt s;		// the other elements of this StackInt
	int n;			// the topmost element of this StackInt
	
	// constructor
	Push (StackInt s, int n) {
		this.s = n;
		this.n = n;
	}
	
	boolean isEmpty() {
		return false;
	}
	int top() {
		return n;
	}
	int size() {
		return 1 + size(s);
	}
	StackInt pop() {
		return s;
	}
}
