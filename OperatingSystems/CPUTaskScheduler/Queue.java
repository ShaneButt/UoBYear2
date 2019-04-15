package Assignment2;

import Assignment.Node;

public class Queue<T>
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
	
	public void add(T Item)
	{
		Node<T> n = new Node<T>(Item, Priority);
		Node<T> current = this.Front;
		
		if(current!=null)
		{
			while(current.getNext()!=null && ComparePriority(n, current.getNext())>=0)
			{
				current = current.getNext();
			}

			n.setNext(current.getNext());
			current.setNext(n);
			incrementSize();
			return;
		}
		this.Front = n;
		return;
	}
	
	public boolean remove(T Item)
	{
		Node<T> current = null;
		if(this.Front != null)
		{
			current = this.Front;
			while(current.getNext().getContent() != Item)
			{
				if(current.getNext() == null)
					return false;
				current = current.getNext();
			}
			Node<T> next = current.getNext().getNext();
			current.setNext(next);
			return true;
		}
		return false;
	}
	
	public boolean remove(int index)
	{
		if(index < 1 || index > size())
			return false;
		
		Node<T> current = null;
		if(this.Front != null)
		{
			current = this.Front;
			for(int i = 0; i < index; i++)
			{
				if(current.getNext()==null)
					return false;
				current = current.getNext();
			}
			Node<T> next = current.getNext().getNext();
			current.setNext(next);
			return true;
		}
		return false;
	}
	
	public T get(int index)
	{
		if(index < 1 || index > size())
			return null;
		
		Node<T> current = null;
		if(this.Front != null)
		{
			current = this.Front;
			for(int i = 0; i < index; i++)
			{
				if(current.getNext()==null)
					return null;
				current = current.getNext();
			}
			T dataFound = current.getNext().getContent();
			return dataFound;
		}
		return null;
	}
	
	private void incrementSize()
	{
		Size++;
	}
	
	public int size()
	{
		return Size+1;
	}
	
	private int ComparePriority(Node<T> n1, Node<T> n2)
	{
		return n1.getPriority() - n2.getPriority();
	}
	
	public void setAlgorithm(AScheduler algorithm)
	{
		this.algorithm = algorithm;
	}
}
