import java.util.NoSuchElementException;

/**
 * A simple list implementation.
 *
 * @author <YOUR NAME>
 */
public class List<E> {

	E[] table;
	int open;
	
	/**
	 * Constructs a stack with sensible defaults.
	 */
	public List() {
		table = (E[])(new Object[10]);
		int open = 0;
	}

	/**
	 * Appends the specified element to the end of this list.
	 *
	 * @param e element to be appended to this list
	 */
	public void add(E e) {
		add(open, e);
	}
	
	/**
	 * Inserts the specified element at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any 
	 * subsequent elements to the right (adds one to their indices).
	 *
	 * @throws IndexOutOfBoundException if the index is out of range 
	 * [index < 0 || index >= size()]
	 * @param index the index at which the specified element will be added
	 * @param e element to be inserted
	 */
	public void add(int index, E e) throws IndexOutOfBoundsException  {
		if (index < 0 || index > open) {
			throw new IndexOutOfBoundsException();
		}
		if (open >= table.length) {
			Object[] nTable = new Object[open * 2];
			for (int i = 0; i < table.length; i++) {
				nTable[i] = table[i];
			}
			table = (E[])nTable;
		}
		for (int i = open; i >= index + 1; i--) {
			table[i] = table[i-1];
		}
		table[index] = e;
		open++;
	}
	
	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @throws IndexOutOfBoundException if the index is out of range 
	 * [index < 0 || index >= size()]
	 * @param index of the element to return
	 * @return the element at the specified position in this list
	 */
	public E get(int index) throws IndexOutOfBoundsException  {
		if (index < 0 || index >= open) {
			throw new IndexOutOfBoundsException();
		}
		return table[index];
	}
	 
	/**
	 * Removes the element at the specified position in this list. Shifts any 
	 * subsequent elements to the left (subtracts one from their indices). 
	 * Returns the element that was removed from the list.
	 *
	 * @throws IndexOutOfBoundException if the index is out of range 
	 * [index < 0 || index >= size()]
	 * @param the index of the element to be removed
	 * @return the element previously at the specified position
	 */
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= open) {
			throw new IndexOutOfBoundsException();
		}
		E t = table[index];
		for(int i = index; i < open - 1; i++) {
			table[i] = table[i+1];
		}
		open--;
		return t;
	}
	
	/**
	 * Replaces the element at the specified position in this list with the 
	 * specified element.
	 *
	 * @throws IndexOutOfBoundException if the index is out of range 
	 * [index < 0 || index >= size()]
	 * @param index index of the element to replace
	 * @param e element to be stored at the specified position
	 * @return the element previously at the specified position
	 */
	public void set(int index, E e) {
		if (index < 0 || index >= open) {
			throw new IndexOutOfBoundsException();
		}
		table[index] = e;
	}

	/**
	 * Removes all of the elements from this list. The list will be empty after
	 * this call returns.
	 */
	public void clear() {
		for (int i = 0; i < open; i++) {
			table[i] = null;
		}
		open = 0;
	}
	
	/**
	 * Returns the number of elements in this list. If this list contains more 
	 * than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this list
	 */
	public int size() {
		return open;
	}
	
	/**
	 * Returns true if this list contains no elements.
	 *
	 * @return if this list contains no elements
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Generates a string representation of this list. The list is represented 
	 * by an opening square bracket, followed by a space delineated list of 
	 * elements from front to back, followed by a closing square bracket. The 
	 * list containing the elements 12, 42, and 10 (where 12 is the first
	 * element and 10 is the last) would have the string representation 
	 * "[ 12 42 10 ]". The empty stack has the string representation "[ ]" 
	 * where only a single space separates the square brackets.
	 *
	 * @return the string representation of this list.
	 */
	public String toString() {
		String ret = "[ ";
		for (int i = 0; i < open; i++) {
			ret += (table[i] + " ");
		}
		return (ret + "]");
	}
}