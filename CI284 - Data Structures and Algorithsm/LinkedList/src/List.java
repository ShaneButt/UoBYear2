public class List{

    private Node Head;
    private static int Size;

    public List() // empty list constructor
    {
        this.Head = new Node(null);
        this.Size = 0;
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
        }

        return n;
    }

    public boolean Delete(int index)
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

    public boolean Delete(Node toDelete)
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

    public Object Find(int index)
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
            return current.getContent();
        }
        return current;
    }

    public boolean Find(Node node)
    {
        if(node == null)
        {
            return false; // cannot have an index lower than 0
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

    public Node UpdateNode(Node toFind, Object Data, int Priority)
    {
        if(toFind == null || Data == null)
            return null;

        if(Priority == 0)
            Priority = 5;

        Node current = this.Head;
        while(current.getTail()!=toFind)
        {
            if(current.getTail()==null)
                return null;
            current = current.getTail();
        }

        if(current.getTail() == toFind)
        {
            toFind = current.getTail();
            boolean success = this.Delete(toFind);
            Node newNode = this.Add(Data, Priority);
            return newNode;
        }

        return null;
    }

    public int Size()
    {
        return this.Size;
    }

    public void incrementSize()
    {
        this.Size++;
    }

    public void decrementSize()
    {
        this.Size--;
    }

    public String toString()
    {
        String s = "";
        if(this.Head!=null)
        {
            Node current = this.Head.getTail();
            while(current != null)
            {
                s += "\n\t{" + current.getContent().toString() +"\t}"  + ";"; // "Example" -> "Example2"
                current = current.getTail();
            }
        }
        return s;
    }

    public int CompareNodePriority(Node n1, Node n2)
    {
        return n1.getPriority() - n2.getPriority();
    }
}