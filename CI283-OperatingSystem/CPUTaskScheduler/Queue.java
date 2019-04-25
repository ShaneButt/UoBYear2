package Assignment2;

import java.util.Iterator;

public class Queue<T> implements Iterable<T>
{
	
	protected int Priority; // priority of the queue
	private int Size; // size of the queue
	Node<T> Front; // first element of the queue
	private AScheduler algorithm; // algorithm that handles the queue
	
	public Queue(int Priority)
	{
		this.Front = null; // no first item added yet set to null
		this.Priority = Priority; // set priority to priority
	}
	
	public Queue()
	{
		this.Front = null; // front is null
		this.Priority = 1; // default priority is 1
	}
	
	public void add(T Item)
	{
		Node<T> n = new Node<T>(Item, Priority); // creates a new node for the new item
		Node<T> current = this.Front; // gets the current head of the queue
		
		if (current != null) // if there is element(s)
		{
			while (current.getNext() != null && ComparePriority(n, current.getNext()) >= 0) // run through till we find correct placement
			{
				current = current.getNext(); // setting current to the next element
			}
			
			n.setNext(current.getNext()); // sets new node's next tail to the curren't next tail
			current.setNext(n); // sets current's next tail to new node thus no data lost
			incrementSize(); // increments the size
			return; // returns out
		}
		
		this.Front = n; // otherwise no elements, set front to new node
		incrementSize(); // increment size
		return; // return out
	}
	
	public void add(T Item, int Index) // adds an item to a specific index of the queue
	{
		Node<T> n = new Node<T>(Item, Priority); // creates a new node
		Node<T> current = this.Front; // holds the front of the queue
		
		if (current != null && (Index < size() || Index > 0)) // check for non-null front & index in bounds
		{
			for (int i = 0; i < Index; i++) // runs through to the index
			{
				if (current.getNext() == null) // another index out of bounds exception check
					return; // return if hit
				current = current.getNext(); // sets current to next item
			}
			n.setNext(current.getNext()); // sets new node next to current next
			current.setNext(n); // current next set to new node
			incrementSize(); // increment the size
			return;// return out
		}
	}
	
	public boolean remove(T Item) // removes a specific item
	{
		if(this.Front==null) // checks if non empty
			return true; // if empty it has been removed
		
		if (this.Front.getContent() == Item) // if first element is the item
		{
			this.Front = this.Front.getNext(); // remove the first element
			decrementSize(); // decrement size
			return true; // return true
		}
		
		Node<T> current; // current is empty node
		if (this.Front != null) // if front is non null
		{
			current = this.Front; // current is front
			while (current.getNext() != null) // while next is non null
			{
				if (current.getNext().getContent() == Item) // if next is the item
				{
					current.setNext(current.getNext().getNext()); // override/delete it by setting current next to next's next
					decrementSize(); // decrement size
					return true; // return true
				}
				current = current.getNext(); // current is next
			}
			return false; // didnt remove it, return false
		}
		return false; // front was null return false
	}
	
	public boolean remove(int index) // remove by index
	{
		if (index < 1 || index > size()) // index out of bounds check
			return false; // return false as invalid index
		
		Node<T> current = null; // current var
		if (this.Front != null) // if front is non empty
		{
			current = this.Front; // current is front
			for (int i = 0; i < index; i++) // iterate through until we reach the index
			{
				if (current.getNext() == null) // check if we are out of bounds
					return false; // return false
				current = current.getNext(); // current is set to next
			}
			Node<T> next = current.getNext().getNext(); // next's next is stored
			current.setNext(next); // set current next to next's next removing next
			decrementSize(); // decrement size
			return true; // return true
		}
		return false; // return false if front is null
	}
	
	public T get(int index) // get by index method
	{
		if (index == 0)
			if (this.Front != null)
				return this.Front.getContent();
			else
				return null;
		if (index < 1 || index > size())
			return null;
		
		Node<T> current = null;
		if (this.Front != null)
		{
			current = this.Front;
			for (int i = 0; i < index; i++)
			{
				if (current.getNext() == null)
					return null;
				current = current.getNext();
			}
			T dataFound = current.getContent();
			return dataFound;
		}
		
		return null;
	}
	
	public T get(T Item) // get by item method
	{
		Node<T> current = null;
		if (this.Front != null)
		{
			current = this.Front;
			while (current.getNext() != null)
			{
				if (current.getNext().getContent() == Item)
					return current.getNext().getContent();
				current = current.getNext();
			}
			return null;
		}
		return null;
	}
	
	private void incrementSize() // increments the size
	{
		Size++;
	}
	
	private void decrementSize() // decrements the size
	{
		Size--;
	}
	
	public int size() // gets the size
	{
		return Size;
	}
	
	private int ComparePriority(Node<T> n1, Node<T> n2) // compares priority of nodes
	{
		return n1.getPriority() - n2.getPriority();
	}
	
	public void setAlgorithm(AScheduler algorithm) // sets the algorithm
	{
		this.algorithm = algorithm;
	}
	
	public AScheduler getAlgorithm() // gets the algorithm
	{
		return this.algorithm;
	}
	
	public boolean isEmpty() // checks if empty
	{
		return size() == 0;
	}
	
	@Override
	public Iterator<T> iterator() // allows a for each to be called over this list
	{
		Iterator<T> it = new Iterator<T>()
		{
			
			private int ind = 0; // holds indexer
			
			@Override
			public boolean hasNext()
			{
				return ind < size() && get(ind) != null; // checks if there is a next
			}
			
			@Override
			public T next()
			{
				return get(ind++); // gets the next
			}
			
			public void remove()
			{
				throw new UnsupportedOperationException(); // doesnt allow removal of elements
			}
		};
		return it;
	}
	
	@Override
	public String toString() // overrides toString() to print out formatted queue
	{
		String s = "";
		StringBuilder sb = new StringBuilder(s);
		if (size() == 0)
			return "empty"; // no point printing out "" so clarify what happened
		for (int i = 0; i < size(); i++)
		{
			sb.append(String.format("%s\n", get(i))); // append nodes to the string
		}
		return sb.toString(); // return the sb.toString()
	}
}
