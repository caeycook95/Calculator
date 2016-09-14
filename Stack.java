package myUtil;

/**
 * This generic interface is the standard Stack API 
 * @author Chung-Chih Li
 * @param <T>
 */

public interface Stack<T> {
	
	/**
	 * Push the item on the top of this Stack.
	 * @param item of type T
	 * @return the same item that is pushed. 
	 */
	T push(T item);  
	
	/**
	 * Peek at the top of this Stack.
	 * @throws an EmptyStackException if this Stack is empty.
	 * @return the item at the top of this Stack.
	 */
	T peek();    
	
	/**
	 * Pop out the item at the top of this Stack.
	 * @return the item is is popped out.
	 * @throws an EmptyStackException if this Stack is empty.
	 */
	T pop();        
	
	/**
	 * @return true if this Stack is empty, false otherwise
	 */	
	boolean empty();
}

