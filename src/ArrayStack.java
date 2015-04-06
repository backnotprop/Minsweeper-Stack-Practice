import java.util.Arrays;
//import java.util.Stack;

public class ArrayStack<T> implements StackADT<T>{

	 private final static int DEFAULT_CAPACITY = 100;  
	 private int top;  
	 private T[] stack;

	 /**     
	  * Creates an empty stack using the default capacity.     
	  */    
	 protected ArrayStack()    {        
		 this(DEFAULT_CAPACITY);   
		 }    
	 
	 /**     
	  * Creates an empty stack using the specified capacity.     
	  * @param initialCapacity the initial size of the array      
	  */    
	 @SuppressWarnings("unchecked")
	public ArrayStack(int initialCapacity)    {   
		 
		 top = 0;       
		 stack = (T[])(new Object[initialCapacity]);   
		 }
	 
	 /**     
	  * push operator     
	  */ 
	@Override
	public void push(T element) {
		if (size() == stack.length)
			expandCapacity();
		
		stack[top]= element;
		top++;
		
	}

	 /**     
	  * Creates a new array to store the contents of this stack with     
	  * twice the capacity of the old one.     
	  */    
	public void expandCapacity()    {        
		 stack = Arrays.copyOf(stack, stack.length * 2);    
	}
	
	@Override
	public T pop()  {       
		       
	    top--;       
	    T result = stack[top];       
	    stack[top] = null;        
	   
	    return result;   
	  }

	@Override
	public T peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
