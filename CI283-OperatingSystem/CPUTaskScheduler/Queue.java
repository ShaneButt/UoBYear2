package Assignment2;

import java.util.Iterator;

public class Queue<T> implements Iterable<T>
{
	
	protected int Priority;
	private int Size;
	Node<T> Front;
	private AScheduler algorithm;
	
	public Queue(int Priority)
	{
		this.Front = null;
		this.Priority = Priority;
	}
	
	public Queue()
	{
		this.Front = null;
		this.Priority = 1;
	}
	
	public void add(T Item)
	{
		Node<T> n = new Node<T>(Item, Priority);
		Node<T> current = this.Front;
		
		if (current != null)
		{
			while (current.getNext() != null && ComparePriority(n, current.getNext()) >= 0)
			{
				current = current.getNext();
			}
			
			n.setNext(current.getNext());
			current.setNext(n);
			incrementSize();
			return;
		}
		this.Front = n;
		incrementSize();
		return;
	}
	
	public void add(T Item, int Index)
	{
		Node<T> n = new Node<T>(Item, Priority);
		Node<T> current = this.Front;
		
		if (current != null && (Index < size() || Index > 0))
		{
			for (int i = 0; i < Index; i++)
			{
				if (current.getNext() == null)
					return;
				current = current.getNext();
			}
			n.setNext(current.getNext());
			current.setNext(n);
			return;
		}
	}
	
	public boolean remove(T Item)
	{
		if(this.Front==null)
			return true;
		
		if (this.Front.getContent() == Item)
		{
			this.Front = this.Front.getNext();
			decrementSize();
			return true;
		}
		
		Node<T> current;
		if (this.Front != null)
		{
			current = this.Front;
			while (current.getNext() != null)
			{
				if (current.getNext().getContent() == Item)
				{
					current.setNext(current.getNext().getNext());
					decrementSize();
					return true;
				}
				current = current.getNext();
			}
			return false;
		}
		return false;
	}
	
	public boolean remove(int index)
	{
		if (index < 1 || index > size())
			return false;
		
		Node<T> current = null;
		if (this.Front != null)
		{
			current = this.Front;
			for (int i = 0; i < index; i++)
			{
				if (current.getNext() == null)
					return false;
				current = current.getNext();
			}
			Node<T> next = current.getNext().getNext();
			current.setNext(next);
			decrementSize();
			return true;
		}
		return false;
	}
	
	public T get(int index)
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
	
	public T get(T Item)
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
	
	private void incrementSize()
	{
		Size++;
	}
	
	private void decrementSize()
	{
		Size--;
	}
	
	public int size()
	{
		return Size;
	}
	
	private int ComparePriority(Node<T> n1, Node<T> n2)
	{
		return n1.getPriority() - n2.getPriority();
	}
	
	public void setAlgorithm(AScheduler algorithm)
	{
		this.algorithm = algorithm;
	}
	
	public AScheduler getAlgorithm()
	{
		return this.algorithm;
	}
	
	public boolean isEmpty()
	{
		return size() == 0;
	}
	
	@Override
	public Iterator<T> iterator()
	{
		Iterator<T> it = new Iterator<T>()
		{
			
			private int ind = 0;
			
			@Override
			public boolean hasNext()
			{
				return ind < size() && get(ind) != null;
			}
			
			@Override
			public T next()
			{
				return get(ind++);
			}
			
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
		return it;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		StringBuilder sb = new StringBuilder(s);
		if (size() == 0)
			return "empty";
		for (int i = 0; i < size(); i++)
		{
			sb.append(String.format("%s\n", get(i)));
		}
		return sb.toString();
	}
}
