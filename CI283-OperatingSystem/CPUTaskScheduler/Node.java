package Assignment2;

public class Node<T>
{
	
	private T Content; // the Object that the node holds
	private int Priority; // the priority of the node
	private Node<T> Back; // the previous node
	private Node<T> Front; // the next node
	
	// constructs new node with a given object item and priority
	public Node(T Item, int Priority)
	{
		Content = Item;
		this.Priority = Priority;
	}
	
	public void setNext(Node<T> node) // sets next node
	{
		Front = node;
	}
	
	public Node<T> getNext() // gets next node
	{
		return Front;
		
	}
	
	public T getContent() // gets content of node
	{
		return Content;
	}
	
	public Node<T> getPrevious() // gets previous node
	{
		return Back;
	}
	
	public int getPriority() // gets priority of node
	{
		return Priority;
	}
}
