package Assignment2;

public class Node<T>
{
	
	private T Content;
	private int Priority;
	private Node<T> Back;
	private Node<T> Front;
	
	
	public Node(T Item, int Priority)
	{
		Content = Item;
		this.Priority = Priority;
	}
	
	public void setNext(Node<T> node)
	{
		Front = node;
	}
	
	public Node<T> getNext()
	{
		return Front;
		
	}
	
	public T getContent()
	{
		return Content;
	}
	
	public Node<T> getPrevious()
	{
		return Back;
	}
	
	public int getPriority()
	{
		return Priority;
	}
}
