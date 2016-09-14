/*
 * File name: AStack.java
 *
 * Programmer: Casey Cook
 * ULID: clcoo10
 *
 * Date: Oct 7, 2015
 *
 * Class: IT 179
 * Instructor: Dr. Li
 */
package myUtil;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Class to implement interface Stack<T> as an array.
 *
 * @author Casey Cook
 *
 */
public class AStack<T> implements Stack<T>
{
	/** Storage for stack */
	private T[] theData;
	
	/** Index to top of stack */
	int top = -1; // Initially empty
	
	/** Default initial capacity */
	private static final int INITIAL_CAPACITY = 10;
	
	/** capacity of stack */
	int capacity;
	
	/**
	 * Construct an empty stack with the default initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public AStack()
	{
		theData = (T[]) new Object[INITIAL_CAPACITY];
		capacity = INITIAL_CAPACITY;
	}

	/**
	 * Push the item on the top of this Stack.
	 * @param item of type T
	 * @return the same item that is pushed. 
	 */
	@Override
	public T push(T item)
	{
		if(top == theData.length - 1)
		{
			reallocate();
		}
		top++;
		theData[top] = item;
		return item;
	}

	/**
	 * Peek at the top of this Stack.
	 * @throws an EmptyStackException if this Stack is empty.
	 * @return the item at the top of this Stack.
	 */
	@Override
	public T peek()
	{
		if (empty())
		{
			throw new EmptyStackException();
		}
		return theData[top];
	}

	/**
	 * Pop out the item at the top of this Stack.
	 * @return the item is is popped out.
	 * @throws an EmptyStackException if this Stack is empty.
	 */
	@Override
	public T pop()
	{
		if (empty())
		{
			throw new EmptyStackException();
		}
		
		return theData[top--];
		
	}

	/**
	 * @return true if this Stack is empty, false otherwise
	 */	
	@Override
	public boolean empty()
	{
		return top == -1;
	}
	
	/**
	 * Doubles array capacity.
	 */
	private void reallocate()
	{
		capacity = 2 * capacity;
		theData = Arrays.copyOf(theData, capacity);
	}

}
