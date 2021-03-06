package CI284;

import java.util.NoSuchElementException;

public class List{

    private Node Head;
    private int Size;

    public List() // empty list constructor
    {
        this.Head = new Node(null);
        ChangeSize(0);
    }

    public Node Add(Object data, int priority) {
        Node n = new Node(data, priority);
        Node current = this.Head;
        if (current != null) {
            while (current.getTail() != null && CompareNodePriority(n, current.getTail()) >= 0)
            {
                current = current.getTail();
            }
            n.setTail(current.getTail()); // set new node tail to current node's tail
            current.setTail(n); // set this node's tail to new node
            incrementSize(); // increment the size of the list
            return n;
        }
        this.Head = n;
        return n;
    }

    public boolean DeleteAll()
    {
        this.Head = null;
        Size = 0;
        return true;
    }

    public boolean DeleteIndex(int index)
    {
        // Index Out Of Bounds Exception check
        if(index < 1 || index > Size())
            return false;

        Node current = this.Head;
        if(this.Head != null)
        {
            for(int i = 0; i < index; i++)
            {
                if(current.getTail()==null)
                    return false;
                current = current.getTail();
            }
            current.setTail(current.getTail().getTail());
            decrementSize();
            return true;
        }
        return false;
    }

    public boolean DeleteNode(Node toDelete)
    {
        if(toDelete==null) // Node must exist
            return false;

        Node current = this.Head;
        if(this.Head != null)
        {
            while(current.getTail()!=toDelete)
            {
                if(current.getTail()==null)
                    return false;
                current = current.getTail();
            }
            current.setTail(current.getTail().getTail());
            decrementSize();
            return true;
        } else
            return false;
    }

    public boolean Delete(Object Data)
	{
		Node current = null;
		if(this.Head != null) {
			current = this.Head;
			while(current.getTail().getContent() != Data)
			{
				if(current.getTail() == null)
					return false;
				current = current.getTail();
			}
			current.setTail(current.getTail().getTail());
			decrementSize();
			return true;
		}
		return false;
	}

	//<summary>
    // Retrieves the Data at a given position in the List
    //</summary>
    public Node Find(int index)
    {
        if(index < 0)
        {
            return null; // cannot have an index lower than 0
        }

        Node current = null;
        if(this.Head != null)
        {
            current = this.Head.getTail();
            for(int i = 0; i < index; i++)
            {
                if(current.getTail() == null) return null;

                current = current.getTail();
            }
            return current;
        }
        return current;
    }
    
    public boolean Find(Object data)
    {
        if(data == null)
        {
            return false; // cannot have a null search
        }

        Node current = null;
        if(this.Head != null)
        {
            current = this.Head;
            while(current.getTail().getContent()!=data)
            {
                if(current.getTail() == null)
                    return false;
                current = current.getTail();
            }
            return current.getTail().getContent()==data;
        }
        return false;
    }

    //<summary>
    // Simple check to see if the List contains an element using a linear search
    //</summary>
    public boolean Contains(Node node)
    {
        if(node == null)
        {
            return false; // cannot have a null Node search
        }

        Node current = null;
        if(this.Head != null)
        {
            current = this.Head.getTail();
            while(current!=node)
            {
                if(current.getTail() == null)
                    return false;
                current = current.getTail();
            }
            return true;
        }
        return false;
    }

    //<summary>
    // Simple check to see if the List contains an element that has certain data using a linear search
    //</summary>
    public boolean Contains(Object data)
    {
        if(data == null)
        {
            return false; // cannot a null data to search for
        }

        Node current = null;
        if(this.Head != null)
        {
            current = this.Head.getTail();
            while(current.getContent()!=data)
            {
                if(current.getTail() == null)
                    return false;
                current = current.getTail();
            }
            return true;
        }
        return false;
    }

    public Node Peek() throws NullPointerException
    {
        if (this.Head.getTail() != null)
            return this.Head.getTail();
        throw new NullPointerException();
    }
    
    //<summary>
    /*
        Simple method for updating a node
        Firsts check if the input Node is not null and the replacing Data is also not null
            If either check fails, the method returns null as it cannot perform an update without
            either parameters
        I then check if the priority entered is in range, if not sets it automatically to 5
        Next we crawl through the list until we find Node toFind based on current.getTail()
        Next we check to make sure that the tail of current is in fact toFind
        Finally we DeleteNode(toFind) and Add(Data, Priority) to the List and return the newNode;
     */
    //</summary>
    public Node UpdateNode(Node toFind, Object Data, int Priority) throws NoSuchElementException
    {
        if(toFind == null || Data == null)
            throw new NoSuchElementException();
        if(Priority < 1 || Priority > 5)
            Priority = 5;
        
        Node current = null;
        if(this.Head != null)
        {
        	current = this.Head;
        	while(current.getTail() != toFind)
        	{
        		if(current.getTail()==null)
        			throw new NoSuchElementException();
        		current = current.getTail();
        	}
        	toFind = current.getTail();
        	this.DeleteNode(toFind);
        	return this.Add(Data,  Priority);
        		
        }
        throw new NoSuchElementException();
    }

    //<summary>
    // Simple check to see if the List is empty
    //</summary>
    public boolean isEmpty()
    {
        return this.Head==null;
    }

    //<summary>
    // Simple method to return the size of the List
    //</summary>
    public int Size()
    {
        return Size;
    }

    //<summary>
	// Simple override method for toString()
	// Allowing to output the array without having
	// The Object method toString() output  the className and HashCode
	//</summary>
	public String toString()
	{
	    String s = "";
	    StringBuilder sb = new StringBuilder();
	    sb.append(s);
	    if(this.Head!=null)
	    {
	        Node current = this.Head.getTail();
	        while(current != null)
	        {
	            sb.append("\n\t{").append(current.getContent().toString()).append("\t}").append(";"); // "Example" -> "Example2"
	            current = current.getTail();
	        }
	    }
	    return sb.toString();
	}

	//<summary>
    // Simple size incrementing method
    //</summary>
    private void incrementSize()
    {
        Size++;
    }

    //<summary>
    // Simple size decrementing method
    //</summary>
    private void decrementSize()
    {
        Size--;
    }
    
    private void ChangeSize(int newSize)
    {
    	Size = newSize;
    }

    //<summary>
    // Simple integer comparison method
    // Subtracts the currentNode's priority from the newNode's Priority
    //</summary>
    private int CompareNodePriority(Node n1, Node n2)
    {
        return n1.getPriority() - n2.getPriority();
    }
}