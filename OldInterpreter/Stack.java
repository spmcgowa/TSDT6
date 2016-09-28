import java.util.NoSuchElementException;

/**
 * A simple stack implementation.
 *
 * @author Justin W Cain
 */
public class Stack<E> {

	private Link header;
	private int size;
	/**
	 * Constructs a stack with sensible defaults.
	 */
	public Stack() {
		header = null;
		size = 0;
	}

	/**
	 * Pushes the specified element on to the stack.
	 *
	 * @param e the specified element
	 */
	public void push(E e) {
		Link t = new Link(e, header);
		header = t;
		size++;
	}
	
	/**
	 * Pops the top element off the stack, returning the element.
	 *
	 * @throws NoSuchElementException if the stack is empty
	 * @return the popped value
	 */
	public E pop() {
		Link t = header;
		header = header.last;
		size--;
		return t.value;
	}
	
	/**
	 * Returns the top element.
	 *
	 * @throws NoSuchElementException if the stack is empty
	 * @return the top element
	 */
	public E top() {
		if (isEmpty()) {
			return null;
		}
		return header.value;
	}
	
	/**
	 * Clears every element from the stack.
	 */
	public void clear() {
		size = 0;
		while (header != null) {
			header.value = null;
			header = header.last;
		}
	}
	
	/**
	 * Returns the number of elements contained in this stack.
	 *
	 * @return the number of elements contained in this stack.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Determines if the stack is empty (size == 0).
	 *
	 * @return true if the stack is empty; false otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Generates a string represtentation of this stack. The stack is
	 * represented by an opening square bracket, followed by a space delineated
	 * list of elements from bottom to top, followed by a closing square 
	 * bracket. The stack containing the elements 12, 42, and 10 (where 10 is
	 * the top element) would have the string representation "[ 12 42 10 ]".
	 * The empty stack has the string representation "[ ]" where only a single
	 * space separates the square brackets.
	 *
	 * @return the string representation of this stack.
	 */
	public String toString() {
		String ret = " ]";
		Link t = header;
		while(isEmpty() == false) {
			ret = (" " + t.value) + ret;
			if (t.last == null) {
				break;
			}
			t = t.last;
		}
		return ("[" + ret);
	}
	
	class Link {
		E value;
		Link last;
		
		public Link(E value, Link last) {
			this.value = value;
			this.last = last;
		}
	}
}